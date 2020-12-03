package controllers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import dominio.Notificacion;
import dominio.Usuario;
import model.RepositorioNotificaciones;
import model.RepositorioUsuarios;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateEngine;

public class NotificacionesController {

	RepositorioNotificaciones repositorio = RepositorioNotificaciones.getInstance();
	SessionController sessionController = new SessionController();

	public Object getNotificaciones(Request request, Response response, TemplateEngine engine) {

		sessionController.validarSesion(request.session(), response);

		Integer idUsuario = request.session().attribute("idUsuario");

		Usuario usuario = RepositorioUsuarios.getInstance().hallarUsuario(idUsuario);

		Map<String, Object> modelo = new HashMap<>();
		List<Notificacion> notificacionesFiltradas;
		String egresoDefault = "0";
		String fechaDefault = "1800-01-01";

		String egresoFiltro = request.queryParams("egresoFiltro") != null && request.queryParams("egresoFiltro") != ""
				? request.queryParams("egresoFiltro")
				: egresoDefault;
		String fechaFiltro = request.queryParams("fechaFiltro") != null && request.queryParams("fechaFiltro") != ""
				? request.queryParams("fechaFiltro")
				: fechaDefault;

		DateTimeFormatter formatters = DateTimeFormatter.ofPattern("uuuu-MM-dd");

		List<Notificacion> notificaciones = usuario.getNotificaciones();

		if (egresoFiltro.equals(egresoDefault)) {
			notificacionesFiltradas = notificaciones.stream()
					.filter(n -> n.getFecha().isAfter(LocalDate.parse(fechaFiltro, formatters))
							|| n.getFecha().equals(LocalDate.parse(fechaFiltro, formatters)))
					.collect(Collectors.toList());
		} else {
			notificacionesFiltradas = notificaciones.stream()
					.filter(n -> n.getEgresoId().equals(Integer.parseInt(egresoFiltro))
							&& (n.getFecha().isAfter(LocalDate.parse(fechaFiltro, formatters))
									|| (n.getFecha().equals(LocalDate.parse(fechaFiltro, formatters)))))
					.collect(Collectors.toList());
		}

		if (!egresoFiltro.equals(egresoDefault)) {
			if (!fechaFiltro.equals(fechaDefault)) {
				modelo.put("resultadosFiltro",
						"Estás filtrando por fecha: "
								+ LocalDate.parse(fechaFiltro, formatters)
										.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
								+ " y egreso: " + egresoFiltro + ".");
			} else {
				modelo.put("resultadosFiltro", "Estás filtrando por egreso: " + egresoFiltro + ".");
			}
		} else {
			if (!fechaFiltro.equals(fechaDefault)) {
				modelo.put("resultadosFiltro", "Estás filtrando por fecha: "
						+ LocalDate.parse(fechaFiltro, formatters).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
						+ ".");
			}
		}

		modelo.put("opciones",
				sessionController.opcionesDisponiblesSegunUsuario(request.session().attribute("idUsuario")));
		modelo.put("notificaciones", notificacionesFiltradas);

		return engine.render(new ModelAndView(modelo, "notificaciones.html.hbs"));
	}

	public Object getNotificacion(Request request, Response response, TemplateEngine engine) {

		sessionController.validarSesion(request.session(), response);

		Integer idUsuario = request.session().attribute("idUsuario");

		Usuario usuario = RepositorioUsuarios.getInstance().hallarUsuario(idUsuario);

		Map<String, Object> modelo = new HashMap<>();
		Integer id = Integer.parseInt(request.params(":id"));

		Notificacion notificacion = repositorio.buscarNotificacion(id);
		List<Notificacion> notificaciones = usuario.getNotificaciones();
		modelo.put("opciones",
				sessionController.opcionesDisponiblesSegunUsuario(request.session().attribute("idUsuario")));
		modelo.put("mensajes", notificacion.getMensajes());
		modelo.put("notificaciones", notificaciones);
		modelo.put("notificacion", notificacion.getId());

		try {
			return engine.render(new ModelAndView(modelo, "notificaciones.html.hbs"));

		} catch (NumberFormatException e) {
			response.status(400);
			System.out.println("El id ingresado (" + id + ") no es un número");
			return "Bad Request";
		}
	}

	public Object borrarNotificacion(Request request, Response response, TemplateEngine engine) {

		sessionController.validarSesion(request.session(), response);

		Integer idUsuario = request.session().attribute("idUsuario");

		Map<String, Object> modelo = new HashMap<>();
		Integer id = Integer.parseInt(request.params(":id"));

		Usuario usuario = RepositorioUsuarios.getInstance().hallarUsuario(idUsuario);

		Notificacion notificacion = repositorio.buscarNotificacion(id);

		repositorio.borrarNotificacion(notificacion);
		
		modelo.put("opciones",
				sessionController.opcionesDisponiblesSegunUsuario(request.session().attribute("idUsuario")));
		modelo.put("confirmacionBorrado", "La notificacion ha sido borrada exitosamente");
		modelo.put("notificaciones", usuario.getNotificaciones());

		try {
			return engine.render(new ModelAndView(modelo, "notificaciones.html.hbs"));

		} catch (NumberFormatException e) {
			response.status(400);
			System.out.println("El id ingresado (" + id + ") no es un número");
			return "Bad Request";
		}
	}

	public Void filtrar(Request request, Response response) {

		String egresoFiltro = request.queryParams("egreso");
		String fechaFiltro = request.queryParams("fecha");

		response.redirect("/notificaciones?fechaFiltro=" + fechaFiltro + "&egresoFiltro=" + egresoFiltro);
		return null;
	}

}