package utez.edu.mx.ComiteVecinal.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utez.edu.mx.ComiteVecinal.models.Colonia;
import utez.edu.mx.ComiteVecinal.repository.ColoniaRepository;

import java.util.List;

@Service
public class ColoniaServiceImpl implements IColoniaService {

    @Autowired
    ColoniaRepository coloniaRepository;

    @Override
    public List<Colonia> listarColonias() {
        return coloniaRepository.findAll();
    }

    @Override
    public Colonia guardarColonia(Colonia colonia) {
        return coloniaRepository.save(colonia);
    }

    @Override
    public Colonia mostrarColoniaPorId(Long id) {
        return coloniaRepository.findById(id).get();
    }

    @Override
    public Colonia actualizarColonia(Colonia colonia) {
        return coloniaRepository.save(colonia);
    }

    @Override
    public void eliminarColonia(Long id) {
        coloniaRepository.deleteById(id);
    }

    @Override
    public List<Colonia> buscarPorMunicipio(Long id) {
        return coloniaRepository.findByMunicipioId(id);
    }
}
