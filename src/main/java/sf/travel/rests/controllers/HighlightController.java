package sf.travel.rests.controllers;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import sf.travel.entities.Customer;
import sf.travel.entities.Highlight;
import sf.travel.entities.New;
import sf.travel.rests.types.*;
import sf.travel.services.HighlightService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/highlights")
@AllArgsConstructor
public class HighlightController {
    @Autowired
    private final HighlightService highlightService;

    @PostMapping("/")
    public Highlight create(@RequestBody CreateHighlightReq req) {
        return highlightService.create(req);
    }

    @GetMapping("")
    public ApiResponse<Highlight> findAll(@ModelAttribute HighlightFilter filter){
        Page<Highlight> pageResult = highlightService.findAll(filter);
        ApiResponse<Highlight> response = new ApiResponse<>();
        response.setItems(pageResult.getContent());
        response.setTotal(pageResult.getTotalElements());
        response.setSize(pageResult.getSize());
        response.setPage(pageResult.getNumber());

        return response;
    }

    @GetMapping("/{id}")
    public Optional<Highlight> findById(@PathVariable Long id){
        return highlightService.findById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        highlightService.delete(id);
    }

    @PutMapping("/{id}")
    public Highlight partialUpdate(@PathVariable Long id, @RequestBody UpdateHighlightReq req){
        return highlightService.partialUpdate(id, req);
    }
}
