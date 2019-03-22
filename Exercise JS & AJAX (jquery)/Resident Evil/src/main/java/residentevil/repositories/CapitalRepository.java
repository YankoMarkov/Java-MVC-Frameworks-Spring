package residentevil.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import residentevil.entities.Capital;

import java.util.List;
import java.util.Optional;

@Repository
public interface CapitalRepository extends JpaRepository<Capital, String> {

    Optional<Capital> findByName(String name);

    @Query("SELECT c FROM residentevil.entities.Capital c ORDER BY c.name")
    List<Capital> findAllOrderByName();
}
