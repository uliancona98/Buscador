package mx.uady.appbusqueda.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "tokens")
public class TokenBlacklist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String token;

    public TokenBlacklist(){
    }

    public TokenBlacklist(String token) {
        this.token = token;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken() {
        this.token = token;
    }
}