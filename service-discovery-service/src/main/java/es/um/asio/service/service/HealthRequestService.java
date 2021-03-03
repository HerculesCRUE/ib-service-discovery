package es.um.asio.service.service;

import es.um.asio.audit.abstractions.service.DeleteService;
import es.um.asio.audit.abstractions.service.QueryService;
import es.um.asio.audit.abstractions.service.SaveService;
import es.um.asio.service.filter.HealthRequestFilter;
import es.um.asio.service.model.service.discovery.HealthRequest;
import java.util.Date;
import java.util.List;

public interface HealthRequestService
        extends QueryService<HealthRequest, Long, HealthRequestFilter>, SaveService<HealthRequest>, DeleteService<HealthRequest, Long> {

    List<HealthRequest> getAllNodes();

    void deleteOldHealthRequest(Date date);

}
