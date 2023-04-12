package utez.edu.mx.ComiteVecinal.services;

import utez.edu.mx.ComiteVecinal.models.Comite;
import java.util.List;

public interface IComiteService {

    List<Comite> listarComites();

    Comite guardarComite(Comite comite);

    Comite mostrarComitePorId(Long id);

    Comite actualizarComite(Comite comite);

    void eliminarComite(Long id);
}
