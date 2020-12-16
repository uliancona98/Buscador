package mx.uady.sicei.model;

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

@Entity
@Table(name = "tutorias")
public class Tutoria {

    @EmbeddedId
    private TutoriaId id;

    @ManyToOne(optional=false)
    @JoinColumn(name="id_alumno",referencedColumnName="id", insertable=false, updatable=false)
    Alumno alumno;

    @ManyToOne(optional=false)
    @JoinColumn(name="id_profesor",referencedColumnName="id", insertable=false, updatable=false)
    Profesor profesor;

    @Column(name = "horas")
    private Integer horas;

    @Embeddable
    public static class TutoriaId implements Serializable {

        private static final long serialVersionUID = 1L;

        @Column(name = "id_alumno", insertable=false,updatable=false)
        private Integer alumnoId;

        @Column(name = "id_profesor", insertable=false, updatable=false)
        private Integer profesorId;

        public TutoriaId() { }

        /**
         * @return the alumnoId
         */
        public Integer getAlumnoId() {
            return alumnoId;
        }/**
         * @param alumnoId the alumnoId to set
         */
        public void setAlumnoId(Integer alumnoId) {
            this.alumnoId = alumnoId;
        }/**
         * @return the profesorId
         */
        public Integer getProfesorId() {
            return profesorId;
        }/**
         * @param profesorId the profesorId to set
         */
        public void setProfesorId(Integer profesorId) {
            this.profesorId = profesorId;
        }
    }

    public Tutoria() {
    }

    public void setHoras(Integer horas) {
        this.horas = horas;
    }

    public Integer getHoras() {
        return horas;
    }

    public void setId(TutoriaId id) {
        this.id = id;
    }

    public TutoriaId getId() {
        return id;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    public Profesor getProfesor() {
        return profesor;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", alumno='" + getAlumno() + "'" +
            ", profesor='" + getProfesor() + "'" +
            ", horas='" + getHoras() + "'" +
            "}";
    }
    


}