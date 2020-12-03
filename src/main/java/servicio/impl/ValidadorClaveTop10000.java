package servicio.impl;
import java.io.IOException;

import dominio.exception.PasswordDebilException;
import servicio.RutasSistema;
import servicio.ValidacionClave;
import servicio.exception.BaseCredencialesNoDisponibleException;
import utilidades.LectorArchivos;

public class ValidadorClaveTop10000 implements ValidacionClave{
	private final String path = RutasSistema.listaTop10KClaves();
	private LectorArchivos lector = new LectorArchivos(path);

	public void validarClave(String usuario, String password) {
		try {
			  if (lector.existeEnArchivo(password))
			  {
					throw new PasswordDebilException("La password ingresada es débil");
			  }
		} catch (IOException e) {
			throw new BaseCredencialesNoDisponibleException(e.getMessage());
		}
	}
}
