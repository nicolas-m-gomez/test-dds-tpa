package servicio;

import java.util.List;

import dominio.Moneda;
import dominio.Ubicacion;

public interface ServicioUbicacionYMoneda {
	
	public Ubicacion consultarUbicacion(String codigoPostal);
	
	public List<String> obtenerIdsMonedas();
	
	public Moneda obtenerMoneda(String id);

}
