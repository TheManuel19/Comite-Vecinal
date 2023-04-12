package utez.edu.mx.ComiteVecinal.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utez.edu.mx.ComiteVecinal.models.IntegranteComite;
import utez.edu.mx.ComiteVecinal.repository.IntegranteRepository;

import java.util.List;

@Service
public class IntegranteServiceImpl implements IIntegranteService {

    @Autowired
    IntegranteRepository integranteRepository;

    @Override
    public List<IntegranteComite> listarIntegrantes() {
        return integranteRepository.findAll();
    }

    @Override
    public void guardarIntegrante(IntegranteComite integranteComite) {
        integranteRepository.save(integranteComite);
    }

    @Override
    public IntegranteComite mostrarIntegrantePorId(Long id) {
        return integranteRepository.findById(id).get();
    }

    @Override
    public IntegranteComite actualizarIntegrante(IntegranteComite integranteComite) {
        return integranteRepository.save(integranteComite);
    }

    @Override
    public void eliminarIntegrante(Long id) {
        integranteRepository.deleteById(id);
    }
}
