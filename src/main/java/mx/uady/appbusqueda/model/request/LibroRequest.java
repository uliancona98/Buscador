package mx.uady.appbusqueda.model.request;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.time.LocalDate;
import java.time.LocalDate;

public class LibroRequest{
    
    @NotNull
    @NotEmpty
    private String usuario;

    @NotNull
    @NotEmpty
    @Size(min = 3, max = 255)
    private String titulo;

    private String contenido;
 
    @NotEmpty
    @PastOrPresent(message = "La fecha no cumple con el formato correcto")
    //@DateTimeFormat(pattern="yyyy-MM-dd")
    @NotNull(message = "Por favor, tiene que ingresar un valor")
    private LocalDate fechaPublicacion;


    private String editorial;
    
    private String nombreArchivo;

    private String isbn;

    public LibroRequest(){
        
    }

    public String getUsuario() {
        return this.usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getTitulo() {
        return this.titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * @param contenido the contenido to set
     */
    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    /**
     * @return the contenido
     */
    public String getContenido() {
        return this.contenido;
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

    public String getNombreArchivo() {
        return this.nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
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