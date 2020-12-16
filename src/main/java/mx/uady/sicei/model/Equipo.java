package mx.uady.sicei.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "equipos")
public class Equipo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    @Column
    public String modelo;

    /*@OneToMany(mappedBy = "equipo")
    List<Alumno> alumnos;*/

    public Equipo() {
    }

    public Equipo(Integer id, String modelo) {
        this.id = id;
        this.modelo = modelo;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getModelo() {
        return this.modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Equipo id(Integer id) {
        this.id = id;
        return this;
    }

    public Equipo modelo(String modelo) {
        this.modelo = modelo;
        return this;
    }

    /*public List<Alumno> getAlumnos() {
        return alumnos;
    }*/

    /*public void setAlumnos(List<Alumno> alumnos) {
        this.alumnos = alumnos;
    }*/

    
    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", modelo='" + getModelo() + "'" +
            "}";
    }


}