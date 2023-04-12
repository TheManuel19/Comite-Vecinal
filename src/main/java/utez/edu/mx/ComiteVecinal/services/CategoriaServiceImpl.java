package utez.edu.mx.ComiteVecinal.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utez.edu.mx.ComiteVecinal.models.Categoria;
import utez.edu.mx.ComiteVecinal.repository.CategoriaRepository;
import java.util.List;

@Service
public class CategoriaServiceImpl implements ICategoriaService{

    @Autowired
    CategoriaRepository categoriaRepository;

    @Override
    public List<Categoria> listarCategorias() {
        return categoriaRepository.findAll();
    }

    @Override
    public Categoria guardarCategoria(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    @Override
    public Categoria mostrarCategoriaPorId(Long id) {
        return categoriaRepository.findById(id).get();
    }

    @Override
    public Categoria actualizarCategoria(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    @Override
    public void eliminarCategoria(Long id) {
        categoriaRepository.deleteById(id);
    }
}
