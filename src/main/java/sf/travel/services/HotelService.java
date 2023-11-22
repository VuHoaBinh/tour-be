package sf.travel.services;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import sf.travel.entities.Hotel;
import sf.travel.errors.ConflictError;
import sf.travel.errors.ErrorCode;
import sf.travel.helper.specifications.HotelSpec;
import sf.travel.repositories.HotelRepository;
import sf.travel.rests.types.*;

import java.util.Optional;

@Service
@AllArgsConstructor
public class HotelService {
    @Autowired
    private final HotelRepository hotelRepo;
    @Autowired private final HotelSpec hotelSpec;

    public Hotel create(CreateHotelReq input) {
        Hotel newHotel = new Hotel();
        newHotel.setType(input.getType());
        newHotel.setName(input.getName());
        newHotel.setDescription(input.getDescription());
        newHotel.setPrice(input.getPrice());
        return hotelRepo.save(newHotel);
    }

    public Page<Hotel> findAll(HotelFilter filter){
        Specification<Hotel> spec = null;
        if (filter.getType() != null) {
            spec = hotelSpec.createSpecification("type", filter.getType());
        }

        if (filter.getSearchText() != null) {
            spec = hotelSpec.createLikeSpecification("description", filter.getSearchText())
                    .or(hotelSpec.createLikeSpecification("name", filter.getSearchText()));
        }

        System.out.println("Conditions: " + spec);

        Pageable pageable = PageRequest.of(filter.getPage(), filter.getSize(), Sort.by("id").descending());

        Page<Hotel> res = hotelRepo.findAll(spec, pageable);
        return res;
    }

    public Optional<Hotel> findById(Long id){
        return hotelRepo.findById(id);
    }

    public Hotel partialUpdate(Long id, UpdateHotelReq input){
        Optional<Hotel> newHotel = hotelRepo.findById(id);
        if (newHotel.isPresent()){
            Hotel newUpdate = newHotel.get();
            if (input.getName() != null){
                newUpdate.setName(input.getName());
            }
            if (input.getDescription() != null){
                newUpdate.setDescription(input.getDescription());
            }
            if (input.getPrice() != -1){
                newUpdate.setPrice(input.getPrice());
            }
            if(input.getType() != null){
                newUpdate.setType(input.getType());
            }

            return hotelRepo.save(newUpdate);
        } else {
            throw new ConflictError(ErrorCode.HOTEL_NOT_FOUND);
        }
    }

    public void delete(Long id){
        hotelRepo.deleteById(id);
    }
}
