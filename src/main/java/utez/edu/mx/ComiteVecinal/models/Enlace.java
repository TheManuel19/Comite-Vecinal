package utez.edu.mx.ComiteVecinal.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.List;

@Entity
@Table(name = "enlaces")
public class Enlace {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/*@NotNull
	@NotBlank
	@Size(min = 2, max = 100)
	@Pattern(regexp = "[A-Za-zÀ-ÿ '-.]*")*/
	@Column(nullable = false, name = "nombre_completo", length = 70)
	private String nombreCompleto;

	//@NotNull
	@Column(name = "numero_empleado")
	private Integer numeroEmpleado;

	@Column(nullable = false)
	private String fotografia;

	/*@Size(min = 10, max = 10)
	@Pattern(regexp = "[0-9()-]*")*/
	@Column(nullable = false)
	private String telefono;

	/*@NotNull
	@NotBlank
	@Size(min = 11, max = 50)
	@Email*/
	@Column(nullable = false, length = 50)
	private String correo;

	@ManyToOne
	@JoinColumn(name = "municipio_id", nullable = false)
	private Municipio municipio;

	@OneToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "usuario_id")
	private Usuario usuario;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "enlace")
	private List<Incidencia> incidencias;

	public Enlace(Long id, String nombreCompleto, Integer numeroEmpleado, String fotografia, String telefono, String correo, Municipio municipio, Usuario usuario, List<Incidencia> incidencias) {
		this.id = id;
		this.nombreCompleto = nombreCompleto;
		this.numeroEmpleado = numeroEmpleado;
		this.fotografia = fotografia;
		this.telefono = telefono;
		this.correo = correo;
		this.municipio = municipio;
		this.usuario = usuario;
		this.incidencias = incidencias;
	}

	public Enlace() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombreCompleto() {
		return nombreCompleto;
	}

	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}

	public Integer getNumeroEmpleado() {
		return numeroEmpleado;
	}

	public void setNumeroEmpleado(Integer numeroEmpleado) {
		this.numeroEmpleado = numeroEmpleado;
	}

	public String getFotografia() {
		return fotografia;
	}

	public void setFotografia(String fotografia) {
		this.fotografia = fotografia;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public Municipio getMunicipio() {
		return municipio;
	}

	public void setMunicipio(Municipio municipio) {
		this.municipio = municipio;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Incidencia> getIncidencias() {
		return incidencias;
	}

	public void setIncidencias(List<Incidencia> incidencias) {
		this.incidencias = incidencias;
	}

	@Override
	public String toString() {
		return "Enlace{" +
				"id=" + id +
				", nombreCompleto='" + nombreCompleto + '\'' +
				", numeroEmpleado=" + numeroEmpleado +
				", fotografia='" + fotografia + '\'' +
				", telefono='" + telefono + '\'' +
				", correo='" + correo + '\'' +
				", municipio=" + municipio +
				", usuario=" + usuario +
				'}';
	}
}
