package utez.edu.mx.ComiteVecinal.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import utez.edu.mx.ComiteVecinal.services.RolServiceImpl;
import utez.edu.mx.ComiteVecinal.services.UsuarioServiceImpl;

@Configuration
public class datosIniciales implements CommandLineRunner {

    @Autowired private RolServiceImpl rolService;

    @Autowired private UsuarioServiceImpl usuarioService;


    @Override
    public void run(String... args) throws Exception {
        rolService.datosIniciales();
        usuarioService.datosIniciales();
    }
}
