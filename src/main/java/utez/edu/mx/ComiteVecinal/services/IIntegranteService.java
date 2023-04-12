package utez.edu.mx.ComiteVecinal.services;

import utez.edu.mx.ComiteVecinal.models.Enlace;
import utez.edu.mx.ComiteVecinal.models.IntegranteComite;

import java.util.List;

public interface IIntegranteService {

    List<IntegranteComite> listarIntegrantes();

    void guardarIntegrante(IntegranteComite integranteComite);

    IntegranteComite mostrarIntegrantePorId(Long id);

    IntegranteComite actualizarIntegrante(IntegranteComite integranteComite);

    void eliminarIntegrante(Long id);
}
