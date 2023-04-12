package utez.edu.mx.ComiteVecinal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import utez.edu.mx.ComiteVecinal.models.Colonia;

import java.util.List;

@Repository
public interface ColoniaRepository extends JpaRepository<Colonia,Long> {

    List<Colonia> findByMunicipioId(Long id);
}
