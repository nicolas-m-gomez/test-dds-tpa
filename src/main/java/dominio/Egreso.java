package dominio;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "egresos")
public class Egreso {

	@Id
	@GeneratedValue
	@Column(name = "egreso_id")
	private Integer id;

	@ManyToOne
	private Proveedor proveedor;

	private LocalDate fecha;

	@ManyToOne
	private MedioPago medioPago;

	@ManyToOne
	private DocumentoComercial docuComercial;

	@OneToMany
	// @JoinTable (name = "Items")
	private List<ItemEgreso> itemsEgreso = new ArrayList<ItemEgreso>();

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "egreso_id")
	private List<Presupuesto> presupuestos = new ArrayList<Presupuesto>();

	// @OneToOne
	// private ListadoRevisores revisores = new ListadoRevisores();

	private Boolean requierePresupuesto = false;

	@Enumerated(EnumType.STRING)
	private EstadoEgreso estado = EstadoEgreso.PENDIENTE;

	@Transient
	private CriterioSeleccionPresupuesto criterio;

	@ElementCollection
	@CollectionTable(name = "Etiquetas")
	private List<String> etiquetas = new ArrayList<String>();

	@OneToMany
	private List<Usuario> revisores = new ArrayList<Usuario>();

	public Egreso() {
	}

	public Egreso(Proveedor _prov, LocalDate _fecha, MedioPago _medioPago, Boolean _requierePresupuesto) {
		super();
		this.proveedor = Objects.requireNonNull(_prov, "Debe indicar un proveedor");
		this.fecha = Objects.requireNonNull(_fecha, "Debe indicar una fecha");
		this.medioPago = Objects.requireNonNull(_medioPago, "Debe indicar un medio de pago");
		this.requierePresupuesto = Objects.requireNonNull(_requierePresupuesto, "Debe indicar si requiere presupuesto");
	}

	public void setDocuComercial(DocumentoComercial _docu) {
		this.docuComercial = _docu;
	}

	public DocumentoComercial getDocuComercial() {
		return docuComercial;
	}

	public Proveedor getProveedor() {
		return proveedor;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public List<String> getEtiquetas() {
		return etiquetas;
	}

	public boolean tieneEtiqueta(String unaEtiqueta) {
		return this.etiquetas.contains(unaEtiqueta);
	}

	public void agregarEtiqueta(String unaEtiqueta) {
		if (!this.etiquetas.contains(unaEtiqueta)) {
			this.etiquetas.add(unaEtiqueta);
		}
	}

	public MedioPago getMedioPago() {
		return medioPago;
	}

	public void cargarItem(ItemEgreso _item) {
		itemsEgreso.add(_item);
	}

	public List<ItemEgreso> getItems() {
		return itemsEgreso;
	}

	public double precioTotal() {
		return itemsEgreso.stream().mapToDouble(item -> item.getPrecio()).sum();
	}

	public void agregarPresupuesto(Presupuesto presupuesto) {
		presupuestos.add(presupuesto);
	}

	// public void agregarPresupuesto(Proveedor _provP, LocalDate _fechaP, MedioPago
	// _medioPagoP,DocumentoComercial _docuComercialP) {
	// this.proveedor = Objects.requireNonNull(_provP, "Debe indicar un proveedor al
	// presupuesto");
	// this.fecha = Objects.requireNonNull(_fechaP, "Debe indicar una fecha de
	// vigentcia del presupuesto");
	// this.medioPago = Objects.requireNonNull(_medioPagoP, "Debe indicar un medio
	// de pago");
	// this.docuComercial= Objects.requireNonNull(_docuComercialP, "Debe indicar el
	// documento comercal");
	// }

	// public void cargarItemAlPresupuesto(Item _itemP) {
	// itemsP.add(_itemP);
	// }
	//
	// public List<Item> getItemsP(){
	// return itemsP;
	// }
	//
	// public double precioTotalDelPresupuesto() {
	// return itemsP.stream().mapToDouble(itemP -> itemP.getPrecio()).sum();
	// }

	public void setListadoRevisores(List<Usuario> revisores) {
		this.revisores = revisores;
	}

	public void cambiarEstado(EstadoEgreso nuevoEstado, Notificacion notificacion) {
		this.estado = nuevoEstado;
		revisores.stream().forEach(revisor -> revisor.recibirNotificacion(notificacion));
	}

	public Boolean getRequierePresupuesto() {
		return this.requierePresupuesto;
	}

	public void altaRevisor(Usuario revisor) {
		this.revisores.add(revisor);
	}

	public void bajaRevisor(Usuario revisor) {
		this.revisores.remove(revisor);
	}

	public List<Presupuesto> getPresupuestos() {
		return this.presupuestos;
	}

	public int cantidadPresupuestosAsociados() {
		return presupuestos.size();
	}

	public EstadoEgreso getEstado() {
		return this.estado;
	}

	public CriterioSeleccionPresupuesto getCriterioSeleccionPresupuesto() {
		return this.criterio;
	}

	public List<Usuario> getRevisores() {
		return this.revisores;
	}

	public void setCriterioSeleccionPresupuesto(CriterioSeleccionPresupuesto criterio) {
		this.criterio = criterio;
	}

	public Integer getId() {
		return id;
	}

	public String getTipoMedio() {
		return medioPago.getTipoMedio();
	}

}
