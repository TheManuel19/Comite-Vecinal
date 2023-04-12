package utez.edu.mx.ComiteVecinal.services;

import utez.edu.mx.ComiteVecinal.models.Comentario;

public interface IComentarioService {

    void guardarComentario(Comentario comentario);
    Comentario buscarPorId(Long id);
    void eliminarComentario(Long id);
}
