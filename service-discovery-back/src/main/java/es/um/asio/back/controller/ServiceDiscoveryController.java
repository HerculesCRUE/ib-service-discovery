package es.um.asio.back.controller;

import es.um.asio.service.model.service.discovery.NodeEnt;
import es.um.asio.service.model.service.discovery.TypeEnt;
import es.um.asio.service.service.impl.ServiceDiscoveryServiceImpl;
import es.um.asio.service.validation.group.Create;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ServiceDiscoveryController.Mappings.BASE)
public class ServiceDiscoveryController {

    @Autowired
    ServiceDiscoveryServiceImpl service;

    @PostMapping(Mappings.SERVICE)
    @ApiOperation(value = "Add new Service in Node")
    public NodeEnt addService(
            @ApiParam(name = "nodeName", value = "Node name", required = true)
            @RequestParam(required = true) @Validated(Create.class) final String nodeName,
            @ApiParam(name = "serviceName", value = "Service name", required = true)
            @RequestParam(required = true) @Validated(Create.class) final String serviceName,
            @ApiParam(name = "host", value = "Service host", required = true)
            @RequestParam(required = true) @Validated(Create.class) final String host,
            @ApiParam(name = "port", value = "Service port", required = false)
            @RequestParam(required = false) @Validated(Create.class) final String port,
            @ApiParam(name = "healthEndpoint", value = "Health end point", required = false)
            @RequestParam(required = false) @Validated(Create.class) final String healthEndpoint
    ) {
        return service.addService(nodeName,serviceName,host,(port!=null)?Integer.valueOf(port):null,healthEndpoint);
    }

    @PostMapping(Mappings.TYPE)
    @ApiOperation(value = "Add new Type in Service and Node")
    public NodeEnt addType(
            @ApiParam(name = "nodeName", value = "Node name", required = true)
            @RequestParam(required = true) @Validated(Create.class) final String nodeName,
            @ApiParam(name = "serviceName", value = "Service name", required = true)
            @RequestParam(required = true) @Validated(Create.class) final String serviceName,
            @ApiParam(name = "typeName", value = "Type name", required = true)
            @RequestParam(required = true) @Validated(Create.class) final String typeName,
            @ApiParam(name = "suffixURL", value = "Suffix URL", required = true)
            @RequestParam(required = true) @Validated(Create.class) final String suffixURL
    ) {
        return service.addType(nodeName,serviceName,typeName,suffixURL);
    }

    @GetMapping()
    @ApiOperation(value = "Get All")
    public List<NodeEnt> getAll(
    ) {
        return service.getAllNodes();
    }

    @GetMapping(Mappings.NODE)
    @ApiOperation(value = "Get Node by name")
    public NodeEnt getNodeByName(
            @ApiParam(name = "nodeName", value = "Node name", required = true)
            @RequestParam(required = true) @Validated(Create.class) final String nodeName
    ) {
        return service.getNode(nodeName);
    }

    @GetMapping(Mappings.SERVICE)
    @ApiOperation(value = "Get Service by name")
    public List<NodeEnt> getServiceByName(
            @ApiParam(name = "serviceName", value = "Service name", required = true)
            @RequestParam(required = true) @Validated(Create.class) final String serviceName
    ) {
        return service.getServices(serviceName);
    }

    @GetMapping(Mappings.SERVICE_TYPE)
    @ApiOperation(value = "Get Service by name and Type")
    public List<NodeEnt> getServiceByNameAndType(
            @ApiParam(name = "serviceName", value = "Service name", required = true)
            @RequestParam(required = true) @Validated(Create.class) final String serviceName,
            @ApiParam(name = "typeName", value = "Type name", required = true)
            @RequestParam(required = true) @Validated(Create.class) final String typeName
    ) {
        return service.getServices(serviceName,typeName);
    }



    /**
     * Mappgins.
     */
    static final class Mappings {

        private Mappings(){}

        /**
         * Controller request mapping.
         */
        protected static final String BASE = "/service-discovery";

        protected static final String SERVICE = "/service";

        protected static final String SERVICE_TYPE = "/service/type";

        protected static final String NODE = "/node";

        protected static final String TYPE = "/type";
    }
}
