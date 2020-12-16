package mx.uady.sicei.model;

import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import org.springframework.data.repository.cdi.Eager;

@Entity
@Table(name = "alumnos")
public class Alumno {

     // POJO: Plain Java Object. No existe ninguna accion

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // f(x) = y
    public Integer id;

    @Column 
    private String nombre;

    @Column(name = "licenciatura")
    @Enumerated(EnumType.STRING)
    private Licenciatura licenciatura;

    // JOIN Usuario WHERE alumno.id_usario = usuarios.id

    // LAZY vs EAGER

    @ManyToOne
    @JoinColumn(name = "id_equipo")
    @JsonBackReference
    private Equipo equipoId;

    @OneToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuarioId;

    public Alumno() {
    }

    public Alumno(Integer id, String nombre, Licenciatura licenciatura) {
        this.id = id;
        this.nombre = nombre;
        this.licenciatura = licenciatura;
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

    public Alumno id(Integer id) {
        this.id = id;
        return this;
    }

    public Alumno nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setUsuario(Usuario usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Licenciatura getLicenciatura() {
        return licenciatura;
    }

    public void setLicenciatura(Licenciatura licenciatura) {
        this.licenciatura = licenciatura;
    }
    
    public Usuario getUsuario() {
        return usuarioId;
    }

    public void setEquipo(Equipo equipoId) {
        this.equipoId = equipoId;
    }

    public Equipo getEquipo() {
        return equipoId;
    }


    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", nombre='" + getNombre() + "'" +
            ", usuarioId='" + getUsuario() + "'" +
            "}";
    }
    
}