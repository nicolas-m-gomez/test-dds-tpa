package dominio;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Enumerated;

@Entity
@Table(name = "doc_comerciales")
public class DocumentoComercial {
	
	@Id
	@Column(name = "doc_comercial_codigo")
	private String codigo;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "doc_comercial_tipo")
	private TipoDocumento tipoDocumento;
	
	
	public DocumentoComercial() {
	}
	
	public DocumentoComercial(String _codigo, TipoDocumento _tipoDocumento) {
		super();
		this.codigo = _codigo;
		this.tipoDocumento = _tipoDocumento;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public TipoDocumento getTipoDocumento() {
		return tipoDocumento;
	}
	public void setTipoDocumento(TipoDocumento tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
}
