package dominio;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table (name = "reglas_negocio")
public abstract class ReglaNegocio {
	
	@Id
	@GeneratedValue
	@Column(name = "regla_negocio_id")
	private Integer id;
	
	public abstract CategoriaReglaNegocio getCategoriaReglaNegocio();

    public abstract void verificarReglaNegocio(Organizacion entidad, Egreso egreso);

}
