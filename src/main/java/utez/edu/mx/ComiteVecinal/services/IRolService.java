package utez.edu.mx.ComiteVecinal.services;

import utez.edu.mx.ComiteVecinal.models.Rol;

import java.util.List;

public interface IRolService {

    List<Rol> listarRoles();

    Rol buscarPorTipo(String tipo);

}
