package negocio;

import static org.junit.Assert.assertFalse;
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
import dominio.ValidarAgregarEntidadBase;
import dominio.ValidarAgregarseAEntidadJuridica;
import dominio.ValidarMontoEgreso;
import dominio.exception.EgresoRechazadoException;
import dominio.exception.EntidadBaseRechazadaException;

public class TestEntidades {
	Egreso nuevaCompra;
	ItemEgreso computadora;
	ItemEgreso impresora;
	ItemEgreso silla;
	EntidadJuridica entidadJuridica = new EntidadJuridica();
	EntidadBase entidadBase =  new EntidadBase();
	Categoria categoriaJuridica = new Categoria("Judicial");
	Categoria categoriaBase = new Categoria("ONG");
	ReglaNegocio reglaMontoMaximo = new ValidarMontoEgreso(CategoriaReglaNegocio.VALIDAR_MONTO_EGRESO);
	ReglaNegocio reglaAgregarEntidadBase = new ValidarAgregarEntidadBase(CategoriaReglaNegocio.AGREGAR_ENTIDAD_BASE);
	ReglaNegocio reglaPuedeAgregarseAEntidadJuridica = new ValidarAgregarseAEntidadJuridica(CategoriaReglaNegocio.PUEDE_AGREGARSE_A_ENTIDAD_JURIDICA);

	@Before
	public void init() {
		nuevaCompra = new Egreso(new Proveedor(), LocalDate.of(2020,05,15), new MedioPago(),true);
		computadora = new ItemEgreso("computadora",2, new Importe(new BigDecimal ("2000"),new Moneda()));
		impresora = new ItemEgreso("impresora", 3,new Importe(new BigDecimal ("50000"), new Moneda()));
		silla = new ItemEgreso("silla",5,new Importe(new BigDecimal ("23000"), new Moneda()));

		nuevaCompra.cargarItem(computadora);
		nuevaCompra.cargarItem(impresora);
		nuevaCompra.cargarItem(silla);
		
		categoriaJuridica.agregarRegla(reglaMontoMaximo);
		categoriaJuridica.agregarRegla(reglaAgregarEntidadBase);
		categoriaJuridica.agregarRegla(reglaPuedeAgregarseAEntidadJuridica);
		
		categoriaBase.agregarRegla(reglaMontoMaximo);

		entidadJuridica.setCategoria(categoriaJuridica);
		entidadBase.setCategoria(categoriaBase);
	}

	@Test
	public void seAgregaEgresoAEntidadJuridica() {
		entidadJuridica.setMontoMaximoEgreso(500000);
		entidadJuridica.cargarEgreso(nuevaCompra);
		assertTrue(entidadJuridica.getEgresos().contains(nuevaCompra));
	}

	@Test
	public void seAgregaEgresoAEntidadBase() {
		entidadBase.setMontoMaximoEgreso(500000);
		entidadBase.cargarEgreso(nuevaCompra);
		assertTrue(entidadBase.getEgresos().contains(nuevaCompra));
	}

	@Test (expected = EgresoRechazadoException.class)
	public void noSeAgregaEgresoAEntidadJuridica() {
		entidadJuridica.setMontoMaximoEgreso(100000);
		entidadJuridica.cargarEgreso(nuevaCompra);
		assertFalse(entidadJuridica.getEgresos().contains(nuevaCompra));

	}

	@Test (expected = EgresoRechazadoException.class)
	public void noSeAgregaEgresoAEntidadBase() {
		entidadBase.setMontoMaximoEgreso(100000);
		entidadBase.cargarEgreso(nuevaCompra);
		assertFalse(entidadBase.getEgresos().contains(nuevaCompra));
	}
	
	@Test
	public void seAgregaEntidadBaseAJuridica() {
		entidadBase.setPermiteAgregarseAEntidadJuridica(true);
		entidadJuridica.setCantidadMaximaEntidadesBase(1);
		
		entidadJuridica.agregarEntidadBase(entidadBase);
		assertTrue(entidadJuridica.getEntidadesBase().contains(entidadBase));
	}
	
	@Test (expected = EntidadBaseRechazadaException.class)
	public void noSePermiteAgregarEntidadBaseAJuridica() {
		entidadBase.setPermiteAgregarseAEntidadJuridica(false);
		
		entidadJuridica.agregarEntidadBase(entidadBase);
		assertFalse(entidadJuridica.getEntidadesBase().contains(entidadBase));
	}
	
	@Test (expected = EntidadBaseRechazadaException.class)
	public void noSeAgregaEntidadBaseAJuridica() {
		//creo una entidad base auxiliar para que no pueda ser agregada a la entidad juridica
		//ya que el numero maximo permitido sera 1.
		EntidadBase entidadBaseAux = new EntidadBase();
		Categoria categoriaBaseAux = new Categoria("Industria Agropecuaria");
		entidadBaseAux.setCategoria(categoriaBaseAux);
		
		entidadBase.setPermiteAgregarseAEntidadJuridica(true);
		entidadBaseAux.setPermiteAgregarseAEntidadJuridica(true);
		entidadJuridica.setCantidadMaximaEntidadesBase(1);
		
		entidadJuridica.agregarEntidadBase(entidadBase);
		entidadJuridica.agregarEntidadBase(entidadBaseAux);
		
		assertTrue(entidadJuridica.getEntidadesBase().contains(entidadBase));
		assertFalse(entidadJuridica.getEntidadesBase().contains(entidadBaseAux));
		
		
	}

}
