package controllers;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dominio.Egreso;
import dominio.Importe;
import dominio.ItemEgreso;
import dominio.Moneda;
import dominio.Organizacion;
import model.RepositorioEgresos;
import model.RepositorioItemsEgreso;
import model.RepositorioOrganizaciones;
import servicio.viewmodel.ItemEgresoViewModel;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class ItemsEgresoController {

	SessionController sessionController = new SessionController();

	public ModelAndView getItemsEgreso(Request request, Response response) {

		sessionController.validarSesion(request.session(), response);

		Egreso egreso = RepositorioEgresos.getInstance().buscar(Integer.valueOf(request.params(":idEgreso")));
		Organizacion organizacion = RepositorioOrganizaciones.getInstance().buscarPorEgreso(
				Integer.valueOf(request.params(":idEgreso")), Integer.valueOf(request.params(":idOrganizacion")));
		Map<String, Object> modelo = new HashMap<>();
		if (organizacion == null || egreso == null) {
			response.header("mensaje_error",
					"El recurso al que quiere acceder no existe o no tiene permisos para verlo.<br>Contacte al administrador");
			response.redirect("/error");
		}

		List<ItemEgresoViewModel> itemsEgresosVM = RepositorioEgresos.getInstance()
				.getItemsEgresosViewModel(egreso.getItems());

		modelo.put("opciones",
				sessionController.opcionesDisponiblesSegunUsuario(request.session().attribute("idUsuario")));

		modelo.put("organizacion", organizacion);
		modelo.put("egreso", egreso);
		modelo.put("items_egreso", itemsEgresosVM);

		return new ModelAndView(modelo, "items_egreso.html.hbs");
	}

	public ModelAndView getItemEgreso(Request request, Response response) {

		sessionController.validarSesion(request.session(), response);

		Egreso egreso = RepositorioEgresos.getInstance().buscar(Integer.valueOf(request.params(":idEgreso")));
		Organizacion organizacion = RepositorioOrganizaciones.getInstance().buscarPorEgreso(
				Integer.valueOf(request.params(":idEgreso")), Integer.valueOf(request.params(":idOrganizacion")));
		ItemEgreso itemEgreso = RepositorioEgresos.getInstance().getItemEgreso(egreso,
				Integer.valueOf(request.params(":idItemEgreso")));
		Map<String, Object> modelo = new HashMap<>();
		if (organizacion == null || egreso == null) {
			response.header("mensaje_error",
					"El recurso al que quiere acceder no existe o no tiene permisos para verlo.<br>Contacte al administrador");
			response.redirect("/error");
		}

		ItemEgresoViewModel itemEgresosVM = new ItemEgresoViewModel(itemEgreso);

		modelo.put("opciones",
				sessionController.opcionesDisponiblesSegunUsuario(request.session().attribute("idUsuario")));

		modelo.put("organizacion", organizacion);
		modelo.put("egreso", egreso);
		modelo.put("item_egreso", itemEgresosVM);
		modelo.put("disabled", "disabled");

		return new ModelAndView(modelo, "item_egreso.html.hbs");
	}

	public ModelAndView cargarItemEgreso(Request request, Response response) {
		sessionController.validarSesion(request.session(), response);

		Map<String, Object> modelo = new HashMap<>();
		Egreso egreso = RepositorioEgresos.getInstance().buscar(Integer.valueOf(request.params(":idEgreso")));
		Organizacion organizacion = RepositorioOrganizaciones.getInstance().buscarPorEgreso(
				Integer.valueOf(request.params(":idEgreso")), Integer.valueOf(request.params(":idOrganizacion")));

		List<Moneda> monedas = RepositorioItemsEgreso.getInstance().getMonedas();

		modelo.put("opciones",
				sessionController.opcionesDisponiblesSegunUsuario(request.session().attribute("idUsuario")));

		modelo.put("organizacion", organizacion);
		modelo.put("egreso", egreso);
		modelo.put("monedas", monedas);

		return new ModelAndView(modelo, "nuevo_item_egreso.html.hbs");
	}

	public Void guardarItemEgreso(Request request, Response response) {
		sessionController.validarSesion(request.session(), response);

		Egreso egreso = RepositorioEgresos.getInstance().buscar(Integer.valueOf(request.params(":idEgreso")));		
		
		Importe importe = new Importe(BigDecimal.valueOf(Double.valueOf(request.queryParams("iprecio_unitario"))),
				RepositorioItemsEgreso.getInstance().buscarMonedaPorId(request.queryParams("imoneda")));

		ItemEgreso itemAGuardar = new ItemEgreso(request.queryParams("idesc"),
				Integer.valueOf(request.queryParams("icantidad")), importe);

		RepositorioItemsEgreso.getInstance().crearItemEgreso(egreso, itemAGuardar, importe);

		response.redirect("/organizaciones/" + request.params(":idOrganizacion") + "/egresos/"
				+ request.params(":idEgreso") + "/items");

		return null;
	}

	public Void eliminarItemEgreso(Request request, Response response) {
		sessionController.validarSesion(request.session(), response);

		Egreso egreso = RepositorioEgresos.getInstance().buscar(Integer.valueOf(request.params(":idEgreso")));

		RepositorioItemsEgreso.getInstance().eliminarItemEgreso(egreso, RepositorioEgresos.getInstance()
				.getItemEgreso(egreso, Integer.valueOf(request.params(":idItemEgreso"))));
		
		response.redirect("/organizaciones/" + request.params(":idOrganizacion") + "/egresos/"
				+ request.params(":idEgreso") + "/items");
		
		return null;
	}
}
