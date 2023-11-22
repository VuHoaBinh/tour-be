package sf.travel.rests.controllers;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import sf.travel.entities.Promotion;
import sf.travel.entities.Travel;
import sf.travel.rests.types.ApiResponse;
import sf.travel.rests.types.CreatePromotionReq;
import sf.travel.rests.types.PromotionFilter;
import sf.travel.rests.types.UpdatePromotionReq;
import sf.travel.services.PromotionService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/promotions")
@AllArgsConstructor
public class PromotionController {
    @Autowired private final PromotionService promotionService;

    @PostMapping("/")
    public Promotion create(@RequestBody CreatePromotionReq req) {
        return promotionService.create(req);
    }

    @GetMapping("")
    public ApiResponse<Promotion> findAll(@ModelAttribute PromotionFilter filter){
        Page<Promotion> pageResult = promotionService.findAll(filter);
        ApiResponse<Promotion> response = new ApiResponse<>();
        response.setItems(pageResult.getContent());
        response.setTotal(pageResult.getTotalElements());
        response.setSize(pageResult.getSize());
        response.setPage(pageResult.getNumber());

        return response;
    }

    @GetMapping("/{id}")
    public Optional<Promotion> findById(@PathVariable Long id){
        return promotionService.findById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        promotionService.delete(id);
    }

    @PutMapping("/{id}")
    public Promotion partialUpdate(@PathVariable Long id, @RequestBody UpdatePromotionReq req){
        return promotionService.partialUpdate(id, req);
    }
}
