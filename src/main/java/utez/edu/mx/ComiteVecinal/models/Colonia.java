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
@Table(name = "colonias")
public class Colonia {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/*@NotNull
	@NotBlank
	@Size(min = 4, max = 70)
	@Pattern(regexp = "[A-Za-zÀ-ÿ '-.]*")*/
	@Column(nullable = false, unique = true)
	private String nombre;

	/*@NotNull
	@NotBlank
	@Size(min = 5, max = 5)
	@Pattern(regexp = "[0-9]*")*/
	@Column(name = "codigo_postal", nullable = false)
	private String codigoPostal;

	@ManyToOne
	@JoinColumn(name = "municipio_id", nullable = false)
	private Municipio municipio;

	public Colonia(Long id, String nombre, String codigoPostal, Municipio municipio) {
		this.id = id;
		this.nombre = nombre;
		this.codigoPostal = codigoPostal;
		this.municipio = municipio;
	}

	public Colonia() {
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Municipio getMunicipio() {
		return municipio;
	}

	public void setMunicipio(Municipio municipio) {
		this.municipio = municipio;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}
}
