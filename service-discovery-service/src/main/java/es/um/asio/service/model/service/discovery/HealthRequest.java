package es.um.asio.service.model.service.discovery;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = HealthRequest.TABLE)
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class HealthRequest {

    public static final String TABLE = "health_request";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = Columns.ID)
    @EqualsAndHashCode.Include
    @ApiModelProperty(hidden = true)
    private long id;

    @Column(name = Columns.REQUEST_DATE, nullable = false)
    @EqualsAndHashCode.Include
    @ApiModelProperty(	example="12233556568844",allowEmptyValue = false, position =1, value = "Required: Timestamp in millisecons from last request. ", required = true)
    private long requestDate;

    @Column(name = Columns.STATUS, nullable = false,columnDefinition = "VARCHAR(40) DEFAULT 'UNKNOWN'",length = 40)
    @Enumerated(value = EnumType.STRING)
    @EqualsAndHashCode.Include
    @ApiModelProperty(	example="12233556568844",allowEmptyValue = false, position =2, value = "Required: The status of last last request. ", required = true)
    private Status status;

    @JsonIgnore
    @ManyToOne(optional = false, cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private ServiceEnt service;

    public HealthRequest(ServiceEnt service, Status status) {
        this.requestDate = new Date().getTime();
        this.status = status;
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
         * BASE_URL column.
         */
        protected static final String REQUEST_DATE = "request_timestamp";
        /**
         * STATUS column.
         */
        protected static final String STATUS = "status";
    }
}
