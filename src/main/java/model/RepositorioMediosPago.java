package model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import dominio.MedioPago;

public class RepositorioMediosPago implements WithGlobalEntityManager {
	List<MedioPago> mediosPago = new ArrayList<MedioPago>();
	
	private static RepositorioMediosPago instancia = new RepositorioMediosPago();

	public static RepositorioMediosPago getInstance() {
		if (instancia == null)
			instancia = new RepositorioMediosPago();

		return instancia;
	}

	private RepositorioMediosPago() {
	}
	
	public List<MedioPago> listarMediosPago(){
		CriteriaBuilder builder = entityManager().getCriteriaBuilder();
        CriteriaQuery<MedioPago> critera = builder.createQuery(MedioPago.class);
        critera.from(MedioPago.class);
        return entityManager().createQuery(critera).getResultList();        
	}
	
	public MedioPago buscarPorId(Integer id) {
		return entityManager().find(MedioPago.class, id);
	}
}
