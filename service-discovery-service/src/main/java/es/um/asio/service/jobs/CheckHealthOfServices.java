package es.um.asio.service.jobs;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import es.um.asio.service.model.service.discovery.HealthRequest;
import es.um.asio.service.model.service.discovery.NodeEnt;
import es.um.asio.service.model.service.discovery.ServiceEnt;
import es.um.asio.service.model.service.discovery.Status;
import es.um.asio.service.service.HealthRequestService;
import es.um.asio.service.service.NodeService;
import es.um.asio.service.service.ServiceService;
import org.apache.commons.lang3.tuple.Pair;
import org.jsoup.Connection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

import java.text.SimpleDateFormat;

@Component
public class CheckHealthOfServices {

    @Autowired
    NodeService nodeService;

    @Autowired
    ServiceService serviceService;

    @Autowired
    HealthRequestService healthRequestService;


    private static final Logger log = LoggerFactory.getLogger(CheckHealthOfServices.class);
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.sss");

    @Scheduled(fixedRateString = "${app.check-services-status-period}", initialDelay=1000)
    public void checkHealthOfServices() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE,-7);
        healthRequestService.deleteOldHealthRequest(c.getTime());
        List<NodeEnt> nodes = nodeService.getAllNodes();
        for (NodeEnt node: nodes) {
            for (ServiceEnt service: node.getServices()) {
                try {
                    URL url = service.buildHealthURL();
                    if (url!=null && !url.equals("")) {
                        try {
                            AbstractMap.SimpleEntry<Integer, JsonElement> response = doRequest(url, Connection.Method.GET, null, null, null);
                            if (response.getKey() >= 200 && response.getKey() <= 399) {
                                service.setStatus(Status.UP);
                                service.getHealthRequests().add(new HealthRequest(service, Status.UP));
                            } else {
                                service.getHealthRequests().add(new HealthRequest(service, Status.DOWN));
                            }
                        } catch (Exception e) {
                            service.setStatus(Status.DOWN);
                            service.getHealthRequests().add(new HealthRequest(service, Status.DOWN));
                        }
                    } else {
                        service.setStatus(Status.UNKNOWN);
                    }
                    serviceService.save(service);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    service.setStatus(Status.UNKNOWN);
                    service.getHealthRequests().add(new HealthRequest(service, Status.UNKNOWN));
                }
                log.info("Node: {},\tService: {},\tStatus: {}",node.getName(),service.getName(),service.getStatus());
            }
        }
    }

    public void checkHealthOfServices(NodeEnt node) {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE,-7);
        healthRequestService.deleteOldHealthRequest(c.getTime());
        for (ServiceEnt service: node.getServices()) {
            try {
                URL url = service.buildHealthURL();
                if (url!=null && !url.equals("")) {
                    try {
                        AbstractMap.SimpleEntry<Integer, JsonElement> response = doRequest(url, Connection.Method.GET, null, null, null);
                        if (response.getKey() >= 200 && response.getKey() <= 399) {
                            service.setStatus(Status.UP);
                            service.getHealthRequests().add(new HealthRequest(service, Status.UP));
                        } else {
                            service.getHealthRequests().add(new HealthRequest(service, Status.DOWN));
                        }
                    } catch (Exception e) {
                        service.setStatus(Status.DOWN);
                        service.getHealthRequests().add(new HealthRequest(service, Status.DOWN));
                    }
                } else {
                    service.setStatus(Status.UNKNOWN);
                }
                serviceService.save(service);
            } catch (MalformedURLException e) {
                e.printStackTrace();
                service.setStatus(Status.UNKNOWN);
                service.getHealthRequests().add(new HealthRequest(service, Status.UNKNOWN));
            }
            log.info("Node: {},	Service: {},	Status: {}",node.getName(),service.getName(),service.getStatus());
        }
    }

    private AbstractMap.SimpleEntry<Integer,JsonElement> doRequest(URL url, Connection.Method method, Map<String,String> headers, Map<String,String> params, Map<String,String> queryParams) throws IOException {
        if (queryParams!=null) {
            url = buildQueryParams(url,queryParams);
        }
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod(method.toString());
        if (headers!=null) {
            for (Map.Entry<String, String> headerEntry : headers.entrySet()) {
                con.setRequestProperty(headerEntry.getKey(),headerEntry.getValue());
            }
        }
        if (params!=null) {
            for (Map.Entry<String, String> paramEntry : params.entrySet()) {
                con.setRequestProperty(paramEntry.getKey(),paramEntry.getValue());
            }
        }
        con.setDoOutput(true);
        con.connect();
        int code = con.getResponseCode();
        StringBuilder response;
        try(BufferedReader br = new BufferedReader(
                new InputStreamReader(con.getInputStream(), "utf-8"))) {
            response = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
        }
        con.disconnect();
        JsonElement jResponse;
        try {
            jResponse = new Gson().fromJson(response.toString(), JsonElement.class);
        } catch (Exception e) {
            jResponse = new JsonObject();
        }
        return new AbstractMap.SimpleEntry<Integer,JsonElement>(code, jResponse) {
        };

    }

    private URL buildQueryParams(URL baseURL, Map<String,String> queryParams) throws MalformedURLException, UnsupportedEncodingException {
        StringBuffer base = new StringBuffer();
        base.append(baseURL.toString());
        if (queryParams!=null && queryParams.size()>0) {
            base.append("?");
            List<String> qpList = new ArrayList<>();
            for (Map.Entry<String, String> qpEntry : queryParams.entrySet()) {
                qpList.add(qpEntry.getKey()+"="+ URLEncoder.encode(qpEntry.getValue(), StandardCharsets.UTF_8.toString()));
            }
            base.append(String.join("&",qpList));
        }
        return new URL(base.toString());
    }
}
