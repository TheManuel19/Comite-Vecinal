package utez.edu.mx.ComiteVecinal.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utez.edu.mx.ComiteVecinal.models.Comentario;
import utez.edu.mx.ComiteVecinal.repository.ComentarioRepository;

@Service
public class ComentarioServiceImpl implements IComentarioService{

    @Autowired
    private ComentarioRepository comentarioRepository;

    @Override
    public void guardarComentario(Comentario comentario) {
        comentarioRepository.save(comentario);
    }

    @Override
    public Comentario buscarPorId(Long id) {
        return comentarioRepository.findById(id).get();
    }

    @Override
    public void eliminarComentario(Long id) {
        comentarioRepository.deleteById(id);
    }
}
