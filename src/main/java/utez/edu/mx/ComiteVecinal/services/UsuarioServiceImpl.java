package utez.edu.mx.ComiteVecinal.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import utez.edu.mx.ComiteVecinal.models.Rol;
import utez.edu.mx.ComiteVecinal.models.Usuario;
import utez.edu.mx.ComiteVecinal.repository.RolRepository;
import utez.edu.mx.ComiteVecinal.repository.UsuarioRepository;

import java.util.List;
import java.util.Set;

@Service
public class UsuarioServiceImpl implements IUsuarioService{

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    @Override
    public void guardarUsuario(Usuario usuario) {
        usuarioRepository.save(usuario);

    }

    @Override
    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id).get();
    }

    @Override
    public Usuario buscarPorUsername(String username) {
        return usuarioRepository.findByUserName(username);
    }

    public void datosIniciales() {
        if (usuarioRepository.count() > 0) return;
        Rol admin = rolRepository.findByTipo("ROLE_ADMINISTRADOR");
            var superadmin = new Usuario("admin", passwordEncoder.encode("admin"), Set.of(admin));
            usuarioRepository.save(superadmin);
    }
}
