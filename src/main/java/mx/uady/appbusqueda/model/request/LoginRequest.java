package mx.uady.appbusqueda.model.request;

import javax.validation.constraints.NotEmpty;

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
