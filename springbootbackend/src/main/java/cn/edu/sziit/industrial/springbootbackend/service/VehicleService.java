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

    /**
     * 获取所有耐用品（可按规格ID筛选）
     */
    public List<DurableGoods> getDurablesBySpecID(String durableSpecID) {
        if ("All".equalsIgnoreCase(durableSpecID) || durableSpecID == null) {
            return durableService.getAllDurables();
        } else {
            return durableService.getDurablesBySpecID(durableSpecID);
        }
    }

    /**
     * 根据耐用品规格号生成唯一载具编号，并创建载具
     */
    public Optional<Carrier> createCarrierForDurable(String durableSpecID, String locationID) {
        // 查询耐用品
        List<DurableGoods> durables = durableService.getDurablesBySpecID(durableSpecID);
        if (durables.isEmpty()) {
            return Optional.empty();
        }
        DurableGoods durable = durables.get(0);

        // 获取已有载具ID集合
        List<Carrier> allCarriers = carrierService.getAllCarriers();
        Set<String> existingIds = allCarriers.stream()
                .map(Carrier::getCarrierID)
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
            // 随机编号，MA-xxxx
            Random rand = new Random();
            int attempts = 0;
            String candidate;
            do {
                candidate = prefix + String.format("%04d", rand.nextInt(10000));
                attempts++;
                if (attempts > 10000) {
                    return Optional.empty(); // 无法生成唯一编号
                }
            } while (existingIds.contains(candidate));
            newCarrierId = candidate;
        }

        Carrier carrier = new Carrier();
        carrier.setCarrierID(newCarrierId);
        carrier.setCarrierType(durable.getDurableType());
        carrier.setCarrierDetailType(durable.getDurableDetailType());
        carrier.setDurableSpecID(durableSpecID);
        carrier.setLocationID(locationID != null ? locationID : "Bank");
        carrier.setCarrierStatus("Create");
        carrier.setCleaningStatus("Dirty");
        carrier.setLockStatus("Unlocked");
        carrier.setCapacityStatus("Empty");
        carrier.setBatchNumber("");
        carrier.setBatchQuantity(0);
        carrier.setEqpId("");
        carrier.setPortId("");
        carrier.setEditTime(new Date());
        carrier.setCreateTime(new Date());
        carrier.setMaxCleaningCount(durable.getMaxUseCountAfterClean());
        carrier.setCleaningCount(0);
        Carrier savedCarrier = carrierService.saveCarrier(carrier);
        return Optional.of(savedCarrier);
    }
}
