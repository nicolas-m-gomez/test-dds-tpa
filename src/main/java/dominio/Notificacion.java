package dominio;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.OneToOne;
import javax.persistence.ElementCollection;
import javax.persistence.CollectionTable;
import javax.persistence.Column;

@Entity
@Table (name = "notificaciones")
public class Notificacion {
	
	@Id
	@GeneratedValue
	@Column(name = "notificacion_id")
	private Integer id;
	

	@ElementCollection
	@CollectionTable(
	        name = "mensajes",
	        joinColumns=@JoinColumn(name = "notificacion_id", referencedColumnName = "notificacion_id")
	    )
	private List<String> mensajes;
	
	@OneToOne
	@JoinColumn(name = "egreso_id")
	private Egreso egreso;
	
	@Column(name = "notificacion_fecha")
	private LocalDate fecha;

	
	public Notificacion () {
		super();
	}
	
	public Notificacion (Egreso egreso) {
		super();
		this.egreso = egreso;
		this.fecha = LocalDate.now();
	}
		
	public void agregarMensaje(String mensaje)
	{
		if (this.mensajes == null)
			{
				this.mensajes = new ArrayList<String>();
			}
		this.mensajes.add(mensaje);
	}
	
	public List<String> getMensajes() {
		return this.mensajes;
	}
	
	public Egreso getEgreso() {
		return this.egreso;
	}
	
	public Integer getEgresoId() {
		return this.egreso.getId();
	}
	
	public LocalDate getFecha() {
		return fecha;
	}
	
	public EstadoEgreso getEstadoEgreso() {
		return this.egreso.getEstado();
	}
	
	public String getTitulo() {
		return "Fecha: " + fecha.toString() + " Egreso: " + egreso.getId() ;
	}
	


	public Integer getId() {
		return id;
	}

}
