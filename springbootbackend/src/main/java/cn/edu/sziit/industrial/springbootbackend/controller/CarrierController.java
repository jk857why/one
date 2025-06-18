package cn.edu.sziit.industrial.springbootbackend.controller;

import cn.edu.sziit.industrial.springbootbackend.entity.Carrier;
import cn.edu.sziit.industrial.springbootbackend.service.CarrierService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/carriers")
public class CarrierController {
    private final CarrierService carrierService;

    public CarrierController(CarrierService carrierService) {
        this.carrierService = carrierService;
    }

    // 查询所有载具
    @GetMapping
    public List<Carrier> getAllCarriers() {
        return carrierService.getAllCarriers();
    }

    // 根据 ID 查询载具
    @GetMapping("/{id}")
    public ResponseEntity<Carrier> getCarrierById(@PathVariable("id") String id) {
        Optional<Carrier> carrier = carrierService.getCarrierById(id);
        return carrier.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 根据多个条件查询载具，参数可选
    @GetMapping("/search")
    public List<Carrier> findByConditions(
            @RequestParam(required = false) String carrierDetailType,
            @RequestParam(required = false) String cleaningStatus,
            @RequestParam(required = false) String durableSpecID,
            @RequestParam(required = false) String carrierStatus,
            @RequestParam(required = false) String capacityStatus
    ) {
        return carrierService.findCarriersByConditions(carrierDetailType, cleaningStatus,
                durableSpecID, carrierStatus, capacityStatus);
    }

    // 新增或更新载具
    @PostMapping
    public Carrier saveCarrier(@RequestBody Carrier carrier) {
        return carrierService.saveCarrier(carrier);
    }

    // 清洗载具（更新清洗次数和状态）
    @PutMapping("/{id}/clean")
    public ResponseEntity<String> cleanCarrier(@PathVariable("id") String id) {
        boolean success = carrierService.cleanCarrier(id);
        if (success) {
            return ResponseEntity.ok("Carrier cleaned successfully.");
        } else {
            return ResponseEntity.badRequest().body("Cleaning failed: max cleaning count reached or carrier not found.");
        }
    }

    // 更新载具锁状态
    @PutMapping("/{id}/lockStatus")
    public ResponseEntity<String> updateLockStatus(@PathVariable("id") String id,
                                                   @RequestParam String lockStatus) {
        boolean success = carrierService.updateLockStatus(id, lockStatus);
        if (success) {
            return ResponseEntity.ok("Lock status updated.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 更新载具状态
    @PutMapping("/{id}/status")
    public ResponseEntity<String> updateCarrierStatus(@PathVariable("id") String id,
                                                      @RequestParam String carrierStatus) {
        boolean success = carrierService.updateCarrierStatus(id, carrierStatus);
        if (success) {
            return ResponseEntity.ok("Carrier status updated.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 获取某字段的所有去重值（只支持几个固定字段）
    @GetMapping("/distinct/{fieldName}")
    public ResponseEntity<List<String>> getDistinctFieldValues(@PathVariable("fieldName") String fieldName) {
        try {
            List<String> values = carrierService.getDistinctValuesByField(fieldName);
            return ResponseEntity.ok(values);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
