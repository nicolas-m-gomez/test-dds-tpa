package servicio.batch;

import java.util.Collections;
import java.util.List;

import dominio.Egreso;
import dominio.ItemEgreso;
import dominio.ItemPresupuesto;
import dominio.Notificacion;
import dominio.Presupuesto;


public class CriterioValidacionSeleccionCorrecta implements CriterioValidacionEgreso{

	@Override
	public Boolean esValido(Egreso egreso, Notificacion notificacion)
	{
		Boolean esEgresoValido = true;
		Presupuesto presupuestoCorrecto = egreso.getCriterioSeleccionPresupuesto().getPresupuesto(egreso);
		
		if (!sonCompatibles(egreso, presupuestoCorrecto)){
			notificacion.agregarMensaje("El egreso indicado no se aplicó sobre un presupuesto acorde al criterio de selección");
			esEgresoValido = false;
		}
	
		return esEgresoValido;
	}
	
	public Boolean sonCompatibles(Egreso egreso, Presupuesto presupuestoCorrecto)
	{
		return tienenMismaCantidadItems(egreso.getItems(), presupuestoCorrecto.getItems())
		&& tienenMismosItems(egreso.getItems(), presupuestoCorrecto.getItems());
	}
	
	
	public Boolean tienenMismaCantidadItems(List<ItemEgreso> itemsEgreso, List<ItemPresupuesto> itemsPresupuesto) {
			return itemsPresupuesto.size() == itemsEgreso.size();
	}

	public Boolean tienenMismosItems(List<ItemEgreso> itemsEgreso, List<ItemPresupuesto> itemsPresupuesto) {
	
		Collections.sort(itemsPresupuesto);
		Collections.sort(itemsEgreso);
		
		return (itemsPresupuesto.equals(itemsEgreso));
	}
	
}
