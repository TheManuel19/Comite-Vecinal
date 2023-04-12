package utez.edu.mx.ComiteVecinal.services;

import java.util.List;

import utez.edu.mx.ComiteVecinal.models.Enlace;

public interface IEnlaceService {
	List<Enlace> listarEnlaces();

	Enlace guardarEnlaces(Enlace enlace);

	Enlace mostrarEnlacePorId(Long id);

	Enlace actualizarEnlace(Enlace enlace);

	void eliminarEnlace(Long id);
}


