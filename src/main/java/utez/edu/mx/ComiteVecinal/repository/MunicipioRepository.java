package utez.edu.mx.ComiteVecinal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import utez.edu.mx.ComiteVecinal.models.Municipio;

@Repository
public interface MunicipioRepository extends JpaRepository<Municipio,Long> {
}
