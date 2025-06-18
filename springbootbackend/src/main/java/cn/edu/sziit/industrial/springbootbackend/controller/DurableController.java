package cn.edu.sziit.industrial.springbootbackend.controller;

import cn.edu.sziit.industrial.springbootbackend.entity.DurableGoods;
import cn.edu.sziit.industrial.springbootbackend.service.DurableService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/durable")
@RequiredArgsConstructor
public class DurableController {

    private final DurableService durableService;

    @GetMapping("/all")
    public List<DurableGoods> getAllDurables() {
        return durableService.getAllDurables();
    }
    @GetMapping("/by-spec-id/{id}")
    public List<DurableGoods> getBySpecId(@PathVariable("id") String id) {
        return durableService.getDurablesBySpecID(id);
    }

    @GetMapping("/distinct-ids")
    public List<String> getDistinctIds() {
        return durableService.getDistinctDurableSpecIDs();
    }

    @GetMapping("/with-carrier")
    public List<Object[]> getWithCarrier() {
        return durableService.getDurablesWithCarrier();
    }
}
