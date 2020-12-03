package controllers;


import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class HomeController {

    public ModelAndView getHome(Request request, Response response) {
    	if (request.session().attribute("idUsuario")!=null) {
    		response.redirect("/organizaciones");
    	}
    	
        Map<String, Object> modelo = new HashMap<>();
        modelo.put("anio", LocalDate.now().getYear());
        
        return new ModelAndView(modelo, "index.html.hbs");
    }
    
    public ModelAndView getPaginaError(Request request, Response response) {
		Map<String, Object> modelo = new HashMap<>();
		modelo.put("mensaje_error", "El recurso al que quiere acceder no existe o no tiene permisos para verlo. Contacte al administrador");
		return new ModelAndView(modelo, "error.html.hbs");
	}
}