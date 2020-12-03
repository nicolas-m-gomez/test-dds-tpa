package dominio;

import dominio.exception.EgresoRechazadoException;

public class ValidarMontoEgreso extends ReglaNegocio{
	CategoriaReglaNegocio categoria;
	
	public ValidarMontoEgreso(CategoriaReglaNegocio categoria) {
		this.categoria = categoria;
	}

	@Override
	public CategoriaReglaNegocio getCategoriaReglaNegocio() {
		return categoria;
	}

	@Override
	public void verificarReglaNegocio(Organizacion entidad, Egreso egreso) {
		if(egreso.precioTotal()>entidad.getMontoMaximoEgreso()) {
			throw new EgresoRechazadoException("Se rechaza egreso por superar monto maximo establecido.");
		}
	}

}
