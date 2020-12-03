package main;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.uqbarproject.jpa.java8.extras.EntityManagerOps;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

import dominio.Categoria;
import dominio.CreadorMoneda;
import dominio.Direccion;
import dominio.DocumentoComercial;
import dominio.Egreso;
import dominio.EntidadBase;
import dominio.EntidadJuridica;
import dominio.Importe;
import dominio.ItemEgreso;
import dominio.ItemPresupuesto;
import dominio.MedioPago;
import dominio.Notificacion;
import dominio.Presupuesto;
import dominio.Proveedor;
import dominio.TipoDocumento;
import dominio.TipoMedio;
import dominio.TipoUsuario;
import dominio.Ubicacion;
import dominio.Usuario;
import model.RepositorioCategorias;
import model.RepositorioEntidades;
import servicio.ServicioUbicacionYMoneda;
import servicio.ServicioUbicacionYMonedaMercadoLibre;

public class Bootstrap implements WithGlobalEntityManager, EntityManagerOps, TransactionalOps {
	public static void main(String[] args) {
		new Bootstrap().run();
	}

	public void run() {
		Usuario admin = new Usuario("admin", "admin2020", TipoUsuario.ADMINISTRADOR);
		Usuario estandar = new Usuario("user", "user2020", TipoUsuario.ESTANDAR);

		Categoria ong = new Categoria("ONG");
		Categoria cooperativa = new Categoria("Cooperativa");
		Categoria judiciales = new Categoria("Judiciales");
		Categoria indAgro = new Categoria("Industria Agropecuaria");

		Ubicacion ubi = new Ubicacion("Argentina", "C.A.B.A.", "C.A.B.A.");
		Direccion dir1 = new Direccion("Calle Falsa", 123, "PB", "1407", ubi);
		Direccion dir2 = new Direccion("Av. Siempreviva", 742, "PB", "1408", ubi);
		Direccion dir3 = new Direccion("Loyola", 123, "PB", "1407", ubi);

		DocumentoComercial doc1 = new DocumentoComercial("TST01", TipoDocumento.FACTURA);
		DocumentoComercial doc2 = new DocumentoComercial("TST02", TipoDocumento.RECIBO);
		DocumentoComercial docuComercial1 = new DocumentoComercial("Factura A", TipoDocumento.FACTURA);
		DocumentoComercial docuComercial2 = new DocumentoComercial("Factura B", TipoDocumento.FACTURA);
		DocumentoComercial docuComercial3 = new DocumentoComercial("Factura C", TipoDocumento.FACTURA);

		MedioPago masterCard = new MedioPago("Master Card", TipoMedio.TARJETA_CREDITO);
		MedioPago visa = new MedioPago("Visa", TipoMedio.TARJETA_CREDITO);
		MedioPago pesos = new MedioPago("Pesos", TipoMedio.EFECTIVO);

		Proveedor proveedorKing = new Proveedor("Proveedor King", dir3);
		Proveedor yoTeProveo = new Proveedor("Yo te Proveo", dir2);

		Egreso egreso1 = new Egreso(proveedorKing, LocalDate.of(2020, 05, 15), pesos, true);
		Egreso egreso2 = new Egreso(proveedorKing, LocalDate.of(2020, 06, 25), visa, true);
		Egreso egreso3 = new Egreso(proveedorKing, LocalDate.of(2020, 07, 05), masterCard, true);
		Egreso egreso4 = new Egreso(yoTeProveo, LocalDate.of(2019, 05, 25), visa, true);
		Egreso egreso5 = new Egreso(yoTeProveo, LocalDate.of(2020, 07, 15), pesos, true);
		Egreso egreso6 = new Egreso(yoTeProveo, LocalDate.of(2020, 10, 30), masterCard, true);

		ServicioUbicacionYMoneda servicioML = new ServicioUbicacionYMonedaMercadoLibre();
		CreadorMoneda creador = CreadorMoneda.getCreadorMoneda(servicioML);

		Importe importe2K = new Importe(new BigDecimal("2000"), creador.crearMoneda("ARS"));
		Importe importe5K = new Importe(new BigDecimal("5000"), creador.crearMoneda("ARS"));
		Importe importe23K = new Importe(new BigDecimal("23000"), creador.crearMoneda("ARS"));
		Importe importe3K = new Importe(new BigDecimal("3000"), creador.crearMoneda("ARS"));
		Importe importe6K = new Importe(new BigDecimal("6000"), creador.crearMoneda("ARS"));
		Importe importe24K = new Importe(new BigDecimal("24000"), creador.crearMoneda("ARS"));
		Importe importe4K = new Importe(new BigDecimal("4000"), creador.crearMoneda("ARS"));
		Importe importe7K = new Importe(new BigDecimal("7000"), creador.crearMoneda("ARS"));
		Importe importe25K = new Importe(new BigDecimal("25000"), creador.crearMoneda("ARS"));

		Importe importe1K = new Importe(new BigDecimal("1000"), creador.crearMoneda("ARS"));
		Importe importe8K = new Importe(new BigDecimal("8000"), creador.crearMoneda("ARS"));
		Importe importe26K = new Importe(new BigDecimal("26000"), creador.crearMoneda("ARS"));

		ItemEgreso computadora1 = new ItemEgreso("Notebook HP 15\"", 2, importe2K);
		ItemEgreso computadora2 = new ItemEgreso("Notebook MSI", 20, importe5K);
		ItemEgreso computadora3 = new ItemEgreso("Notebook Lenovo", 6, importe23K);
		ItemEgreso impresora1 = new ItemEgreso("impresora HP", 2, importe3K);
		ItemEgreso impresora2 = new ItemEgreso("impresora EPSON", 1, importe6K);
		ItemEgreso impresora3 = new ItemEgreso("Multifunción HP", 6, importe24K);
		ItemEgreso silla1 = new ItemEgreso("silla ergnómica", 20, importe4K);
		ItemEgreso silla2 = new ItemEgreso("banqueta", 15, importe7K);
		ItemEgreso silla3 = new ItemEgreso("sillón individual", 3, importe25K);

		ItemPresupuesto presupuestoComputadora1 = new ItemPresupuesto("Notebook HP 15\"", 2, importe1K);
		ItemPresupuesto presupuestoComputadora2 = new ItemPresupuesto("Notebook MSI", 20, importe8K);
		ItemPresupuesto presupuestoComputadora3 = new ItemPresupuesto("Notebook Lenovo ", 6, importe26K);

		Presupuesto presupuesto1 = new Presupuesto(proveedorKing, docuComercial1,
				Arrays.asList(presupuestoComputadora1, presupuestoComputadora2, presupuestoComputadora3));		

		egreso1.cargarItem(computadora1);
		egreso1.cargarItem(impresora1);
		egreso2.cargarItem(computadora2);
		egreso2.cargarItem(impresora2);
		egreso3.cargarItem(computadora3);
		egreso3.cargarItem(impresora3);
		egreso4.cargarItem(silla1);
		egreso5.cargarItem(silla2);
		egreso6.cargarItem(silla3);
		egreso1.setDocuComercial(docuComercial1);
		egreso2.setDocuComercial(docuComercial2);
		egreso3.setDocuComercial(docuComercial3);
		egreso4.setDocuComercial(docuComercial1);
		egreso5.setDocuComercial(docuComercial2);
		egreso6.setDocuComercial(docuComercial3);
		egreso1.agregarPresupuesto(presupuesto1);

		EntidadBase coopteba = new EntidadBase();
		coopteba.setCategoria(cooperativa);
		coopteba.setNombreFicticio("Coopteba");
		coopteba.cargarEgreso(egreso5);
		coopteba.cargarEgreso(egreso6);

		EntidadJuridica greenpeace = new EntidadJuridica();
		greenpeace.setCategoria(ong);
		greenpeace.setNombreFicticio("Greenpeace");
		greenpeace.cargarEgreso(egreso1);
		greenpeace.cargarEgreso(egreso2);
		greenpeace.setCantidadMaximaEntidadesBase(5);

		EntidadJuridica medicosSinFronteras = new EntidadJuridica();
		medicosSinFronteras.setCategoria(ong);
		medicosSinFronteras.setNombreFicticio("Medicos Sin Fronteras");
		medicosSinFronteras.cargarEgreso(egreso3);
		medicosSinFronteras.cargarEgreso(egreso4);
		medicosSinFronteras.setCantidadMaximaEntidadesBase(1);
		medicosSinFronteras.agregarEntidadBase(coopteba);

		Notificacion notificacionOk = new Notificacion(egreso5);
		notificacionOk.agregarMensaje("El egreso ha sido validado");

		Notificacion notificacionError = new Notificacion(egreso5);
		notificacionError.agregarMensaje("El egreso tiene errores de destinatario");
		notificacionError.agregarMensaje("El egreso no tiene presupuesto asignado");

		admin.recibirNotificacion(notificacionOk);
		admin.recibirNotificacion(notificacionError);

		withTransaction(() -> {
			RepositorioCategorias.instancia.agregar(ong);
			RepositorioCategorias.instancia.agregar(cooperativa);
			RepositorioEntidades.instancia.agregarOrganizacion(greenpeace);
			RepositorioEntidades.instancia.agregarOrganizacion(medicosSinFronteras);
			RepositorioEntidades.instancia.agregarOrganizacion(coopteba);
			persist(judiciales);
			persist(indAgro);
			persist(dir1);
			persist(dir2);
			persist(dir3);
			persist(doc1);
			persist(doc2);
			persist(visa);
			persist(masterCard);
			persist(pesos);
			persist(docuComercial1);
			persist(docuComercial2);
			persist(docuComercial3);
			persist(proveedorKing);
			persist(yoTeProveo);
			List<String> ids = servicioML.obtenerIdsMonedas();
			for (String idMoneda : ids) {
				persist(creador.crearMoneda(idMoneda));
			}
			persist(importe2K);
			persist(importe5K);
			persist(importe23K);
			persist(importe3K);
			persist(importe6K);
			persist(importe24K);
			persist(importe4K);
			persist(importe7K);
			persist(importe25K);
			persist(importe1K);
			persist(importe8K);
			persist(importe26K);
			persist(computadora1);
			persist(computadora2);
			persist(computadora3);
			persist(presupuestoComputadora1);
			persist(presupuestoComputadora2);
			persist(presupuestoComputadora3);
			persist(presupuesto1);
			persist(impresora1);
			persist(impresora2);
			persist(impresora3);
			persist(silla1);
			persist(silla2);
			persist(silla3);
			persist(egreso1);
			persist(egreso2);
			persist(egreso3);
			persist(egreso4);
			persist(egreso5);
			persist(egreso6);
			persist(greenpeace);
			persist(medicosSinFronteras);
			persist(coopteba);

			persist(estandar);
			persist(admin);
		});
	}
}
