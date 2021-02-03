  
package mx.uady.appbusqueda.model.request;

import javax.validation.constraints.NotNull;

public class AutorLibroRequest {
    
    @NotNull
    private Integer idAutor;
    
    @NotNull
    private Integer idLibro;

    public AutorLibroRequest() {
        //empty
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
            "idAutor='" + getIdAutor() + "'" +
            ", idLibro='" + getIdLibro() + "'" +
            "}";
    }
}