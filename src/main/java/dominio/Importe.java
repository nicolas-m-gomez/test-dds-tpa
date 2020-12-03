package dominio;

import java.math.BigDecimal;

import javax.persistence.Column;
//import java.util.List;
//
//import servicio.ServicioUbicacionYMoneda;
//import servicio.ServicioUbicacionYMonedaMercadoLibre;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;

@Entity
@Table (name = "Importes")
public class Importe {
	
	@Id
	@GeneratedValue
	@Column(name = "importe_id")
	private Integer id;
	
	@Column(name = "precio_unitario")
	private BigDecimal precioUnitario;
//private List<String> idsMonedas;
//private ServicioUbicacionYMoneda servicioMonedaML;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "moneda_id"/*, nullable = false*/)
	private Moneda moneda;
	
	public Importe() {}
	
	public Importe(BigDecimal precioUnitario, Moneda moneda) {
		this.precioUnitario = precioUnitario;
		this.moneda = moneda;
	}

	public Double getPrecioUnitario() {
		return precioUnitario.doubleValue();
	}

//	public void cargarIdsMonedas() {
//		servicioMonedaML = new ServicioUbicacionYMonedaMercadoLibre();
//		setIdsMonedas(servicioMonedaML.obtenerIdsMonedas());
//	}
	
//	public void pesoArgentino() {
//		servicioMonedaML = new ServicioUbicacionYMonedaMercadoLibre();
//		moneda = new Moneda();
//		moneda = servicioMonedaML.obtenerMoneda(sacarIdLista("ARS"));
//	}
//	
//	public void real() {
//		servicioMonedaML = new ServicioUbicacionYMonedaMercadoLibre();
//		moneda = new Moneda();
//		moneda = servicioMonedaML.obtenerMoneda(sacarIdLista("BRL"));
//	}
//	
//	public void euro() {
//		servicioMonedaML = new ServicioUbicacionYMonedaMercadoLibre();
//		moneda = new Moneda();
//		moneda = servicioMonedaML.obtenerMoneda(sacarIdLista("EUR"));
//	}
//	
//	public void dolar() {
//		servicioMonedaML = new ServicioUbicacionYMonedaMercadoLibre();
//		moneda = new Moneda();
//		moneda = servicioMonedaML.obtenerMoneda(sacarIdLista("USD"));
//	}

//	public List<String> getIdsMonedas() {
//		return idsMonedas;
//	}
//
//	public void setIdsMonedas(List<String> idsMonedas) {
//		this.idsMonedas = idsMonedas;
//	}

	public Moneda getMoneda() {
		return moneda;
	}

	public void setMoneda(Moneda moneda) {
		this.moneda = moneda;
	}
	
//	public String sacarIdLista(String id) {
//		for(String s : idsMonedas) {
//     	   if(s.equals(id)) {
//     	      id = s;
//     	   }
//		}
//		return id;
//	}
}
