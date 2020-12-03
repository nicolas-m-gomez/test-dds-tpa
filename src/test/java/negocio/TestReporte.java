package negocio;

import static org.junit.Assert.assertTrue;


import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

import dominio.Categoria;
import dominio.CategoriaReglaNegocio;
import dominio.Egreso;
import dominio.EntidadBase;
import dominio.EntidadJuridica;
import dominio.Importe;
import dominio.ItemEgreso;
import dominio.ItemPresupuesto;
import dominio.MedioPago;
import dominio.Moneda;
import dominio.Proveedor;
import dominio.ReglaNegocio;
import dominio.ValidarMontoEgreso;

public class TestReporte {
	Egreso hogarCompra;
	Egreso indumentariaCompra;
	ItemEgreso computadora;
	ItemEgreso impresora;
	ItemEgreso silla;
	EntidadJuridica entidadJuridica = new EntidadJuridica();
	EntidadBase entidadBase =  new EntidadBase();
	Categoria categoriaJuridica = new Categoria("Judicial");
	Categoria categoriaBase = new Categoria("ONG");
	ReglaNegocio regla = new ValidarMontoEgreso(CategoriaReglaNegocio.VALIDAR_MONTO_EGRESO);


	@Before
	public void init() {
		hogarCompra = new Egreso(new Proveedor(), LocalDate.of(2020,05,15), new MedioPago(),true);
		indumentariaCompra = new Egreso(new Proveedor(), LocalDate.of(2020,05,15), new MedioPago(),true);

		computadora = new ItemEgreso("computadora",2, new Importe(new BigDecimal ("2000"),new Moneda()));
		impresora = new ItemEgreso("impresora", 3,new Importe(new BigDecimal ("50000"), new Moneda()));
		silla = new ItemEgreso("silla",5,new Importe(new BigDecimal ("23000"), new Moneda()));
		
		
		entidadBase.setNombreFicticio("NombreFicticio1");
		entidadJuridica.setNombreFicticio("NombreFicticio2");

		hogarCompra.cargarItem(computadora);
		hogarCompra.cargarItem(impresora);
		hogarCompra.cargarItem(silla);
		hogarCompra.agregarEtiqueta("Hogar");

		indumentariaCompra.cargarItem(silla);
		indumentariaCompra.cargarItem(silla);
		indumentariaCompra.agregarEtiqueta("Indumentaria");
		
		categoriaJuridica.agregarRegla(regla);
		categoriaBase.agregarRegla(regla);

		entidadJuridica.setCategoria(categoriaJuridica);

		entidadBase.setCategoria(categoriaBase);
	}

	@Test
	public void seAgregaEgresoAEntidadJuridica() {
		entidadJuridica.setMontoMaximoEgreso(500000);
		entidadJuridica.cargarEgreso(hogarCompra);
		entidadJuridica.generarReporte();
		assertTrue(entidadJuridica.getEgresos().contains(hogarCompra));
	}

	@Test
	public void seAgregaEgresoAEntidadBase() {
		entidadBase.setMontoMaximoEgreso(500000);
		entidadBase.cargarEgreso(hogarCompra);
		entidadBase.cargarEgreso(indumentariaCompra);
		entidadBase.generarReporte();
		assertTrue(entidadBase.getEgresos().contains(indumentariaCompra));
	}
}
