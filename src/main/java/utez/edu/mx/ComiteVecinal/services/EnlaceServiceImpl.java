package utez.edu.mx.ComiteVecinal.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utez.edu.mx.ComiteVecinal.models.Enlace;
import utez.edu.mx.ComiteVecinal.repository.EnlaceRepository;

@Service
public class EnlaceServiceImpl implements IEnlaceService {

	@Autowired
	private EnlaceRepository enlaceRepository;
	
	@Override
	public List<Enlace> listarEnlaces() {
		//verificar el findAll
		return enlaceRepository.findAll();
	}

	@Override
	public Enlace guardarEnlaces(Enlace enlace) {
		return enlaceRepository.save(enlace);
	}

	@Override
	public Enlace mostrarEnlacePorId(Long id) {
		return enlaceRepository.findById(id).get();
	}

	@Override
	public Enlace actualizarEnlace(Enlace enlace) {
		return enlaceRepository.save(enlace);
	}

	@Override
	public void eliminarEnlace(Long id) {
		enlaceRepository.deleteById(id);
	}

}
