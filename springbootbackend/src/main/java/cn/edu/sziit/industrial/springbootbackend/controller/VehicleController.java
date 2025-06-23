package cn.edu.sziit.industrial.springbootbackend.controller;

import cn.edu.sziit.industrial.springbootbackend.dto.CreateCarrierRequest;
import cn.edu.sziit.industrial.springbootbackend.entity.Carrier;
import cn.edu.sziit.industrial.springbootbackend.entity.DurableGoods;
import cn.edu.sziit.industrial.springbootbackend.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/vehicles")
@RequiredArgsConstructor
public class VehicleController {

    private final VehicleService vehicleService;

    @GetMapping("/durables")
    public ResponseEntity<List<DurableGoods>> getDurables(
            @RequestParam(required = false, defaultValue = "All") String specId) {
        List<DurableGoods> durables = vehicleService.getDurablesBySpecID(specId);
        return ResponseEntity.ok(durables);
    }

    @PostMapping("/carriers/create")
    public ResponseEntity<?> createCarrier(@RequestBody CreateCarrierRequest request) {
        String durableSpecID = request.getDurableSpecID();
        String locationID = request.getLocationID();

        Optional<Carrier> optCarrier = vehicleService.createCarrierForDurable(durableSpecID, locationID);
        if (optCarrier.isPresent()) {
            // 返回Carrier对象，方便前端直接用
            return ResponseEntity.ok(optCarrier.get());
        } else {
            return ResponseEntity.badRequest().body("载具编号生成失败或耐用品不存在");
        }
    }
}
