package es.um.asio.back.controller;

import es.um.asio.service.model.service.discovery.NodeEnt;
import es.um.asio.service.model.service.discovery.TypeEnt;
import es.um.asio.service.service.NodeService;
import es.um.asio.service.service.ServiceService;
import es.um.asio.service.service.impl.ServiceDiscoveryServiceImpl;
import es.um.asio.service.validation.group.Create;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(ServiceDiscoveryController.Mappings.BASE)
public class ServiceDiscoveryController {

    @Autowired
    ServiceDiscoveryServiceImpl service;

    @Autowired
    NodeService nodeService;

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

    @GetMapping(Mappings.EXCLUDE_DEACTIVATE)
    @ApiOperation(value = "Get All")
    public List<NodeEnt> getAllExcludeDeactivate(
            @ApiParam(name = "nodeName", value = "Node name", required = true)
            @RequestParam(required = true) @Validated(Create.class) final String nodeName
    ) {
        return nodeService.getAllNodes(nodeName);
    }

    @GetMapping(Mappings.EXCLUDE_DEACTIVATE_NODES_NAME)
    @ApiOperation(value = "Get All")
    public List<String> getAllExcludeDeactivateNodesName(
            @ApiParam(name = "nodeName", value = "Node name", required = true)
            @RequestParam(required = true) @Validated(Create.class) final String nodeName
    ) {
        return nodeService.getAllNodes(nodeName).stream().map(n->n.getName()).collect(Collectors.toList());
    }

    @GetMapping(Mappings.NODE)
    @ApiOperation(value = "Get Node by name")
    public NodeEnt getNodeByName(
            @ApiParam(name = "nodeName", value = "Node name", required = true)
            @RequestParam(required = true) @Validated(Create.class) final String nodeName
    ) {
        return service.getNode(nodeName);
    }

    @PostMapping(Mappings.NODE_ADD_DEACTIVATE)
    @ApiOperation(value = "Add deactivate by Node Name")
    public NodeEnt addDeactivateByNode (
            @ApiParam(name = "nodeName", value = "Node name", required = true)
            @RequestParam(required = true) @Validated(Create.class) final String nodeName,
            @ApiParam(name = "deactivateFor", value = "Deactivate for", required = true)
            @RequestParam(required = true) @Validated(Create.class) final String deactivateFor
    ) throws Exception {
        if (nodeName.equals(deactivateFor))
            throw new Exception("Node name ("+ nodeName+") and deactivateFor("+deactivateFor+") can´t be equals");
        return nodeService.addDeactivate(nodeName,deactivateFor);
    }

    @PostMapping(Mappings.NODE_REMOVE_DEACTIVATE)
    @ApiOperation(value = "Remove deactivate by Node Name")
    public NodeEnt removeDeactivateByNode (
            @ApiParam(name = "nodeName", value = "Node name", required = true)
            @RequestParam(required = true) @Validated(Create.class) final String nodeName,
            @ApiParam(name = "activateFor", value = "Deactivate for", required = true)
            @RequestParam(required = true) @Validated(Create.class) final String activateFor
    ) throws Exception {
        if (nodeName.equals(activateFor))
            throw new Exception("Node name ("+ nodeName+") and deactivateFor("+activateFor+") can´t be equals");
        return nodeService.removeDeactivate(nodeName,activateFor);
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

        protected static final String EXCLUDE_DEACTIVATE = "/exclude";

        protected static final String EXCLUDE_DEACTIVATE_NODES_NAME = "/exclude/nodes/name";

        protected static final String NODE_ADD_DEACTIVATE = "/node/add-deactivate";

        protected static final String NODE_REMOVE_DEACTIVATE = "/node/remove-deactivate";

        protected static final String TYPE = "/type";
    }
}
