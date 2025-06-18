package cn.edu.sziit.industrial.springbootbackend.repository;

import cn.edu.sziit.industrial.springbootbackend.entity.Carrier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface CarrierRepository extends JpaRepository<Carrier, String> {
    @Query("SELECT c FROM Carrier c WHERE " +
            "(:carrierID IS NULL OR c.carrierID LIKE %:carrierID%) AND " +
            "(:carrierDetailType IS NULL OR c.carrierDetailType = :carrierDetailType) AND " +
            "(:cleaningStatus IS NULL OR c.cleaningStatus = :cleaningStatus) AND " +
            "(:durableSpecID IS NULL OR c.durableSpecID = :durableSpecID) AND " +
            "(:carrierStatus IS NULL OR c.carrierStatus = :carrierStatus) AND " +
            "(:capacityStatus IS NULL OR c.capacityStatus = :capacityStatus) AND " +
            "(:start IS NULL OR c.editTime >= :start) AND " +
            "(:end IS NULL OR c.editTime <= :end)")
    List<Carrier> findByConditions(
            @Param("carrierID") String carrierID,
            @Param("carrierDetailType") String carrierDetailType,
            @Param("cleaningStatus") String cleaningStatus,
            @Param("durableSpecID") String durableSpecID,
            @Param("carrierStatus") String carrierStatus,
            @Param("capacityStatus") String capacityStatus,
            @Param("start") Date start,
            @Param("end") Date end
    );

    // 固定字段的 distinct 查询方法，JPQL 不支持动态字段名参数
    @Query(value = "SELECT DISTINCT CarrierDetailType FROM carrier_list WHERE CarrierDetailType IS NOT NULL AND CarrierDetailType <> '' ORDER BY CarrierDetailType ASC", nativeQuery = true)
    List<String> findDistinctCarrierDetailType();

    @Query(value = "SELECT DISTINCT CleaningStatus FROM carrier_list WHERE CleaningStatus IS NOT NULL AND CleaningStatus <> '' ORDER BY CleaningStatus ASC", nativeQuery = true)
    List<String> findDistinctCleaningStatus();

    @Query(value = "SELECT DISTINCT DurableSpecID FROM carrier_list WHERE DurableSpecID IS NOT NULL AND DurableSpecID <> '' ORDER BY DurableSpecID ASC", nativeQuery = true)
    List<String> findDistinctDurableSpecID();

    @Query(value = "SELECT DISTINCT CarrierStatus FROM carrier_list WHERE CarrierStatus IS NOT NULL AND CarrierStatus <> '' ORDER BY CarrierStatus ASC", nativeQuery = true)
    List<String> findDistinctCarrierStatus();

    @Query(value = "SELECT DISTINCT CapacityStatus FROM carrier_list WHERE CapacityStatus IS NOT NULL AND CapacityStatus <> '' ORDER BY CapacityStatus ASC", nativeQuery = true)
    List<String> findDistinctCapacityStatus();
}
