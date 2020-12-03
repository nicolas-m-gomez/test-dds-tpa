package controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dominio.Categoria;
import dominio.Organizacion;
import model.RepositorioCategorias;
import model.RepositorioEntidades;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class CategoriasController {
	SessionController sessionController = new SessionController();

	public ModelAndView getFormularioEntidadesPorCategoria(Request request, Response response) {
		String categoriaSeleccionada = request.queryParams("categorias");
		Map<String, Object> modelo = new HashMap<>();
		List<Categoria> categoriasMostradas = RepositorioCategorias.instancia.listar();
		modelo.put("categorias", categoriasMostradas);
		List<Organizacion> organizacionesMostradas = categoriaSeleccionada != null
				? RepositorioEntidades.instancia.listarSegunCategoria(Integer.parseInt(categoriaSeleccionada))
				: RepositorioEntidades.instancia.listarEntidades();

		sessionController.validarSesion(request.session(), response);

		modelo.put("opciones",
				sessionController.opcionesDisponiblesSegunUsuario(request.session().attribute("idUsuario")));
		modelo.put("entidades", organizacionesMostradas);

		return new ModelAndView(modelo, "categoria-entidades.html.hbs");
	}
}
