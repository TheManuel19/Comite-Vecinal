package utez.edu.mx.ComiteVecinal.models;

import java.sql.Blob;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "comentarios")
public class Comentario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/*@NotNull
	@NotBlank
	@Size(min = 2, max = 250)*/
	@Column(nullable = false, columnDefinition = "longtext null")
	private String texto;

	/*@NotNull
	@NotBlank*/
	private String anexo;
	
	@ManyToOne
	@JoinColumn(name = "incidencia_id")
	private Incidencia incidencia;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "usuario_id")
	private Usuario usuario;

	public Comentario(Long id, String texto, String anexo, Incidencia incidencia, Usuario usuario) {
		this.id = id;
		this.texto = texto;
		this.anexo = anexo;
		this.incidencia = incidencia;
		this.usuario = usuario;
	}

	public Comentario() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public String getAnexo() {
		return anexo;
	}

	public void setAnexo(String anexo) {
		this.anexo = anexo;
	}

	public Incidencia getIncidencia() {
		return incidencia;
	}

	public void setIncidencia(Incidencia incidencia) {
		this.incidencia = incidencia;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}