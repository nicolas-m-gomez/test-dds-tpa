package negocio;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import java.util.Arrays;

import dominio.CreadorMoneda;
import dominio.Moneda;
import servicio.ServicioUbicacionYMoneda;
import servicio.ServicioUbicacionYMonedaMercadoLibre;

public class TestMoneda {
	Moneda mockPesoArgentino;
	Moneda mockDolar;
	Moneda mockReal;
	Moneda mockEuro;
	ServicioUbicacionYMoneda servicioMockeado;
	ServicioUbicacionYMoneda servicioPosta;
	CreadorMoneda creadorMoneda;
	
	static final String pesoArgentino = "Peso argentino";
	static final String real = "Real";
	static final String euro = "Euro";
	static final String dolar = "Dólar";
	
	
	@Before
	public void init() {
		//servicioPosta = new ServicioUbicacionYMonedaMercadoLibre();
		mockPesoArgentino = mock(Moneda.class);
		mockDolar = mock(Moneda.class);
		mockReal = mock(Moneda.class);
		mockEuro = mock(Moneda.class);		
		when(mockPesoArgentino.getDescripcion()).thenReturn("Peso argentino");
		when(mockDolar.getDescripcion()).thenReturn("Dólar");
		when(mockReal.getDescripcion()).thenReturn("Real");
		when(mockEuro.getDescripcion()).thenReturn("Euro");
		servicioMockeado = mock(ServicioUbicacionYMonedaMercadoLibre.class);
		when(servicioMockeado.obtenerIdsMonedas()).thenReturn(Arrays.asList("ARS","USD","BRL","EUR"));
		when(servicioMockeado.obtenerMoneda("ARS")).thenReturn(mockPesoArgentino);
		when(servicioMockeado.obtenerMoneda("USD")).thenReturn(mockDolar);
		when(servicioMockeado.obtenerMoneda("BRL")).thenReturn(mockReal);
		when(servicioMockeado.obtenerMoneda("EUR")).thenReturn(mockEuro);												
	}
	
	@Test
	public void testCrearMonedaPesoArgentino() {
		creadorMoneda = CreadorMoneda.getCreadorMoneda(servicioMockeado);
		assertEquals(pesoArgentino, creadorMoneda.crearMoneda("ARS").getDescripcion());
	}
	
	@Test
	public void testCrearMonedaReal() {
		creadorMoneda = CreadorMoneda.getCreadorMoneda(servicioMockeado);
		assertEquals(real, creadorMoneda.crearMoneda("BRL").getDescripcion());
	}
	
	@Test
	public void testCrearMonedaEuro() {
		creadorMoneda = CreadorMoneda.getCreadorMoneda(servicioMockeado);
		assertEquals(euro, creadorMoneda.crearMoneda("EUR").getDescripcion());
	}
	
	@Test
	public void testCrearMonedaDolar() {
		creadorMoneda = CreadorMoneda.getCreadorMoneda(servicioMockeado);
		assertEquals(dolar, creadorMoneda.crearMoneda("USD").getDescripcion());
	}
}
