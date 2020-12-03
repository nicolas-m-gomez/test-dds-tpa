package servicio.impl;

import dominio.exception.PasswordDebilException;
import servicio.ValidacionClave;

public class ValidadorClaveLargo implements ValidacionClave{ 

	private static final int LARGO_CLAVE_MINIMO = 8;
	
	public void validarClave(String usuario, String clave)
	{
		if (clave.length() < LARGO_CLAVE_MINIMO)
		{
			throw new PasswordDebilException("La password ingresada es demasiado corta");
		}
	}
}
