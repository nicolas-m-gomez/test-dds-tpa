package model;

import java.util.List;

import org.uqbarproject.jpa.java8.extras.EntityManagerOps;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

import dominio.Egreso;
import dominio.Importe;
import dominio.ItemEgreso;
import dominio.Moneda;

public class RepositorioItemsEgreso implements WithGlobalEntityManager, EntityManagerOps, TransactionalOps {
	private static RepositorioItemsEgreso instancia = new RepositorioItemsEgreso();

	public static RepositorioItemsEgreso getInstance() {
		if (instancia == null)
			instancia = new RepositorioItemsEgreso();

		return instancia;
	}

	private RepositorioItemsEgreso() {
	}

	public List<Moneda> getMonedas() {
		return entityManager().createQuery("from Moneda", Moneda.class).getResultList();
	}

	public Moneda buscarMonedaPorId(String idMoneda) {
		return entityManager().find(Moneda.class, idMoneda);
	}

	public void crearItemEgreso(Egreso egreso, ItemEgreso itemEgreso, Importe importe) {
		egreso.getItems().add(itemEgreso);
		withTransaction(() -> {
			persist(importe);
			persist(itemEgreso);
			persist(egreso);
		});
	}
	
	public void eliminarItemEgreso(Egreso egreso, ItemEgreso itemEgreso) {
		Importe importe = itemEgreso.getImporte();
		egreso.getItems().remove(itemEgreso);
		withTransaction(() -> {
			remove(importe);
			remove(itemEgreso);
			persist(egreso);
		});
	}
}
