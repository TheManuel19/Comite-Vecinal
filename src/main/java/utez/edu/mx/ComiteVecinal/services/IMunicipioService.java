package utez.edu.mx.ComiteVecinal.services;

import utez.edu.mx.ComiteVecinal.models.Enlace;
import utez.edu.mx.ComiteVecinal.models.Municipio;
import java.util.List;

public interface IMunicipioService {

    List<Municipio> listarMunicipios();
    Municipio guardarMunicipio(Municipio municipio);

    Municipio mostrarMunicipioPorId(Long id);
    Municipio actualizarMunicipio(Municipio municipio);

    void eliminarMunicipio(Long id);
}
