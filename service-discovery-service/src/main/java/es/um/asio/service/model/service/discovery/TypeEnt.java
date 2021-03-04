package es.um.asio.service.model.service.discovery;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(
        name = TypeEnt.TABLE,
        uniqueConstraints = @UniqueConstraint(columnNames = {"name","service_id"})
)
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class TypeEnt {

    public static final String TABLE = "type";
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = Columns.ID)
    @ApiModelProperty(hidden = true)
    private long id;

    @Column(name = Columns.NAME,columnDefinition = "NVARCHAR(100)",updatable = false, nullable = false)
    @EqualsAndHashCode.Include
    @ApiModelProperty(	example="sparql",allowEmptyValue = false, position =1, value = "Required: Name of the type of service. ", required = true)
    private String name;

    @Column(name = Columns.SUFFIX_URL,columnDefinition = "NVARCHAR(400)",updatable = true, nullable = true)
    @EqualsAndHashCode.Include
    @ApiModelProperty(	example="/sparql",allowEmptyValue = true, position =2, value = "Required: Path of url by type. ", required = false)
    private String suffixURL;

    @JsonIgnore
    @ManyToOne(optional = false, cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id", nullable = false)
    private ServiceEnt service;

    public TypeEnt(ServiceEnt service, String name, String suffixURL) {
        this.name = name;
        this.suffixURL = suffixURL;
        this.service = service;
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
         * NAME column.
         */
        protected static final String NAME = "name";
        /**
         * BASE_URL column.
         */
        protected static final String SUFFIX_URL = "suffix_url";
        /**
         * ID column.
         */
        protected static final String PORT = "port";
        /**
         * ID column.
         */
        protected static final String HEALTH_ENDPOINT = "port";
    }
}
