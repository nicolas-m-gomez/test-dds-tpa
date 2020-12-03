package model;

import java.util.List;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import dominio.Categoria;

public class RepositorioCategorias implements WithGlobalEntityManager{
	
	  public static RepositorioCategorias instancia = new RepositorioCategorias();

	 
	 
	  public List<Categoria> listar() {
	    return entityManager()
	        .createQuery("from Categoria", Categoria.class) 
	        .getResultList();
	  }
	  
	  public static RepositorioCategorias getInstancia(){
	        if (instancia == null) 
	        	instancia = new RepositorioCategorias(); 
	  
	        return instancia;
	  }
	  
	  public Categoria buscar(Integer id) {
		    return entityManager().find(Categoria.class, id);
		  }
	  
	  public void agregar(Categoria categoria) {
		    entityManager().persist(categoria);
		  }
	  
}
