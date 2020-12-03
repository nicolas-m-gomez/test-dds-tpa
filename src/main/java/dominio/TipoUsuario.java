package dominio;

import java.util.Arrays;
import java.util.List;

import servicio.Funcion;

public enum TipoUsuario {
	ESTANDAR (
			Arrays.asList(
					Funcion.VER_BANDEJA_DE_MENSAJES, 
					Funcion.VER_ENTIDADES_POR_CATEGORIA)
	),
	ADMINISTRADOR(
			Arrays.asList(
					Funcion.ASOCIAR_ENTIDAD_POR_CATEGORIA, 
					Funcion.CARGAR_ENTIDAD_BASE, 
					Funcion.CARGAR_ENTIDAD_JURIDICA, 
					Funcion.VER_BANDEJA_DE_MENSAJES,
					Funcion.VER_ENTIDADES_POR_CATEGORIA)
	);
	
	private List<Funcion> funcionesDisponibles;
	
	TipoUsuario(List<Funcion> funcionesDisponibles){
		this.funcionesDisponibles = funcionesDisponibles;
	}
	
	public List<Funcion> getFuncionesDisponibles(){
		return this.funcionesDisponibles;
	}
}
