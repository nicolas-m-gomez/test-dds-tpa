package dominio;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.GeneratedValue;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;

@Entity
@Table (name = "organizaciones")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Organizacion {
	
	@Id
	@GeneratedValue
	@Column(name = "organizacion_id")
	private Integer codigo;
	
	@Column(name = "organizacion_nombre_ficticio")
	private String nombreFicticio;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "categoria_id", nullable = false)
	private Categoria categoria;
	
	@Column(name = "organizacion_monto_max_egreso")
	private double montoMaximoEgreso;
	
	@OneToMany
	private List<Egreso> egresos = new ArrayList<Egreso>();

	public abstract void generarReporte();
	public abstract Map<String, Double> totalesPorEtiqueta();
	public abstract int getEntidadesBaseExistentes();
	public abstract int getCantidadMaximaEntidadesBase();
	public abstract boolean getPermiteAgregarseAEntidadJuridica();
	

	public void setCategoria(Categoria _categoria) {
		this.categoria = _categoria;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setNombreFicticio(String _nombreFicticio) {
		this.nombreFicticio = _nombreFicticio;
	}
	
	public String getNombreFicticio() {
		return nombreFicticio;
	}

	public void cargarEgreso(Egreso egreso) {
		categoria.ejecutarReglaNegocio(
				CategoriaReglaNegocio.VALIDAR_MONTO_EGRESO,
				this,
				egreso
		);

		egresos.add(egreso);
	}
	
	public void setMontoMaximoEgreso(double montoMaximoEgreso) {
		this.montoMaximoEgreso = montoMaximoEgreso;
	}
	
	public List<Egreso> getEgresos(){
		return egresos;
	}

	
	public double getMontoMaximoEgreso() {
		return montoMaximoEgreso;
	}
	
	public Integer getCodigo() {
		return this.codigo;
	}

}
//done