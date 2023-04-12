package utez.edu.mx.ComiteVecinal.services;

import utez.edu.mx.ComiteVecinal.models.Categoria;
import java.util.List;

public interface ICategoriaService {

    List<Categoria> listarCategorias();

    Categoria guardarCategoria(Categoria categoria);

    Categoria mostrarCategoriaPorId(Long id);
    Categoria actualizarCategoria(Categoria categoria);

    void eliminarCategoria(Long id);
}
