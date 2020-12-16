package mx.uady.appbusqueda.resource;

import java.util.LinkedList;
import java.util.List;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import mx.uady.appbusqueda.model.Alumno;
import mx.uady.appbusqueda.model.Profesor;
import mx.uady.appbusqueda.model.Usuario;
import mx.uady.appbusqueda.repository.AlumnoRepository;
import mx.uady.appbusqueda.repository.ProfesorRepository;
import mx.uady.appbusqueda.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;

import java.net.URI;
import java.net.URISyntaxException;

import javax.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;

import mx.uady.appbusqueda.exception.NotFoundException;

import mx.uady.appbusqueda.model.Licenciatura;

import mx.uady.appbusqueda.service.AlumnoSerivce;
import mx.uady.appbusqueda.service.UsuarioService;
import mx.uady.appbusqueda.service.LoginService;

import mx.uady.appbusqueda.repository.UsuarioRepository;

import mx.uady.appbusqueda.config.JwtTokenUtil;

import mx.uady.appbusqueda.model.request.LoginRequest;
import mx.uady.appbusqueda.model.request.AlumnoRequest;
import mx.uady.appbusqueda.model.JwtResponse;


@RestController
@RequestMapping("/api")
public class LoginRest {

    @Autowired
    private AlumnoSerivce alumnoService;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private LoginService loginService;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    
    // POST /login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest request) throws RuntimeException{

        Usuario usuario = usuarioService.getUsuario(request.getUsuario());

        if (usuario == null) {
            String errMessage = "El usuario o la contrasena son incorrectos";
            return ResponseEntity.ok(errMessage);
        }

        String token = jwtTokenUtil.generateToken(usuario);

        return ResponseEntity.ok(new JwtResponse(token));
    }

    @GetMapping("/quienSoy")
    //devuelve al usuario logeado
    public ResponseEntity<Usuario> getQuienSoy() {
        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok().body(usuario);
    }

    @PostMapping("/signout")
    public ResponseEntity<JwtResponse> logout(@RequestHeader("Authorization") String auth) {
        loginService.logout(auth);
        return ResponseEntity.ok(new JwtResponse(auth));
    }

    @PostMapping("/register")
    //crea un usuario con un nuevo token
    public ResponseEntity register(@RequestBody @Valid AlumnoRequest request) throws URISyntaxException{
        
        Alumno alumno = alumnoService.crearAlumno(request);

        if(alumno != null) {
            return ResponseEntity
            .created(new URI("/alumnos/" + alumno.getId()))
            .body(alumno);
        } else {
             return ResponseEntity.status(HttpStatus.CONFLICT).body(alumno);
        }
    }
}
