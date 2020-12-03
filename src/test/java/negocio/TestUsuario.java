package negocio;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;

import dominio.CreadorUsuario;
import dominio.Egreso;
import dominio.MedioPago;
import dominio.Notificacion;
import dominio.Proveedor;
import dominio.TipoUsuario;
import dominio.Usuario;
import dominio.exception.PasswordDebilException;

public class TestUsuario {
	Usuario admin;
	Usuario estandar;
	Usuario usuarioPassDebil;
	Usuario usuarioRevisor;
	CreadorUsuario creadorUsuario;
	Egreso egreso1;
	Egreso egreso2;
	Egreso egreso3;
	Notificacion notificacion1;
	Notificacion notificacion2;
	Notificacion notificacion3;
	Notificacion notificacion4;

	@Before
	public void init() {
		//Entrega 1
		admin = new Usuario("admin", "DdsUtn2020", TipoUsuario.ADMINISTRADOR);
		estandar = new Usuario("user1", "user12020", TipoUsuario.ESTANDAR);
		creadorUsuario = new CreadorUsuario();
		//Entrga 2
		usuarioRevisor = creadorUsuario.nuevoUsuario("revisor", "StrongPass2315", TipoUsuario.ESTANDAR);
		egreso1 = new Egreso(new Proveedor(), LocalDate.of(2020,05,15), new MedioPago(),true);
		egreso2 = new Egreso(new Proveedor(), LocalDate.of(2020,05,16), new MedioPago(),true);
		egreso3 = new Egreso(new Proveedor(), LocalDate.of(2020,05,17), new MedioPago(),true);
		notificacion1 = new Notificacion(egreso1);
		notificacion1.agregarMensaje("Se ha APROBADO el egreso");

		notificacion2 = new Notificacion(egreso2);
		notificacion2.agregarMensaje("Se ha APROBADO el egreso");

		notificacion3 = new Notificacion(egreso3);
		notificacion3.agregarMensaje("Se ha APROBADO el egreso");

		notificacion4 = new Notificacion(egreso1);
		notificacion4.agregarMensaje("Egreso DESAPROBADO");

		usuarioRevisor.recibirNotificacion(notificacion1);
		usuarioRevisor.recibirNotificacion(notificacion2);
		usuarioRevisor.recibirNotificacion(notificacion3);
		usuarioRevisor.recibirNotificacion(notificacion4);
	}

	@Test
	public void testEsUsuarioAdmin() {
		assertTrue(admin.tipoUsuario() == TipoUsuario.ADMINISTRADOR);
	}

	@Test
	public void testEsUsuarioEstandar() {
		assertTrue(estandar.tipoUsuario() == TipoUsuario.ESTANDAR);
	}

	@Test (expected = PasswordDebilException.class)
	public void testValidacionPassDebil() {
		creadorUsuario.nuevoUsuario("usuTest", "1234", TipoUsuario.ESTANDAR);
	}

	@Test
	public void testObtenerNotificacionEgreso1MasReciente() {
		assertEquals(notificacion2, usuarioRevisor.verNotificacionRecienteDe(egreso2));
	}

}
