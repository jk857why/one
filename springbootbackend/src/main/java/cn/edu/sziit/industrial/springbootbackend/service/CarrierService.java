package cn.edu.sziit.industrial.springbootbackend.service;

import cn.edu.sziit.industrial.springbootbackend.entity.Carrier;

import java.util.List;
import java.util.Optional;

public interface CarrierService {

    List<Carrier> findAll();

    Optional<Carrier> findById(String carrierID);
    Carrier save(Carrier carrier);
    void deleteById(String carrierID);
    List<String> getDistinctFieldValues(String fieldName);

    List<Carrier> findByConditions(String detailType, String cleaningStatus, String durableSpecID,
                                   String carrierStatus, String capacityStatus);
    Carrier updateCarrierStatus(String carrierID, String action);
    Carrier createCarrier(Carrier carrier);

}