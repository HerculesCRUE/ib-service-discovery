package es.um.asio.service.model.service.discovery;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;


import javax.persistence.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(
        name = ServiceEnt.TABLE,
        uniqueConstraints = @UniqueConstraint(columnNames = {"name","node_id"})
)
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class ServiceEnt {

    public static final String TABLE = "service";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = Columns.ID)
    @ApiModelProperty(hidden = true)
    private long id;


    @Column(name = Columns.NAME,columnDefinition = "NVARCHAR(100)",updatable = false, nullable = false)
    @EqualsAndHashCode.Include
    @ApiModelProperty(	example="federation",allowEmptyValue = false, position =1, value = "Required: Service name represents the tag of service. ", required = true)
    private String name;

    @Column(name = Columns.BASE_URL,columnDefinition = "NVARCHAR(400)",updatable = true, nullable = false)
    @ApiModelProperty(	example="http://localhost",allowEmptyValue = false, position =2, value = "Required: The URL where the service is available", required = true)
    private String baseURL;

    @Column(name = Columns.PORT,columnDefinition = "INT",updatable = true, nullable = true)
    @ApiModelProperty(	example="8080",allowEmptyValue = false, position =3, value = "Optional: The port where the service is available", required = false)
    private Integer port;

    @Column(name = Columns.HEALTH_ENDPOINT,columnDefinition = "NVARCHAR(200)",updatable = true, nullable = true)
    @ApiModelProperty(	example="http://localhost",allowEmptyValue = false, position =3, value = "Required: The PATH where the service is available", required = true)
    private String healthEndpoint;

    @Column(name = Columns.STATUS, nullable = false,columnDefinition = "VARCHAR(40) DEFAULT 'UNKNOWN'",length = 40)
    @Enumerated(value = EnumType.STRING)
    @ApiModelProperty(	example="http://localhost",allowEmptyValue = false, position =3, value = "Required: The last status of the service", required = true)
    private Status status;

    @JsonIgnore
    @ManyToOne(optional = false, cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinColumn(name = "node_id",nullable = false)
    @EqualsAndHashCode.Include
    private NodeEnt nodeEnt;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "service", cascade = CascadeType.ALL)
    @ApiModelProperty(	example="sparql", allowEmptyValue = true ,position =4, readOnly=false, value = "Optional: Set Types of the service", required = false)
    private Set<TypeEnt> types;

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "service", cascade = CascadeType.ALL)
    @ApiModelProperty( allowEmptyValue = true ,position =5, readOnly=false, value = "Optional: Set of request to check service status", required = false)
    private List<HealthRequest> healthRequests;

    public ServiceEnt(NodeEnt node, String name, String baseURL,Integer port, String healthEndpoint) {
        this.name = name;
        this.baseURL = baseURL;
        this.port = port;
        this.healthEndpoint = healthEndpoint;
        this.status = Status.UNKNOWN;
        this.nodeEnt = node;
        this.types = new HashSet<>();
        this.healthRequests = new ArrayList<>();
    }

    public URL buildBaseURL() throws MalformedURLException {
        StringBuffer sb = new StringBuffer();
        if (this.baseURL!=null) {
            sb.append(this.baseURL.endsWith("/") ? this.baseURL.substring(0, this.baseURL.length() - 1) : this.baseURL);
        }

        if (this.port != null) {
            sb.append(String.format(":%d",this.port));
        }
        return new URL(sb.toString());
    }

    public URL buildHealthURL() throws MalformedURLException {
        if (healthEndpoint == null)
            return null;
        StringBuffer sb = new StringBuffer();
        sb.append(buildBaseURL());
        if (this.healthEndpoint!=null) {
            sb.append(this.healthEndpoint.startsWith("/")?this.healthEndpoint:String.format("/%s",this.healthEndpoint));
        }
        return new URL(sb.toString());
    }

    public void merge(ServiceEnt other) {
        this.baseURL = other.baseURL;
        this.port = other.port;
        this.healthEndpoint = other.healthEndpoint;
        this.status = other.status;
    }

    public List<TypeEnt> filterTypeByName(String name) {
        return this.types.stream().filter(te -> te.getName().equals(name)).collect(Collectors.toList());
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
        protected static final String BASE_URL = "base_url";
        /**
         * PORT column.
         */
        protected static final String PORT = "port";
        /**
         * HEALTH_ENDPOINT column.
         */
        protected static final String HEALTH_ENDPOINT = "health_endpoint";
        /**
         * STATUS column.
         */
        protected static final String STATUS = "status";
    }
}
