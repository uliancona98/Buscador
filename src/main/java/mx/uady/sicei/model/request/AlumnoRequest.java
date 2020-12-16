package mx.uady.sicei.model.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import mx.uady.sicei.model.Licenciatura;

public class AlumnoRequest{
    
    @NotNull
    @NotEmpty
    @Size(min = 3, max = 255)
    private String nombre;

    @NotNull
    @NotEmpty
    private String usuario;

    @NotNull
    @Size(min=8, max=25)
    @NotEmpty
    private String password;

    @NotNull
    private Licenciatura licenciatura;

    private Integer equipoId;

    public AlumnoRequest(){
        
    }

    public AlumnoRequest(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsuario() {
        return this.usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    public Integer getEquipoId() {
        return this.equipoId;
    }

    public void setEquipoId(Integer equipoId) {
        this.equipoId = equipoId;
    }

    public AlumnoRequest licenciatura(Licenciatura licenciatura) {
        this.licenciatura = licenciatura;
        return this;
    }

    public Licenciatura getLicenciatura() {
        return this.licenciatura;
    }

    public void setLicenciatura(Licenciatura licenciatura) {
        this.licenciatura = licenciatura;
    }

    public AlumnoRequest nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    @Override
    public String toString() {
        return "{" +
            " nombre='" + getNombre() + "'" +
            "}";
    }
    
}