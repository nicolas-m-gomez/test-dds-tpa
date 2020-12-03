package dominio;

import dominio.exception.EntidadBaseRechazadaException;

public class ValidarAgregarEntidadBase extends ReglaNegocio {
	

	CategoriaReglaNegocio categoria;
	
	public ValidarAgregarEntidadBase(CategoriaReglaNegocio categoria) {
		this.categoria = categoria;
	}

	@Override
	public CategoriaReglaNegocio getCategoriaReglaNegocio() {
		return categoria;
	}

	@Override
	public void verificarReglaNegocio(Organizacion entidad, Egreso egreso) {
		if(entidad.getEntidadesBaseExistentes()>=entidad.getCantidadMaximaEntidadesBase()) {
			throw new EntidadBaseRechazadaException("Se rechaza la adhesion de la entidad base por superar el numero permitido.");
		}
	}
	
}

