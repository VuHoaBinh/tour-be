package sf.travel.rests.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import lombok.AllArgsConstructor;
import sf.travel.entities.Travel;
import sf.travel.rests.types.ApiResponse;
import sf.travel.rests.types.CreateTravelReq;
import sf.travel.rests.types.TravelFilter;
import sf.travel.rests.types.UpdateTravelReq;
import sf.travel.services.TravelService;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/travels")
@AllArgsConstructor
public class TravelController {
    @Autowired
    private final TravelService travelService;

    @PostMapping("/")
    public Travel create(@RequestBody CreateTravelReq req) {
        return travelService.create(req);
    }

    @GetMapping("")
    public ApiResponse<Travel> findAll(@ModelAttribute TravelFilter filter){
        Page<Travel> pageResult = travelService.findAll(filter);
        ApiResponse<Travel> response = new ApiResponse<>();
        response.setItems(pageResult.getContent());
        response.setTotal(pageResult.getTotalElements());
        response.setSize(pageResult.getSize());
        response.setPage(pageResult.getNumber());

        return response;
    }

    @GetMapping("/{id}")
    public Optional<Travel> findById(@PathVariable Long id){
        return travelService.findById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        travelService.delete(id);
    }

    @PutMapping("/{id}")
    public Travel partialUpdate(@PathVariable Long id, @RequestBody UpdateTravelReq req){
        return travelService.partialUpdate(id, req);
    }
}
