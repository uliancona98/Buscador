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

import mx.uady.appbusqueda.model.Equipo;
import mx.uady.appbusqueda.service.EquipoService;
import mx.uady.appbusqueda.model.request.EquipoRequest;

@RestController
@RequestMapping("/api")
public class EquipoRest {

    @Autowired
    private EquipoService equipoService;

    @GetMapping("/equipos")
    public ResponseEntity<List<Equipo>> getEquipos() {
        List<Equipo> equipos = equipoService.getEquipos();
        return ResponseEntity.ok(equipos);
    }

     @PostMapping("/equipos")
    public ResponseEntity<Equipo> postEquipo(@RequestBody @Valid EquipoRequest request) throws URISyntaxException {
        
        Equipo equipo = equipoService.crearEquipo(request);

        // 201 Created
        // Header: Location
        return ResponseEntity
            .created(new URI("/equipos/" + equipo.getId()))
            .body(equipo);
    }


    @PutMapping("/equipos/{id}")
    public ResponseEntity<Equipo> putEquipos(@PathVariable Integer id, @RequestBody EquipoRequest request)
            throws URISyntaxException {

        Equipo equipo = equipoService.editarEquipos(id, request);

        return ResponseEntity
            .ok()
            .body(equipo);
    }

    @GetMapping("/equipos/{id}")
    public ResponseEntity<Equipo> getEquipo(@PathVariable Integer id){

        return ResponseEntity.ok().body(equipoService.getEquipo(id));
    }

      @DeleteMapping("/equipos/{id}")
    public ResponseEntity deleteEquipo(@PathVariable Integer id){
        
        if(equipoService.borrarEquipo(id)==true){
            return ResponseEntity
            .ok()
            .body("Equipo Borrado");
        }
        return ResponseEntity.badRequest().body("Hay usuarios con este equipo asginado, no se puede borrar");        
    }

    
}