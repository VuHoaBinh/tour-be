package sf.travel.rests.controllers;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sf.travel.entities.Customer;
import sf.travel.entities.New;
import sf.travel.rests.dto.CredentialsDTO;
import sf.travel.rests.dto.UserDTO;
import sf.travel.rests.types.ApiResponse;
import sf.travel.rests.types.CreateCustomerReq;
import sf.travel.rests.types.CustomerFilter;
import sf.travel.rests.types.UpdateCustomerReq;
import sf.travel.services.CustomerService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customers")
@AllArgsConstructor
public class CustomerController {
    @Autowired
    private final CustomerService customerService;

    //TODO: ---------------- LOGIN -----------------
//    @PostMapping("/login")
//    public ResponseEntity<UserDTO> login(@RequestBody @Valid CredentialsDTO credentialsDto) {
//        UserDTO userDto = CustomerService.login(credentialsDto);
//        userDto.setToken(userAuthenticationProvider.createToken(userDto));
//        return ResponseEntity.ok(userDto);
//    }

    //TODO: ----------------- REGISTER -----------------
//    @PostMapping("/register")
//    public ResponseEntity<UserDTO> register(@RequestBody @Valid SignUpDto user) {
//        UserDto createdUser = userService.register(user);
//        createdUser.setToken(userAuthenticationProvider.createToken(createdUser));
//        return ResponseEntity.created(URI.create("/users/" + createdUser.getId())).body(createdUser);
//    }
//
//    @GetMapping("user/getUserInfo")
//    public ResponseEntity<UserDto> getUserInfo() {
//        // Lấy thông tin người dùng đã được xác thực từ SecurityContextHolder
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if(authentication != null && authentication.getPrincipal() instanceof UserDto) {
//            UserDto userDto = (UserDto) authentication.getPrincipal();
//            return ResponseEntity.ok(userDto);
//        } else {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//        }
//    }

    @PostMapping("/")
    public Customer create(@RequestBody CreateCustomerReq req) {
        return customerService.create(req);
    }
    // create
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
    // find
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        customerService.delete(id);
    }
    // delete
    @PutMapping("/{id}")
    public Customer partialUpdate(@PathVariable Long id, @RequestBody UpdateCustomerReq req){
        return customerService.partialUpdate(id, req);
    }
    //update
}
