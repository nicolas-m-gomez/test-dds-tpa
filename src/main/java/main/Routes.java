package main;

import static spark.Spark.after;
import static spark.Spark.before;
import static spark.Spark.halt;

import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;

import controllers.CategoriasController;
import controllers.EgresosController;
import controllers.FormulariosController;
import controllers.HomeController;
import controllers.ItemsEgresoController;
import controllers.LogoutController;
import controllers.NotificacionesController;
import controllers.OpcionesController;
import controllers.PresupuestosController;
import controllers.UsuariosController;
import spark.Spark;
import spark.template.handlebars.HandlebarsTemplateEngine;

public class Routes {

	public static void main(String[] args) {
		System.out.println("Iniciando servidor");

		Spark.port(getHerokuAssignedPort());
		Spark.staticFileLocation("/public");

		new Bootstrap().run();

		HandlebarsTemplateEngine engine = new HandlebarsTemplateEngine();

		HomeController homeController = new HomeController();
		UsuariosController usuariosController = new UsuariosController();
		NotificacionesController notificacionesController = new NotificacionesController();
		OpcionesController opcionController = new OpcionesController();
		LogoutController logoutController = new LogoutController();
		EgresosController egresosController = new EgresosController();
		ItemsEgresoController itemsEgresoController = new ItemsEgresoController();
		FormulariosController formulariosController = new FormulariosController();
		CategoriasController categoriaController = new CategoriasController();
		PresupuestosController presupuestosController = new PresupuestosController();

		before((request, response) -> {
			boolean autenticado = request.session().attribute("idUsuario") != null;
			if (!autenticado && (request.pathInfo().contains("notificaciones")
					|| (request.pathInfo().contains("organizaciones")))) {
				halt(401, "Acceso denegado");
			}
		});

		Spark.get("/", (request, response) -> homeController.getHome(request, response), engine);

		Spark.get("/login", (request, response) -> usuariosController.getFormularioLogin(request, response), engine);
		Spark.get("/error", (request, response) -> homeController.getPaginaError(request, response), engine);
		Spark.post("/login", (request, response) -> usuariosController.iniciarSesion(request, response));
		Spark.get("/organizaciones", opcionController::mostrarIndex, engine);
		Spark.get("/logout", (request, response) -> logoutController.cerrarSesion(request, response));
		Spark.get("/categoria-entidades",
				(request, response) -> categoriaController.getFormularioEntidadesPorCategoria(request, response),
				engine);

		Spark.get("/organizaciones/:idOrganizacion/egresos/:idEgreso/presupuestos/:idPresupuesto/items",
				(request, response) -> presupuestosController.getItemsPresupuesto(request, response), engine);
		
		Spark.get("/organizaciones/:idOrganizacion/egresos/:idEgreso/presupuestos/:idPresupuesto/items/nuevo_item",
				(request, response) -> presupuestosController.cargarItemPresupuesto(request, response), engine);
		
		Spark.post("/organizaciones/:idOrganizacion/egresos/:idEgreso/presupuestos/:idPresupuesto/items/nuevo_item",
				(request, response) -> presupuestosController.guardarItemPresupuesto(request, response));
		
		Spark.get("/organizaciones/:idOrganizacion/egresos/:idEgreso/presupuestos/:idPresupuesto/items/:idItemPresupuesto",
				(request, response) -> presupuestosController.getItemPresupuesto(request, response), engine);
		
		Spark.post("/organizaciones/:idOrganizacion/egresos/:idEgreso/presupuestos/:idPresupuesto/items/:idItemPresupuesto",
				(request, response) -> presupuestosController.borrarItemPresupuesto(request, response));
		
		Spark.get("/organizaciones/:idOrganizacion/egresos/:idEgreso/items/nuevo_item",
				(request, response) -> itemsEgresoController.cargarItemEgreso(request, response), engine);

		Spark.post("/organizaciones/:idOrganizacion/egresos/:idEgreso/items/nuevo_item",
				(request, response) -> itemsEgresoController.guardarItemEgreso(request, response));
		
		Spark.get("/organizaciones/:idOrganizacion/egresos/:idEgreso/presupuestos/nuevo_presupuesto",
				(request, response) -> presupuestosController.cargarPresupuesto(request, response), engine);
		
		Spark.post("/organizaciones/:idOrganizacion/egresos/:idEgreso/presupuestos/nuevo_presupuesto",
				(request, response) -> presupuestosController.guardarPresupuesto(request, response));

		Spark.get("/organizaciones/:idOrganizacion/egresos/:idEgreso/items/:idItemEgreso",
				(request, response) -> itemsEgresoController.getItemEgreso(request, response), engine);

		Spark.post("/organizaciones/:idOrganizacion/egresos/:idEgreso/items/:idItemEgreso",
				(request, response) -> itemsEgresoController.eliminarItemEgreso(request, response));

		Spark.get("/organizaciones/:idOrganizacion/egresos/:idEgreso/presupuestos/:idPresupuesto",
				(request, response) -> presupuestosController.getPresupuesto(request, response), engine);
		
		Spark.post("/organizaciones/:idOrganizacion/egresos/:idEgreso/presupuestos/:idPresupuesto",
				(request, response) -> presupuestosController.borrarPresupuesto(request, response));

		Spark.get("/organizaciones/:idOrganizacion/egresos/:idEgreso/nuevo_item",
				(request, response) -> egresosController.getEgreso(request, response), engine);

		Spark.get("/organizaciones/:idOrganizacion/egresos/:idEgreso/items",
				(request, response) -> itemsEgresoController.getItemsEgreso(request, response), engine);

		Spark.get("/organizaciones/:idOrganizacion/egresos/:idEgreso/presupuestos",
				(request, response) -> presupuestosController.getPresupuestos(request, response), engine);

		Spark.get("organizaciones/:idOrganizacion/egresos/nuevo_egreso",
				(request, response) -> egresosController.cargarEgreso(request, response), engine);

		Spark.post("organizaciones/:idOrganizacion/egresos/nuevo_egreso",
				(request, response) -> egresosController.guardarEgreso(request, response));

		Spark.get("/organizaciones/:idOrganizacion/egresos/:idEgreso",
				(request, response) -> egresosController.getEgreso(request, response), engine);

		Spark.post("/organizaciones/:idOrganizacion/egresos/:idEgreso",
				(request, response) -> egresosController.eliminarEgreso(request, response));

		Spark.get("/organizaciones/:idOrganizacion/egresos",
				(request, response) -> egresosController.getEgresos(request, response), engine);

		// Notificaciones rutas

		Spark.get("/notificaciones",
				(request, response) -> notificacionesController.getNotificaciones(request, response, engine));
		Spark.get("/notificaciones/:id",
				(request, response) -> notificacionesController.getNotificacion(request, response, engine));
		Spark.get("/notificaciones/borrar/:id",
				(request, response) -> notificacionesController.borrarNotificacion(request, response, engine));
		Spark.post("/notificaciones", (request, response) -> notificacionesController.filtrar(request, response));

		// Cargado de entidades rutas

		Spark.get("/organizaciones/nueva/entidad-juridica", (request, response) -> formulariosController
				.getFormularioCreacionDeEntidadesJuridicas(request, response), engine);
		Spark.get("/organizaciones/nueva/entidad-base",
				(request, response) -> formulariosController.getFormularioCreacionDeEntidadesBase(request, response),
				engine);
		Spark.post("/organizaciones/nueva/entidad-juridica",
				(request, response) -> formulariosController.crearEntidadJuridica(request, response));
		Spark.post("/organizaciones/nueva/entidad-base",
				(request, response) -> formulariosController.crearEntidadBase(request, response));

		after((request, response) -> {
			PerThreadEntityManagers.getEntityManager();
			PerThreadEntityManagers.closeEntityManager();
		});
	}
	
	static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 8080; //return default port if heroku-port isn't set (i.e. on localhost)
    }
}
