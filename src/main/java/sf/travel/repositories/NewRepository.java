package sf.travel.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import sf.travel.entities.New;
import sf.travel.entities.Travel;

public interface NewRepository extends JpaRepository<New, Long>, JpaSpecificationExecutor<New> {
}
