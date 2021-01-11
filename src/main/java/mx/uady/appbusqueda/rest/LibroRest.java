package mx.uady.appbusqueda.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.uady.appbusqueda.model.Libro;
import mx.uady.appbusqueda.model.request.LibroRequest;
import mx.uady.appbusqueda.service.LibroService;

@RestController
@RequestMapping("/api")
public class LibroRest {

    @Autowired
    private LibroService libroService;

    // GET /api/libros
    @GetMapping("/libros")
    public ResponseEntity<List<Libro>> getLibros() {

        // ResponseEntity es una abstraccion de una respuesta HTTP, con body y headers
        return ResponseEntity.ok().body(libroService.obtenerLibros());
    }

    // POST /api/libros
    @PostMapping("/libros")
    public ResponseEntity<Libro> postLibros(@RequestBody @Valid LibroRequest request) throws URISyntaxException {
        
        // RequestBody le indica a Java que estamos esperando un request que cumpla con los campos del Objeto LibroRequest
        
        Libro libro = libroService.crearLibro(request);

        // 201 Created
        // Header: Location
        return ResponseEntity
            .created(new URI("/libros/" + libro.getId()))
            .body(libro);
    }

    // GET /api/libros/3 -> 200
    // Validar que exista, si no existe Lanzar un RuntimeException
    @GetMapping("/libros/{id}")
    public ResponseEntity<Libro> getLibro(@PathVariable Integer id){

        return ResponseEntity.ok().body(libroService.getLibro(id));
    }

    // Validar que exista, si no existe Lanzar un RuntimeException
    @PutMapping("/libros/{id}")
    public ResponseEntity<Libro> putLibros(@PathVariable Integer id, @RequestBody LibroRequest request)
            throws URISyntaxException {

        Libro libro = libroService.editarLibro(id, request);

        return ResponseEntity
            .ok()
            .body(libro);
    }

    // Validar que exista, si no existe Lanzar un RuntimeException
    @DeleteMapping("/libros/{id}")
    public ResponseEntity<Map<String, String>> deleteLibro(@PathVariable Integer id){

        String response = libroService.borrarLibro(id);

        return ResponseEntity
            .ok()
            .body(Collections.singletonMap("Respuesta", response));
    }


}