package dominio;

import dominio.exception.EntidadBaseRechazadaException;

public class ValidarAgregarseAEntidadJuridica extends ReglaNegocio {
	CategoriaReglaNegocio categoria;
	
	public ValidarAgregarseAEntidadJuridica(CategoriaReglaNegocio categoria) {
		this.categoria = categoria;
	}

	@Override
	public CategoriaReglaNegocio getCategoriaReglaNegocio() {
		return categoria;
	}

	@Override
	public void verificarReglaNegocio(Organizacion entidad, Egreso egreso) {
		if(!entidad.getPermiteAgregarseAEntidadJuridica()) {
			throw new EntidadBaseRechazadaException("La entidad base no permite ser adherida a entidades juridicas.");
		}
	}
	
}
