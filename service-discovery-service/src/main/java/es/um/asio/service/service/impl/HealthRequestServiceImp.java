package es.um.asio.service.service.impl;

import es.um.asio.audit.abstractions.exception.NoSuchEntityException;
import es.um.asio.service.filter.HealthRequestFilter;
import es.um.asio.service.filter.SearchCriteria;
import es.um.asio.service.filter.SearchOperation;
import es.um.asio.service.model.service.discovery.HealthRequest;
import es.um.asio.service.repository.HealthRequestRepository;
import es.um.asio.service.service.HealthRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Date;
import java.util.Optional;

@Service
public class HealthRequestServiceImp implements HealthRequestService {

    @Autowired
    HealthRequestRepository repository;

    // @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public List<HealthRequest> getAllNodes() {
        return repository.findAll();
    }

    // @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public void delete(HealthRequest entity) {
        repository.delete(entity);
    }

    // @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public void delete(Long identifier) {
        repository.deleteById(identifier);
    }

    // @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public Optional<HealthRequest> find(Long identifier) {
        return repository.findById(identifier);
    }

    // @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public Page<HealthRequest> findPaginated(HealthRequestFilter filter, Pageable pageable) {
        return repository.findAll(filter, pageable);
    }

    // @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public List<HealthRequest> findAll() {
        return repository.findAll();
    }

    // @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public HealthRequest save(HealthRequest entity) {
        return repository.save(entity);
    }

    // @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public List<HealthRequest> save(Iterable<HealthRequest> entities) {
        return repository.saveAll(entities);
    }

    // @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public HealthRequest update(HealthRequest entity) throws NoSuchEntityException {
        return repository.save(entity);
    }

    // @Override
    public void deleteOldHealthRequest(Date date) {
        HealthRequestFilter f = new HealthRequestFilter();
        f.add(new SearchCriteria("requestDate", new Date().getTime(), SearchOperation.LESS_THAN_EQUAL));
        List<HealthRequest> healthRequestsOld = repository.findAll(f);
        repository.deleteAll(healthRequestsOld);
    }
}
