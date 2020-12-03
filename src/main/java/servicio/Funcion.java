package servicio;

public enum Funcion {
	CARGAR_ENTIDAD_BASE ("Cargar Entidad Base", "/organizaciones/nueva/entidad-base", TipoFuncion.NAVBAR),
	CARGAR_ENTIDAD_JURIDICA ("Cargar Entidad Jurídica", "/organizaciones/nueva/entidad-juridica", TipoFuncion.NAVBAR),
	ASOCIAR_ENTIDAD_POR_CATEGORIA ("Asociar Entidad por Categoria", "/asoc-entidad", TipoFuncion.NAVBAR),	
	VER_ENTIDADES_POR_CATEGORIA ("Ver Entidades por Categoria","/categoria-entidades", TipoFuncion.NAVBAR),
	VER_BANDEJA_DE_MENSAJES ("Ver Bandeja de Mensajes", "/notificaciones", TipoFuncion.NAVBAR),
	
	VER_EGRESOS ("Ver Egresos", "egresos", TipoFuncion.ABM_EGRESOS),
	NUEVO_EGRESO("Nuevo", "egresos/nuevo_egreso", TipoFuncion.ABM_EGRESOS),
	BORRAR_EGRESO("Borrar", "#", TipoFuncion.ABM_EGRESOS),
	VER_ITEMS("Ver Items", "egresos", TipoFuncion.ABM_EGRESOS),
	VER_PRESUPUESTOS("Ver Presupuestos", "egresos", TipoFuncion.ABM_EGRESOS);
	
	private String denominacion;
	private String url;
	private TipoFuncion tipo; 
	
	Funcion(String denominacion, String url, TipoFuncion tipo){
		this.denominacion = denominacion;
		this.url = url;
		this.tipo = tipo;
	}
	
	public String getDenominacion() {
		return this.denominacion;
	}
	
	public String getUrl() {
		return this.url;
	}
	
	public TipoFuncion getTipo() {
		return this.tipo;
	}

}
