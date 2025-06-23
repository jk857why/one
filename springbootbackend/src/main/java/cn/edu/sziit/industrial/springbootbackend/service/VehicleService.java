package cn.edu.sziit.industrial.springbootbackend.service;

import cn.edu.sziit.industrial.springbootbackend.entity.Carrier;
import cn.edu.sziit.industrial.springbootbackend.entity.DurableGoods;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VehicleService {

    private final DurableService durableService;
    private final CarrierService carrierService;

    private static final String DEFAULT_LOCATION = "Bank";

    /**
     * 获取所有耐用品（可按规格ID筛选）
     */
    public List<DurableGoods> getDurablesBySpecID(String durableSpecID) {
        if (durableSpecID == null || "All".equalsIgnoreCase(durableSpecID.trim())) {
            return Optional.ofNullable(durableService.getAllDurables())
                    .orElse(Collections.emptyList());
        } else {
            return Optional.ofNullable(durableService.getDurablesBySpecID(durableSpecID.trim()))
                    .orElse(Collections.emptyList());
        }
    }

    /**
     * 根据耐用品规格号生成唯一载具编号，并创建载具
     */
    public Optional<Carrier> createCarrierForDurable(String durableSpecID, String locationID) {
        // 参数校验
        if (durableSpecID == null || durableSpecID.trim().isEmpty()) {
            return Optional.empty();
        }

        // 查询耐用品
        List<DurableGoods> durables = getDurablesBySpecID(durableSpecID);
        if (durables.isEmpty()) {
            return Optional.empty();
        }
        DurableGoods durable = durables.get(0);

        // 获取已有载具ID集合
        List<Carrier> allCarriers = Optional.ofNullable(carrierService.getAllCarriers())
                .orElse(Collections.emptyList());
        Set<String> existingIds = allCarriers.stream()
                .map(Carrier::getCarrierID)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        String prefix;
        boolean isSequential = true;

        if (durableSpecID.startsWith("BKF_M")) {
            prefix = "BKFM";
        } else if (durableSpecID.startsWith("2FZJ")) {
            prefix = "PG-CAS";
        } else {
            prefix = "MA-";
            isSequential = false;
        }

        String newCarrierId;

        if (isSequential) {
            // 顺序编号
            List<Integer> usedIndices = existingIds.stream()
                    .filter(id -> id.startsWith(prefix))
                    .map(id -> id.substring(prefix.length()))
                    .filter(suffix -> suffix.matches("\\d+"))
                    .map(Integer::parseInt)
                    .sorted()
                    .collect(Collectors.toList());

            int newIndex = 1;
            for (int used : usedIndices) {
                if (used != newIndex) break;
                newIndex++;
            }
            newCarrierId = prefix + String.format("%04d", newIndex);
            if (existingIds.contains(newCarrierId)) {
                return Optional.empty(); // 编号冲突
            }
        } else {
            // 随机编号
            Random rand = new Random();
            int attempts = 0;
            String candidate;
            do {
                candidate = prefix + String.format("%04d", rand.nextInt(10000));
                attempts++;
                if (attempts > 10000) {
                    System.err.println("Failed to generate unique CarrierID after 10000 attempts.");
                    return Optional.empty();
                }
            } while (existingIds.contains(candidate));
            newCarrierId = candidate;
        }

        Carrier carrier = new Carrier();
        carrier.setCarrierID(newCarrierId);
        carrier.setCarrierType(Optional.ofNullable(durable.getDurableType()).orElse("Unknown"));
        carrier.setCarrierDetailType(Optional.ofNullable(durable.getDurableDetailType()).orElse("Unknown"));
        carrier.setDurableSpecID(durableSpecID);
        carrier.setLocationID(locationID != null ? locationID : DEFAULT_LOCATION);
        carrier.setCarrierStatus("Create");
        carrier.setCleaningStatus("Dirty");
        carrier.setLockStatus("Unlocked");
        carrier.setCapacityStatus("Empty");
        carrier.setBatchNumber("");
        carrier.setBatchQuantity(0);
        carrier.setEqpId("EQP001");
        carrier.setPortId(null);
        carrier.setEditTime(new Date());
        carrier.setCreateTime(new Date());
        carrier.setMaxCleaningCount(Optional.ofNullable(durable.getMaxUseCountAfterClean()).orElse(0));
        carrier.setCleaningCount(0);

        Carrier savedCarrier = carrierService.saveCarrier(carrier);
        return Optional.ofNullable(savedCarrier);
    }
}
