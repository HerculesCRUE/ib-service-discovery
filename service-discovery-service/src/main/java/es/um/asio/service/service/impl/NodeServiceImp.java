package es.um.asio.service.service.impl;

import es.um.asio.audit.abstractions.exception.NoSuchEntityException;
import es.um.asio.service.filter.NodeFilter;
import es.um.asio.service.model.service.discovery.NodeEnt;
import es.um.asio.service.repository.NodeRepository;
import es.um.asio.service.service.NodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NodeServiceImp implements NodeService {

    @Autowired
    NodeRepository repository;

    // @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public List<NodeEnt> getAllNodes() {
        return repository.findAll();
    }

    // @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public NodeEnt getNodeByName(String name) {
        return repository.findByName(name).orElse(null);
    }

    @Override
    public List<NodeEnt> getAllNodes(String filterByDeactivateFor) {
        return repository.findAll().stream().filter(n -> !n.getDeactivatedFor().contains(filterByDeactivateFor)).collect(Collectors.toList());
    }

    @Override
    public NodeEnt addDeactivate(String node, String deactivateFor) {
        NodeEnt nodeEnt = repository.findByName(node).orElse(null);
        if (nodeEnt!=null) {
            nodeEnt.addDeactivate(deactivateFor);
            save(nodeEnt);
        }
        return nodeEnt;
    }

    @Override
    public NodeEnt removeDeactivate(String node, String deactivateFor) {
        NodeEnt nodeEnt = repository.findByName(node).orElse(null);
        if (nodeEnt!=null) {
            nodeEnt.removeDeactivate(deactivateFor);
            save(nodeEnt);
        }
        return nodeEnt;
    }

    // @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public void delete(NodeEnt entity) {
        repository.delete(entity);
    }

    // @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public void delete(String identifier) {
        repository.deleteById(identifier);
    }

    // @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public Optional<NodeEnt> find(String identifier) {
        return repository.findById(identifier);
    }

    // @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public Page<NodeEnt> findPaginated(final NodeFilter filter, Pageable pageable) {
        return repository.findAll(filter, pageable);
    }

    // @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public List<NodeEnt> findAll() {
        return repository.findAll();
    }

    // @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public NodeEnt save(NodeEnt entity) {
        return repository.save(entity);
    }

    // @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public List<NodeEnt> save(Iterable<NodeEnt> entities) {
        return repository.saveAll(entities);
    }

    // @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public NodeEnt update(NodeEnt entity) throws NoSuchEntityException {
        return repository.save(entity);
    }
}
