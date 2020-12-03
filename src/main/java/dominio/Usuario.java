package dominio;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;


import javax.persistence.OneToMany;

@Entity
@Table (name = "usuarios")
public class Usuario {
	
	@Id
	@GeneratedValue
	@Column(name = "usuario_id")
	private Integer id;
	
	@Column(name = "usuario")
	private String username;

	@Column(name = "usuario_pass")
	private String password;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "usuario_tipo")
	private TipoUsuario tipoUsuario;
	
	@OneToMany(cascade=CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "usuario_id")
	private List<Notificacion> notificaciones = new ArrayList<Notificacion>();


	public Usuario() {
		super();
	}	
	
	public Usuario(String username, String password, TipoUsuario tipoUsuario) {
		super();
		this.username = username;
		this.password = password;
		this.tipoUsuario = tipoUsuario;
	}

	public TipoUsuario tipoUsuario() {
		return this.tipoUsuario;
	}
	

	public void recibirNotificacion(Notificacion notificacion) {
		notificaciones.add(notificacion);
	}
	
	public Notificacion verNotificacionRecienteDe(Egreso egreso) {
		return notificaciones.stream()
						.filter(notificacion->notificacion.getEgreso().equals(egreso))
						.max(Comparator.comparing(Notificacion::getFecha))
						.get();
	}
	
	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public Integer getId() {
		return id;
	}
	
	public List<Notificacion> getNotificaciones() {
		return notificaciones;
	}
	
	public TipoUsuario getTipoUsuario() {
		return this.tipoUsuario;
	}
	
}