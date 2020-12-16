package mx.uady.sicei.model;

import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "profesores")
public class Profesor {

    // POJO: Plain Java Object. No existe ninguna accion

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // f(x) = y
    private Integer id;

    @Column
    private String nombre;

    @Column
    private Integer horas;

    public Profesor() {
    }

    public Profesor(Integer id, String nombre, Integer horas) {
        this.id = id;
        this.nombre = nombre;
        this.horas = horas;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Profesor id(Integer id) {
        this.id = id;
        return this;
    }


    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Profesor nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public Integer getHoras() {
        return this.horas;
    }

    public void setHoras(Integer horas) {
        this.horas = horas;
    }

    public Profesor horas(Integer horas) {
        this.horas = horas;
        return this;
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", nombre='" + getNombre() + "'" +
            ", horas='" + getHoras() + "'" +
            "}";
    }

}