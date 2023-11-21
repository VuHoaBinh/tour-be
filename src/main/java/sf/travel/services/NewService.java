package sf.travel.services;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import sf.travel.entities.New;
import sf.travel.entities.Order;
import sf.travel.errors.ConflictError;
import sf.travel.errors.ErrorCode;
import sf.travel.helper.specifications.NewSpec;
import sf.travel.repositories.NewRepository;
import sf.travel.rests.types.CreateNewReq;
import sf.travel.rests.types.NewFilter;
import sf.travel.rests.types.UpdateNewReq;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class NewService {
    @Autowired private final NewRepository newRepo;
    @Autowired private final NewSpec newSpec;

    public New create(CreateNewReq input) {
        New newPage = new New();
        newPage.setName(input.getName());
        newPage.setDescription(input.getDescription());
        return newRepo.save(newPage);
    }

    public Page<New> findAll(NewFilter filter){
        Specification<New> spec = null;
        if (filter.getName() != null) {
            spec = newSpec.createSpecification("name", filter.getName());
        }

        if (filter.getSearchText() != null) {
            spec = newSpec.createLikeSpecification("description", filter.getSearchText())
                    .or(newSpec.createLikeSpecification("name", filter.getSearchText()));
        }

        System.out.println("Conditions: " + spec);

        Pageable pageable = PageRequest.of(filter.getPage(), filter.getSize(), Sort.by("id").descending());

        Page<New> res = newRepo.findAll(spec, pageable);
        return res;
    }

    public Optional<New> findById(Long id){
        return newRepo.findById(id);
    }

    public New partialUpdate(Long id, UpdateNewReq input){
        Optional<New> newPage = newRepo.findById(id);
        if (newPage.isPresent()){
            New newUpdate = newPage.get();
            if (input.getName() != null){
                newUpdate.setName(input.getName());
            }
            if (input.getDescription() != null){
                newUpdate.setDescription(input.getDescription() );
            }

            return newRepo.save(newUpdate);
        } else {
            throw new ConflictError(ErrorCode.NEW_NOT_FOUND);
        }
    }

    public void delete(Long id){
        newRepo.deleteById(id);
    }
}
