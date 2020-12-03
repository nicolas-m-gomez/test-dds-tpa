package dominio;

import servicio.ServicioUbicacionYMoneda;

public class CreadorDireccion {
	private String calle;
	private int altura;
	private String piso;
	private String codigoPostal;
	private Ubicacion ubicacion;
	
	public CreadorDireccion(String codigoPostal, ServicioUbicacionYMoneda servicio) {
		this.ubicacion = servicio.consultarUbicacion(codigoPostal);
	}
	
	public void cargarCalle(String calle) {
		this.calle = calle;
	}
	
	public void cargarAltura(int altura) {
		this.altura = altura;
	}
	
	public void cargarPiso(String piso) {
		this.piso = piso;
	}
	
	public Direccion crearDireccion() {
		return new Direccion(calle, altura, piso, codigoPostal, ubicacion);
	}
}