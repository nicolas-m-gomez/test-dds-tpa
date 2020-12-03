package servicio.batch;

import dominio.Egreso;
import dominio.Notificacion;

public class CriterioEgresoCantidadPresupuestos implements CriterioValidacionEgreso{

	public static final int cantidadExigidaPrespuestos = 3;
	
	@Override
	public Boolean esValido(Egreso egreso, Notificacion notificacion) {
		
		Boolean esEgresoValido = true;
		
		if (egreso.cantidadPresupuestosAsociados() < cantidadExigidaPrespuestos) {
			notificacion.agregarMensaje("El egreso no tiene la cantidad exigida de presupuestos");
			esEgresoValido = false;
		}
		
		return esEgresoValido;
		
	}

}
