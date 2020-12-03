package servicio;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import dominio.CriterioSeleccionPresupuestoMenorValor;
import dominio.DocumentoComercial;
import dominio.Egreso;
import dominio.EstadoEgreso;
import dominio.Importe;
import dominio.ItemEgreso;
import dominio.ItemPresupuesto;
import dominio.MedioPago;
import dominio.Moneda;
import dominio.Presupuestador;
import dominio.Presupuesto;
import dominio.Proveedor;
import dominio.TipoDocumento;
import dominio.TipoUsuario;
import dominio.Usuario;
import servicio.batch.*;

public class TestValidadorEgreso {

	Egreso nuevaCompra;
	ItemEgreso computadora;
	ItemEgreso impresora;
	ItemEgreso silla;
	
	ItemPresupuesto computadoraPresupuesto;
	ItemPresupuesto impresoraPresupuesto;
	ItemPresupuesto sillaPresupuesto;
	
	List<Egreso> egresos = new ArrayList<Egreso>();
	Usuario usuario1 = new Usuario("usuario1", "micontrasenia", TipoUsuario.ESTANDAR);
	Usuario usuario2 = new Usuario("usuario2", "micontrasenia", TipoUsuario.ESTANDAR);
	List<Usuario> revisores = new ArrayList<Usuario>();
	Proveedor prov = new Proveedor();
	DocumentoComercial documento = new DocumentoComercial("d2", TipoDocumento.CHEQUE);
	ValidadorEgresos validador;
	List<CriterioValidacionEgreso> criterios;

	@Before
	public void init() {
		nuevaCompra = new Egreso(prov, LocalDate.of(2020, 05, 15), new MedioPago(), true);
		computadora = new ItemEgreso("computadora", 2, new Importe(new BigDecimal("2000"), new Moneda()));
		impresora = new ItemEgreso("impresora", 3, new Importe(new BigDecimal("50000"), new Moneda()));
		silla = new ItemEgreso("silla", 5, new Importe(new BigDecimal("23000"), new Moneda()));
		revisores.add(usuario1);
		revisores.add(usuario2);
		
		criterios = new ArrayList<CriterioValidacionEgreso>();
		criterios.add(new CriterioEgresoAplicaPresupuesto());
		criterios.add(new CriterioEgresoCantidadPresupuestos());
		criterios.add(new CriterioValidacionSeleccionCorrecta());
		validador = new ValidadorEgresos();
		
		computadoraPresupuesto = new ItemPresupuesto("computadora", 2, new Importe(new BigDecimal("2000"), new Moneda()));
		impresoraPresupuesto = new ItemPresupuesto("impresora", 3, new Importe(new BigDecimal("50000"), new Moneda()));
		sillaPresupuesto = new ItemPresupuesto("silla", 5, new Importe(new BigDecimal("23000"), new Moneda()));
		
	}
//
//	@Test
//	public void antePresupuestoAprobadoSeInformaNotificacionAUSuario() {
//		nuevaCompra.cargarItem(computadora);
//		nuevaCompra.cargarItem(impresora);
//		nuevaCompra.cargarItem(silla);
//		nuevaCompra.setCriterioSeleccionPresupuesto(new CriterioSeleccionPresupuestoMenorValor());
//		nuevaCompra.setDocuComercial(documento);
//		nuevaCompra.setListadoRevisores(new ArrayList<Usuario>(revisores));
//		Presupuestador presupuestador = new Presupuestador();
//		presupuestador.cargarDocumentoComercial(documento);
//		presupuestador.cargarItemPresupuesto(sillaPresupuesto);
//		presupuestador.cargarItemPresupuesto(impresoraPresupuesto);
//		presupuestador.cargarItemPresupuesto(computadoraPresupuesto);
//		presupuestador.cargarProveedor(prov);
//		presupuestador.generarPresupuesto(nuevaCompra);
//		presupuestador.generarPresupuesto(nuevaCompra);
//		presupuestador.generarPresupuesto(nuevaCompra);
//		egresos.add(nuevaCompra);
//		validador.validar(egresos, criterios);
//		assert (usuario1.verNotificacionRecienteDe(nuevaCompra).getMensajes().contains("Se ha APROBADO el egreso"));
//	}

	@Test
	public void rechazarEgresoPorPresupuestoInvalido() {
		// cargo items de compra y selecciono criterio de eleccion de presupuesto
		nuevaCompra.cargarItem(computadora);
		nuevaCompra.cargarItem(impresora);
		nuevaCompra.cargarItem(silla);
		nuevaCompra.setCriterioSeleccionPresupuesto(new CriterioSeleccionPresupuestoMenorValor());
		// seteo documento comercial utilizado por la compra
		DocumentoComercial docu = new DocumentoComercial("d1", TipoDocumento.BOLETA);
		nuevaCompra.setDocuComercial(docu);
		// creo presupuesto aplicado por compra con DocumentoComercial distinto
		List<ItemPresupuesto> listaItemsPresupuesto = new ArrayList<ItemPresupuesto>();
		listaItemsPresupuesto.add(sillaPresupuesto);
		listaItemsPresupuesto.add(impresoraPresupuesto);
		listaItemsPresupuesto.add(computadoraPresupuesto);
		Presupuesto presupuestoAux1 = new Presupuesto(new Proveedor(), docu, listaItemsPresupuesto);
		Presupuesto presupuestoAux2 = new Presupuesto(new Proveedor(), docu, listaItemsPresupuesto);
		// seteo presupuestoAplicado en compra
		nuevaCompra.agregarPresupuesto(presupuestoAux1);
		nuevaCompra.agregarPresupuesto(presupuestoAux2);
		nuevaCompra.setListadoRevisores(new ArrayList<Usuario>(revisores));
		egresos.add(nuevaCompra);
		validador.validar(egresos, criterios);
		assert (nuevaCompra.getEstado().equals(EstadoEgreso.RECHAZADO));
	}

	@Test
	public void rechazarEgresoPorCantidadInsuficienteDePres() {
		// cargo items de compra y selecciono criterio de eleccion de presupuesto
		nuevaCompra.cargarItem(computadora);
		nuevaCompra.cargarItem(impresora);
		nuevaCompra.cargarItem(silla);
		nuevaCompra.setCriterioSeleccionPresupuesto(new CriterioSeleccionPresupuestoMenorValor());
		
		List<ItemPresupuesto> listaItemsPresupuesto = new ArrayList<ItemPresupuesto>();
		listaItemsPresupuesto.add(sillaPresupuesto);
		listaItemsPresupuesto.add(impresoraPresupuesto);
		listaItemsPresupuesto.add(computadoraPresupuesto);
		
		// seteo documento comercial utilizado por la compra
		DocumentoComercial docu = new DocumentoComercial("d1", TipoDocumento.BOLETA);
		nuevaCompra.setDocuComercial(docu);
		// creo presupuesto aplicado por compra
		Presupuesto presupuestoAplicado;
		presupuestoAplicado = new Presupuesto(prov, docu, listaItemsPresupuesto);
		// seteo presupuestoAplicado en compra, quedando un unico presupuesto en la
		// lista de presupuestos, cuando se necesitan al menos 3.
		nuevaCompra.agregarPresupuesto(presupuestoAplicado);
		// seteo listado de revisores
		nuevaCompra.setListadoRevisores(new ArrayList<Usuario>(revisores));
		// valido compra
		egresos.add(nuevaCompra);
		validador.validar(egresos, criterios);
		assert (nuevaCompra.getEstado().equals(EstadoEgreso.RECHAZADO));

	}

	@Test
	public void aprobarEgreso() {
		// cargo items de compra y selecciono criterio de eleccion de presupuesto
		nuevaCompra.cargarItem(computadora);
		nuevaCompra.cargarItem(impresora);
		nuevaCompra.cargarItem(silla);
		nuevaCompra.setCriterioSeleccionPresupuesto(new CriterioSeleccionPresupuestoMenorValor());
		List<ItemPresupuesto> listaItemsPresupuesto = new ArrayList<ItemPresupuesto>();
		listaItemsPresupuesto.add(sillaPresupuesto);
		listaItemsPresupuesto.add(impresoraPresupuesto);
		listaItemsPresupuesto.add(computadoraPresupuesto);
		
		// creo documento comercial utilizado por compra y presupuesto
		DocumentoComercial docu = new DocumentoComercial("d1", TipoDocumento.BOLETA);
		nuevaCompra.setDocuComercial(docu);
		// creo presupuesto aplicado
		Presupuesto presupuestoAplicado = new Presupuesto(prov, docu, listaItemsPresupuesto);
		// creo presupuestos auxiliares
		Presupuesto presupuestoAux1 = new Presupuesto(new Proveedor(), docu, listaItemsPresupuesto);
		Presupuesto presupuestoAux2 = new Presupuesto(new Proveedor(), docu, listaItemsPresupuesto);
		// seteo presupuestoAplicado en nuevaCompra
		nuevaCompra.agregarPresupuesto(presupuestoAplicado);
		nuevaCompra.agregarPresupuesto(presupuestoAux1);
		nuevaCompra.agregarPresupuesto(presupuestoAux2);
		// seteo listado de revisores de compra
		nuevaCompra.setListadoRevisores(new ArrayList<Usuario>(revisores));
		// valido egreso
		egresos.add(nuevaCompra);
		validador.validar(egresos, criterios);
		assert (!nuevaCompra.getEstado().equals(EstadoEgreso.APROBADO));

	}

}
