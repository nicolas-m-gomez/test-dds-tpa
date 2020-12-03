package negocio;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;

import dominio.CreadorUsuario;
import dominio.Egreso;
import dominio.EstadoEgreso;
import dominio.MedioPago;
import dominio.Notificacion;
import dominio.Proveedor;
import dominio.TipoUsuario;
import dominio.Usuario;

public class TestListadoRevisores {
	CreadorUsuario creadorUsuario;
	ArrayList<Usuario> usuarios;
	Egreso egreso;
	Usuario revisor1;
	Usuario revisor2;
	Usuario revisor3;
	Usuario revisor4;
	Notificacion notificacion;

	@Before
	public void init() {
		creadorUsuario = new CreadorUsuario();
		revisor1 = creadorUsuario.nuevoUsuario("revisor1", "StrongPass01", TipoUsuario.ESTANDAR);
		revisor2 = creadorUsuario.nuevoUsuario("revisor2", "StrongPass02", TipoUsuario.ESTANDAR);
		revisor3 = creadorUsuario.nuevoUsuario("revisor3", "StrongPass03", TipoUsuario.ESTANDAR);
		revisor4 = creadorUsuario.nuevoUsuario("revisor4", "StrongPass04", TipoUsuario.ESTANDAR);
		usuarios = new ArrayList<Usuario>(Arrays.asList(revisor1, revisor2, revisor3));		
		egreso = new Egreso(new Proveedor(), LocalDate.of(2020,05,15), new MedioPago(),true);
		egreso.setListadoRevisores(usuarios);
		notificacion = new Notificacion(egreso);
		notificacion.agregarMensaje("Se ha APROBADO el egreso");
	}

	@Test
	public void testRecepcionNotificacionValidezDeEgreso() {
		egreso.cambiarEstado(EstadoEgreso.APROBADO, notificacion);
		assertEquals(notificacion, revisor1.verNotificacionRecienteDe(egreso));
		assertEquals(notificacion, revisor2.verNotificacionRecienteDe(egreso));
		assertEquals(notificacion, revisor3.verNotificacionRecienteDe(egreso));
	}

	@Test
	public void testAltaRevisor() {
		egreso.altaRevisor(revisor4);
		egreso.cambiarEstado(EstadoEgreso.APROBADO, notificacion);
		assertEquals(notificacion, revisor4.verNotificacionRecienteDe(egreso));
	}

	@Test(expected = NoSuchElementException.class)
	public void testBajaRevisor() {
		egreso.bajaRevisor(revisor3);
		egreso.cambiarEstado(EstadoEgreso.APROBADO, notificacion);
		revisor3.verNotificacionRecienteDe(egreso);
	}
}
