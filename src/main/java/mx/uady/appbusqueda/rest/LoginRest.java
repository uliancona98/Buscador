package mx.uady.appbusqueda.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.http.HttpStatus;
import java.util.List;
import java.util.Collections;

import java.net.URI;
import java.net.URISyntaxException;

import javax.validation.Valid;

import mx.uady.appbusqueda.config.JwtTokenUtil;

import mx.uady.appbusqueda.model.Usuario;
import mx.uady.appbusqueda.model.request.LoginRequest;
import mx.uady.appbusqueda.model.request.UsuarioRequest;
import mx.uady.appbusqueda.model.JwtResponse;
import mx.uady.appbusqueda.service.UsuarioService;
import mx.uady.appbusqueda.service.LoginService;


@RestController
@RequestMapping("/api")
public class LoginRest {

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private LoginService loginService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    
    // POST /login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest request){
        System.out.println(request.getPassword()+request.getUsuario());
        Usuario usuario = usuarioService.getUsuario(request.getUsuario());

        if (usuario == null) {
            //String errMessage = "El usuario o la contrasena son incorrectos";
            String errMessage = "El usuario o la contrasena son incorrectos";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("message", errMessage));
            //return ResponseEntity.ok(errMessage);
        }else{
            if(usuario.getUsuario().equals(request.getUsuario()) && usuario.getPassword().equals(request.getPassword())){
                String token = jwtTokenUtil.generateToken(usuario);
                return ResponseEntity.ok(new JwtResponse(token));
            }

            String errMessage = "El usuario o la contrasena son incorrectos";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("message", errMessage));
        }

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
    public ResponseEntity<Usuario> register(@RequestBody @Valid UsuarioRequest request) throws URISyntaxException{
        
        Usuario usuario = usuarioService.crearUsuario(request);

        if(usuario != null) {
            return ResponseEntity
            .created(new URI("/usuario/" + usuario.getId()))
            .body(usuario);
        } else {
             return ResponseEntity.status(HttpStatus.CONFLICT).body(usuario);
        }
    }
}
