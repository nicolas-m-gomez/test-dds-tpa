package utilidades;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class TestLectorArchivos {
	LectorArchivos lector1;
	LectorArchivos lector2;
	String pathCorrecto;
	String pathErroneo;
	
	@Before
	public void init() {
		pathCorrecto = "resources/commonCredentials/10k-most-common.txt";
		pathErroneo = "resources/commonCredentials/test.txt";
		lector1 = new LectorArchivos(pathCorrecto);
		lector2 = new LectorArchivos(pathErroneo);
	}
	
	@Test
	public void testPathValido() {
		assertTrue(lector1.pathArchivoValido());
	}
	
	@Test
	public void testPathErroneo() {
		assertTrue(!lector2.pathArchivoValido());
	}
}
