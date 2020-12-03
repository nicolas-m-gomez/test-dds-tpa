package servicio.viewmodel;

import dominio.ItemPresupuesto;

public class ItemPresupuestoViewModel {
	private Integer id;
	private String descripcion;
	private Integer cantidad;
	private Double precioUnitario;
	private String idMoneda;

	public ItemPresupuestoViewModel(Integer id, String descripcion, Integer cantidad, Double precioUnitario,
			String idMoneda) {
		this.id = id;
		this.descripcion = descripcion;
		this.cantidad = cantidad;
		this.precioUnitario = precioUnitario;
		this.idMoneda = idMoneda;
	}

	public ItemPresupuestoViewModel(ItemPresupuesto itemPresupuesto) {
		this.id = itemPresupuesto.getId();
		this.descripcion = itemPresupuesto.getDescripcion();
		this.cantidad = itemPresupuesto.getCantidad();
		this.precioUnitario = itemPresupuesto.getPrecioUnitario();
		this.idMoneda = itemPresupuesto.getImporte().getMoneda().getId() + " - " + itemPresupuesto.getImporte().getMoneda().getDescripcion();
	}

	public Integer getId() {
		return id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public Double getPrecioUnitario() {
		return precioUnitario;
	}

	public String getIdMoneda() {
		return idMoneda;
	}
}
