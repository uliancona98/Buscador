package mx.uady.appbusqueda.model.request;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Set;
import java.util.HashSet;
import org.springframework.format.annotation.DateTimeFormat;
public class LibroRequest{
    
    @NotNull
    @NotEmpty
    @Size(min = 3, max = 255)
    private String titulo;
 
    @PastOrPresent(message = "La fecha no cumple con el formato correcto")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @NotNull(message = "Por favor, tiene que ingresar un valor")
    private LocalDate fechaPublicacion;


    private String editorial;
    
    private String isbn;

    private String autor;

    private Set<AutorRequest> autores = new HashSet<>();

    public LibroRequest(){
        
    }
    public void setAutor(AutorRequest autor){
        this.autores.add(autor);
    }

    public Set<AutorRequest> getAutores(){
        return this.autores;
    }

    public String getTitulo() {
        return this.titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getAutor() {
        return this.autor;
    }

    public LocalDate getFechaPublicacion() {
        return this.fechaPublicacion;
    }

    public void setFechaPublicacion(LocalDate fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public String getEditorial() {
        return this.editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public String getIsbn() {
        return this.isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    @Override
    public String toString() {
        return "{" +
            " titulo='" + getTitulo() + "'" +
            "}";
    }
    
}