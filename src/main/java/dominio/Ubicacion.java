package dominio;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Ubicacion {
	
	@Column(name = "ubicacion_pais")
	private String pais;
	
	@Column(name = "ubicacion_provincia")
	private String provincia;
	
	@Column(name = "ubicacion_ciudad")
	private String ciudad;
	
	public Ubicacion() {}
	
	public Ubicacion(String pais, String provincia, String ciudad) {
		this.pais = pais;
		this.provincia = provincia;
		this.ciudad = ciudad;
	}
	
	public String getPais() {
		return pais;
	}
	public void setPais(String pais) {
		this.pais = pais;
	}
	public String getProvincia() {
		return provincia;
	}
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
	public String getCiudad() {
		return ciudad;
	}
	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}	
}
