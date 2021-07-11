package es.um.asio.service.service;


import es.um.asio.audit.abstractions.service.DeleteService;
import es.um.asio.audit.abstractions.service.QueryService;
import es.um.asio.audit.abstractions.service.SaveService;
import es.um.asio.service.filter.NodeFilter;
import es.um.asio.service.model.service.discovery.NodeEnt;

import java.util.List;

public interface NodeService
        extends QueryService<NodeEnt, String, NodeFilter>, SaveService<NodeEnt>, DeleteService<NodeEnt, String> {

    List<NodeEnt> getAllNodes();

    List<NodeEnt> getAllNodes(String filterByDeactivateFor);

    NodeEnt getNodeByName(String name);

    NodeEnt addDeactivate(String node, String deactivateFor);

    NodeEnt removeDeactivate(String node, String deactivateFor);

}
