package mx.uady.appbusqueda.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.Set;
import java.util.HashSet;
import java.time.format.DateTimeFormatter;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.OneToOne;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.CascadeType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import javax.persistence.JoinColumn;

@Entity
@Table(name = "libros")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column
    private String titulo;

    @Column 
    private LocalDate fechaPublicacion;

    @Column
    private String editorial;

    @Column
    private String autor;

    @Column
    private String url;

    @Column
    private String isbn;

    @OneToOne
    @JoinColumn(name = "idUsuario")
    private Usuario idUsuario;

    @ManyToMany(cascade = {
        CascadeType.PERSIST,
        CascadeType.MERGE
    })
    @JoinTable(
        name = "autores_libros",
        joinColumns = {@JoinColumn(name = "id_libro")},
        inverseJoinColumns = {@JoinColumn(name = "id_autor")}
    )
    @JsonManagedReference
    private Set<Autor> autores;

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
    
    public void setAutores(Set<Autor> autores){
        this.autores = autores;
    }

    public Set<Autor> getAutores(){
        return this.autores;
    }

    public Usuario getUsuario() {
        return idUsuario;
    }

    public String getTitulo() {
        return this.titulo;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getAutor() {
        return this.autor;
    }
    public void setURL(String url) {
        this.url = url;
    }

    public String getURL() {
        return this.url;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
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