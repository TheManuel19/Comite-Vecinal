package utez.edu.mx.ComiteVecinal.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "comites")
public class Comite {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "municipio_id", nullable = false)
	private Municipio municipio;

	@ManyToOne
	@JoinColumn(name = "colonias_id", nullable = false)
	private Colonia colonias;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "comite")
	private List<IntegranteComite> integrantes;

	public Comite(Long id, Municipio municipio, Colonia colonias, List<IntegranteComite> integrantes) {
		this.id = id;
		this.municipio = municipio;
		this.colonias = colonias;
		this.integrantes = integrantes;
	}

	public Comite() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Colonia getColonias() {
		return colonias;
	}

	public void setColonias(Colonia colonias) {
		this.colonias = colonias;
	}

	public Municipio getMunicipio() {
		return municipio;
	}

	public void setMunicipio(Municipio municipio) {
		this.municipio = municipio;
	}

	public List<IntegranteComite> getIntegrantes() {
		return integrantes;
	}

	public void setIntegrantes(List<IntegranteComite> integrantes) {
		this.integrantes = integrantes;
	}
}
