package sf.travel.services;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import sf.travel.entities.*;
import sf.travel.errors.ConflictError;
import sf.travel.errors.ErrorCode;
import sf.travel.helper.specifications.OrderSpec;
import sf.travel.repositories.CustomerRepository;
import sf.travel.repositories.OrderRepository;
import sf.travel.repositories.TravelRepository;
import sf.travel.rests.types.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// import lib thumbnail for img
import net.coobird.thumbnailator.Thumbnails;
@Service
@AllArgsConstructor
public class OrderService {
    @Autowired private final OrderRepository orderRepo;
    @Autowired private final TravelRepository travelRepo;
    @Autowired private final CustomerRepository customerRepo;
    @Autowired private final OrderSpec orderSpec;

    public Order create(CreateOrderReq input) {
        Optional<Travel> travel = travelRepo.findById(input.getTravelId());
        Optional<Customer> customer = customerRepo.findById(input.getCustomerId());


        List<MultipartFile> imageFiles = ((MultipartHttpServletRequest) input).getFiles("imageFiles");

        Order order = new Order();
        order.setName(input.getName());
        List<FileImage> imageFileList = new ArrayList<>();
        for(MultipartFile file : imageFiles) {
            try{
                byte[] resizedImageBytes = Thumbnails.of(file.getInputStream())
                        .size(372, 372)
                        .outputFormat("jpg")
                        .outputQuality(0.5)
                        .asByteArray();
                FileImage imageFile = new FileImage();
                imageFile.setImageByte(resizedImageBytes);
                imageFileList.add(imageFile);
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
        order.setDescription(input.getDescription());
        order.setPrice(input.getPrice());
        order.setStatus(input.getStatus());

        if (travel.isPresent()){
            order.setTravel(travel.get());
        } else {
            throw new ConflictError(ErrorCode.TRAVEL_NOT_FOUND);
        }

        if(customer.isPresent()){
            order.setCustomer(customer.get());
        } else {
            throw new ConflictError(ErrorCode.CUSTOMER_NOT_FOUND);
        }

        return orderRepo.save(order);
    }

    public Page<Order> findAll(OrderFilter filter){
        Specification<Order> spec = null;
        if (filter.getName() != null) {
            spec = orderSpec.createSpecification("name", filter.getName());
        }
        if (filter.getStatus() != null) {
            spec = orderSpec.createSpecification("status", filter.getStatus());
        }
        if (filter.getPrice() != -1) {
            spec = orderSpec.createSpecification("price", filter.getPrice());
        }

        if (filter.getSearchText() != null) {
            spec = orderSpec.createLikeSpecification("description", filter.getSearchText())
                    .or(orderSpec.createLikeSpecification("name", filter.getSearchText()));
        }

        System.out.println("Conditions: " + spec);

        Pageable pageable = PageRequest.of(filter.getPage(), filter.getSize(), Sort.by("id").descending());

        Page<Order> res = orderRepo.findAll(spec, pageable);
        return res;
    }

    public Optional<Order> findById(Long id){
        return orderRepo.findById(id);
    }

    public Order partialUpdate(Long id, UpdateOrderReq input){
        Optional<Order> order = orderRepo.findById(id);
        if (order.isPresent()){
            Order newOrder = order.get();
            if (input.getName() != null){
                newOrder.setName(input.getName());
            }
            if (input.getDescription() != null){
                newOrder.setDescription(input.getDescription() );
            }
                newOrder.setPrice(input.getPrice());

            if (input.getStatus() != null){
                newOrder.setStatus(input.getStatus());
            }

            return orderRepo.save(newOrder);
        } else {
            throw new ConflictError(ErrorCode.ORDER_NOT_FOUND);
        }
    }

    public void delete(Long id){
        orderRepo.deleteById(id);
    }
}
