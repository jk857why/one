package cn.edu.sziit.industrial.springbootbackend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import jakarta.persistence.Id;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "durable_goods")
public class DurableGoods {

    @Id
    @Column(name = "DurableSpecID", length = 50, nullable = false)
    private String durableSpecID;

    @Column(name = "DurableName", length = 100, nullable = false)
    private String durableName;

    @Column(name = "DurableType", length = 50, nullable = false)
    private String durableType;

    @Column(name = "DurableDetailType", length = 50)
    private String durableDetailType;

    @Column(name = "DurableColor", length = 50)
    private String durableColor;

    @Column(name = "DurableCapacity", length = 50)
    private String durableCapacity;

    @Column(name = "MaxUseCount")
    private Integer maxUseCount;

    @Column(name = "MaxUseDays")
    private Integer maxUseDays;

    @Column(name = "MaxUseCountAfterClean")
    private Integer maxUseCountAfterClean;

    @Column(name = "MaxUseDaysAfterClean")
    private Integer maxUseDaysAfterClean;

    @Column(name = "AlarmWhenExceedCount")
    private Boolean alarmWhenExceedCount;

    @Column(name = "AlarmWhenExceedDays")
    private Boolean alarmWhenExceedDays;

    @Column(name = "AlarmWhenExceedCountAfterClean")
    private Boolean alarmWhenExceedCountAfterClean;

    @Column(name = "AlarmWhenExceedCountAfterInspection")
    private Boolean alarmWhenExceedCountAfterInspection;

    @Column(name = "AlarmWhenExceedDaysAfterInspection")
    private Boolean alarmWhenExceedDaysAfterInspection;

    @Column(name = "factory_id", length = 50)
    private String factoryId;

    @Column(name = "EditStatus", length = 20)
    private String editStatus;

    @Column(name = "EditTime")
    private LocalDateTime editTime;

    @Column(name = "EditUser", length = 50)
    private String editUser;

    @Column(name = "Event", length = 50)
    private String event;

    @Column(name = "EventCode", length = 50)
    private String eventCode;

    @Column(name = "CreateTime")
    private Timestamp createTime;

}
