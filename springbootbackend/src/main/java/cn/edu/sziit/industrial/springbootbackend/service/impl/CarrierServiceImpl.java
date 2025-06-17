package cn.edu.sziit.industrial.springbootbackend.service.impl;

import cn.edu.sziit.industrial.springbootbackend.entity.Carrier;
import cn.edu.sziit.industrial.springbootbackend.repository.CarrierRepository;
import cn.edu.sziit.industrial.springbootbackend.repository.DurableSpecRepository;
import cn.edu.sziit.industrial.springbootbackend.service.CarrierService;
import cn.edu.sziit.industrial.springbootbackend.entity.DurableSpec;
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
    @Autowired
    private DurableSpecRepository durableSpecRepository;

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
    @Override
    public Carrier updateCarrierStatus(String carrierID, String action) {
        Carrier carrier = carrierRepository.findById(carrierID)
                .orElseThrow(() -> new RuntimeException("Carrier not found: " + carrierID));
        if ("报废".equals(carrier.getCarrierStatus()) && !action.equalsIgnoreCase("cancel_scrap")) {
            throw new IllegalStateException("报废载具无法执行该操作！");
        }else {
            switch (action.toLowerCase()) {
                case "create":
                    carrier.setCarrierStatus("新建");
                    break;
                case "cancel_create":
                    carrier.setCarrierStatus("未使用");
                    break;
                case "cancel_scrap":
                    carrier.setCarrierStatus("未使用");
                    break;
                case "release":
                    carrier.setCarrierStatus("Released");
                    break;
                case "cancel_release":
                    carrier.setCarrierStatus("NotReleased");
                    break;
                case "lock":
                    carrier.setLockStatus("Locked");
                    break;
                case "unlock":
                    carrier.setLockStatus("Unlocked");
                    break;
                case "clean":
                    Integer currentCount = carrier.getCleaningCount() == null ? 0 : carrier.getCleaningCount();
                    Integer maxCount = carrier.getMaxCleaningCount() == null ? 5 : carrier.getMaxCleaningCount(); // 默认最多清洗 5 次

                    if (currentCount >= maxCount) {
                        throw new IllegalStateException("清洗次数已达到上限，无法继续清洗！");
                    }

                    carrier.setCleaningCount(currentCount + 1);
                    carrier.setCleaningStatus("cleaned");
                    break;

                default:
                    throw new IllegalArgumentException("Unsupported action: " + action);
            }
        }
        return carrierRepository.save(carrier);
    }
    @Override
    public Carrier createCarrier(Carrier carrier) {
        String durableSpecID = carrier.getDurableSpecID();
        if (durableSpecID == null || durableSpecID.isEmpty()) {
            throw new IllegalArgumentException("DurableSpecID不能为空");
        }

        DurableSpec durableSpec = durableSpecRepository.findById(durableSpecID)
                .orElseThrow(() -> new RuntimeException("找不到耐用品规格：" + durableSpecID));

        // 查询同规格的非报废载具，按编号降序，取最大序号
        List<Carrier> carriers = carrierRepository.findActiveByDurableSpecIDOrderByCarrierIDDesc(durableSpecID);

        int nextSeq = 1;
        if (!carriers.isEmpty()) {
            String lastId = carriers.get(0).getCarrierID();
            String suffix = lastId.substring(durableSpecID.length() + 1); // 跳过规格ID和"-"
            try {
                nextSeq = Integer.parseInt(suffix) + 1;
            } catch (Exception ignored) {}
        }

        String newCarrierID = String.format("%s-%03d", durableSpecID, nextSeq);

        carrier.setCarrierID(newCarrierID);
        carrier.setCarrierType(durableSpec.getDurableType());
        carrier.setCapacityStatus(durableSpec.getDurableCapacity());
        carrier.setMaxCleaningCount(durableSpec.getMaxUseCountAfterClean() != null
                ? durableSpec.getMaxUseCountAfterClean()
                : (durableSpec.getMaxUseCount() != null ? durableSpec.getMaxUseCount() : 0));
        carrier.setCarrierStatus("新建");
        carrier.setCleaningStatus("未清洗");
        carrier.setLockStatus("未锁定");
        carrier.setCleaningCount(0);

        return carrierRepository.save(carrier);
    }
}

