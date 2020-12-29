package mx.uady.appbusqueda.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.validation.Valid;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.uady.appbusqueda.exception.NotFoundException;
import mx.uady.appbusqueda.model.Autor;
import mx.uady.appbusqueda.model.request.AutorRequest;
import mx.uady.appbusqueda.service.AutorService;

@RestController // Metaprogramacion
@RequestMapping("/api")
public class AutorRest {

    @Autowired
    private AutorService autorService;

    // GET /api/autores
    @GetMapping("/autores")
    public ResponseEntity<List<Autor>> getAutores() {

        // ResponseEntity es una abstraccion de una respuesta HTTP, con body y headers
        return ResponseEntity.ok().body(autorService.obtenerAutores());
    }

    // POST /api/autores
    @PostMapping("/autores")
    public ResponseEntity<Autor> postAutores(@RequestBody @Valid AutorRequest request) throws URISyntaxException {
        
        // RequestBody le indica a Java que estamos esperando un request que cumpla con los campos del Objeto AutorRequest
        
        Autor autor = autorService.crearAutor(request);

        // 201 Created
        // Header: Location
        return ResponseEntity
            .created(new URI("/autores/" + autor.getId()))
            .body(autor);
    }

    // GET /api/autores/3 -> 200
    // Validar que exista, si no existe Lanzar un RuntimeException
    @GetMapping("/autores/{id}")
    public ResponseEntity<Autor> getAutor(@PathVariable Integer id){

        return ResponseEntity.ok().body(autorService.getAutor(id));
    }

    // Validar que exista, si no existe Lanzar un RuntimeException
    @PutMapping("/autores/{id}")
    public ResponseEntity<Autor> putAutores(@PathVariable Integer id, @RequestBody AutorRequest request)
            throws URISyntaxException {

        Autor autor = autorService.editarAutor(id, request);

        return ResponseEntity
            .ok()
            .body(autor);
    }

    // Validar que exista, si no existe Lanzar un RuntimeException
    @DeleteMapping("/autores/{id}")
    public ResponseEntity deleteAutor(@PathVariable Integer id){

        String response = autorService.borrarAutor(id);

        return ResponseEntity
            .ok()
            .body(Collections.singletonMap("Respuesta", response));
    }


}