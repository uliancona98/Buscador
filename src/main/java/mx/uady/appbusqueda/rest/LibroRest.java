package mx.uady.appbusqueda.rest;
import java.io.InputStream;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import javax.validation.Valid;
import java.util.Collections;
import java.io.IOException;
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
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mx.uady.appbusqueda.exception.NotFoundException;
import mx.uady.appbusqueda.model.Libro;
import mx.uady.appbusqueda.model.Usuario;
import mx.uady.appbusqueda.model.request.LibroRequest;
import mx.uady.appbusqueda.model.ResponseMessage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.io.InputStreamReader;
import mx.uady.appbusqueda.service.LibroService;
import org.springframework.security.core.context.*;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.core.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
@RestController // Metaprogramacion
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
        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Libro libro = libroService.crearLibro(request, usuario);

        // 201 Created
        // Header: Location
        return ResponseEntity
            .created(new URI("/libros/" + libro.getId()))
            .body(libro);
    }
/*Upload csv or pdf*/
    @PostMapping("/upload") 
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
      String message = "";
  
      if (libroService.hasCSVFormat(file)) {
        try {
          Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

          InputStream inputStream = file.getInputStream();

          List<Libro> libros = libroService.saveCSV(file, usuario);
          message = "Uploaded the file successfully: " + file.getOriginalFilename();
          return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(libros));
        } catch (Exception e) {
          message = "Could not upload the file: " + file.getOriginalFilename() + "!";
          return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
      }else if(libroService.hasPdfFormat(file)){
        //guarda el pdf en una carpeta
        try {
          libroService.savePDF(file);
          message = "Saved the file successfully: " + file.getOriginalFilename();
          return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
          message = "Could not save the file: " + file.getOriginalFilename() + "!";
          return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
      //}
      }
      message = "Please upload a csv or pdf file!";
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
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
    public ResponseEntity deleteLibro(@PathVariable Integer id){

        String response = libroService.borrarLibro(id);

        return ResponseEntity
            .ok()
            .body(Collections.singletonMap("Respuesta", response));
    }


}