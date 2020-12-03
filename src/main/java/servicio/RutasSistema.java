package servicio;

public final class RutasSistema {

	private static final String LISTA_NEGRA_CLAVES = "resources/commonCredentials/GesocBlackList.txt";
	private static final String LISTA_TOP10K_CLAVES = "resources/commonCredentials/10k-most-common.txt";

	public static String listaTop10KClaves() {
		return LISTA_TOP10K_CLAVES;
	}
	
	public static String listaNegraClaves() {
		return LISTA_NEGRA_CLAVES;
	}
}
