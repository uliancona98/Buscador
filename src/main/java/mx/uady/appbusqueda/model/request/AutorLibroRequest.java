  
package mx.uady.appbusqueda.model.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class AutorLibroRequest {

    private Integer horas;
    
    @NotNull
    private Integer idAutor;
    
    @NotNull
    private Integer idLibro;

    public AutorLibroRequest() {

    }

    public Integer getIdAutor() {
        return idAutor;
    }

    public void setIdAutor(Integer idAutor) {
        this.idAutor = idAutor;
    }

    public Integer getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(Integer idLibro) {
        this.idLibro = idLibro;
    }

    @Override
    public String toString() {
        return "{" +
            "horas='" + getHoras() + "'" +
            ", idAutor='" + getIdAutor() + "'" +
            ", idLibro='" + getIdLibro() + "'" +
            "}";
    }
}