package mx.uady.appbusqueda.model;
import mx.uady.appbusqueda.model.*;
import java.util.List;
import java.io.Serializable;
import javax.persistence.Embedded;
import javax.persistence.Id;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.MapsId;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;


@Entity
@Table(name = "autores_libros")
public class AutorLibro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @JsonManagedReference
    @ManyToOne(optional=false)
    @JoinColumn(name="id_libro",referencedColumnName="id", insertable=false, updatable=false)
    Libro libro;

    @JsonBackReference
    @ManyToOne(optional=false)
    @JoinColumn(name="id_autor",referencedColumnName="id", insertable=false, updatable=false)
    Autor autor;


    public AutorLibro() {
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Autor getAutor() {
        return this.autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public Libro getLibro() {
        return this.libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    /*@Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", titulo='" + getLibro() + "'" +
            "}";
    }*/


}