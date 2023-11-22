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
import sf.travel.entities.Highlight;
import sf.travel.entities.New;
import sf.travel.entities.Travel;
import sf.travel.errors.ConflictError;
import sf.travel.errors.ErrorCode;
import sf.travel.helper.specifications.HighlightSpec;
import sf.travel.repositories.HighlightRepository;
import sf.travel.repositories.TravelRepository;
import sf.travel.rests.types.CreateHighlightReq;
import sf.travel.rests.types.HighlightFilter;
import sf.travel.rests.types.UpdateCustomerReq;
import sf.travel.rests.types.UpdateHighlightReq;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class HighlightService {
    @Autowired private final HighlightRepository highlightRepo;
    @Autowired private final TravelRepository travelRepo;
    @Autowired private final HighlightSpec highlightSpec;


    public Highlight create(CreateHighlightReq input){
        Highlight highlight = new Highlight();

        highlight.setRank(input.getRank());
        highlight.setToDate(input.getToDate());
        highlight.setFromDate(input.getFromDate());
        highlight.setStatus(input.getStatus());
        if (input.getTravelId() != null){
            Optional<Travel> travel = travelRepo.findById(input.getTravelId());
            if(travel.isPresent()){
                highlight.setTravel(travel.get());
            } else {
                throw new ConflictError(ErrorCode.HIGHLIGHT_NOT_FOUND);
            }
        }

        return highlightRepo.save(highlight);
    }

    public Page<Highlight> findAll(HighlightFilter filter){
        Specification<Highlight> spec = null;
        if (filter.getRank() != null) {
            spec = highlightSpec.createSpecification("rank", filter.getRank());
        }
        if (filter.getStatus() != null) {
            spec = highlightSpec.createSpecification("status", filter.getStatus());
        }
        if(filter.getSearchDate() != null){
            spec = highlightSpec.findByTimePeriod("startDate","toDate",filter.getSearchDate());
        }

        System.out.println("Conditions: " + spec);

        Pageable pageable = PageRequest.of(filter.getPage(), filter.getSize(), Sort.by("id").descending());

        Page<Highlight> res = highlightRepo.findAll(spec, pageable);
        return res;
    }

    public Optional<Highlight> findById(Long id){
        return highlightRepo.findById(id);
    }

    public Highlight partialUpdate(Long id, UpdateHighlightReq input){
        Optional<Highlight> highlight = highlightRepo.findById(id);

        if (highlight.isPresent()){
            Highlight newHighlight = highlight.get();
            if (input.getRank() != null){
                newHighlight.setRank(input.getRank());
            }
            if (input.getStatus() != null){
                newHighlight.setStatus(input.getStatus());
            }
            if (input.getFromDate() != null){
                newHighlight.setFromDate(input.getFromDate());
            }
            if (input.getToDate() != null){
                newHighlight.setToDate(input.getToDate());
            }
            if (input.getTravelId() != null){
                Optional<Travel> travel = travelRepo.findById(input.getTravelId());
                if(travel.isPresent()){
                    newHighlight.setTravel(travel.get());
                } else {
                    throw new ConflictError(ErrorCode.TRAVEL_NOT_FOUND);
                }
            }

            return highlightRepo.save(newHighlight);
        } else {
            throw new ConflictError(ErrorCode.HIGHLIGHT_NOT_FOUND);
        }
    }

    public void delete(Long id){
        highlightRepo.deleteById(id);
    }
}
