package cn.edu.sziit.industrial.springbootbackend.repository;

import cn.edu.sziit.industrial.springbootbackend.entity.DurableSpec;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DurableSpecRepository extends JpaRepository<DurableSpec, String> {
    // JpaRepository 已经自带 findById, save, delete 等方法
}

