package sf.travel.services;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import sf.travel.entities.Customer;
import sf.travel.entities.New;
import sf.travel.errors.ConflictError;
import sf.travel.errors.ErrorCode;
import sf.travel.helper.specifications.CustomerSpec;
import sf.travel.repositories.CustomerRepository;
import sf.travel.rests.types.CreateCustomerReq;
import sf.travel.rests.types.CustomerFilter;
import sf.travel.rests.types.UpdateCustomerReq;
import sf.travel.rests.types.UpdateTravelReq;


import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CustomerService {
    @Autowired private final CustomerRepository customerRepo;
    @Autowired private final CustomerSpec customerSpec;

    public Customer create(CreateCustomerReq input) {
        Customer customer = new Customer();
        customer.setName(input.getName());
        customer.setEmail(input.getEmail());
        customer.setDob(input.getDob());
        return customerRepo.save(customer);
    }

    public Page<Customer> findAll(CustomerFilter filter){
        Specification<Customer> spec = null;
        if (filter.getName() != null) {
            spec = customerSpec.createSpecification("name", filter.getName());
        }
        if (filter.getEmail() != null) {
            spec = customerSpec.createSpecification("email", filter.getEmail());
        }

        if (filter.getSearchText() != null) {
            spec = customerSpec.createLikeSpecification("email", filter.getSearchText())
                    .or(customerSpec.createLikeSpecification("name", filter.getSearchText()));
        }

        System.out.println("Conditions: " + spec);

        Pageable pageable = PageRequest.of(filter.getPage(), filter.getSize(), Sort.by("id").descending());

        Page<Customer> res = customerRepo.findAll(spec, pageable);
        return res;
    }

    public Optional<Customer> findById(Long id){
        return customerRepo.findById(id);
    }

    public Customer partialUpdate(Long id, UpdateCustomerReq input){
        Optional<Customer> customer = customerRepo.findById(id);
        if (customer.isPresent()){
            Customer newCustomer = customer.get();
            if (input.getName() != null){
                newCustomer.setName(input.getName());
            }
            if (input.getEmail() != null){
                newCustomer.setEmail(input.getEmail() );
            }
            if (input.getDob() != null){
                newCustomer.setDob(input.getDob());
            }

            return customerRepo.save(newCustomer);
        } else {
            throw new ConflictError(ErrorCode.CUSTOMER_NOT_FOUND);
        }
    }


    public void delete(Long id){
        customerRepo.deleteById(id);
    }
}
