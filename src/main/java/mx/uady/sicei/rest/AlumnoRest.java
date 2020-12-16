package mx.uady.sicei.rest;

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

import mx.uady.sicei.exception.NotFoundException;
import mx.uady.sicei.model.Alumno;
import mx.uady.sicei.model.Licenciatura;
import mx.uady.sicei.model.request.AlumnoRequest;
import mx.uady.sicei.service.AlumnoSerivce;

@RestController // Metaprogramacion
@RequestMapping("/api")
public class AlumnoRest {

    @Autowired
    private AlumnoSerivce alumnoService;

    // GET /api/alumnos
    @GetMapping("/alumnos")
    public ResponseEntity<List<Alumno>> getAlumnos() {

        // ResponseEntity es una abstraccion de una respuesta HTTP, con body y headers

        return ResponseEntity.ok().body(alumnoService.obtenerAlumnos());
    }

    // POST /api/alumnos
    /*@PostMapping("/alumnos")
    public ResponseEntity<Alumno> postAlumnos(@RequestBody @Valid AlumnoRequest request) throws URISyntaxException {
        
        // RequestBody le indica a Java que estamos esperando un request que cumpla con los campos del Objeto AlumnoRequest
        
        Alumno alumno = alumnoService.crearAlumno(request);

        // 201 Created
        // Header: Location
        return ResponseEntity
            .created(new URI("/alumnos/" + alumno.getId()))
            .body(alumno);
    }*/

    // GET /api/alumnos/3 -> 200
    // Validar que exista, si no existe Lanzar un RuntimeException
    @GetMapping("/alumnos/{id}")
    public ResponseEntity<Alumno> getAlumno(@PathVariable Integer id){

        return ResponseEntity.ok().body(alumnoService.getAlumno(id));
    }

    // Validar que exista, si no existe Lanzar un RuntimeException
    @PutMapping("/alumnos/{id}")
    public ResponseEntity<Alumno> putAlumnos(@PathVariable Integer id, @RequestBody AlumnoRequest request)
            throws URISyntaxException {

        Alumno alumno = alumnoService.editarAlumno(id, request);

        return ResponseEntity
            .ok()
            .body(alumno);
    }

    // Validar que exista, si no existe Lanzar un RuntimeException
    @DeleteMapping("/alumnos/{id}")
    public ResponseEntity deleteAlumno(@PathVariable Integer id){

        String response = alumnoService.borrarAlumno(id);

        return ResponseEntity
            .ok()
            .body(Collections.singletonMap("Respuesta", response));
    }


}