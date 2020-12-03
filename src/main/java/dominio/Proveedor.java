package dominio;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table (name = "proveedores")
public class Proveedor {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "proveedor_id")
	private Integer id;
	
	@Column(name = "proveedor_denominacion")
	private String denominacion;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "direccion_id")
	private Direccion direccion;
	
	public Proveedor() {}
	
	public Proveedor(String denominacion, Direccion direccion) {
		super();
		this.denominacion = denominacion;		
		this.direccion = direccion;
	}
	
	public String getDenominacion() {
		return this.denominacion;
	}
	
	public Direccion getDireccion() {
		return this.direccion;
	}
	
	public Integer getId() {
		return this.id;
	}
}
