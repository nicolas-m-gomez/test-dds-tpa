package servicio;

import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import servicio.exception.ApiNoDisponibleException;

public class MercadoLibreApi {
	WebResource webResource = Client.create().resource("https://api.mercadolibre.com/");
	
	public ClientResponse getProvincia(String codigoPostal) {
		try { ClientResponse response = webResource
			    .path("countries/AR/zip_codes/"+codigoPostal)
			    .accept(MediaType.APPLICATION_JSON)
			    .get(ClientResponse.class); 
		
			return response; 
		}
		catch (ClientHandlerException ex) {
			throw new ApiNoDisponibleException("Api MercadoLibre no se encuentra disponible");
		}
		
	}
	
	public ClientResponse getIdsMonedas() {
		try { ClientResponse response = webResource
			    .path("currencies")
			    .accept(MediaType.APPLICATION_JSON)
			    .get(ClientResponse.class); 
		
			return response; 
		}
		catch (ClientHandlerException ex) {
			throw new ApiNoDisponibleException("Api MercadoLibre no se encuentra disponible");
		}
	}
	
	public ClientResponse getMoneda(String id) {
		try { ClientResponse response = webResource
			    .path("currencies/"+id)
			    .accept(MediaType.APPLICATION_JSON)
			    .get(ClientResponse.class); 
		
			return response; 
		}
		catch (ClientHandlerException ex) {
			throw new ApiNoDisponibleException("Api MercadoLibre no se encuentra disponible");
		}
		
	}
}
