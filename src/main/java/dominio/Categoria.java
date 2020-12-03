package dominio;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.OneToMany;
import javax.persistence.Transient;


@Entity
@Table (name = "categorias")
public class Categoria{
	
	@Id
	@GeneratedValue
	@Column(name = "categoria_id")
	private Integer id;
	
	private String nombre;
	
	@ManyToMany
	private List<ReglaNegocio> reglasNegocio = new ArrayList<>();

    public Categoria() {
    }
	
    public Categoria(String nombre) {
        this.nombre = nombre;
    }

  
    public void ejecutarReglaNegocio(
            CategoriaReglaNegocio categoriaReglaNegocio,
            Organizacion organizacion,
            Egreso egreso
    ) {
        Optional<ReglaNegocio> reglaNegocio = reglasNegocio.stream()
                .filter( regla -> regla.getCategoriaReglaNegocio() == categoriaReglaNegocio)
                .findAny();

        reglaNegocio.ifPresent(
                reglaNegocio1 -> reglaNegocio1.verificarReglaNegocio(organizacion, egreso)
        );
    }

    public void agregarRegla(ReglaNegocio reglaNegocio) {
        reglasNegocio.add(reglaNegocio);
    }

    public void quitarRegla(ReglaNegocio reglaNegocio) {
        reglasNegocio.remove(reglaNegocio);
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getNombre() {
    	return this.nombre;
    }
    
    public Integer getId() {
    	return this.id;
    }
    
}


