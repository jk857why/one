package cn.edu.sziit.industrial.springbootbackend.service;

import cn.edu.sziit.industrial.springbootbackend.entity.Carrier;
import cn.edu.sziit.industrial.springbootbackend.entity.DurableGoods;
import cn.edu.sziit.industrial.springbootbackend.repository.DurableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DurableService {

    private final DurableRepository durableRepository;

    public List<DurableGoods> getAllDurables() {
        return durableRepository.findAll();
    }

    public List<DurableGoods> getDurablesBySpecID(String durableSpecID) {
        return durableRepository.findByDurableSpecID(durableSpecID);
    }

    public List<String> getDistinctDurableSpecIDs() {
        return durableRepository.findDistinctDurableSpecID();
    }

    public List<Object[]> getDurablesWithCarrier() {
        return durableRepository.findDurablesWithCarrier();
    }
}
