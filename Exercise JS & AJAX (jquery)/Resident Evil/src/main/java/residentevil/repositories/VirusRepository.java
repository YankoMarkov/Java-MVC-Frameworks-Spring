package residentevil.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import residentevil.entities.Virus;

import java.util.Optional;

@Repository
public interface VirusRepository extends JpaRepository<Virus, String> {
}
