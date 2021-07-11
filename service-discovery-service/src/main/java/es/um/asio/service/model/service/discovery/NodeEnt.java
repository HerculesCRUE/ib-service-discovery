package es.um.asio.service.model.service.discovery;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name= NodeEnt.TABLE)
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@AllArgsConstructor
public class NodeEnt {

    public static final String TABLE = "node";

    public NodeEnt() {
        this.services = new HashSet<>();
    }

    public NodeEnt(String name) {
        this.name = name;
        this.services = new HashSet<>();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = Columns.ID)
    @ApiModelProperty(hidden = true)
    private long id;

    @Column(name = Columns.NAME,columnDefinition = "NVARCHAR(100)",updatable = false, nullable = false, unique = true)
    @EqualsAndHashCode.Include
    @ApiModelProperty(	example="um",allowEmptyValue = false, position =1, value = "Required: Node name represents the highest level of the namespace. ", required = true)
    private String name;

    @ApiModelProperty(	example="federation", allowEmptyValue = true ,position =3, readOnly=false, value = "Optional: Set of services of Node", required = false)
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "nodeEnt", cascade = CascadeType.ALL)
    private Set<ServiceEnt> services;

    @ElementCollection
    private Set<String> deactivatedFor = new HashSet<>();

    public void addService(ServiceEnt serviceEnt) {
        if (this.services.contains(serviceEnt)) {
            for (ServiceEnt s : this.services) {
                if (s.getName().equals(serviceEnt.getName()))
                    s.merge(serviceEnt);
            }
        } else {
            this.services.add(serviceEnt);
        }
    }

    public void addDeactivate(String node) {
        this.deactivatedFor.add(node);
    }

    public void removeDeactivate(String node) {
        this.deactivatedFor.remove(node);
    }
    /**
     * Column name constants.
     */
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    static final class Columns {
        /**
         * ID column.
         */
        protected static final String ID = "id";
        /**
         * ID column.
         */
        protected static final String NAME = "name";
    }
}
