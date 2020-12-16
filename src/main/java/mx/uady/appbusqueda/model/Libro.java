package mx.uady.appbusqueda.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.List;
import java.time.format.DateTimeFormatter;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "libros")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column
    private String titulo;

    @Column
    private String contenido;

    @Column 
    private LocalDate fechaPublicacion;

    @Column
    private String editorial;
    
    @Column
    private String nombreArchivo;

    @Column
    private String isbn;

    @OneToOne
    @JoinColumn(name = "idUsuario")
    private Usuario idUsuario;


    public Libro() {
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public void setUsuario(Usuario idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Usuario getUsuario() {
        return idUsuario;
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

    public Libro id(Integer id) {
        this.id = id;
        return this;
    }

    public Libro titulo(String titulo) {
        this.titulo = titulo;
        return this;
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", titulo='" + getTitulo() + "'" +
            "}";
    }


}