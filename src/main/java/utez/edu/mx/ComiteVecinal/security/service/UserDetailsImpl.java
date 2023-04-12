package utez.edu.mx.ComiteVecinal.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import utez.edu.mx.ComiteVecinal.models.Usuario;
import utez.edu.mx.ComiteVecinal.services.IUsuarioService;
@Service
public class UserDetailsImpl implements UserDetailsService {
    @Autowired
    IUsuarioService usuarioService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioService.buscarPorUsername(username);
        return UsuarioPrincipal.build(usuario);
    }
}
