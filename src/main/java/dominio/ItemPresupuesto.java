package dominio;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Items")
public class ItemPresupuesto implements Comparable<ItemPresupuesto> {

	@Id
	@GeneratedValue
	private Integer id;

	private String descripcion;
	private Integer cantidad;

	@OneToOne
	private Importe importe;

	public ItemPresupuesto() {
		super();
	}

	public ItemPresupuesto(String _descripcion, int _cantidad, Importe importe) {
		super();
		this.descripcion = _descripcion;
		this.cantidad = _cantidad;
		this.importe = importe;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public Double getPrecioUnitario() {
		return importe.getPrecioUnitario();
	}
	
	public Importe getImporte() {
		return this.importe;
	}

	public Double getPrecio() {
		return cantidad * this.getPrecioUnitario();
	}

	public Integer getId() {
		return this.id;
	}

	public int compareTo(ItemPresupuesto unItem) {
		return this.getDescripcion().compareTo(unItem.getDescripcion());
	}

}
