package dominio;

import java.util.Comparator;
import java.util.List;


public class CriterioSeleccionPresupuestoMenorValor implements CriterioSeleccionPresupuesto{

	Comparator<Presupuesto> comparador = Comparator.comparing(Presupuesto::precioTotal);
	
	public Presupuesto getPresupuesto(Egreso egreso)
	{
		List <Presupuesto> presupuestos = egreso.getPresupuestos();
		return presupuestos.stream().min(comparador).get(); 
	
	}

}
