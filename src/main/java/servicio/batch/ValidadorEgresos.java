package servicio.batch;

import java.util.List;
import java.util.stream.Collectors;

import dominio.Egreso;
import dominio.EstadoEgreso;
import dominio.Notificacion;

public class ValidadorEgresos {
	
	public void validar(List<Egreso> egresos, List<CriterioValidacionEgreso> criterios)
	{
				
		List<Egreso> egresosAValidar = egresos.stream().filter(egreso -> egreso.getRequierePresupuesto() 
				&& egreso.getEstado().equals(EstadoEgreso.PENDIENTE)).collect(Collectors.toList());
		
		egresosAValidar.stream().forEach(egreso-> {
				
			Notificacion notificacion = new Notificacion(egreso);
			
			if (esEgresoValido(egreso,criterios,notificacion))
			{
				notificacion.agregarMensaje("Se ha APROBADO el egreso");
				egreso.cambiarEstado(EstadoEgreso.APROBADO,notificacion);
			} else {
				egreso.cambiarEstado(EstadoEgreso.RECHAZADO,notificacion);
			}
		}
		);
	}	
	
	private Boolean esEgresoValido(Egreso egreso, List<CriterioValidacionEgreso> criterios, Notificacion notificacion)
	{
		
		Boolean esValido = true;
		
        for (CriterioValidacionEgreso criterio: criterios)  
        { 
            if (!criterio.esValido(egreso, notificacion)) 
            { 
            	esValido = false;
            } 
        } 
	
		return esValido;
	}


}
