package sf.travel.services;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import sf.travel.entities.Promotion;
import sf.travel.errors.ConflictError;
import sf.travel.errors.ErrorCode;
import sf.travel.helper.specifications.PromotionSpec;
import sf.travel.repositories.PromotionRepository;
import sf.travel.rests.types.CreatePromotionReq;
import sf.travel.rests.types.PromotionFilter;
import sf.travel.rests.types.UpdatePromotionReq;

import java.util.Optional;

@Service
@AllArgsConstructor
public class PromotionService {
    @Autowired private final PromotionRepository promotionRepo;
    @Autowired private final PromotionSpec promotionSpec;

    public Promotion create(CreatePromotionReq input) {
        Promotion promotion = new Promotion();
        promotion.setName(input.getName());
        promotion.setDescription(input.getDescription());
        promotion.setValue(input.getValue());
        promotion.setFromDate(input.getFromDate());
        promotion.setToDate(input.getToDate());
        promotion.setStatus(input.getStatus());
        return promotionRepo.save(promotion);
    }

    public Page<Promotion> findAll(PromotionFilter filter){
        Specification<Promotion> spec = null;
        if (filter.getName() != null) {
            spec = promotionSpec.createSpecification("name", filter.getName());
        }
        if (filter.getStatus() != null) {
            spec = promotionSpec.createSpecification("status", filter.getStatus());
        }
        if (filter.getValue() != -1) {
            spec = promotionSpec.createSpecification("value", filter.getValue());
        }

        if (filter.getSearchText() != null) {
            spec = promotionSpec.createLikeSpecification("description", filter.getSearchText())
                    .or(promotionSpec.createLikeSpecification("name", filter.getSearchText()));
        }

        if(filter.getSearchDate() != null){
            spec = promotionSpec.findByTimePeriod("startDate","toDate",filter.getSearchDate());
        }

        System.out.println("Conditions: " + spec);

        Pageable pageable = PageRequest.of(filter.getPage(), filter.getSize(), Sort.by("id").descending());

        Page<Promotion> res = promotionRepo.findAll(spec, pageable);
        return res;
    }

    public Optional<Promotion> findById(Long id){
        return promotionRepo.findById(id);
    }

    public Promotion partialUpdate(Long id, UpdatePromotionReq input){
        Optional<Promotion> promotion = promotionRepo.findById(id);

        if (promotion.isPresent()){
            Promotion newPromotion = promotion.get();
            if (input.getName() != null){
                newPromotion.setName(input.getName());
            }
            if (input.getDescription() != null){
                newPromotion.setDescription(input.getDescription() );
            }
            if (input.getFromDate() != null){
                newPromotion.setFromDate(input.getFromDate());
            }
            if (input.getToDate() != null){
                newPromotion.setToDate(input.getToDate());
            }
            newPromotion.setValue(input.getValue());

            if (input.getStatus() != null){
                newPromotion.setStatus(input.getStatus());
            }

            return promotionRepo.save(newPromotion);
        } else {
            throw new ConflictError(ErrorCode.PROMOTION_NOT_FOUND);
        }
    }

    public void delete(Long id){
        promotionRepo.deleteById(id);
    }
}
