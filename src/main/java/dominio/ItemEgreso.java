package dominio;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.OneToOne;

@Entity
@Table (name = "Items")
public class ItemEgreso implements Comparable<ItemEgreso>{

	@Id
	@GeneratedValue
	private Integer id;
	
	private String descripcion;
	private Integer cantidad;
	
	@OneToOne
	private Importe importe;

	public ItemEgreso() {
		super();
	}
	
	public ItemEgreso(String _descripcion, int _cantidad, Importe importe) {
		super();
		this.descripcion = _descripcion;
		this.cantidad = _cantidad;
		this.importe = importe;
	}

	public String getDescripcion()
	{
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
	

	@Override
    public boolean equals(Object itemAValidar) {
    	
		ItemEgreso unItem = (ItemEgreso) itemAValidar;
		
    	return (unItem.getCantidad() == this.getCantidad() 
    			&& unItem.getDescripcion() == this.getDescripcion()
    			&& unItem.getPrecioUnitario().equals(this.getPrecioUnitario()));
    }
    
    
    public int compareTo(ItemEgreso unItem) {
        return this.getDescripcion().compareTo(unItem.getDescripcion());
    }
    
    public Integer getId() {
    	return this.id;
    }
}
