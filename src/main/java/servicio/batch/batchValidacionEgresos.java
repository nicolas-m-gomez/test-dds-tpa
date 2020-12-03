package servicio.batch;

import java.util.ArrayList;
import java.util.List;

import dominio.Egreso;
import model.RepositorioEgresos;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;


public class batchValidacionEgresos implements Job {
	
	public void execute(JobExecutionContext context)
	throws JobExecutionException {
		System.out.println("Iniciando validador...");
		List<CriterioValidacionEgreso> criterios = new ArrayList<CriterioValidacionEgreso>();
		criterios.add(new CriterioEgresoAplicaPresupuesto());
		criterios.add(new CriterioEgresoCantidadPresupuestos());
		criterios.add(new CriterioValidacionSeleccionCorrecta());
		
		System.out.println("Obteniendo egresos...");
		List<Egreso> egresos = RepositorioEgresos.getInstance().obtenerEgresos();
		
		System.out.println("Validando egresos...");
		ValidadorEgresos validador = new ValidadorEgresos();
		validador.validar(egresos, criterios);
		
		System.out.println("Finalizando validador...");
	}
	
}


