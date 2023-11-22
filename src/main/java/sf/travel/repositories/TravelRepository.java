package sf.travel.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import sf.travel.entities.Travel;

public interface TravelRepository extends JpaRepository<Travel, Long> , JpaSpecificationExecutor<Travel> {
}
