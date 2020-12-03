package controllers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dominio.DocumentoComercial;
import dominio.Egreso;
import dominio.Importe;
import dominio.ItemPresupuesto;
import dominio.Moneda;
import dominio.Organizacion;
import dominio.Presupuesto;
import dominio.Proveedor;
import model.RepositorioEgresos;
import model.RepositorioItemsEgreso;
import model.RepositorioOrganizaciones;
import model.RepositorioPresupuestos;
import servicio.viewmodel.ItemPresupuestoViewModel;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class PresupuestosController {

	SessionController sessionController = new SessionController();

	public ModelAndView getPresupuestos(Request request, Response response) {

		Map<String, Object> modelo = new HashMap<>();

		Egreso egreso = RepositorioEgresos.getInstance().buscar(Integer.valueOf(request.params(":idEgreso")));
		Organizacion organizacion = RepositorioOrganizaciones.getInstance()
				.buscar(Integer.valueOf(request.params(":idOrganizacion")));

		sessionController.validarSesion(request.session(), response);

		List<Presupuesto> presupuestos = egreso.getPresupuestos();

		modelo.put("opciones",
				sessionController.opcionesDisponiblesSegunUsuario(request.session().attribute("idUsuario")));
		modelo.put("egreso", egreso);
		modelo.put("organizacion", organizacion);
		modelo.put("presupuestos", presupuestos);

		return new ModelAndView(modelo, "presupuestos.html.hbs");
	}

	public ModelAndView getPresupuesto(Request request, Response response) {
		Map<String, Object> modelo = new HashMap<>();

		Egreso egreso = RepositorioEgresos.getInstance().buscar(Integer.valueOf(request.params(":idEgreso")));
		Organizacion organizacion = RepositorioOrganizaciones.getInstance()
				.buscar(Integer.valueOf(request.params(":idOrganizacion")));
		Presupuesto presupuesto = RepositorioPresupuestos.getInstance()
				.buscarPresupuestoPorId(Integer.valueOf(request.params(":idPresupuesto")));

		sessionController.validarSesion(request.session(), response);

		modelo.put("opciones",
				sessionController.opcionesDisponiblesSegunUsuario(request.session().attribute("idUsuario")));
		modelo.put("egreso", egreso);
		modelo.put("organizacion", organizacion);
		modelo.put("presupuesto", presupuesto);
		modelo.put("disabled", "disabled");

		return new ModelAndView(modelo, "presupuesto.html.hbs");
	}

	public ModelAndView cargarPresupuesto(Request request, Response response) {
		Map<String, Object> modelo = new HashMap<>();

		Egreso egreso = RepositorioEgresos.getInstance().buscar(Integer.valueOf(request.params(":idEgreso")));
		Organizacion organizacion = RepositorioOrganizaciones.getInstance()
				.buscar(Integer.valueOf(request.params(":idOrganizacion")));

		sessionController.validarSesion(request.session(), response);

		modelo.put("opciones",
				sessionController.opcionesDisponiblesSegunUsuario(request.session().attribute("idUsuario")));
		modelo.put("egreso", egreso);
		modelo.put("organizacion", organizacion);
		modelo.put("proveedores", RepositorioEgresos.getInstance().listarProveedores());
		modelo.put("documentosComerciales", RepositorioEgresos.getInstance().listarDocumentosComerciales());

		return new ModelAndView(modelo, "nuevo_presupuesto.html.hbs");
	}

	public Void guardarPresupuesto(Request request, Response response) {

		Egreso egreso = RepositorioEgresos.getInstance().buscar(Integer.valueOf(request.params(":idEgreso")));

		Proveedor proveedor = RepositorioEgresos.getInstance()
				.buscarProveedorPorId(Integer.valueOf(request.queryParams("iproveedores")));

		DocumentoComercial docuComercial = RepositorioEgresos.getInstance()
				.buscarDocumentoComercialPorId(request.queryParams("idocucomercial"));

		Presupuesto presupuesto = new Presupuesto(proveedor, docuComercial, new ArrayList<ItemPresupuesto>());

		egreso.getPresupuestos().add(presupuesto);

		RepositorioPresupuestos.getInstance().crearPresupuesto(egreso, presupuesto);

		response.redirect("/organizaciones/" + request.params(":idOrganizacion") + "/egresos/"
				+ request.params("idEgreso") + "/presupuestos");

		return null;
	}

	public Void borrarPresupuesto(Request request, Response response) {
		Egreso egreso = RepositorioEgresos.getInstance().buscar(Integer.valueOf(request.params(":idEgreso")));
		Presupuesto presupuesto = RepositorioPresupuestos.getInstance()
				.buscarPresupuestoPorId(Integer.valueOf(request.params(":idPresupuesto")));

		RepositorioPresupuestos.getInstance().borrarPresupuesto(egreso, presupuesto);

		response.redirect("/organizaciones/" + request.params(":idOrganizacion") + "/egresos/"
				+ request.params(":idEgreso") + "/presupuestos");

		return null;
	}

	public ModelAndView getItemsPresupuesto(Request request, Response response) {
		Map<String, Object> modelo = new HashMap<>();

		Egreso egreso = RepositorioEgresos.getInstance().buscar(Integer.valueOf(request.params(":idEgreso")));
		Organizacion organizacion = RepositorioOrganizaciones.getInstance()
				.buscar(Integer.valueOf(request.params(":idOrganizacion")));
		Presupuesto presupuesto = RepositorioPresupuestos.getInstance()
				.buscarPresupuestoPorId(Integer.valueOf(request.params(":idPresupuesto")));

		List<ItemPresupuestoViewModel> itemsPresupuestoVM = RepositorioPresupuestos.getInstance()
				.getItemsPresupuestoViewModel(presupuesto.getItems());

		sessionController.validarSesion(request.session(), response);

		modelo.put("opciones",
				sessionController.opcionesDisponiblesSegunUsuario(request.session().attribute("idUsuario")));
		modelo.put("egreso", egreso);
		modelo.put("organizacion", organizacion);
		modelo.put("presupuesto", presupuesto);
		modelo.put("items_presupuesto", itemsPresupuestoVM);

		return new ModelAndView(modelo, "items_presupuesto.html.hbs");
	}

	public ModelAndView getItemPresupuesto(Request request, Response response) {
		Map<String, Object> modelo = new HashMap<>();

		Egreso egreso = RepositorioEgresos.getInstance().buscar(Integer.valueOf(request.params(":idEgreso")));
		Organizacion organizacion = RepositorioOrganizaciones.getInstance()
				.buscar(Integer.valueOf(request.params(":idOrganizacion")));
		Presupuesto presupuesto = RepositorioPresupuestos.getInstance()
				.buscarPresupuestoPorId(Integer.valueOf(request.params(":idPresupuesto")));

		sessionController.validarSesion(request.session(), response);

		ItemPresupuestoViewModel itemPresupuestoVM = new ItemPresupuestoViewModel(RepositorioPresupuestos.getInstance()
				.buscarItemPresupuestoPorId(Integer.valueOf(request.params(":idItemPresupuesto"))));

		modelo.put("opciones",
				sessionController.opcionesDisponiblesSegunUsuario(request.session().attribute("idUsuario")));
		modelo.put("egreso", egreso);
		modelo.put("organizacion", organizacion);
		modelo.put("presupuesto", presupuesto);
		modelo.put("item_presupuesto", itemPresupuestoVM);
		modelo.put("disabled", "disabled");

		return new ModelAndView(modelo, "item_presupuesto.html.hbs");
	}

	public ModelAndView cargarItemPresupuesto(Request request, Response response) {
		Map<String, Object> modelo = new HashMap<>();

		Egreso egreso = RepositorioEgresos.getInstance().buscar(Integer.valueOf(request.params(":idEgreso")));
		Organizacion organizacion = RepositorioOrganizaciones.getInstance()
				.buscar(Integer.valueOf(request.params(":idOrganizacion")));
		Presupuesto presupuesto = RepositorioPresupuestos.getInstance()
				.buscarPresupuestoPorId(Integer.valueOf(request.params(":idPresupuesto")));

		List<Moneda> monedas = RepositorioItemsEgreso.getInstance().getMonedas();

		modelo.put("opciones",
				sessionController.opcionesDisponiblesSegunUsuario(request.session().attribute("idUsuario")));

		modelo.put("organizacion", organizacion);
		modelo.put("egreso", egreso);
		modelo.put("presupuesto", presupuesto);
		modelo.put("monedas", monedas);

		return new ModelAndView(modelo, "nuevo_item_presupuesto.html.hbs");
	}

	public Void guardarItemPresupuesto(Request request, Response response) {
		sessionController.validarSesion(request.session(), response);

		Presupuesto presupuesto = RepositorioPresupuestos.getInstance()
				.buscarPresupuestoPorId(Integer.valueOf(request.params(":idPresupuesto")));

		Importe importe = new Importe(BigDecimal.valueOf(Double.valueOf(request.queryParams("iprecio_unitario"))),
				RepositorioItemsEgreso.getInstance().buscarMonedaPorId(request.queryParams("imoneda")));

		ItemPresupuesto itemAGuardar = new ItemPresupuesto(request.queryParams("idesc"),
				Integer.valueOf(request.queryParams("icantidad")), importe);

		RepositorioPresupuestos.getInstance().crearItemPresupuesto(presupuesto, itemAGuardar, importe);

		response.redirect("/organizaciones/" + request.params(":idOrganizacion") + "/egresos/"
				+ request.params(":idEgreso") + "/presupuestos/" + request.params(":idPresupuesto") + "/items");

		return null;
	}

	public Void borrarItemPresupuesto(Request request, Response response) {
		sessionController.validarSesion(request.session(), response);

		RepositorioPresupuestos.getInstance().borrarItemPresupuesto(
				RepositorioPresupuestos.getInstance().buscarPresupuestoPorId(Integer.valueOf(request.params(":idPresupuesto"))), 
				RepositorioPresupuestos.getInstance().buscarItemPresupuestoPorId(Integer.valueOf(request.params(":idItemPresupuesto"))));
		
		response.redirect("/organizaciones/" + request.params(":idOrganizacion") + "/egresos/"
				+ request.params(":idEgreso") + "/presupuestos/" + request.params(":idPresupuesto") + "/items");
		
		return null;
	}
}
