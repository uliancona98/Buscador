  
package mx.uady.sicei.model.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class TutoriaRequest {

    @NotNull
    @Min(value = 0, message="valor tiene que tener un minimo de 0 horas")
    @Max(value= 2, message="valor no puede exceder las 2 horas")
    private Integer horas;
    
    @NotNull
    private Integer alumnoId;
    
    @NotNull
    private Integer profesorId;

    public TutoriaRequest() {

    }

    public Integer getHoras() {
        return this.horas;
    }

    public void setHoras(Integer horas) {
        this.horas = horas;
    }

    public Integer getAlumnoId() {
        return alumnoId;
    }

    public void setAlumnoId(Integer alumnoId) {
        this.alumnoId = alumnoId;
    }

    public Integer getProfesorId() {
        return profesorId;
    }

    public void setProfesorId(Integer profesorId) {
        this.profesorId = profesorId;
    }

    @Override
    public String toString() {
        return "{" +
            "horas='" + getHoras() + "'" +
            ", id_alumno='" + getAlumnoId() + "'" +
            ", id_profesor='" + getProfesorId() + "'" +
            "}";
    }
}