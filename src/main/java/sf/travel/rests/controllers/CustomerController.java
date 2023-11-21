package sf.travel.rests.controllers;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import sf.travel.entities.Customer;
import sf.travel.entities.New;
import sf.travel.rests.types.ApiResponse;
import sf.travel.rests.types.CreateCustomerReq;
import sf.travel.rests.types.CustomerFilter;
import sf.travel.rests.types.UpdateCustomerReq;
import sf.travel.services.CustomerService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customers")
@AllArgsConstructor
public class CustomerController {
    @Autowired
    private final CustomerService customerService;

    @PostMapping("/")
    public Customer create(@RequestBody CreateCustomerReq req) {
        return customerService.create(req);
    }

    @GetMapping("")
    public ApiResponse<Customer> findAll(@ModelAttribute CustomerFilter filter){
        Page<Customer> pageResult = customerService.findAll(filter);
        ApiResponse<Customer> response = new ApiResponse<>();
        response.setItems(pageResult.getContent());
        response.setTotal(pageResult.getTotalElements());
        response.setSize(pageResult.getSize());
        response.setPage(pageResult.getNumber());

        return response;
    }

    @GetMapping("/{id}")
    public Optional<Customer> findById(@PathVariable Long id){
        return customerService.findById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        customerService.delete(id);
    }

    @PutMapping("/{id}")
    public Customer partialUpdate(@PathVariable Long id, @RequestBody UpdateCustomerReq req){
        return customerService.partialUpdate(id, req);
    }
}
