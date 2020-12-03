package model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.uqbarproject.jpa.java8.extras.EntityManagerOps;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

import dominio.DocumentoComercial;
import dominio.Egreso;
import dominio.ItemEgreso;
import dominio.Organizacion;
import dominio.Presupuesto;
import dominio.Proveedor;
import servicio.viewmodel.ItemEgresoViewModel;

public class RepositorioEgresos implements WithGlobalEntityManager, EntityManagerOps, TransactionalOps {
	List<Egreso> egresos = new ArrayList<Egreso>();

	private static RepositorioEgresos instancia = new RepositorioEgresos();

	public static RepositorioEgresos getInstance() {
		if (instancia == null)
			instancia = new RepositorioEgresos();

		return instancia;
	}

	private RepositorioEgresos() {
	}

	public List<Egreso> obtenerEgresos() {
		// return this.egresos;
		return entityManager().createQuery("from Egreso", Egreso.class).getResultList();
	}

	public void agregarEgreso(Egreso egreso) {
		this.egresos.add(egreso);
	}

	public void limpiarRepositorio() {
		this.egresos.clear();
	}

	public List<Egreso> listarEgresosDeOrgnizacion(Integer id) {
		return entityManager().find(Organizacion.class, id).getEgresos();
	}

	public Egreso buscar(Integer id) {
		return entityManager().find(Egreso.class, id);
	}

	public List<Proveedor> listarProveedores() {
		return entityManager().createQuery("from Proveedor", Proveedor.class).getResultList();
	}

	public List<DocumentoComercial> listarDocumentosComerciales() {
		return entityManager().createQuery("from DocumentoComercial", DocumentoComercial.class).getResultList();
	}

	public void crearEgreso(Egreso egreso, Organizacion organizacion) {
		withTransaction(() -> {
			persist(egreso);
			persist(organizacion);
		});
	}

	public List<ItemEgresoViewModel> getItemsEgresosViewModel(List<ItemEgreso> itemsEgreso) {
		List<ItemEgresoViewModel> itemsEgresoVM = new ArrayList<>();
		for (ItemEgreso item : itemsEgreso) {
			itemsEgresoVM.add(new ItemEgresoViewModel(item));
		}
		return itemsEgresoVM;
	}

	public ItemEgreso getItemEgreso(Egreso egreso, Integer idItem) {
		return egreso.getItems().stream().filter(item -> item.getId().equals(idItem)).collect(Collectors.toList())
				.get(0);
	}

	public Proveedor buscarProveedorPorId(Integer idProveedor) {
		return entityManager().find(Proveedor.class, idProveedor);
	}

	public DocumentoComercial buscarDocumentoComercialPorId(String idDocumentoComercial) {
		return entityManager().find(DocumentoComercial.class, idDocumentoComercial);
	}

	public Organizacion buscarOrganizacionPorId(Integer idOrganizacion) {
		return entityManager().find(Organizacion.class, idOrganizacion);
	}

	public void eliminarEgreso(Integer idEgreso, Integer idOrganizacion) {
		Egreso egresoAEliminar = this.buscar(idEgreso);
		Organizacion organizacion = RepositorioOrganizaciones.getInstance().buscar(idOrganizacion);

		organizacion.getEgresos().remove(egresoAEliminar);

		withTransaction(() -> {
			persist(organizacion);
			remove(egresoAEliminar);
		});
	}

	public List<Presupuesto> getPresupuestosDeEgreso(Egreso egreso) {
		return egreso.getPresupuestos();
	}
}
