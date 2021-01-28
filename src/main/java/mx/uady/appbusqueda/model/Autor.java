package mx.uady.appbusqueda.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.ManyToMany;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "autores")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String nombre;

    @ManyToMany(mappedBy = "autores")
    @JsonBackReference
    private Set<Libro> libros;
    
    public Autor() {
        //empty
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Set<Libro> getLibros() {
        return this.libros;
    }

    public void setLibros(Set<Libro> libros) {
        this.libros = libros;
    }

    public Autor id(Integer id) {
        this.id = id;
        return this;
    }

    public Autor nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", nombre='" + getNombre() + "'" +
            "}";
    }


}