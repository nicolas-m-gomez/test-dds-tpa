package negocio;

import static org.junit.Assert.assertEquals;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

import dominio.Egreso;
import dominio.Importe;
import dominio.ItemEgreso;
import dominio.ItemPresupuesto;
import dominio.MedioPago;
import dominio.Moneda;
import dominio.Proveedor;

public class TestEgreso {
	Egreso nuevaCompra;
	ItemEgreso computadora;
	ItemEgreso impresora;
	ItemEgreso silla;

	@Before
	public void init() {
		nuevaCompra = new Egreso(new Proveedor(), LocalDate.of(2020,05,15), new MedioPago(),true);
		computadora = new ItemEgreso("computadora",2, new Importe(new BigDecimal ("2000"),new Moneda()));
		impresora = new ItemEgreso("impresora", 3,new Importe(new BigDecimal ("50000"), new Moneda()));
		silla = new ItemEgreso("silla",5,new Importe(new BigDecimal ("23000"), new Moneda()));
	}

	@Test
	public void seCargaronItemsDeCompra() {
		nuevaCompra.cargarItem(computadora);
		nuevaCompra.cargarItem(impresora);
		nuevaCompra.cargarItem(silla);
		assertTrue(nuevaCompra.getItems().contains(computadora));
		assertTrue(nuevaCompra.getItems().contains(impresora));
		assertTrue(nuevaCompra.getItems().contains(silla));
	}

	@Test
	public void precioTotalEsElEsperado() {
		nuevaCompra.cargarItem(computadora);
		nuevaCompra.cargarItem(impresora);
		nuevaCompra.cargarItem(silla);
		assertEquals(269000.0,nuevaCompra.precioTotal(),0.00);
		//assertEquals(valor_esperado,valor_existente,diferencia)
	}

}
