package dominio.exception;

public class EgresoInvalidoException extends RuntimeException {
	public EgresoInvalidoException(String mensaje) {
		super(mensaje);
	};
}
