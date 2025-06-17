package cn.edu.sziit.industrial.springbootbackend.repository;

import cn.edu.sziit.industrial.springbootbackend.entity.Carrier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CarrierRepository extends JpaRepository<Carrier, String>, JpaSpecificationExecutor<Carrier> {

    @Query("select distinct c.CarrierDetailType from Carrier c")
    List<String> findDistinctCarrierDetailType();

    @Query("select distinct c.CleaningStatus from Carrier c")
    List<String> findDistinctCleaningStatus();

    @Query("select distinct c.DurableSpecID from Carrier c")
    List<String> findDistinctDurableSpecID();

    @Query("select distinct c.CarrierStatus from Carrier c")
    List<String> findDistinctCarrierStatus();

    @Query("select distinct c.CapacityStatus from Carrier c")

    List<String> findDistinctCapacityStatus();
    @Query("SELECT c FROM Carrier c WHERE c.DurableSpecID = :durableSpecID AND c.CarrierStatus <> '报废' ORDER BY c.CarrierID DESC")
    List<Carrier> findActiveByDurableSpecIDOrderByCarrierIDDesc(@Param("durableSpecID") String durableSpecID);
}
