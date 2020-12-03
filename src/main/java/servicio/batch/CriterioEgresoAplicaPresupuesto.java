package servicio.batch;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import dominio.Egreso;
import dominio.ItemEgreso;
import dominio.ItemPresupuesto;
import dominio.Notificacion;
import dominio.Presupuesto;

public class CriterioEgresoAplicaPresupuesto implements CriterioValidacionEgreso {
	
	@Override
	public Boolean esValido (Egreso egresoAValidar, Notificacion notificacion) {

		Boolean esEgresoValido = true;
		
		if (!hayPresupuestoCoincidente(egresoAValidar))
				{
					notificacion.agregarMensaje("El egreso no se aplicó sobre ninguno de los presupuestos");
					esEgresoValido = false;
				}
		return esEgresoValido;
	}
	
	private Boolean hayPresupuestoCoincidente(Egreso egreso)
	{
		return (egreso.getPresupuestos().stream().anyMatch(
				(presupuesto)-> datosPrincipalesCoinciden(egreso, presupuesto)
					&& detallesCoinciden(egreso,presupuesto)));
	}
	
	private Boolean datosPrincipalesCoinciden(Egreso egreso, Presupuesto presupuesto) {
		
		return(egreso.getDocuComercial().equals(presupuesto.getDocumentoComercial()) &&
				egreso.getProveedor().equals(presupuesto.getProveedor()));
	}
	
	private Boolean detallesCoinciden(Egreso egreso, Presupuesto presupuesto) {
		
		return(cantidadesItemsCoinciden(egreso, presupuesto) &&
				itemsCoinciden(egreso,presupuesto));
	}
	
	private Boolean cantidadesItemsCoinciden(Egreso egreso, Presupuesto presupuesto) {
		return (presupuesto.getItems().size() == egreso.getItems().size());
	}

	private Boolean itemsCoinciden(Egreso egreso, Presupuesto presupuesto) {
		
		List<ItemPresupuesto> itemsPresupuesto = presupuesto.getItems().stream().collect(Collectors.toList());
		List<ItemEgreso> itemsEgreso = egreso.getItems().stream().collect(Collectors.toList());

		Collections.sort(itemsPresupuesto);
		Collections.sort(itemsEgreso);

		return itemsPresupuesto.equals(itemsEgreso);
	}
}
