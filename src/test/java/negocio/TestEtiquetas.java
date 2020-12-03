package negocio;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import dominio.Egreso;
import dominio.ReporteEgresos;

public class TestEtiquetas {
	List<String> etiquetas;
	List<Egreso> egresos;
	ReporteEgresos reporteEgresos;
	
	Egreso egreso1;
	Egreso egreso2;
	Egreso egreso3;
	
	
	@Before
	public void init() {
		
		egreso1 = mock(Egreso.class);
		egreso2 = mock(Egreso.class);
		egreso3 = mock(Egreso.class);
		
		when(egreso1.tieneEtiqueta("Hogar")).thenReturn(true);
		when(egreso1.getEtiquetas()).thenReturn(Arrays.asList("Hogar"));
		when(egreso1.precioTotal()).thenReturn(1000.00);
		
		when(egreso2.tieneEtiqueta("Amoblamiento")).thenReturn(true);
		when(egreso2.tieneEtiqueta("Indumentaria")).thenReturn(true);
		when(egreso2.getEtiquetas()).thenReturn(Arrays.asList("Amoblamiento","Indumentaria"));
		when(egreso2.precioTotal()).thenReturn(1000.00);
		
		when(egreso3.tieneEtiqueta("Indumentaria")).thenReturn(true);
		when(egreso3.getEtiquetas()).thenReturn(Arrays.asList("Indumentaria"));
		when(egreso3.precioTotal()).thenReturn(1000.00);		
		
		egresos = Arrays.asList(egreso1, egreso2, egreso3);
		
		reporteEgresos = new ReporteEgresos();
	}
	
	
	@Test
	public void testTotalPorEtiquetaIndumentaria() {
		assertEquals(Double.valueOf(2000.00), reporteEgresos.obtenerTotalesPorEtiqueta(egresos).get("Indumentaria"));
	}
	
	@Test
	public void testTotalPorEtiquetaHogar() {
		assertEquals(Double.valueOf(1000.00), reporteEgresos.obtenerTotalesPorEtiqueta(egresos).get("Hogar"));
	}
}