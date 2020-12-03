package negocio;
import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

import dominio.DocumentoComercial;
import dominio.Egreso;
import dominio.Importe;
import dominio.ItemEgreso;
import dominio.ItemPresupuesto;
import dominio.MedioPago;
import dominio.Moneda;
import dominio.Presupuestador;
import dominio.Proveedor;
import dominio.TipoDocumento;

public class TestPresupuesto {
	Egreso nuevaCompra;
	ItemEgreso silla;
	ItemPresupuesto sillaPresupuesto;
	Presupuestador nuevoPresupuesto;

	@Before
	public void init() {
		nuevoPresupuesto = new Presupuestador();
		nuevaCompra = new Egreso(new Proveedor(), LocalDate.of(2020,05,15), new MedioPago(),true);
		silla = new ItemEgreso("silla",5,new Importe(new BigDecimal ("23000"),new Moneda()));
		nuevaCompra.cargarItem(silla);
		sillaPresupuesto = new ItemPresupuesto("silla",5,new Importe(new BigDecimal ("23000"),new Moneda()));
	}


	@Test
	public void presupuestoAsociadoAUnEgreso() {
		nuevoPresupuesto.cargarDocumentoComercial(new DocumentoComercial("codigo",TipoDocumento.FACTURA));
		nuevoPresupuesto.cargarProveedor(new Proveedor());						
		nuevoPresupuesto.cargarItemPresupuesto(sillaPresupuesto);
		nuevoPresupuesto.generarPresupuesto(nuevaCompra);
		assertEquals(1,nuevaCompra.cantidadPresupuestosAsociados(),0.00);
	}
}
