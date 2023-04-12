package utez.edu.mx.ComiteVecinal.services;

import utez.edu.mx.ComiteVecinal.models.Colonia;
import java.util.List;

public interface IColoniaService {

    List<Colonia> listarColonias();
    Colonia guardarColonia(Colonia colonia);


    Colonia mostrarColoniaPorId(Long id);

    Colonia actualizarColonia(Colonia colonia);

    void eliminarColonia(Long id);

    List<Colonia> buscarPorMunicipio(Long id);
}
