package controllers;

import java.util.HashMap;
import java.util.Map;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class OpcionesController {

	SessionController sessionController = new SessionController();

	public ModelAndView mostrarIndex(Request request, Response response) {

		Map<String, Object> modelo = new HashMap<>();

		modelo.put("opciones",
				sessionController.opcionesDisponiblesSegunUsuario(request.session().attribute("idUsuario")));

		return new ModelAndView(modelo, "home.html.hbs");
	}

}
