//FormulariosController.java

package controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

import dominio.Categoria;
import dominio.Direccion;
import dominio.EntidadBase;
import dominio.EntidadJuridica;
import dominio.TipoEmpresa;
import dominio.Ubicacion;
import model.RepositorioCategorias;
import model.RepositorioEntidades;
import model.RepositorioUsuarios;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class FormulariosController implements WithGlobalEntityManager, TransactionalOps {

	RepositorioUsuarios repositorio = RepositorioUsuarios.getInstance();
	RepositorioCategorias categorias = RepositorioCategorias.getInstancia();
	RepositorioEntidades entidades = RepositorioEntidades.getInstancia();
	SessionController sessionController = new SessionController();

	public ModelAndView getFormularioCreacionDeEntidadesJuridicas(Request request, Response response) {
		Map<String, Object> modelo = new HashMap<>();
		List<Categoria> _categorias = RepositorioCategorias.instancia.listar();

		sessionController.validarSesion(request.session(), response);

		modelo.put("opciones",
				sessionController.opcionesDisponiblesSegunUsuario(request.session().attribute("idUsuario")));

		modelo.put("categorias", _categorias);

		return new ModelAndView(modelo, "formularioEJuridica.html.hbs");
	}

	public ModelAndView getFormularioCreacionDeEntidadesBase(Request request, Response response) {

		Map<String, Object> modelo = new HashMap<>();

		List<Categoria> categorias = RepositorioCategorias.instancia.listar();
		List<EntidadJuridica> entidadesJuridicas = RepositorioEntidades.instancia.listarEntidadesJuridicasConEspacio();

		sessionController.validarSesion(request.session(), response);

		modelo.put("opciones",
				sessionController.opcionesDisponiblesSegunUsuario(request.session().attribute("idUsuario")));
		modelo.put("categorias", categorias);

		modelo.put("entidadesJuridicas", entidadesJuridicas);

		return new ModelAndView(modelo, "formularioEBase.html.hbs");
	}

	public Void crearEntidadJuridica(Request request, Response response) {
		String nombre = request.queryParams("nombre");

		Categoria categoria = RepositorioCategorias.instancia
				.buscar(Integer.parseInt(request.queryParams("categoria")));
		Integer montoMax = Integer.parseInt(request.queryParams("montoMax"));

		String pais = request.queryParams("pais");
		String cuidad = request.queryParams("cuidad");
		String provincia = request.queryParams("provincia");

		Ubicacion ubicacion = new Ubicacion(pais, provincia, cuidad);

		String calle = request.queryParams("calle");
		Integer altura = Integer.parseInt(request.queryParams("altura"));
		String piso = request.queryParams("piso");
		String codigoPostal = request.queryParams("codigo_postal");

		Direccion direccion = new Direccion(calle, altura, piso, codigoPostal, ubicacion);

		Integer cuit = Integer.parseInt(request.queryParams("cuit"));
		Integer codigoIGJ = Integer.parseInt(request.queryParams("codigoIGJ"));
		Integer entidadesMax = Integer.parseInt(request.queryParams("entidadesMax"));
		String razonSocial = request.queryParams("razon_social");

		EntidadJuridica nuevaJuridica = new EntidadJuridica();

		TipoEmpresa tipoEmpresa = TipoEmpresa.valueOf(request.queryParams("tipoEmpresa"));

		nuevaJuridica.setRazonSocial(razonSocial);
		nuevaJuridica.setNombreFicticio(nombre);
		nuevaJuridica.setCategoria(categoria);
		nuevaJuridica.setMontoMaximoEgreso(montoMax);
		nuevaJuridica.setCantidadMaximaEntidadesBase(entidadesMax);
		nuevaJuridica.setCuit(cuit);
		nuevaJuridica.setCodigoIGJ(codigoIGJ);
		nuevaJuridica.setDireccion(direccion);
		nuevaJuridica.setTipoEmpresa(tipoEmpresa);
		RepositorioEntidades.instancia.agregarDireccionEntidad(direccion);
		RepositorioEntidades.instancia.agregarEntidadJuridica(nuevaJuridica);

		response.redirect("/home");// cuando se pueda hay que cambiarlo al redirecct de los detalles de la entidad
									// recien creada TODO
		return null;
	}

	public Void crearEntidadBase(Request request, Response response) {

		String nombre = request.queryParams("nombre");

		Categoria categoria = RepositorioCategorias.instancia
				.buscar(Integer.parseInt(request.queryParams("categoria")));

		Integer montoMax = Integer.parseInt(request.queryParams("montoMax"));
		String descripcion = request.queryParams("descripcion");

		Boolean permiteAgregarse = (1 == Integer.parseInt(request.queryParams("permiteAgregarse")));

		EntidadBase nuevaBase = new EntidadBase();

		nuevaBase.setCategoria(categoria);
		nuevaBase.setNombreFicticio(nombre);
		nuevaBase.setMontoMaximoEgreso(montoMax);
		nuevaBase.setDescripcion(descripcion);
		nuevaBase.setPermiteAgregarseAEntidadJuridica(permiteAgregarse);

		withTransaction(() -> {
			RepositorioEntidades.instancia.agregarEntidadBase(nuevaBase);
			System.out.println("Se añadio la entidad base");
			if (permiteAgregarse) {
				EntidadJuridica juridica = RepositorioEntidades.instancia
						.buscarEntidadJuridica(Integer.parseInt(request.queryParams("entidadJuridica")));
				juridica.agregarEntidadBase(nuevaBase);
				System.out.println("y se añadio la entidad base a la entidad Juridica");
			}

		});

		response.redirect("/home");// cuando se pueda hay que cambiarlo al redirecct de los detalles de la entidad
									// recien creada TODO
		return null;
	}

}
