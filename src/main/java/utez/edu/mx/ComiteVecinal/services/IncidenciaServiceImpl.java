package utez.edu.mx.ComiteVecinal.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utez.edu.mx.ComiteVecinal.models.Incidencia;
import utez.edu.mx.ComiteVecinal.repository.IncidenciaRepository;
import java.util.List;

@Service
public class IncidenciaServiceImpl implements IIncidenciaService {

    @Autowired
    IncidenciaRepository incidenciaRepository;

    @Override
    public List<Incidencia> listarIncidencia() {
        return incidenciaRepository.findAll();
    }

    @Override
    public Incidencia guardarIncidencia(Incidencia incidencia) {
        return incidenciaRepository.save(incidencia);
    }

    @Override
    public Incidencia mostrarIncidenciaPorId(Long id) {
        return incidenciaRepository.findById(id).get();
    }

    @Override
    public Incidencia actualizarIncidencia(Incidencia incidencia) {
        return incidenciaRepository.save(incidencia);
    }

    @Override
    public void eliminarIncidencia(Long id) {
        incidenciaRepository.deleteById(id);
    }
}
