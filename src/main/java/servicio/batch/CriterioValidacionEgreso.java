package servicio.batch;

import dominio.Egreso;
import dominio.Notificacion;

public interface CriterioValidacionEgreso {
	
	public Boolean esValido(Egreso egreso, Notificacion notificacion);

}
