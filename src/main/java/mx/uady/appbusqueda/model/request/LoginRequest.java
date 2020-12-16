package mx.uady.appbusqueda.model.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class LoginRequest {

    @NotEmpty
    private String usuario;

    @NotEmpty
    private String password;

    public String getUsuario() {
        return this.usuario;
    }

    public String getPassword() {
        return this.password;
    }
}
