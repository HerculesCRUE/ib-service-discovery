package es.um.asio.service.service;

import es.um.asio.audit.abstractions.service.DeleteService;
import es.um.asio.audit.abstractions.service.QueryService;
import es.um.asio.audit.abstractions.service.SaveService;
import es.um.asio.service.filter.ServiceFilter;
import es.um.asio.service.model.service.discovery.ServiceEnt;

import java.util.List;

public interface ServiceService
        extends QueryService<ServiceEnt, String, ServiceFilter>, SaveService<ServiceEnt>, DeleteService<ServiceEnt, String> {

    List<ServiceEnt> getAllNodes();

    List<ServiceEnt> getServiceByName(String name);

}
