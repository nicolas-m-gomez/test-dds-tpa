package dominio;
import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ReporteEgresos{

	
	public void hacerReporte(List<Egreso> listaEgresos, String nombreFicticio)
	{
		HashMap<String, Double> listaReportePorEtiqueta = obtenerTotalesPorEtiqueta(listaEgresos);

		try {
			PrintWriter writer = new PrintWriter("Reporte/ReporteEntidad.txt", "UTF-8");
			writer.println("Reporte de Entidad de gastos por Etiqueta");
			writer.println("-----------");
			writer.println("Nombre Entidad Base:"+ nombreFicticio);
			writer.println();
			writer.println(listaReportePorEtiqueta);
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public HashMap<String, Double> obtenerTotalesPorEtiqueta(List<Egreso> egresos)
	{
		
		HashMap<String, Double> listaReportePorEtiqueta = new HashMap<String, Double>();
        Set<String> listaEtiquetasExistentes = egresos.stream()
      	      .map(Egreso::getEtiquetas)
      	      .flatMap(Collection::stream)
      	      .collect(Collectors.toSet());
        
        for (String _etiqueta:listaEtiquetasExistentes) {

			List<Egreso> listaFiltrada = egresos.stream().filter( egreso -> egreso.tieneEtiqueta(_etiqueta)).collect(Collectors.toList());
			Double total = listaFiltrada.stream().mapToDouble(egreso->egreso.precioTotal()).sum();

			listaReportePorEtiqueta.put(_etiqueta,total);
		}
        
        return listaReportePorEtiqueta;
	}
	
	
}