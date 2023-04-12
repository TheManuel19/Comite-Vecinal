package utez.edu.mx.ComiteVecinal.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utez.edu.mx.ComiteVecinal.models.Rol;
import utez.edu.mx.ComiteVecinal.repository.RolRepository;

import java.util.ArrayList;
import java.util.List;
@Service
public class RolServiceImpl implements IRolService{

    @Autowired
    private RolRepository rolRepository;

    @Override
    public List<Rol> listarRoles() {
        return rolRepository.findAll();
    }

    @Override
    public Rol buscarPorTipo(String tipo) {
        return rolRepository.findByTipo(tipo);
    }

    public void datosIniciales() {
        if (rolRepository.count() > 0) return;
        List<Rol> inicial = new ArrayList<>();
        inicial.add(new Rol("ROLE_ADMINISTRADOR"));
        inicial.add(new Rol("ROLE_ENLACE"));
        inicial.add(new Rol("ROLE_PRESIDENTE"));
        rolRepository.saveAll(inicial);
    }
}
