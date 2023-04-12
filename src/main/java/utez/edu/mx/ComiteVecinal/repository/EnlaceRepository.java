package utez.edu.mx.ComiteVecinal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import utez.edu.mx.ComiteVecinal.models.Enlace;

@Repository
public interface EnlaceRepository extends JpaRepository<Enlace,Long>{

}
