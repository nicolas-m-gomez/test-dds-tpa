package model;

import java.util.HashMap;
import java.util.Map;

public class CreadorMenu {
	
	public static Map<String, String> crearMenu(String tipoUsuario){
	
	Map<String, String> lista = new HashMap<>();
	
	if (tipoUsuario.equals("ADMINISTRADOR")) {
		lista.put("/organizaciones/nueva/entidad-base", "Cargar Entidad Base");
		lista.put("/organizaciones/nueva/entidad-juridica", "Cargar Entidad Juridica");
		lista.put("/asoc-entidad", "Asociar Entidad por Categoria");
	}
	
	lista.put("/categoria-entidades", "Ver Entidades por Categoria");
	lista.put("/notificaciones", "Ver Bandeja de Mensajes");

	return lista;
	
	}

}
