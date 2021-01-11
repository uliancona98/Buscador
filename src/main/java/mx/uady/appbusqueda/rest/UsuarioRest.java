package mx.uady.appbusqueda.rest;
import java.util.UUID;
import java.util.List;
import java.net.URI;
import java.net.URISyntaxException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;

import mx.uady.appbusqueda.model.Usuario;
import mx.uady.appbusqueda.model.request.UsuarioRequest;
import mx.uady.appbusqueda.service.UsuarioService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/api")
public class UsuarioRest {

    @Autowired
    private UsuarioService usuarioService;
    
    @PostMapping("/usuarios")
    public ResponseEntity<Usuario> postUsuario(@RequestBody @Valid UsuarioRequest request) throws URISyntaxException {
        
        Usuario usuario = usuarioService.crearUsuario(request);
        // 201 Created
        // Header: Location
        return ResponseEntity
            .created(new URI("/usuarios/" + usuario.getId()))
            .body(usuario);
    }

    // GET /api/usuarios
    @GetMapping("/usuarios")
    public ResponseEntity<List<Usuario>> getUsuario() {
        List<Usuario> usuarios = usuarioService.getUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    // GET /api/usuario/3
    @GetMapping("/usuarios/{id}")
    public ResponseEntity<Usuario> getUsuario(@PathVariable Integer id) {
        Usuario u = usuarioService.getUsuario(id);
        return ResponseEntity.status(HttpStatus.OK).body(u);
    }
    
    // PUT /api/usuario/3
    @PutMapping("/usuarios/{id}")
    public ResponseEntity<Usuario> putUsuario(@PathVariable Integer id, @RequestBody UsuarioRequest request) {
        Usuario u = usuarioService.editarUsuario(id, request);

        return ResponseEntity
            .ok()
            .body(u);
    }
    
    //DELETE /api/usuario/3
    /*@DeleteMapping("/usuarios/{id}")
    public ResponseEntity deleteUsuario(@PathVariable Integer id){

        usuarioService.borrarUsuario(id);

        return ResponseEntity
            .ok()
            .body("Usuario eliminado");
    }*/
}
