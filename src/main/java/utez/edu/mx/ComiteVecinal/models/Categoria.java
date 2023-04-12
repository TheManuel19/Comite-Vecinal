package utez.edu.mx.ComiteVecinal.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "categorias")
public class Categoria {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/*@NotNull
	@NotBlank
	@Size(min = 4, max = 50)
	@Pattern(regexp = "[A-Za-zÀ-ÿ '-.]*")*/
	@Column(nullable = false, unique = true)
	private String nombre;

	public Categoria(Long id, String nombre) {
		this.id = id;
		this.nombre = nombre;
	}

	public Categoria(String nombre) {
		this.nombre = nombre;
	}

	public Categoria() {
		super();
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
	
}
