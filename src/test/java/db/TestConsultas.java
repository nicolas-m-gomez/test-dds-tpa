//package db;
//
//import static org.junit.Assert.assertEquals;
//
//import javax.persistence.EntityManager;
//import javax.persistence.EntityTransaction;
//
//import org.junit.After;
//import org.junit.AfterClass;
//import org.junit.Before;
//import org.junit.BeforeClass;
//import org.junit.Test;
//import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;
//
//import dominio.Moneda;
//import servicio.Fixture;
//
//public class TestConsultas {
//	EntityManager em;
//	EntityTransaction tran;
//	static Fixture fixture = new Fixture();
//
//	@BeforeClass
//	public static void iniciar() {
//		fixture.agregarMonedas();
//		fixture.agregarCategorias();
//		fixture.agregarDirecciones();
//		fixture.agregarMediosPago();
//		fixture.agregarImportes();
//		fixture.agregarItems();
//		fixture.agregarProveedores();
//		fixture.agregarEgreso();
//	}
//
//	@Before
//	public void prepareTest() {
//		this.em = PerThreadEntityManagers.getEntityManager();
//		this.tran = em.getTransaction();
//		this.tran.begin();
//	}
//
//	@After
//	public void finishTest() {
//		this.tran.commit();
//	}
//
//	@AfterClass
//	public static void finalizar() {
//		fixture.borrarEgreso();
//		fixture.borrarProveedores();
//		fixture.borrarItems();
//		fixture.borrarImportes();
//		fixture.borrarDirecciones();
//		fixture.borrarMonedas();
//		fixture.borrarCategorias();
//		fixture.borrarMediosPago();
//	}
//
//	@Test
//	public void testObtenerMonedaPesoDeBD() {
//		assertEquals("$", em.find(Moneda.class, "ARS").getSimbolo());
//	}
//
//	@Test
//	public void testObtenerCategoriaONGTest() {
//		assertEquals("ONG TEST", em.createNativeQuery("SELECT nombre FROM categorias WHERE nombre = \'ONG TEST\'")
//				.getSingleResult().toString());
//	}
//
//	@Test
//	public void testObtenerDireccionCalleFalsa() {
//		assertEquals("Calle Falsa",
//				em.createNativeQuery("SELECT direccion_calle FROM direcciones WHERE direccion_calle = \'Calle Falsa\'")
//						.getSingleResult().toString());
//	}
//
//	@Test
//	public void testObtenerItemsDeBD() {
//		assertEquals("computadora",
//				em.createNativeQuery("SELECT descripcion FROM Items WHERE descripcion = \'computadora\'")
//						.getSingleResult().toString());
//		assertEquals("impresora",
//				em.createNativeQuery("SELECT descripcion FROM Items WHERE descripcion = \'impresora\'")
//						.getSingleResult().toString());
//		assertEquals("silla", em.createNativeQuery("SELECT descripcion FROM Items WHERE descripcion = \'silla\'")
//				.getSingleResult().toString());
//	}
//
//	@Test
//	public void testObtenerEgresoDeBD() {
//		assertEquals("2020-05-15",
//				em.createNativeQuery("SELECT CAST(fecha as char) FROM Egresos WHERE fecha = \'20200515\'")
//						.getSingleResult().toString());
//	}
//
//}
