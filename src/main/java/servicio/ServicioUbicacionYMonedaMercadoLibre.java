package servicio;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.jersey.api.client.ClientResponse;

import dominio.Moneda;
import dominio.Ubicacion;
import servicio.exception.CodigoPostalInexistenteException;

public class ServicioUbicacionYMonedaMercadoLibre implements ServicioUbicacionYMoneda {
	
	@Override
	public Ubicacion consultarUbicacion(String codigoPostal) {
		MercadoLibreApi apiMercadoLibreUbicacion = new MercadoLibreApi();
		ClientResponse responseUbicacion = apiMercadoLibreUbicacion.getProvincia(codigoPostal);
		
		String respuestaUbicacion = responseUbicacion.getEntity(String.class);
		Ubicacion ubicacion = new Ubicacion();
		
		if(responseUbicacion.getStatus() == 404) {
			throw new CodigoPostalInexistenteException("No existe una provincia para ese codigo postal");
		}else {
			JsonObject jsonObject = JsonParser.parseString(respuestaUbicacion).getAsJsonObject();
	        ubicacion.setPais(jsonObject.getAsJsonObject("country").get("name").getAsString());
	        ubicacion.setProvincia(jsonObject.getAsJsonObject("state").get("name").getAsString());
	        if (jsonObject.getAsJsonObject("city").get("name").isJsonNull()) {
	        	ubicacion.setCiudad(null);
	        }else {
	        	ubicacion.setCiudad(jsonObject.getAsJsonObject("city").get("name").getAsString());
	        }
		}
		
		return ubicacion;
	}
	
	@Override
	public List<String> obtenerIdsMonedas(){
		MercadoLibreApi apiMercadoLibreIds = new MercadoLibreApi();
		ClientResponse responseIds = apiMercadoLibreIds.getIdsMonedas();
		
		String respuestaIds = responseIds.getEntity(String.class);
		
		JsonArray array = JsonParser.parseString(respuestaIds).getAsJsonArray();
  		List<String> lista = new ArrayList<String>();
        for (int i = 0; i < array.size(); i++) {
            lista.add(array.get(i).getAsJsonObject().get("id").getAsString());
        }
		
		return lista;
	}
	
	@Override
	public Moneda obtenerMoneda(String id) {
		MercadoLibreApi apiMercadoLibreMoneda = new MercadoLibreApi();
		ClientResponse responseMoneda = apiMercadoLibreMoneda.getMoneda(id);
		
		String respuestaMoneda = responseMoneda.getEntity(String.class);
		Moneda moneda = new Moneda();
		
		JsonObject jsonObj = JsonParser.parseString(respuestaMoneda).getAsJsonObject();
		moneda.setId(id);
		moneda.setDescripcion(jsonObj.get("description").getAsString());
		moneda.setSimbolo(jsonObj.get("symbol").getAsString());
		
		return moneda;
	}
}
