package cn.edu.sziit.industrial.springbootbackend.controller;

import cn.edu.sziit.industrial.springbootbackend.entity.Carrier;
import cn.edu.sziit.industrial.springbootbackend.service.CarrierService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carriers")
@CrossOrigin(origins = "*")
public class CarrierController {

    private final CarrierService carrierService;

    public CarrierController(CarrierService carrierService) {
        this.carrierService = carrierService;
    }

    // 获取指定字段的所有 distinct 值，供前端下拉框
    @GetMapping("/distinct")
    public List<String> getDistinctValues(@RequestParam String field) {
        return carrierService.getDistinctFieldValues(field);
    }

    // 多条件查询载具列表，支持部分条件为空（null或空串即忽略该条件）
    @GetMapping("/search")
    public List<Carrier> searchCarriers(
            @RequestParam(required = false) String detailType,
            @RequestParam(required = false) String cleaningStatus,
            @RequestParam(required = false) String durableSpecID,
            @RequestParam(required = false) String carrierStatus,   // 这里也设置为false
            @RequestParam(required = false) String capacityStatus
    ) {
        return carrierService.findByConditions(detailType, cleaningStatus, durableSpecID, carrierStatus, capacityStatus);
    }

}
