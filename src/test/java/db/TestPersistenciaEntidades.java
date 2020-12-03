package db;


import org.junit.Test;
import dominio.*;

public class TestPersistenciaEntidades {


	@Test
	public void testUsuario() {
		
		Usuario admin = new Usuario("admin", "DdsUtn2020", TipoUsuario.ADMINISTRADOR);
		Usuario estandar = new Usuario("user1", "user12020", TipoUsuario.ESTANDAR);

		Notificacion notificacion = new Notificacion();
		notificacion.agregarMensaje("TEST");
		admin.recibirNotificacion(notificacion);
		
		EntityManagerHelper.getEntityManager().getTransaction().begin();
		EntityManagerHelper.getEntityManager().persist(admin);
        EntityManagerHelper.getEntityManager().persist(estandar);
        EntityManagerHelper.getEntityManager().getTransaction().commit();
        
        
	}
	
	@Test
	public void testEntidades() {
		
		Categoria ong = new Categoria("ONG");
		Categoria cooperativa = new Categoria("Cooperativa");
		
		EntidadJuridica greenpeace = new EntidadJuridica();
		greenpeace.setCategoria(ong);
		greenpeace.setNombreFicticio("Greenpeace");
		
		EntidadJuridica medicosSinFronteras = new EntidadJuridica();
		medicosSinFronteras.setCategoria(ong);
		medicosSinFronteras.setNombreFicticio("Medicos Sin Fronteras");
		
		EntidadBase coopteba = new EntidadBase();
		coopteba.setCategoria(cooperativa);
		coopteba.setNombreFicticio("Coopteba");
		
		EntityManagerHelper.getEntityManager().getTransaction().begin();
		EntityManagerHelper.getEntityManager().persist(ong);
        EntityManagerHelper.getEntityManager().persist(cooperativa);
        EntityManagerHelper.getEntityManager().persist(greenpeace);
        EntityManagerHelper.getEntityManager().persist(medicosSinFronteras);
        EntityManagerHelper.getEntityManager().persist(coopteba);
        EntityManagerHelper.getEntityManager().getTransaction().commit();
        
        
	}
	
}
