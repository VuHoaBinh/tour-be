package sf.travel.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import sf.travel.entities.User;

public interface UserRespository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
}
