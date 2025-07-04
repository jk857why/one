package cn.edu.sziit.industrial.springbootbackend.service;

import cn.edu.sziit.industrial.springbootbackend.entity.Carrier;
import cn.edu.sziit.industrial.springbootbackend.repository.CarrierRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.apache.tomcat.util.http.FastHttpDateFormat.parseDate;

@Service
public class CarrierService {
    private final CarrierRepository carrierRepository;

    public CarrierService(CarrierRepository carrierRepository) {
        this.carrierRepository = carrierRepository;
    }

    public List<Carrier> getAllCarriers() {
        return carrierRepository.findAll();
    }

    public Optional<Carrier> getCarrierById(String carrierID) {
        return carrierRepository.findById(carrierID);
    }

    public List<Carrier> findCarriersByConditions(String carrierID, String carrierDetailType,
                                                  String cleaningStatus, String durableSpecID,
                                                  String carrierStatus, String capacityStatus,
                                                  String startStr, String endStr) {
        Date start = null;
        Date end = null;

        try {
            if (startStr != null && !startStr.isEmpty()) {
                start = new Date(parseDate(startStr));
            }
            if (endStr != null && !endStr.isEmpty()) {
                end = new Date(parseDate(endStr));
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("日期格式不正确", e);
        }

        return carrierRepository.findByConditions(carrierID, carrierDetailType, cleaningStatus,
                durableSpecID, carrierStatus, capacityStatus, start, end);
    }

    public Carrier saveCarrier(Carrier carrier) {
        return carrierRepository.save(carrier);
    }

    @Transactional
    public boolean cleanCarrier(String carrierID) {
        Optional<Carrier> optional = carrierRepository.findById(carrierID);
        if (!optional.isPresent()) return false;

        Carrier carrier = optional.get();
        Integer cleaningCount = carrier.getCleaningCount() == null ? 0 : carrier.getCleaningCount();
        Integer maxCleaningCount = carrier.getMaxCleaningCount() == null ? 3 : carrier.getMaxCleaningCount();

        if (cleaningCount >= maxCleaningCount) {
            return false; // 已达到最大清洗次数
        }

        cleaningCount++;
        carrier.setCleaningCount(cleaningCount);

        if (cleaningCount.equals(maxCleaningCount)) {
            carrier.setCleaningStatus("Cleaned");
        } else {
            carrier.setCleaningStatus("Clean");
        }

        carrier.setEditTime(new java.util.Date());
        carrierRepository.save(carrier);
        return true;
    }

    @Transactional
    public boolean updateLockStatus(String carrierID, String lockStatus) {
        Optional<Carrier> optional = carrierRepository.findById(carrierID);
        if (!optional.isPresent()) return false;

        Carrier carrier = optional.get();
        carrier.setLockStatus(lockStatus);
        carrier.setEditTime(new java.util.Date());
        carrierRepository.save(carrier);
        return true;
    }

    @Transactional
    public boolean updateCarrierStatus(String carrierID, String carrierStatus) {
        Optional<Carrier> optional = carrierRepository.findById(carrierID);
        if (!optional.isPresent()) return false;

        Carrier carrier = optional.get();
        carrier.setCarrierStatus(carrierStatus);
        carrier.setEditTime(new java.util.Date());
        carrierRepository.save(carrier);
        return true;
    }

    public List<String> getDistinctValuesByField(String fieldName) {
        switch (fieldName) {
            case "carrierDetailType":
                return carrierRepository.findDistinctCarrierDetailType();
            case "cleaningStatus":
                return carrierRepository.findDistinctCleaningStatus();
            case "durableSpecID":
                return carrierRepository.findDistinctDurableSpecID();
            case "carrierStatus":
                return carrierRepository.findDistinctCarrierStatus();
            case "capacityStatus":
                return carrierRepository.findDistinctCapacityStatus();
            default:
                throw new IllegalArgumentException("Unsupported field: " + fieldName);
        }
    }
    @Transactional
    public boolean markCarrierIdle(String carrierID) {
        Optional<Carrier> optional = carrierRepository.findById(carrierID);
        if (!optional.isPresent()) return false;

        Carrier carrier = optional.get();
        carrier.setCarrierStatus("Idle");
        carrier.setEditTime(new Date());

        carrierRepository.save(carrier);
        return true;
    }
}
