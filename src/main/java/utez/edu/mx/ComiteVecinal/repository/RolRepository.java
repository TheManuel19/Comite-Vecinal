package utez.edu.mx.ComiteVecinal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import utez.edu.mx.ComiteVecinal.models.Rol;
@Repository
public interface RolRepository extends JpaRepository<Rol, Long> {

    Rol findByTipo(String tipo);
}
