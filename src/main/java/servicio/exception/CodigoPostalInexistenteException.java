package servicio.exception;

public class CodigoPostalInexistenteException extends RuntimeException{
	public CodigoPostalInexistenteException(String mensaje) {
		super(mensaje);
	}
}
