package model;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import dominio.Organizacion;

public class RepositorioOrganizaciones implements WithGlobalEntityManager {
	private static RepositorioOrganizaciones instancia = new RepositorioOrganizaciones();

	public static RepositorioOrganizaciones getInstance() {
		if (instancia == null)
			instancia = new RepositorioOrganizaciones();

		return instancia;
	}

	private RepositorioOrganizaciones() {
	}

	public Organizacion buscar(Integer id) {
		return entityManager().find(Organizacion.class, id);
	}

	public Organizacion buscarPorEgreso(Integer idEgreso, Integer idOrganizacion) {
		try {
			Query query = entityManager().createNativeQuery(
					"SELECT organizaciones_organizacion_id FROM organizaciones_egresos WHERE egresos_egreso_id = :idEgreso AND organizaciones_organizacion_id = :idOrganizacion");
			query.setParameter("idEgreso", idEgreso);
			query.setParameter("idOrganizacion", idOrganizacion);
			Integer idOrg = (Integer) query.getSingleResult();
			return this.buscar(idOrg);
		} catch (NoResultException e) {
			return null;
		}
	}
}
