package utez.edu.mx.ComiteVecinal.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utez.edu.mx.ComiteVecinal.models.Municipio;
import utez.edu.mx.ComiteVecinal.repository.MunicipioRepository;

import java.util.List;

@Service
public class MunicipioServiceImpl implements IMunicipioService{

    @Autowired
    private MunicipioRepository municipioRepository;

    @Override
    public List<Municipio> listarMunicipios() {
        return municipioRepository.findAll();
    }

    @Override
    public Municipio guardarMunicipio(Municipio municipio) {
        return municipioRepository.save(municipio);
    }

    @Override
    public Municipio mostrarMunicipioPorId(Long id) {
        return municipioRepository.findById(id).get();
    }

    @Override
    public Municipio actualizarMunicipio(Municipio municipio) {
        return municipioRepository.save(municipio);
    }

    @Override
    public void eliminarMunicipio(Long id) {
        municipioRepository.deleteById(id);
    }
}
