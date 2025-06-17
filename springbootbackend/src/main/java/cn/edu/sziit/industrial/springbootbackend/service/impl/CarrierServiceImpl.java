package cn.edu.sziit.industrial.springbootbackend.service.impl;

import cn.edu.sziit.industrial.springbootbackend.entity.Carrier;
import cn.edu.sziit.industrial.springbootbackend.repository.CarrierRepository;
import cn.edu.sziit.industrial.springbootbackend.service.CarrierService;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CarrierServiceImpl implements CarrierService {

    @Autowired
    private CarrierRepository carrierRepository;

    @Override
    public List<Carrier> findAll() {
        return carrierRepository.findAll();
    }

    @Override
    public Optional<Carrier> findById(String carrierID) {
        return carrierRepository.findById(carrierID);
    }

    @Override
    public Carrier save(Carrier carrier) {
        return carrierRepository.save(carrier);
    }

    @Override
    public void deleteById(String carrierID) {
        carrierRepository.deleteById(carrierID);
    }

    @Override
    public List<String> getDistinctFieldValues(String fieldName) {
        switch (fieldName) {
            case "CarrierDetailType":
                return carrierRepository.findDistinctCarrierDetailType();
            case "CleaningStatus":
                return carrierRepository.findDistinctCleaningStatus();
            case "DurableSpecID":
                return carrierRepository.findDistinctDurableSpecID();
            case "CarrierStatus":
                return carrierRepository.findDistinctCarrierStatus();
            case "CapacityStatus":
                return carrierRepository.findDistinctCapacityStatus();
            default:
                return new ArrayList<>();
        }
    }

    @Override
    public List<Carrier> findByConditions(String detailType, String cleaningStatus, String durableSpecID,
                                          String carrierStatus, String capacityStatus) {
        Specification<Carrier> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (detailType != null && !detailType.isEmpty()) {
                predicates.add(cb.equal(root.get("CarrierDetailType"), detailType));
            }
            if (cleaningStatus != null && !cleaningStatus.isEmpty()) {
                predicates.add(cb.equal(root.get("CleaningStatus"), cleaningStatus));
            }
            if (durableSpecID != null && !durableSpecID.isEmpty()) {
                predicates.add(cb.equal(root.get("DurableSpecID"), durableSpecID));
            }
            if (carrierStatus != null && !carrierStatus.isEmpty()) {
                predicates.add(cb.equal(root.get("CarrierStatus"), carrierStatus));
            }
            if (capacityStatus != null && !capacityStatus.isEmpty()) {
                predicates.add(cb.equal(root.get("CapacityStatus"), capacityStatus));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        return carrierRepository.findAll(spec);
    }
}
