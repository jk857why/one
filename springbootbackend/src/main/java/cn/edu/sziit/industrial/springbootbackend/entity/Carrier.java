package cn.edu.sziit.industrial.springbootbackend.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "carrier_list")
public class Carrier {

    @Id
    @Column(name = "CarrierID")
    private String carrierID;

    @Column(name = "CarrierType")
    private String carrierType;

    @Column(name = "CarrierDetailType")
    private String carrierDetailType;

    @Column(name = "DurableSpecID")
    private String durableSpecID;

    @Column(name = "CarrierStatus")
    private String carrierStatus;

    @Column(name = "CleaningStatus")
    private String cleaningStatus;

    @Column(name = "LockStatus")
    private String lockStatus;

    @Column(name = "CapacityStatus")
    private String capacityStatus;

    @Column(name = "BatchNumber")
    private String batchNumber;

    @Column(name = "BatchQuantity")
    private Integer batchQuantity;

    @Column(name = "eqp_id")
    private String eqpId;

    @Column(name = "port_id")
    private String portId;

    @Column(name = "LocationID")
    private String locationID;

    @Column(name = "edit_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date editTime;
    @Column(name = "create_time", nullable = false, updatable = false, insertable = false, columnDefinition = "datetime default CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    @Column(name = "MaxCleaningCount")
    private Integer maxCleaningCount;

    @Column(name = "CleaningCount")
    private Integer cleaningCount;
}

