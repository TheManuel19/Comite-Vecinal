package utez.edu.mx.ComiteVecinal.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utez.edu.mx.ComiteVecinal.models.Comite;
import utez.edu.mx.ComiteVecinal.repository.ComiteRepository;

import java.util.List;

@Service
public class ComiteServiceImpl implements IComiteService{

    @Autowired
    ComiteRepository comiteRepository;

    @Override
    public List<Comite> listarComites() {
        return comiteRepository.findAll();
    }

    @Override
    public Comite guardarComite(Comite comite) {
        return comiteRepository.save(comite);
    }

    @Override
    public Comite mostrarComitePorId(Long id) {
        return comiteRepository.findById(id).get();
    }

    @Override
    public Comite actualizarComite(Comite comite) {
        return comiteRepository.save(comite);
    }

    @Override
    public void eliminarComite(Long id) {
        comiteRepository.deleteById(id);
    }
}
