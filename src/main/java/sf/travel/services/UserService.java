package sf.travel.services;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import sf.travel.entities.*;
import sf.travel.errors.ConflictError;
import sf.travel.errors.ErrorCode;
import sf.travel.helper.specifications.PromotionSpec;
import sf.travel.helper.specifications.UserSpec;
import sf.travel.repositories.OrderRepository;
import sf.travel.repositories.PromotionRepository;
import sf.travel.repositories.UserRespository;
import sf.travel.rests.config.PasswordConfig;
import sf.travel.rests.types.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    @Autowired private final UserRespository userRepository;
    @Autowired private final UserSpec userSpec;

    public User create(CreateUserReq input) {
        User user = new User();
//        user.setId(input.getId());
        user.setEmail(input.getEmail());
        user.setPassWord(input.getPassWord());
        user.setFullName(input.getFullName());
        user.setRole(Roles.valueOf(input.getRole()));
        return userRepository.save(user);
    }
    public Page<User> findAll(UserFilter filter){
        Specification<User> spec = null;
        if (filter.getId() != null) {
            spec = userSpec.createSpecification("id", filter.getId());
        }
        if (filter.getEmail() != null) {
            spec = userSpec.createSpecification("email", filter.getEmail());
        }
        if (filter.getPassWord() != null) {
            spec = userSpec.createSpecification("passWord", filter.getPassWord());
        }
        if (filter.getRole() != null) {
            spec = userSpec.createSpecification("value", filter.getRole());
        }


        Pageable pageable = PageRequest.of(filter.getPage(), filter.getSize(), Sort.by("id").descending());

        Page<User> res = UserRespository.findAll(spec, pageable);
        return res;
    }
    public Optional<User> findById(long id){
        return userRepository.findById(id);
    }
    public User partialUpdate(Long id, UpdateUserReq input){
        Optional<User> user = userRepository.findById(id);

        if (user.isPresent()){
            User newUser = user.get();
            if (input.getId() != null){
                newUser.setId(Long.parseLong(input.getId()));
            }
            if (input.getEmail() != null){
                newUser.setEmail(input.getEmail() );
            }

            if (input.getPassWord() != null){
                newUser.setPassWord(input.getPassWord());
            }
            if (input.getFullName() != null){
                newUser.setFullName(input.getFullName());
            }
            if (input.getRole() != null){
                newUser.setRole(input.getRole());
            }

            return userRepository.save(newUser);
        } else {
            throw new ConflictError(ErrorCode.PROMOTION_NOT_FOUND);
        }
    }

    public void delete(Long id){
        userRepository.deleteById(id);
    }


}
