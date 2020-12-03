package controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import dominio.Usuario;
import model.RepositorioUsuarios;
import servicio.Funcion;
import servicio.TipoFuncion;
import spark.Response;
import spark.Session;

public class SessionController {

	public SessionController() {

	}

	public void validarSesion(Session session, Response response) {
		if (session.attribute("idUsuario") == null) {
			response.redirect("/");
		}
	}

	public Map<String, String> opcionesDisponiblesSegunUsuario(Integer idUsuario) {
		Map<String, String> opciones = new HashMap<>();
		if (idUsuario != null) {
			Usuario usuario = RepositorioUsuarios.getInstance().listar().stream()
					.filter(u -> u.getId().equals(idUsuario)).findFirst().get();

			for (Funcion f : usuario.getTipoUsuario().getFuncionesDisponibles().stream()
					.filter(f -> f.getTipo() == TipoFuncion.NAVBAR).collect(Collectors.toList())) {
				opciones.put(f.getUrl(), f.getDenominacion());
			}			
		}
		return opciones;
	}

}
