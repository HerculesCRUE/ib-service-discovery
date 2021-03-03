package es.um.asio.service.service;

import es.um.asio.audit.abstractions.service.DeleteService;
import es.um.asio.audit.abstractions.service.QueryService;
import es.um.asio.audit.abstractions.service.SaveService;
import es.um.asio.service.filter.TypeFilter;
import es.um.asio.service.model.service.discovery.TypeEnt;

import java.util.List;

public interface TypeService
        extends QueryService<TypeEnt, String, TypeFilter>, SaveService<TypeEnt>, DeleteService<TypeEnt, String> {

    List<TypeEnt> getAllNodes();

    TypeEnt getNodeByName(String name);

}
