package servicio;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import dominio.Categoria;
import dominio.CreadorMoneda;
import dominio.Direccion;
import dominio.DocumentoComercial;
import dominio.Egreso;
import dominio.Importe;
import dominio.ItemEgreso;
import dominio.MedioPago;
import dominio.Proveedor;
import dominio.TipoDocumento;
import dominio.TipoMedio;
import dominio.Ubicacion;

public class Fixture implements WithGlobalEntityManager, TransactionalOps {	 
	Categoria ong = new Categoria("ONG TEST");
	Categoria judiciales = new Categoria("Judiciales TEST");
	Categoria indAgro = new Categoria("Industria Agropecuaria TEST");

	Ubicacion ubi = new Ubicacion("Argentina", "C.A.B.A.", "Test");
	Direccion dir1 = new Direccion("Calle Falsa", 123, "PB", "1407", ubi);
	Direccion dir2 = new Direccion("Av. Siempreviva", 742, "PB", "1408", ubi);
	Direccion dir3 = new Direccion("Loyola", 123, "PB", "1407", ubi);
	
	DocumentoComercial doc1 = new DocumentoComercial("TST01", TipoDocumento.FACTURA);
	DocumentoComercial doc2 = new DocumentoComercial("TST02", TipoDocumento.RECIBO);	
	
	MedioPago medioPagoTest = new MedioPago("TEST", TipoMedio.TARJETA_CREDITO);
	
	Proveedor testProv = new Proveedor("Proveedor TEST", dir3);
	Egreso nuevaCompra = new Egreso(testProv, LocalDate.of(2020,05,15), medioPagoTest,true);
	
	ServicioUbicacionYMoneda servicioML = new ServicioUbicacionYMonedaMercadoLibre(); 
	CreadorMoneda creador = CreadorMoneda.getCreadorMoneda(servicioML);
	
	Importe importe2K = new Importe(new BigDecimal ("2000"), creador.crearMoneda("ARS"));
	Importe importe5K = new Importe(new BigDecimal ("50000"), creador.crearMoneda("ARS"));
	Importe importe23K = new Importe(new BigDecimal ("23000"), creador.crearMoneda("ARS"));
	
	ItemEgreso computadora = new ItemEgreso("computadora",2, importe2K);
	ItemEgreso impresora = new ItemEgreso("impresora", 3, importe5K);
	ItemEgreso silla = new ItemEgreso("silla",5,importe23K);
	
	public void agregarMonedas() {
		List<String> ids = servicioML.obtenerIdsMonedas();
		withTransaction(() -> {
			for(String idMoneda : ids) {
				entityManager().persist(creador.crearMoneda(idMoneda));									
			}
		});
	}
	
	public void borrarMonedas() {
//		ServicioUbicacionYMoneda servicioML = new ServicioUbicacionYMonedaMercadoLibre(); 
//		CreadorMoneda creador = CreadorMoneda.getCreadorMoneda(servicioML);
		List<String> ids = servicioML.obtenerIdsMonedas();
		withTransaction(() -> {
			for(String idMoneda : ids) {
				entityManager().remove(creador.crearMoneda(idMoneda));									
			}
		});
	}
		
	public void agregarCategorias() {				
		withTransaction(() -> {
			entityManager().persist(ong);
			entityManager().persist(judiciales);
			entityManager().persist(indAgro);
		});
	}
	
	public void borrarCategorias() {
		withTransaction(() -> {
			entityManager().remove(ong);
			entityManager().remove(judiciales);
			entityManager().remove(indAgro);
		});
	}
	
	public void agregarDirecciones() {
		withTransaction(() -> {
			entityManager().persist(dir1);
			entityManager().persist(dir2);
			entityManager().persist(dir3);
		});
	}
	
	public void borrarDirecciones() {
		withTransaction(() -> {
			entityManager().remove(dir1);
			entityManager().remove(dir2);
			entityManager().remove(dir3);
		});
	}
	
	public void agregarDocumentosComerciales() {
		withTransaction(() -> {
			entityManager().persist(doc1);
			entityManager().persist(doc2);
		});
	}	
	
	public void borrarDocumentosComerciales() {
		withTransaction(() -> {
			entityManager().remove(doc1);
			entityManager().remove(doc2);
		});
	}
	
	public void agregarImportes() {
		withTransaction(() -> {
			entityManager().persist(importe2K);
			entityManager().persist(importe5K);
			entityManager().persist(importe23K);
		});
	}
	
	public void borrarImportes() {
		withTransaction(() -> {
			entityManager().remove(importe2K);
			entityManager().remove(importe5K);
			entityManager().remove(importe23K);
		});
	}
	
	public void agregarItems() {
		withTransaction(() -> {
			entityManager().persist(computadora);
			entityManager().persist(impresora);
			entityManager().persist(silla);
		});
	}
	
	public void borrarItems() {
		withTransaction(() -> {
			entityManager().remove(computadora);
			entityManager().remove(impresora);
			entityManager().remove(silla);
		});
	}
	
	public void agregarProveedores() {
		withTransaction(() -> {
			entityManager().persist(testProv);			
		});
	}
	
	public void borrarProveedores() {
		withTransaction(() -> {
			entityManager().remove(testProv);			
		});
	}
	
	public void agregarMediosPago() {
		withTransaction(() -> {
			entityManager().persist(medioPagoTest);			
		});
	}
	
	public void borrarMediosPago() {
		withTransaction(() -> {
			entityManager().remove(medioPagoTest);			
		});
	}
	
	public void agregarEgreso() {
		nuevaCompra.cargarItem(computadora);
		nuevaCompra.cargarItem(impresora);
		nuevaCompra.cargarItem(silla);
		System.out.println(nuevaCompra.getFecha().toString());
		withTransaction(() -> {
			entityManager().persist(nuevaCompra);
		});
	}
	
	public void borrarEgreso() {
		withTransaction(() -> {
			entityManager().remove(nuevaCompra);
		});
	}	
}