package cn.edu.sziit.industrial.springbootbackend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "carrier_list")
public class Carrier {

    @Id
    @Column(name = "CarrierID")
    private String CarrierID;

    @Column(name = "CarrierType")
    private String CarrierType;

    @Column(name = "CarrierDetailType")
    private String CarrierDetailType;

    @Column(name = "DurableSpecID")
    private String DurableSpecID;

    @Column(name = "CarrierStatus")
    private String CarrierStatus;

    @Column(name = "CleaningStatus")
    private String CleaningStatus;

    @Column(name = "LockStatus")
    private String LockStatus;

    @Column(name = "CapacityStatus")
    private String CapacityStatus;

    @Column(name = "BatchNumber")
    private String BatchNumber;

    @Column(name = "BatchQuantity")
    private Integer BatchQuantity;

    @Column(name = "eqp_id")
    private String eqp_id;

    @Column(name = "port_id")
    private String port_id;

    @Column(name = "LocationID")
    private String LocationID;

    @Column(name = "edit_time")
    private Date edit_time;

    @Column(name = "MaxCleaningCount")
    private Integer MaxCleaningCount;

    @Column(name = "CleaningCount")
    private Integer CleaningCount;
}