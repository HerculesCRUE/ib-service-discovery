package es.um.asio.service.service.impl;

import es.um.asio.audit.abstractions.exception.NoSuchEntityException;
import es.um.asio.service.filter.ServiceFilter;
import es.um.asio.service.model.service.discovery.ServiceEnt;
import es.um.asio.service.repository.ServiceRepository;
import es.um.asio.service.service.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ServiceServiceImp implements ServiceService {

    @Autowired
    ServiceRepository repository;

    // @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public List<ServiceEnt> getAllNodes() {
        return repository.findAll();
    }

    // @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public List<ServiceEnt> getServiceByName(String name) {
        return repository.findByName(name).orElse(new ArrayList<>());
    }


    // @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public void delete(ServiceEnt entity) {
        repository.delete(entity);
    }

    // @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public void delete(String identifier) {
        repository.deleteById(identifier);
    }

    // @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public Optional<ServiceEnt> find(String identifier) {
        return repository.findById(identifier);
    }

    // @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public Page<ServiceEnt> findPaginated(ServiceFilter filter, Pageable pageable) {
        return repository.findAll(filter, pageable);
    }

    // @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public List<ServiceEnt> findAll() {
        return repository.findAll();
    }

    // @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public ServiceEnt save(ServiceEnt entity) {
        return repository.save(entity);
    }

    // @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public List<ServiceEnt> save(Iterable<ServiceEnt> entities) {
        return repository.saveAll(entities);
    }

    // @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public ServiceEnt update(ServiceEnt entity) throws NoSuchEntityException {
        return repository.save(entity);
    }
}
