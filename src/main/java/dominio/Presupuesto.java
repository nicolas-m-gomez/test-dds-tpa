package dominio;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "presupuestos")
public class Presupuesto {

	@Id
	@GeneratedValue
	@Column(name = "presupuesto_id")
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "proveedor_id")
	private Proveedor proveedor;

	@ManyToOne
	@JoinColumn(name = "doc_comercial_codigo")
	private DocumentoComercial documentoComercial;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "presupuesto_item_id")
	private List<ItemPresupuesto> itemsPresupuesto = new ArrayList<ItemPresupuesto>();

	public Presupuesto() {
	}

	public Presupuesto(Proveedor proveedor, DocumentoComercial documentoComercial,
			List<ItemPresupuesto> itemsPresupuesto) {
		this.proveedor = proveedor;
		this.documentoComercial = documentoComercial;
		this.itemsPresupuesto = itemsPresupuesto;
	}

	public double precioTotal() {
		return itemsPresupuesto.stream().mapToDouble(item -> item.getPrecio()).sum();
	}

	public DocumentoComercial getDocumentoComercial() {
		return this.documentoComercial;
	}

	public Proveedor getProveedor() {
		return this.proveedor;
	}

	public List<ItemPresupuesto> getItems() {
		return this.itemsPresupuesto;
	}

	public Integer getId() {
		return this.id;
	}
}
