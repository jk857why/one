package cn.edu.sziit.industrial.springbootbackend.repository;

import cn.edu.sziit.industrial.springbootbackend.entity.DurableGoods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DurableRepository extends JpaRepository<DurableGoods, String> {

    // 根据 DurableSpecID 查询
    List<DurableGoods> findByDurableSpecID(String durableSpecID);

    // 查询所有不重复的 DurableSpecID
    @Query(value = "SELECT DISTINCT DurableSpecID FROM durable_goods WHERE DurableSpecID IS NOT NULL AND DurableSpecID <> '' ORDER BY DurableSpecID ASC", nativeQuery = true)
    List<String> findDistinctDurableSpecID();
    // 联合 Durable 和 Carrier（返回 Object[]，如需 DTO 可进一步封装）
    @Query(value = """
        SELECT d.*, c.*
        FROM durable_goods d
        LEFT JOIN carrier_list c ON d.DurableSpecID = c.DurableSpecID
        """, nativeQuery = true)
    List<Object[]> findDurablesWithCarrier();
}
