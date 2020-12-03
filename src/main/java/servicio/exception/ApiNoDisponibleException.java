package servicio.exception;

public class ApiNoDisponibleException extends RuntimeException {
	public ApiNoDisponibleException(String mensaje) {
		super(mensaje);
	}
}
