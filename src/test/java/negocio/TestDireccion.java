package negocio;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;

import servicio.ServicioUbicacionYMonedaMercadoLibre;
import servicio.exception.CodigoPostalInexistenteException;
import dominio.CreadorDireccion;
import dominio.Direccion;
import dominio.Ubicacion;

public class TestDireccion {
	Direccion direccion;
	Ubicacion ubicacionCF;
	CreadorDireccion creadorDireccion;
	ServicioUbicacionYMonedaMercadoLibre servicioMockeado;
	
	static final String codigoPostalBelgrano = "1424";
	static final String codigoPostalInvalido = "1410";
	
	@SuppressWarnings("unchecked")
	@Before
	public void init() {
		ubicacionCF = new Ubicacion("Argentina", "Capital Federal", "Capital Federal");
		servicioMockeado = mock(ServicioUbicacionYMonedaMercadoLibre.class);
		when(servicioMockeado.consultarUbicacion(codigoPostalBelgrano)).thenReturn(ubicacionCF);
		when(servicioMockeado.consultarUbicacion(codigoPostalInvalido)).thenThrow(CodigoPostalInexistenteException.class);
	}

	@Test
	public void testProvinciaValida() {
		creadorDireccion = new CreadorDireccion(codigoPostalBelgrano, servicioMockeado);
		direccion = creadorDireccion.crearDireccion();
		assertEquals("Capital Federal", direccion.getUbicacion().getProvincia());		
	}

	@Test (expected = CodigoPostalInexistenteException.class)
	public void testCodigoPostalInvalido() {
		creadorDireccion = new CreadorDireccion(codigoPostalInvalido, servicioMockeado);
		direccion = creadorDireccion.crearDireccion();		
	}
}
