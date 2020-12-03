package dominio;

import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Transient;

@Entity
@Table(name = "entidades_base")
@PrimaryKeyJoinColumn(name= "organizacion")
public class EntidadBase extends Organizacion {
		
	@Transient
	private String nombreFicticio;

	@Column(name = "entidad_base_descripcion")
	private String descripcion;

	@Column(name = "entidad_base_permite_agregarse")
	private boolean permiteAgregarseAEntidadJuridica;

	public void generarReporte() {
		ReporteEgresos reporte = new ReporteEgresos();
		reporte.hacerReporte(super.getEgresos(), this.nombreFicticio);
	}

	public Map<String, Double> totalesPorEtiqueta() {
		ReporteEgresos reporte = new ReporteEgresos();
		return reporte.obtenerTotalesPorEtiqueta(super.getEgresos());
	}

	public void setNombreFicticio(String _nombreFicticio) {
		this.nombreFicticio = _nombreFicticio;
	}

	public void cargarEgreso(Egreso egreso) {
		// categoria.validarEgresoParaEntidad(egreso);
		super.getCategoria().ejecutarReglaNegocio(CategoriaReglaNegocio.VALIDAR_MONTO_EGRESO, this, egreso);

		getEgresos().add(egreso);
	}

	public Categoria getCategoria() {
		return getCategoria();
	}

	public String getNombreFicticio() {
		return nombreFicticio;
	}

	public String getDescripcion() {
		return descripcion;
	}
	
	public void setDescripcion(String _descripcion) {
		this.descripcion = _descripcion;
	}

	public List<Egreso> getEgresos() {
		return super.getEgresos();
	}

	@Override
	public int getEntidadesBaseExistentes() {
		return 0;
	}

	@Override
	public int getCantidadMaximaEntidadesBase() {
		return 0;
	}

	@Override
	public boolean getPermiteAgregarseAEntidadJuridica() {
		return permiteAgregarseAEntidadJuridica;
	}

	public void setPermiteAgregarseAEntidadJuridica(boolean permiteAgregarseAEntidadJuridica) {
		this.permiteAgregarseAEntidadJuridica = permiteAgregarseAEntidadJuridica;
	}
}
