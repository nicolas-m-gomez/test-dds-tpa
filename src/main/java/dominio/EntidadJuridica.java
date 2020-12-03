package dominio;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;




@Entity
@Table(name = "entidades_juridicas")
@PrimaryKeyJoinColumn(name= "organizacion")
public class EntidadJuridica extends Organizacion {
	
	@Transient
	private Integer codigo;
	
	@Column(name = "entidad_juridica_razon_social")
	private String razonSocial;	

	@Transient
	private String nombreFicticio;
	
	@Column(name = "entidad_juridica_ciut")
	private int cuit;

	@OneToOne
	@JoinColumn(name = "entidad_juridica_direccion")
	private Direccion direccion;

	@Column(name = "entidad_juridica_codigo_igj")
	private int codigoIgj;

	@Enumerated(EnumType.STRING)
	@Column(name = "entidad_juridica_tipo_empresa")
	private TipoEmpresa tipoEmpresa;

	@Transient
	private Categoria categoria;

	@Transient
	private double montoMaximoEgreso;
	
	@Column(name = "entidad_juridica_maximo_entidades_base")
	private int cantidadMaximaEntidadesBase;

	@Transient
	private List<Egreso> egresos = new ArrayList<Egreso>();

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "entidad_juridica", nullable = true)
	private List<EntidadBase> entidadesBase = new ArrayList<EntidadBase>();

	public void generarReporte() {
		ReporteEgresos reporte = new ReporteEgresos();
		List<Egreso> egresosCompletos = new ArrayList<Egreso>();
		egresosCompletos.addAll(egresos);
		entidadesBase.stream().forEach(ent -> egresosCompletos.addAll(ent.getEgresos()));
		reporte.hacerReporte(egresosCompletos, this.nombreFicticio);
	}
	
	public boolean tieneEspacioParaEntidadesBase() {
		return (this.entidadesBase.size() < this.cantidadMaximaEntidadesBase);
	}

	public Map<String, Double> totalesPorEtiqueta() {
		ReporteEgresos reporte = new ReporteEgresos();
		return reporte.obtenerTotalesPorEtiqueta(egresos);
	}

	public void setNombreFicticio(String _nombreFicticio) {
		this.nombreFicticio = _nombreFicticio;
	}

	public void cargarEgreso(Egreso egreso) {
		categoria.ejecutarReglaNegocio(CategoriaReglaNegocio.VALIDAR_MONTO_EGRESO, this, egreso);

		egresos.add(egreso);
	}

	public void agregarEntidadBase(EntidadBase entidadBase) {
		categoria.ejecutarReglaNegocio(CategoriaReglaNegocio.AGREGAR_ENTIDAD_BASE, this, null);

		categoria.ejecutarReglaNegocio(CategoriaReglaNegocio.PUEDE_AGREGARSE_A_ENTIDAD_JURIDICA, entidadBase, null);

		entidadesBase.add(entidadBase);
	}

	public void setCategoria(Categoria _categoria) {
		this.categoria = _categoria;
	}

	public String getRazonSocial() {
		return razonSocial;
	}
	public void setRazonSocial(String _razonSocial) {
		this.razonSocial = _razonSocial;
	}
	
	public String getNombreFicticio() {
		return nombreFicticio;
	}
	
	public Integer getCodigo() {
		return this.codigo;
	}

	public int getCuit() {
		return cuit;
	}

	public void setCuit(Integer _cuit) {
		this.cuit = _cuit;
	}
	public int getCodigoIgj() {
		return codigoIgj;
	}
	
	public void setCodigoIGJ(Integer _codigoIGJ) {
		this.codigoIgj = _codigoIGJ;
	}

	public Direccion getDireccion() {
		return direccion;
	}
	
	public void setDireccion(Direccion _direccion) {
		this.direccion = _direccion;
	}

	public TipoEmpresa getTipoEmpresa() {
		return tipoEmpresa;
	}
	
	public void setTipoEmpresa(TipoEmpresa _tipoEmpresa) {
		this.tipoEmpresa = _tipoEmpresa;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public List<Egreso> getEgresos() {
		return this.egresos;
	}

	public List<EntidadBase> getEntidadesBase() {
		return this.entidadesBase;
	}

	public double getMontoMaximoEgreso() {
		return montoMaximoEgreso;
	}

	public void setMontoMaximoEgreso(double montoMaximoEgreso) {
		this.montoMaximoEgreso = montoMaximoEgreso;
	}

	public int getEntidadesBaseExistentes() {
		return entidadesBase.size();
	}

	@Override
	public int getCantidadMaximaEntidadesBase() {
		return cantidadMaximaEntidadesBase;
	}

	public void setCantidadMaximaEntidadesBase(int cantidadMaximaEntidadesBase) {
		this.cantidadMaximaEntidadesBase = cantidadMaximaEntidadesBase;
	}

	@Override
	public boolean getPermiteAgregarseAEntidadJuridica() {
		return false;
	}
}
