package utez.edu.mx.ComiteVecinal.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "integrantes")
public class IntegranteComite {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/*@NotNull
	@NotBlank
	@Size(min = 4, max = 50)
	@Pattern(regexp = "[A-Za-zÀ-ÿ '-.]*")*/
	@Column(nullable = false)
	private String nombre;

	/*@Size(min = 10, max = 10)
	@Pattern(regexp = "[0-9()-]*")*/
	@Column(nullable = false)
	private String telefono;

	/*@NotNull
	@NotBlank*/
	@Column(nullable = true, length = 255)
	private String imagen;
	
	@Column(name = "validacion_presidente",nullable = false)
	private boolean validacionPresidente;
	
	@ManyToOne
	@JoinColumn(name = "comite_id", nullable = false)
	private Comite comite;
	@OneToOne(optional = true)
	@JoinColumn(name = "usuario_id")
	private Usuario username;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "integrante")
	private List<Incidencia> incidencias;

	public IntegranteComite(Long id, String nombre, String telefono, String imagen, boolean validacionPresidente, Comite comite, Usuario username, List<Incidencia> incidencias) {
		this.id = id;
		this.nombre = nombre;
		this.telefono = telefono;
		this.imagen = imagen;
		this.validacionPresidente = validacionPresidente;
		this.comite = comite;
		this.username = username;
		this.incidencias = incidencias;
	}

	public IntegranteComite() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long idIntegrante) {
		this.id = idIntegrante;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public boolean isValidacionPresidente() {
		return validacionPresidente;
	}

	public void setValidacionPresidente(boolean validacionPresidente) {
		this.validacionPresidente = validacionPresidente;
	}

	public Comite getComite() {
		return comite;
	}

	public void setComite(Comite comite) {
		this.comite = comite;
	}

	public Usuario getUsername() {
		return username;
	}

	public void setUsername(Usuario username) {
		this.username = username;
	}

	public List<Incidencia> getIncidencias() {
		return incidencias;
	}

	public void setIncidencias(List<Incidencia> incidencias) {
		this.incidencias = incidencias;
	}

	@Override
	public String toString() {
		return "nombre='" + nombre + '\'' +
				", es Presidente=" + validacionPresidente;
	}
}
