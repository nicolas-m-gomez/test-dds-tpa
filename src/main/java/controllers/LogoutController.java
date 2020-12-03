package controllers;

import spark.Request;
import spark.Response;

public class LogoutController {

    public Void cerrarSesion(Request request, Response response) {
    request.session().removeAttribute("idUsuario");
    request.session().removeAttribute("tipoUsuario");
    response.redirect("/");
   	
	return null;
    }
    
}