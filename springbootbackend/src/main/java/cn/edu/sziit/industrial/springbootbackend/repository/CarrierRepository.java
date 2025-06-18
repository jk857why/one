package cn.edu.sziit.industrial.springbootbackend.repository;

import cn.edu.sziit.industrial.springbootbackend.entity.Carrier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CarrierRepository extends JpaRepository<Carrier, String> {
    @Query("SELECT c FROM Carrier c WHERE " +
            "(:carrierDetailType IS NULL OR c.CarrierDetailType = :carrierDetailType) AND " +
            "(:cleaningStatus IS NULL OR c.CleaningStatus = :cleaningStatus) AND " +
            "(:durableSpecID IS NULL OR c.DurableSpecID = :durableSpecID) AND " +
            "(:carrierStatus IS NULL OR c.CarrierStatus = :carrierStatus) AND " +
            "(:capacityStatus IS NULL OR c.CapacityStatus = :capacityStatus)")
    List<Carrier> findByConditions(
            @Param("carrierDetailType") String carrierDetailType,
            @Param("cleaningStatus") String cleaningStatus,
            @Param("durableSpecID") String durableSpecID,
            @Param("carrierStatus") String carrierStatus,
            @Param("capacityStatus") String capacityStatus
    );

    // 固定字段的 distinct 查询方法，JPQL 不支持动态字段名参数
    @Query("SELECT DISTINCT c.CarrierDetailType FROM Carrier c ORDER BY c.CarrierDetailType ASC")
    List<String> findDistinctCarrierDetailType();

    @Query("SELECT DISTINCT c.CleaningStatus FROM Carrier c ORDER BY c.CleaningStatus ASC")
    List<String> findDistinctCleaningStatus();

    @Query("SELECT DISTINCT c.DurableSpecID FROM Carrier c ORDER BY c.DurableSpecID ASC")
    List<String> findDistinctDurableSpecID();

    @Query("SELECT DISTINCT c.CapacityStatus FROM Carrier c ORDER BY c.CarrierStatus ASC")
    List<String> findDistinctCarrierStatus();

    @Query("SELECT DISTINCT c.CapacityStatus FROM Carrier c ORDER BY c.CapacityStatus ASC")
    List<String> findDistinctCapacityStatus();
}
