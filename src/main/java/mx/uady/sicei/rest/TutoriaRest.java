package mx.uady.sicei.rest;

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

import mx.uady.sicei.model.Tutoria;
import mx.uady.sicei.service.TutoriaService;
import mx.uady.sicei.model.request.TutoriaRequest;

@RestController
@RequestMapping("/api")
public class TutoriaRest {

    @Autowired
    private TutoriaService tutoriaService;

    @GetMapping("/tutorias")
    public ResponseEntity<List<Tutoria>> getTutorias() {
        List<Tutoria> tutorias = tutoriaService.obtenerTutorias();
        return ResponseEntity.ok(tutorias);
    }

    @PostMapping("/tutorias")
    public ResponseEntity<Tutoria> postTutoria(@RequestBody @Valid TutoriaRequest request) throws URISyntaxException {
        
        Tutoria tutoria = tutoriaService.crearTutoria(request);

        // 201 Created
        // Header: Location
        return ResponseEntity
            .created(new URI("/tutorias/" + tutoria.getId()))
            .body(tutoria);
    }


    @PutMapping("/tutorias/{alumnoId}/{profesorId}")
    public ResponseEntity<Tutoria> putTutorias(@PathVariable Integer alumnoId, @PathVariable Integer profesorId, @RequestBody TutoriaRequest request)
            throws URISyntaxException {

        Tutoria tutoria = tutoriaService.editarTutorias(alumnoId, profesorId, request);

        return ResponseEntity
            .ok()
            .body(tutoria);
    }

    @GetMapping("/tutorias/{alumnoId}/{profesorId}")
    public ResponseEntity<Tutoria> getTutoria(@PathVariable Integer alumnoId, @PathVariable Integer profesorId){

        return ResponseEntity.ok().body(tutoriaService.getTutoria(alumnoId, profesorId));
    }

    @DeleteMapping("/tutorias/{alumnoId}/{profesorId}")
    public ResponseEntity deleteTutoria(@PathVariable Integer alumnoId, @PathVariable Integer profesorId){

        tutoriaService.borrarTutoria(alumnoId, profesorId);

        return ResponseEntity
            .ok()
            .body("Tutoria Borrada");
    }




}
