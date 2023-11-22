package sf.travel.rests.controllers;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import sf.travel.entities.New;
import sf.travel.entities.Order;
import sf.travel.entities.Travel;
import sf.travel.rests.types.*;
import sf.travel.services.NewService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/news")
@AllArgsConstructor
public class NewController {
    @Autowired
    private final NewService newService;

    @PostMapping("/")
    public New create(@RequestBody CreateNewReq req){
        return newService.create(req);
    }

    @GetMapping("")
    public ApiResponse<New> findAll(@ModelAttribute NewFilter filter){
        Page<New> pageResult = newService.findAll(filter);
        ApiResponse<New> response = new ApiResponse<>();
        response.setItems(pageResult.getContent());
        response.setTotal(pageResult.getTotalElements());
        response.setSize(pageResult.getSize());
        response.setPage(pageResult.getNumber());

        return response;
    }

    @GetMapping("/{id}")
    public Optional<New> findById(@PathVariable Long id){
        return newService.findById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        newService.delete(id);
    }

    @PutMapping("/{id}")
    public New partialUpdate(@PathVariable Long id, @RequestBody UpdateNewReq req){
        return newService.partialUpdate(id, req);
    }
}
