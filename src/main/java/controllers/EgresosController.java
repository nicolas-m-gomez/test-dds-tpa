package controllers;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dominio.DocumentoComercial;
import dominio.Egreso;
import dominio.MedioPago;
import dominio.Organizacion;
import dominio.Proveedor;
import model.RepositorioEgresos;
import model.RepositorioMediosPago;
import model.RepositorioOrganizaciones;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class EgresosController {

	SessionController sessionController = new SessionController();

	public ModelAndView getEgresos(Request request, Response response) {
		Map<String, Object> modelo = new HashMap<>();

		List<Egreso> egresos = RepositorioEgresos.getInstance()
				.listarEgresosDeOrgnizacion(Integer.valueOf(request.params(":idOrganizacion")));
		Organizacion organizacion = RepositorioOrganizaciones.getInstance()
				.buscar(Integer.valueOf(request.params(":idOrganizacion")));

		sessionController.validarSesion(request.session(), response);

		modelo.put("opciones",
				sessionController.opcionesDisponiblesSegunUsuario(request.session().attribute("idUsuario")));

		modelo.put("egresos", egresos);
		modelo.put("idOrganizacion", request.params(":idOrganizacion"));
		modelo.put("organizacion", organizacion);

		return new ModelAndView(modelo, "egresos.html.hbs");
	}

	public ModelAndView getEgreso(Request request, Response response) {
		Map<String, Object> modelo = new HashMap<>();

		Egreso egreso = RepositorioEgresos.getInstance().buscar(Integer.valueOf(request.params(":idEgreso")));
		Organizacion organizacion = RepositorioOrganizaciones.getInstance().buscarPorEgreso(
				Integer.valueOf(request.params(":idEgreso")), Integer.valueOf(request.params(":idOrganizacion")));

		sessionController.validarSesion(request.session(), response);

		modelo.put("opciones",
				sessionController.opcionesDisponiblesSegunUsuario(request.session().attribute("idUsuario")));

		modelo.put("organizacion", organizacion);
		modelo.put("nombreOrganizacion", organizacion.getNombreFicticio());
		modelo.put("egreso", egreso);
		modelo.put("disabled", "disabled");

		return new ModelAndView(modelo, "egreso.html.hbs");
	}

	public ModelAndView cargarEgreso(Request request, Response response) {
		Map<String, Object> modelo = new HashMap<>();

		Organizacion organizacion = RepositorioOrganizaciones.getInstance()
				.buscar(Integer.valueOf(request.params(":idOrganizacion")));

		List<MedioPago> mediosPago = RepositorioMediosPago.getInstance().listarMediosPago();
		List<Proveedor> proveedores = RepositorioEgresos.getInstance().listarProveedores();
		List<DocumentoComercial> documentosComerciales = RepositorioEgresos.getInstance().listarDocumentosComerciales();
		if (organizacion == null) {
			response.redirect("/error");
		}
		sessionController.validarSesion(request.session(), response);

		modelo.put("opciones",
				sessionController.opcionesDisponiblesSegunUsuario(request.session().attribute("idUsuario")));
		modelo.put("organizacion", organizacion);
		modelo.put("nombreOrganizacion", organizacion.getNombreFicticio());
		modelo.put("disabled", "");
		modelo.put("fecha_del_dia", LocalDate.now().toString());
		modelo.put("mediosPago", mediosPago);
		modelo.put("proveedores", proveedores);
		modelo.put("documentosComerciales", documentosComerciales);

		return new ModelAndView(modelo, "nuevo_egreso.html.hbs");
	}

	public Void guardarEgreso(Request request, Response response) {

		sessionController.validarSesion(request.session(), response);

		LocalDate fechaEgreso = LocalDate.parse(request.queryParams("ifecha"));

		MedioPago medioPagoEgreso = RepositorioMediosPago.getInstance()
				.buscarPorId(Integer.valueOf(request.queryParams("imediosPagos")));

		Proveedor proveedorEgreso = RepositorioEgresos.getInstance()
				.buscarProveedorPorId(Integer.valueOf(request.queryParams("iproveedores")));

		DocumentoComercial docuComercialEgreso = RepositorioEgresos.getInstance()
				.buscarDocumentoComercialPorId(request.queryParams("idocucomercial"));

		Organizacion organizacion = RepositorioEgresos.getInstance()
				.buscarOrganizacionPorId(Integer.valueOf(request.params("idOrganizacion")));

		Egreso egreso = new Egreso(proveedorEgreso, fechaEgreso, medioPagoEgreso, true);

		egreso.setDocuComercial(docuComercialEgreso);
		organizacion.cargarEgreso(egreso);

		RepositorioEgresos.getInstance().crearEgreso(egreso, organizacion);

		response.redirect("/organizaciones/" + request.params(":idOrganizacion") + "/egresos");

		return null;
	}

	public Void eliminarEgreso(Request request, Response response) {

		RepositorioEgresos.getInstance().eliminarEgreso(Integer.valueOf(request.params(":idEgreso")),
				Integer.valueOf(request.params(":idOrganizacion")));

		response.redirect("/organizaciones/" + request.params(":idOrganizacion") + "/egresos");

		return null;
	}
}
