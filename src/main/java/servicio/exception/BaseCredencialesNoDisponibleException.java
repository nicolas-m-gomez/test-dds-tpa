package servicio.exception;

public class BaseCredencialesNoDisponibleException extends RuntimeException {
	public BaseCredencialesNoDisponibleException(String detalleError) {
		super(detalleError);
	}
}