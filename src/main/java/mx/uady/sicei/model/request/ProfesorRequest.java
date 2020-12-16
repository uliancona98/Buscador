package mx.uady.sicei.model.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
public class ProfesorRequest{


    @NotBlank
    @Size(min = 3, max = 255)
    private String nombre;

    private Integer horas;

    public ProfesorRequest(){
        
    }

    public ProfesorRequest(String nombre, Integer horas) {
        this.nombre = nombre;
        this.horas = horas;
    }

    /**
     * @return the profesor
     */
    public String getNombre() {
        return this.nombre;
    }

    /**
     * @param profesor the profesor to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the horas
     */
    public Integer getHoras() {
        return this.horas;
    }
    
    /**
     * @param horas the id to set
     */
    public void setHoras(Integer horas) {
        this.horas = horas;
    }

    @Override
    public String toString() {
        return "{" +
            " nombre='" + getNombre() + "'" +
            ", horas='" + getHoras() + "'" +
            "}";
    }

}