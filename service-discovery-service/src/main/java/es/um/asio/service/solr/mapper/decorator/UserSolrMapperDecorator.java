package es.um.asio.service.solr.mapper.decorator;

import es.um.asio.audit.abstractions.search.PageImplHelper;
import es.um.asio.service.model.User;
import es.um.asio.service.solr.mapper.UserSolrMapper;
import es.um.asio.service.solr.model.UserSolr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * MapStruct Mapper decorator for {@link UserSolr}.
 */
public abstract class UserSolrMapperDecorator implements UserSolrMapper {

    /**
     * Delegate {@link User} mapper.
     */
    @Autowired
    @Qualifier("delegate")
    private UserSolrMapper delegate;

    /**
     * {@inheritDoc}
     */
    @Override
    public PageImplHelper<User> convertFromSolr(final Page<UserSolr> page) {
        if (page == null) {
            return null;
        }

        return new PageImplHelper<>(this.delegate.convertFromSolr(page.getContent()),
                PageRequest.of(page.getNumber(), page.getSize(), page.getSort()), page.getTotalElements());
    }
}
