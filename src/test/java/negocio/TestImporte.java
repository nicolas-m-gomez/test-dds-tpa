package negocio;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import dominio.Importe;

public class TestImporte {
	Importe importe;

	@Before
	public void init() {
		importe = new Importe();
		//importe.cargarIdsMonedas();
	}
	
//	@Test
//	public void testMonedaArgentina() {
//		importe.pesoArgentino();
//		assertEquals("Peso argentino",importe.getMoneda().getDescripcion());
//		assertEquals("$",importe.getMoneda().getSimbolo());
//		assertEquals("ARS",importe.getMoneda().getId());
//	}
//	
//	@Test
//	public void testMonedaReal() {
//		importe.real();
//		assertEquals("Real",importe.getMoneda().getDescripcion());
//	}
//	
//	@Test
//	public void testMonedaEuro() {
//		importe.euro();
//		assertEquals("Euro",importe.getMoneda().getDescripcion());
//	}
//	
//	@Test
//	public void testMonedaDolar() {
//		importe.dolar();
//		assertEquals("Dólar",importe.getMoneda().getDescripcion());
//	}

}
