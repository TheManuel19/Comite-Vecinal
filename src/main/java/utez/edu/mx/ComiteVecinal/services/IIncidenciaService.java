package utez.edu.mx.ComiteVecinal.services;

import utez.edu.mx.ComiteVecinal.models.Incidencia;
import java.util.List;

public interface IIncidenciaService {

    List<Incidencia> listarIncidencia();

    Incidencia guardarIncidencia(Incidencia incidencia);

    Incidencia mostrarIncidenciaPorId(Long id);

    Incidencia actualizarIncidencia(Incidencia incidencia);

    void eliminarIncidencia(Long id);
}
