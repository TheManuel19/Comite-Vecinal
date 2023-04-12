package utez.edu.mx.ComiteVecinal.models;

import java.sql.Blob;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "incidencias")
public class Incidencia {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/*@NotNull
	@NotBlank
	@Size(min = 4, max = 100)*/
	@Column(columnDefinition = "longtext not null", nullable = false)
	private String descripcion;
	
	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fechaRegistro;

	/*@NotNull
	@NotBlank*/
	@Column(nullable = false)
	private String anexo;

	/*@NotNull
	@DecimalMin(value = "0.1")
	@DecimalMax(value = "99999.9")*/
	@Column(nullable = true, name = "monto_cobro")
	private Double montoCobro;
	
	@Column(nullable = true, length = 70)
	private String estadoSolicitud;

	@Column(nullable = true)
	private Boolean esPorCobrar;

	@ManyToOne
	@JoinColumn(name = "integrante_id", nullable = false)
	private IntegranteComite integrante;

	@ManyToOne
	@JoinColumn(name = "categoria_id", nullable = false)
	private Categoria categoria;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "incidencia")
	private List<Comentario> comentarios;

	@ManyToOne
	@JoinColumn(name = "enlace_id", nullable = true)
	private Enlace enlace;

	public Incidencia() {
	}

	public Incidencia(Long id, String descripcion, Date fechaRegistro, String anexo, Double montoCobro, String estadoSolicitud, Boolean esPorCobrar, IntegranteComite integrante, Categoria categoria, List<Comentario> comentarios, Enlace enlace) {
		this.id = id;
		this.descripcion = descripcion;
		this.fechaRegistro = fechaRegistro;
		this.anexo = anexo;
		this.montoCobro = montoCobro;
		this.estadoSolicitud = estadoSolicitud;
		this.esPorCobrar = esPorCobrar;
		this.integrante = integrante;
		this.categoria = categoria;
		this.comentarios = comentarios;
		this.enlace = enlace;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public IntegranteComite getIntegrante() {
		return integrante;
	}

	public void setIntegranteComite(IntegranteComite integrante) {
		this.integrante = integrante;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public void setIntegrante(IntegranteComite integrante) {
		this.integrante = integrante;
	}

	public String getAnexo() {
		return anexo;
	}

	public void setAnexo(String anexo) {
		this.anexo = anexo;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Double getMontoCobro() {
		return montoCobro;
	}

	public void setMontoCobro(Double montoCobro) {
		this.montoCobro = montoCobro;
	}

	public String getEstadoSolicitud() {
		return estadoSolicitud;
	}

	public void setEstadoSolicitud(String estadoSolicitud) {
		this.estadoSolicitud = estadoSolicitud;
	}

	public Boolean getEsPorCobrar() {
		return esPorCobrar;
	}

	public void setEsPorCobrar(Boolean esPorCobrar) {
		this.esPorCobrar = esPorCobrar;
	}

	public List<Comentario> getComentarios() {
		return comentarios;
	}

	public void setComentarios(List<Comentario> comentarios) {
		this.comentarios = comentarios;
	}

	public Enlace getEnlace() {
		return enlace;
	}

	public void setEnlace(Enlace enlace) {
		this.enlace = enlace;
	}
}
