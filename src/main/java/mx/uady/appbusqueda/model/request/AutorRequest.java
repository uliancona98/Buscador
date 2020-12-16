package mx.uady.appbusqueda.model.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotEmpty;

public class AutorRequest{


    @NotNull
    @Size(min = 3, max = 255)
    @NotEmpty
    private String nombre;

    public AutorRequest(){
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return this.nombre;
    }
}