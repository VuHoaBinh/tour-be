package sf.travel.rests.controllers;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import sf.travel.entities.Hotel;
import sf.travel.rests.types.*;
import sf.travel.services.HotelService;

import java.util.Optional;

@RestController
@RequestMapping("/hotels")
@AllArgsConstructor
public class HotelController {
    @Autowired private final HotelService hotelService;

    @PostMapping("/")
    public Hotel create(@RequestBody CreateHotelReq req){
        return hotelService.create(req);
    }

    @GetMapping("")
    public ApiResponse<Hotel> findAll(@ModelAttribute HotelFilter filter){
        Page<Hotel> pageResult = hotelService.findAll(filter);
        ApiResponse<Hotel> response = new ApiResponse<>();
        response.setItems(pageResult.getContent());
        response.setTotal(pageResult.getTotalElements());
        response.setSize(pageResult.getSize());
        response.setPage(pageResult.getNumber());

        return response;
    }

    @GetMapping("/{id}")
    public Optional<Hotel> findById(@PathVariable Long id){
        return hotelService.findById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        hotelService.delete(id);
    }

    @PutMapping("/{id}")
    public Hotel partialUpdate(@PathVariable Long id, @RequestBody UpdateHotelReq req){
        return hotelService.partialUpdate(id, req);
    }
}
