package dominio;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.ManyToOne;
import javax.persistence.Column;
import javax.persistence.Embedded;


@Entity
@Table (name = "direcciones")
public class Direccion {
	
	@Id
	@GeneratedValue
	@Column(name = "direccion_id")
	private Integer id;
	
	@Column(name = "direccion_calle")	
	private String calle;
	
	@Column(name = "direccion_altura")
	private int altura;
	
	@Column(name = "direccion_piso")
	private String piso;
	
	@Column(name = "direccion_codigo_postal")
	private String codigoPostal;
	
	@Embedded
	private Ubicacion ubicacion;
//	private ServicioUbicacionYMoneda servicioUbicacionML;

	public Direccion() {}
	
	public Direccion(String calle, int altura, String piso, String codigoPostal, Ubicacion ubicacion) {
		this.calle = calle;
		this.altura = altura;
		this.piso = piso;
		this.codigoPostal = codigoPostal;
		this.ubicacion = ubicacion;
	}
	
//	public void obtenerUbicacion(String codigoPostal) {
//		servicioUbicacionML = new ServicioUbicacionYMonedaMercadoLibre();
//		ubicacion = new Ubicacion();
//		ubicacion = servicioUbicacionML.consultarUbicacion(codigoPostal);
//	}

	public String getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public Ubicacion getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(Ubicacion ubicacion) {
		this.ubicacion = ubicacion;
	}
	
	public String getCalle() {
		return calle;
	}

	public int getAltura() {
		return altura;
	}

	public String getPiso() {
		return piso;
	}
}