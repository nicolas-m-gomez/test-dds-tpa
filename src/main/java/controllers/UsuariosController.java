package controllers;


import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.NoSuchElementException;

import dominio.Usuario;
import model.RepositorioUsuarios;

public class UsuariosController {

	RepositorioUsuarios repositorio = RepositorioUsuarios.getInstance();
	
    public ModelAndView getFormularioLogin(Request request, Response response) {
        return new ModelAndView(null, "login.html.hbs");
    }

    public Void iniciarSesion(Request request, Response response) {
        String password = request.queryParams("password");
        String nombreUsuario = request.queryParams("usuario");
        try {
        Usuario usuario = repositorio.verificarUsuarioYClave(nombreUsuario,password);
        		
        request.session().attribute("idUsuario", usuario.getId());
        request.session().attribute("tipoUsuario", usuario.tipoUsuario().toString());

        response.redirect("/organizaciones");
        }catch(NoSuchElementException e) {
        	response.redirect("/login");
        }
        
        return null;
    }
}