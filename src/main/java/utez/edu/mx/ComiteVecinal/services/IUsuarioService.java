package utez.edu.mx.ComiteVecinal.services;

import utez.edu.mx.ComiteVecinal.models.Usuario;
import java.util.List;

public interface IUsuarioService {

    List<Usuario> listarUsuarios();
    void guardarUsuario(Usuario usuario);

    Usuario buscarPorId(Long id);

    Usuario buscarPorUsername(String username);
}
