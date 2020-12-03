package servicio;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import dominio.CreadorUsuario;
import dominio.TipoUsuario;
import dominio.Usuario;
import dominio.exception.PasswordDebilException;
import servicio.impl.ValidadorClave;
import utilidades.LectorArchivos;

public class TestContrasenia {
	CreadorUsuario creadorUsuario;
	
	@Before
	public void init() {
		creadorUsuario = new CreadorUsuario();
	}
	
	@Test (expected = PasswordDebilException.class)
	public void claveTieneSecuenciaDeLetrasyNumeros() {
		creadorUsuario.nuevoUsuario("uPassSecuenciaMixta", "abc12345", TipoUsuario.ESTANDAR);
	}
	
	@Test (expected = PasswordDebilException.class)
	public void claveTieneSecuenciaDeLetras() {
		creadorUsuario.nuevoUsuario("uPassSecuenciaLetras", "bcdefghij", TipoUsuario.ESTANDAR);
	}
	
	@Test (expected = PasswordDebilException.class)
	public void claveTieneSecuenciaDeNumeros() {
		creadorUsuario.nuevoUsuario("uPassSecuenciaNumeros", "23456789", TipoUsuario.ESTANDAR);
	}
	
	@Test (expected = PasswordDebilException.class)
	public void claveDeCaracteresRepetidos() {
		creadorUsuario.nuevoUsuario("uPassrepeticion", "bbbbbbbb", TipoUsuario.ESTANDAR);
	}
	
	@Test (expected = PasswordDebilException.class)
	public void claveTienePalabraProhibida() {
		creadorUsuario.nuevoUsuario("uPassPalabraProhibida", "GestionProyectosSociales", TipoUsuario.ESTANDAR);
	}
	
	@Test (expected = PasswordDebilException.class)
	public void claveMasCortaQueLoPermitido() {
		creadorUsuario.nuevoUsuario("uClaveCorta", "clave", TipoUsuario.ESTANDAR);
	}
	
	@Test (expected = PasswordDebilException.class)
	public void claveIgualAlUsuario() {
		creadorUsuario.nuevoUsuario("uClaveIgualUsuario", "uClaveIgualUsuario", TipoUsuario.ESTANDAR);
	}
	
	@Test (expected = PasswordDebilException.class)
	public void testPasswordEstaEnTop10000() {
		creadorUsuario.nuevoUsuario("uClaveTop10000", "password", TipoUsuario.ESTANDAR);
	}
	
	@Test
	public void claveCorrecta() {
		Usuario usuarioCorrecto = creadorUsuario.nuevoUsuario("uCorrecta", "contrasenia1", TipoUsuario.ADMINISTRADOR);
		assertTrue(usuarioCorrecto.tipoUsuario() == TipoUsuario.ADMINISTRADOR);
	}
	

}
