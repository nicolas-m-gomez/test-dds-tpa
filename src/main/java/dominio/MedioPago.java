package dominio;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
@Table (name = "medios_de_pago")
public class MedioPago {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "medio_pago_id")
	private Integer id;
	
	@Column(name = "medio_pago_codigo")
	private String codigo;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "medio_pago_tipo")
	private TipoMedio tipoMedio;
	
	public MedioPago() {}
	
	public MedioPago(String codigo, TipoMedio tipoMedio) {
		super();
		this.codigo = codigo;
		this.tipoMedio = tipoMedio;
	}
	
	public String getTipoMedio() {
		return tipoMedio.toString();
	}
	
	public String getCodigo() {
		return this.codigo;
	}
	
	public Integer getId() {
		return this.id;
	}
}
