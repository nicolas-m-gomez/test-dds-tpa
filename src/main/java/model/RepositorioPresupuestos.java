package model;

import java.util.ArrayList;
import java.util.List;

import org.uqbarproject.jpa.java8.extras.EntityManagerOps;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

import dominio.Egreso;
import dominio.Importe;
import dominio.ItemPresupuesto;
import dominio.Presupuesto;
import servicio.viewmodel.ItemPresupuestoViewModel;

public class RepositorioPresupuestos implements WithGlobalEntityManager, EntityManagerOps, TransactionalOps {

	private static RepositorioPresupuestos instancia = new RepositorioPresupuestos();

	public static RepositorioPresupuestos getInstance() {
		if (instancia == null)
			instancia = new RepositorioPresupuestos();

		return instancia;
	}

	private RepositorioPresupuestos() {
	}

	public Presupuesto buscarPresupuestoPorId(Integer idPresupuesto) {
		return entityManager().find(Presupuesto.class, idPresupuesto);
	}

	public void borrarPresupuesto(Egreso egreso, Presupuesto presupuesto) {
		egreso.getPresupuestos().remove(presupuesto);		
		
		withTransaction(() -> {
			persist(egreso);
			remove(presupuesto);
		});
	}

	public ItemPresupuesto buscarItemPresupuestoPorId(Integer idItemPresupuesto) {
		return entityManager().find(ItemPresupuesto.class, idItemPresupuesto);
	}

	public List<ItemPresupuestoViewModel> getItemsPresupuestoViewModel(List<ItemPresupuesto> itemsPresupuesto) {
		List<ItemPresupuestoViewModel> itemsPresupuestoVM = new ArrayList<>();
		for (ItemPresupuesto item : itemsPresupuesto) {
			itemsPresupuestoVM.add(new ItemPresupuestoViewModel(item));
		}
		return itemsPresupuestoVM;
	}

	public void crearPresupuesto(Egreso egreso, Presupuesto presupuesto) {
		withTransaction(() -> {
			persist(presupuesto);
			persist(egreso);
		});
	}

	public void crearItemPresupuesto(Presupuesto presupuesto, ItemPresupuesto itemPresupuesto, Importe importe) {
		presupuesto.getItems().add(itemPresupuesto);
		withTransaction(() -> {
			persist(importe);
			persist(itemPresupuesto);
			persist(presupuesto);
		});
	}

	public void borrarItemPresupuesto(Presupuesto presupuesto, ItemPresupuesto itemPresupuesto) {
		Importe importe = itemPresupuesto.getImporte();
		presupuesto.getItems().remove(itemPresupuesto);
		withTransaction(() -> {
			remove(importe);
			remove(itemPresupuesto);
			persist(presupuesto);
		});
	}
}
