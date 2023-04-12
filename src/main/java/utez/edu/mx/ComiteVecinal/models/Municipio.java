package utez.edu.mx.ComiteVecinal.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "municipios")
public class Municipio {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/*@NotNull
	@NotBlank
	@Size(min = 3, max = 100)*/
	@Column(nullable = false, unique = true)
	private String nombre;

	@OneToMany
	List<Colonia> colonias;
	
	public Municipio() {
	}

	public Municipio(Long id, String nombre, List<Colonia> colonias) {
		this.id = id;
		this.nombre = nombre;
		this.colonias = colonias;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Colonia> getColonias() {
		return colonias;
	}

	public void setColonias(List<Colonia> colonias) {
		this.colonias = colonias;
	}
}