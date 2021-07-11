package es.um.asio.service.model.service.discovery;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(NodeEnt.class)
public abstract class NodeEnt_ {

	public static volatile SingularAttribute<NodeEnt, String> name;
	public static volatile SingularAttribute<NodeEnt, Long> id;
	public static volatile SetAttribute<NodeEnt, ServiceEnt> services;
	public static volatile SetAttribute<NodeEnt, String> deactivatedFor;

	public static final String NAME = "name";
	public static final String ID = "id";
	public static final String SERVICES = "services";
	public static final String DEACTIVATED_FOR = "deactivatedFor";

}

