package mx.uady.appbusqueda.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.validation.Valid;

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

import mx.uady.appbusqueda.model.AutorLibro;
import mx.uady.appbusqueda.service.AutorLibroService;
import mx.uady.appbusqueda.model.request.AutorLibroRequest;

@RestController
@RequestMapping("/api")
public class AutorLibroRest {

    @Autowired
    private AutorLibroService autorLibroService;

    @GetMapping("/autoresLibros")
    public ResponseEntity<List<AutorLibro>> getAutoresLibros() {
        List<AutorLibro> autoresLibros = autorLibroService.obtenerAutorLibro();
        return ResponseEntity.ok(autoresLibros);
    }

    @PostMapping("/autoresLibros")
    public ResponseEntity<AutorLibro> postAutorLibro(@RequestBody @Valid AutorLibroRequest request) throws URISyntaxException {
        
        AutorLibro autorLibro = autorLibroService.crearAutorLibro(request);
        // 201 Created
        // Header: Location
        return ResponseEntity
            .created(new URI("/autoresLibros/" + autorLibro.getId()))
            .body(autorLibro);
    }


    @PutMapping("/autoresLibros/{idLibro}/{idAutor}")
    public ResponseEntity<AutorLibro> putAutorLibro(@PathVariable Integer idLibro, @PathVariable Integer idAutor, @RequestBody AutorLibroRequest request)
            throws URISyntaxException {

        AutorLibro autorLibro = autorLibroService.editarAutorLibro(idAutor,idLibro, request);
        return ResponseEntity
            .ok()
            .body(autorLibro);
    }

    @GetMapping("/autoresLibros/{idLibro}/{idAutor}")
    public ResponseEntity<AutorLibro> getAutorLibro(@PathVariable Integer idLibro, @PathVariable Integer idAutor){
        return ResponseEntity.ok().body(autorLibroService.getAutorLibro(idAutor, idLibro));
    }

    @DeleteMapping("/autoresLibros/{idLibro}/{idAutor}")
    public ResponseEntity deleteAutorLibro(@PathVariable Integer idLibro, @PathVariable Integer idAutor){
        autorLibroService.borrarAutorLibro(idLibro, idAutor);
        return ResponseEntity
            .ok()
            .body("Autor libro Borrado");
    }




}
