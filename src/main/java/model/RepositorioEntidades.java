package model;

import java.util.List;
import java.util.stream.Collectors;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import dominio.Categoria;
import dominio.Direccion;
import dominio.EntidadBase;
import dominio.EntidadJuridica;
import dominio.Organizacion;

public class RepositorioEntidades implements WithGlobalEntityManager {
	
	
	public static RepositorioEntidades instancia = new RepositorioEntidades();

	/*
	 * public List<EntidadJuridica> listarEntidadesJuridicas() { return
	 * entityManager() .createQuery("from EntidadJuridica", EntidadJuridica.class)
	 * .getResultList(); } public List<EntidadBase> listarEntidadesBase() { return
	 * entityManager() .createQuery("from EntidadBase", EntidadBase.class)
	 * .getResultList(); } public List<Object> listarTodasLasEntidades(){ return
	 * this.listarEntidadesJuridicas().addAll(this.listarEntidadesBase()); } public
	 * EntidadJuridica buscarEntidadJuridica(Integer codigo) { return
	 * entityManager().find(EntidadJuridica.class, codigo); } public void
	 * agregarEntidadJuridica(EntidadJuridica entidad) {
	 * entityManager().persist(entidad); }
	 */
	
	public static RepositorioEntidades getInstancia() {
		if (instancia == null)
			instancia = new RepositorioEntidades();

		return instancia;
	}
	
	public List<Organizacion> listarEntidades() {
		return entityManager().createQuery("from Organizacion", Organizacion.class).getResultList();
	}
	
	public List<Organizacion> listarSegunCategoria(Integer id_categoria){
		Integer cero = Integer.valueOf(0);
		if(id_categoria.equals(cero)) {
			return this.listarEntidades();
		}else {
		return entityManager().
				createQuery("from Organizacion o where o.categoria.id=:id_categoria", Organizacion.class).
				setParameter("id_categoria", id_categoria).
				getResultList();
		}
	}

	public Organizacion buscarOrganizacion(Integer codigo) {
		return entityManager().find(Organizacion.class, codigo);
	}
	
	public List<EntidadJuridica> listarEntidadesJuridicas(){
		return entityManager().createQuery("from EntidadJuridica", EntidadJuridica.class).getResultList();
	}
	
	public List<EntidadJuridica> listarEntidadesJuridicasConEspacio(){
		List<EntidadJuridica> entidadesConEspacio = this.listarEntidadesJuridicas()
								.stream().filter(entidadJuridica -> entidadJuridica.tieneEspacioParaEntidadesBase())
								.collect(Collectors.toList());
		return entidadesConEspacio;
	}

	public EntidadJuridica buscarEntidadJuridica(Integer id) {
		return entityManager().find(EntidadJuridica.class, id);
	}
	
	public void agregarOrganizacion(Organizacion entidad) {
		entityManager().persist(entidad);
	}

	public void agregarEntidadBase(EntidadBase entidadBase) {
	    entityManager().persist(entidadBase);
	}

	public void agregarEntidadJuridica(EntidadJuridica entidadJuridica) {
	    entityManager().persist(entidadJuridica);
	}
	
	public void agregarDireccionEntidad(Direccion direccion) {
		entityManager().persist(direccion);
	}
}