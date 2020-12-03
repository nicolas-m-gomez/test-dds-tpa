package servicio.impl;

import java.io.IOException;

import dominio.exception.PasswordDebilException;
import servicio.RutasSistema;
import servicio.ValidacionClave;
import servicio.exception.BaseCredencialesNoDisponibleException;
import utilidades.LectorArchivos;

public class ValidadorClaveListaNegra implements ValidacionClave{
private final String path = RutasSistema.listaNegraClaves();
private LectorArchivos lector = new LectorArchivos(path);


	public void validarClave(String usuario, String clave)
	{
			validarClaveDistintaUsuario(usuario,clave);
			validarClaveDistintaPalabraProhibida(clave);
	}
		
	private void validarClaveDistintaUsuario(String usuario, String clave)
	{
		if (usuario == clave)
		{
			throw new PasswordDebilException("La password es igual al usuario ingresado");
		}
	}
		
	private void validarClaveDistintaPalabraProhibida(String clave)
	{
		try {
			if (lector.existeEnArchivo(clave))
				{
					throw new PasswordDebilException("La password ingresada no está permitida");
				}
		} catch (IOException e) {
			throw new BaseCredencialesNoDisponibleException(e.getMessage());
	}

		}
}
