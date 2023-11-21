package sf.travel.rests.controllers;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import sf.travel.entities.Order;
import sf.travel.entities.Promotion;
import sf.travel.rests.types.ApiResponse;
import sf.travel.rests.types.CreateOrderReq;
import sf.travel.rests.types.OrderFilter;
import sf.travel.rests.types.UpdateOrderReq;
import sf.travel.services.OrderService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/orders")
@AllArgsConstructor
public class OrderController {
    @Autowired private final OrderService orderService;

    @PostMapping("/")
    public Order create(@RequestBody CreateOrderReq req) {
        return orderService.create(req);
    }

    @GetMapping("")
    public ApiResponse<Order> findAll(@ModelAttribute OrderFilter filter){
        Page<Order> pageResult = orderService.findAll(filter);
        ApiResponse<Order> response = new ApiResponse<>();
        response.setItems(pageResult.getContent());
        response.setTotal(pageResult.getTotalElements());
        response.setSize(pageResult.getSize());
        response.setPage(pageResult.getNumber());

        return response;
    }

    @GetMapping("/{id}")
    public Optional<Order> findById(@PathVariable Long id){
        return orderService.findById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        orderService.delete(id);
    }

    @PutMapping("/{id}")
    public Order partialUpdate(@PathVariable Long id, @RequestBody UpdateOrderReq req){
        return orderService.partialUpdate(id, req);
    }
}
