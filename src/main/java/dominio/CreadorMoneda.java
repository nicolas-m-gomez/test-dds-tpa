package dominio;

import java.util.HashMap;

import servicio.ServicioUbicacionYMoneda;

public class CreadorMoneda {
	private HashMap<String, Moneda> monedas;
	private ServicioUbicacionYMoneda servicio;
	private static CreadorMoneda creadorMoneda;
	
	private CreadorMoneda(ServicioUbicacionYMoneda servicio) {
		this.servicio = servicio;
		this.monedas = new HashMap<String, Moneda>();
		this.obtenerMonedasIds();
	}
	
	public static CreadorMoneda getCreadorMoneda(ServicioUbicacionYMoneda servicio) {
		if(creadorMoneda == null) {
			creadorMoneda = new CreadorMoneda(servicio);
		}			
		return creadorMoneda;
	}
	
	private void obtenerMonedasIds() {
		servicio.obtenerIdsMonedas().stream().forEach(id -> monedas.put(id, null));;

	}
	
	public Moneda crearMoneda(String monedaId) {
		Moneda moneda = monedas.get(monedaId);
		if(moneda == null) {
			moneda = servicio.obtenerMoneda(monedaId);
			monedas.put(monedaId, moneda);
		}
		return moneda;			
	}
}