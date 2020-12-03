package servicio.viewmodel;

import dominio.ItemEgreso;

public class ItemEgresoViewModel {
	private Integer id;
	private String descripcion;
	private Integer cantidad;
	private Double precioUnitario;
	private String idMoneda;

	public ItemEgresoViewModel(Integer id, String descripcion, Integer cantidad, Double precioUnitario,
			String idMoneda) {
		this.id = id;
		this.descripcion = descripcion;
		this.cantidad = cantidad;
		this.precioUnitario = precioUnitario;
		this.idMoneda = idMoneda;
	}

	public ItemEgresoViewModel(ItemEgreso itemEgreso) {
		this.id = itemEgreso.getId();
		this.descripcion = itemEgreso.getDescripcion();
		this.cantidad = itemEgreso.getCantidad();
		this.precioUnitario = itemEgreso.getPrecioUnitario();
		this.idMoneda = itemEgreso.getImporte().getMoneda().getId() + " - " + itemEgreso.getImporte().getMoneda().getDescripcion();
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
