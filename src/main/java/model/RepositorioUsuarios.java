package model;

import java.util.ArrayList;

import dominio.Usuario;
import java.util.List;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;


public class RepositorioUsuarios implements WithGlobalEntityManager {

	List<Usuario> usuarios = new ArrayList<Usuario>();
	
    private static RepositorioUsuarios instance = new RepositorioUsuarios();

    public static RepositorioUsuarios getInstance(){
        if (instance == null) 
        	instance = new RepositorioUsuarios(); 
  
        return instance;
     }
    
    private RepositorioUsuarios(){}
    
    public List<Usuario> listar() {
        return entityManager()
            .createQuery("from Usuario", Usuario.class)
            .getResultList();
    }
    
    public Usuario getById(Integer id){
        return entityManager().find(Usuario.class, id);
    }

    public void agregar(Usuario usuario) {
        entityManager().persist(usuario);
    }
    
    
    public Usuario verificarUsuarioYClave(String nombreUsuario, String password) {
    	return this.listar().stream()
    			.filter(u -> u.getPassword().equals(password) && u.getUsername().equals(nombreUsuario))
    			.findFirst().get();
    }
    
    public Usuario hallarUsuario(Integer id) {
    	return this.listar().stream().filter(u -> u.getId().equals(id))
				.findFirst().get();
    }

    public void limpiarRepositorio(){
    	this.usuarios.clear();
    }
	
}
