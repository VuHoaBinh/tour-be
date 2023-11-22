package sf.travel.services;

import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import sf.travel.entities.Promotion;
import sf.travel.entities.Travel;
import sf.travel.errors.ConflictError;
import sf.travel.errors.ErrorCode;
import sf.travel.helper.specifications.TravelSpec;
import sf.travel.repositories.PromotionRepository;
import sf.travel.repositories.TravelRepository;
import sf.travel.rests.types.CreateTravelReq;
import sf.travel.rests.types.TravelFilter;
import sf.travel.rests.types.UpdateTravelReq;

@Service
@AllArgsConstructor
public class TravelService {
    @Autowired
    private static final Logger LOGGER = Logger.getLogger(TravelService.class.getName());
    private final TravelRepository travelRepo;
    private final PromotionRepository promotionRepo;
    private final TravelSpec travelSpecifications;

    public Travel create(CreateTravelReq input) {
        Travel travel = new Travel();
        travel.setName(input.getName());
        travel.setDescription(input.getDescription());
        travel.setDetail(input.getDetail());
        travel.setType(input.getType());
        travel.setCategories(input.getCategories());
        travel.setAdditionalInfo(input.getAdditionalInfo());
        travel.setMetadata(input.getMetadata());
        return travelRepo.save(travel);
    }

    public Page<Travel> findAll(TravelFilter filter) {
        Specification<Travel> spec = null;
        if (filter.getName() != null) {
            spec = travelSpecifications.createSpecification("name", filter.getName());
        }
        if (filter.getType() != null) {
            spec = travelSpecifications.createSpecification("type", filter.getType());
        }
        if (filter.getSearchText() != null) {
            spec = travelSpecifications.createLikeSpecification("description", filter.getSearchText())
                                       .or(travelSpecifications.createLikeSpecification("name",
                                                                                        filter.getSearchText()))
                                       .or(travelSpecifications.createLikeSpecification("detail",
                                                                                        filter.getSearchText()))
                                       .or(travelSpecifications.createLikeSpecification("metadata",
                                                                                        filter.getSearchText()));

        }
        System.out.println("Conditions: " + spec);

        Pageable pageable = PageRequest.of(filter.getPage(), filter.getSize(), Sort.by("id").ascending());

        Page<Travel> res = travelRepo.findAll(spec, pageable);
        LOGGER.info("condition :: " + res);
        return res;
    }

    public Optional<Travel> findById(Long id) {
        return travelRepo.findById(id);
    }

    public Travel partialUpdate(Long id, UpdateTravelReq input) {
        Optional<Travel> travel = travelRepo.findById(id);

        if (travel.isPresent()) {
            Travel newTravel = travel.get();
            if (input.getName() != null) {
                newTravel.setName(input.getName());
            }
            if (input.getDescription() != null) {
                newTravel.setDescription(input.getDescription());
            }
            if (input.getDetail() != null) {
                newTravel.setDetail(input.getDetail());
            }
            if (input.getType() != null) {
                newTravel.setType(input.getType());
            }
            if (input.getCategories() != null) {
                newTravel.setCategories(input.getCategories());
            }
            if (input.getAdditionalInfo() != null) {
                newTravel.setAdditionalInfo(input.getAdditionalInfo());
            }
            if (input.getMetadata() != null) {
                newTravel.setMetadata(input.getMetadata());
            }
            if (input.getPromotionId() != null) {
                Optional<Promotion> promotion = promotionRepo.findById(input.getPromotionId());
                if (promotion.isPresent()) {
                    newTravel.setPromotion(promotion.get());
                } else {
                    throw new ConflictError(ErrorCode.HIGHLIGHT_NOT_FOUND);
                }
            }

            return travelRepo.save(newTravel);
        } else {
            throw new ConflictError(ErrorCode.TRAVEL_NOT_FOUND);
        }
    }

    public void delete(Long id) {
        travelRepo.deleteById(id);
    }
}
