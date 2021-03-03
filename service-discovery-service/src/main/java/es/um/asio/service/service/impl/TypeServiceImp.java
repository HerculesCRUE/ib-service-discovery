package es.um.asio.service.service.impl;

import es.um.asio.audit.abstractions.exception.NoSuchEntityException;
import es.um.asio.service.filter.TypeFilter;
import es.um.asio.service.model.service.discovery.TypeEnt;
import es.um.asio.service.repository.TypeRepository;
import es.um.asio.service.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TypeServiceImp implements TypeService {

    @Autowired
    TypeRepository repository;

    // @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public List<TypeEnt> getAllNodes() {
        return repository.findAll();
    }

    // @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public TypeEnt getNodeByName(String name) {
        return repository.findByName(name).orElse(null);
    }

    // @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public void delete(TypeEnt entity) {
        repository.delete(entity);
    }

    // @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public void delete(String identifier) {
        repository.deleteById(identifier);
    }

    // @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public Optional<TypeEnt> find(String identifier) {
        return repository.findById(identifier);
    }

    // @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public Page<TypeEnt> findPaginated(TypeFilter filter, Pageable pageable) {
        return repository.findAll(filter, pageable);
    }

    // @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public List<TypeEnt> findAll() {
        return repository.findAll();
    }

    // @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public TypeEnt save(TypeEnt entity) {
        return repository.save(entity);
    }

    // @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public List<TypeEnt> save(Iterable<TypeEnt> entities) {
        return repository.saveAll(entities);
    }

    // @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public TypeEnt update(TypeEnt entity) throws NoSuchEntityException {
        return repository.save(entity);
    }
}
