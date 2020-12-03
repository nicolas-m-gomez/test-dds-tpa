package model;

import java.util.ArrayList;
import java.util.List;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import dominio.Notificacion;
import dominio.Usuario;
import dominio.dao.DAO;
import dominio.dao.DAOHibernate;

public class RepositorioNotificaciones implements WithGlobalEntityManager {

	List<Notificacion> notificaciones = new ArrayList<Notificacion>();
	DAO<Notificacion> dao = new DAOHibernate<>(Notificacion.class);
	
    private static RepositorioNotificaciones instance = new RepositorioNotificaciones();

	
    public static RepositorioNotificaciones getInstance(){
        if (instance == null) 
        	instance = new RepositorioNotificaciones(); 
  
        return instance;
     }
    
    private RepositorioNotificaciones(){}
    
    public List<Notificacion> obtenerNotificaciones(Usuario usuario)
    {
            List<Notificacion> notificaciones = entityManager()
                .createQuery("from Notificacion", Notificacion.class)
                .getResultList();
           
            return notificaciones;
    }
    
    public Notificacion buscarNotificacion(Integer id)
    {
            return entityManager().find(Notificacion.class, id);
   }
    
    
    public void borrarNotificacion(Notificacion notificacion)
    {
    	entityManager().getTransaction().begin();
        entityManager().remove(notificacion);
        entityManager().getTransaction().commit();
    	      
    } 
    
    public void limpiarRepositorio()
    {
    	this.notificaciones.clear();
    }
    
	
}
