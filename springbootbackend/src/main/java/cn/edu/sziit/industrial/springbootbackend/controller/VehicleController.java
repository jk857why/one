package cn.edu.sziit.industrial.springbootbackend.controller;

import cn.edu.sziit.industrial.springbootbackend.entity.Carrier;
import cn.edu.sziit.industrial.springbootbackend.entity.DurableGoods;
import cn.edu.sziit.industrial.springbootbackend.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/vehicles")
@RequiredArgsConstructor
public class VehicleController {

    private final VehicleService vehicleService;

    @GetMapping("/durables")
    public ResponseEntity<List<DurableGoods>> getDurables(@RequestParam(required = false, defaultValue = "All") String specId) {
        List<DurableGoods> durables = vehicleService.getDurablesBySpecID(specId);
        return ResponseEntity.ok(durables);
    }

    @PostMapping("/carriers/create")
    public ResponseEntity<String> createCarrier(@RequestParam String durableSpecID,
                                                @RequestParam(required = false) String locationID) {
        Optional<Carrier> optCarrier = vehicleService.createCarrierForDurable(durableSpecID, locationID);
        if (optCarrier.isPresent()) {
            // 如果想返回 JSON 字符串可以用 Jackson 序列化，或者直接返回 Carrier 对象并修改泛型
            return ResponseEntity.ok("载具创建成功，ID：" + optCarrier.get().getCarrierID());
        } else {
            return ResponseEntity.badRequest().body("载具编号生成失败或耐用品不存在");
        }
    }
}
