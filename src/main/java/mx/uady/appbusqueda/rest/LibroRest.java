package mx.uady.appbusqueda.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.io.File;   
import java.io.IOException;  
import java.util.ArrayList;
import org.springframework.data.repository.CrudRepository;
import java.util.HashMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.Valid;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.apache.pdfbox.pdmodel.PDDocument;   
import org.apache.pdfbox.pdmodel.PDDocumentInformation;  
import mx.uady.appbusqueda.model.Libro;
import mx.uady.appbusqueda.model.Usuario;
import mx.uady.appbusqueda.model.request.LibroRequest;
import mx.uady.appbusqueda.model.response.ResponseMessage;
import mx.uady.appbusqueda.service.LibroService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

@RestController
@RequestMapping("/api")
public class LibroRest {

    @Autowired
    private LibroService libroService;


    // GET /api/libros
    @GetMapping("/libros")
    public ResponseEntity<List<Libro>> getLibros() {
        return ResponseEntity.ok().body(libroService.obtenerLibros());
    }

    // POST /api/libros
    @PostMapping("/libros")
    public ResponseEntity<Libro> postLibros(@RequestBody @Valid LibroRequest request) throws URISyntaxException {
        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Libro libro = libroService.crearLibro(request, usuario);
        return ResponseEntity
            .created(new URI("/libros/" + libro.getId()))
            .body(libro);
    }



    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
      String message = "";
      Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      if (libroService.hasCSVFormat(file)) {
        try {
          // InputStream inputStream = file.getInputStream();

          List<Libro> libros = libroService.saveCSV(file, usuario);
          message = "Uploaded the file successfully: " + file.getOriginalFilename();
          return ResponseEntity.status(HttpStatus.OK).body(libros);
        } catch (Exception e) {
          message = "Could not upload the file: " + file.getOriginalFilename() + "!";
          return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
      }else if(libroService.hasPdfFormat(file)){
        //guarda el pdf en una carpeta
        try {
          Libro libro = libroService.savePDF(file, usuario);
          if(libro!=null){
            return ResponseEntity.status(HttpStatus.OK).body(libro);
          }
        } catch (Exception e) {
          message = "Could not save the file: " + file.getOriginalFilename() + "!";
          return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
      }
      message = "Please upload a csv or pdf file!";
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
    }

    @GetMapping("/libros/{id}")
    public ResponseEntity<Libro> getLibro(@PathVariable Integer id){

        return ResponseEntity.ok().body(libroService.getLibro(id));
    }

    @PutMapping("/libros/{id}")
    public ResponseEntity<Libro> putLibros(@PathVariable Integer id, @RequestBody LibroRequest request)
            throws URISyntaxException {

        Libro libro = libroService.editarLibro(id, request);

        return ResponseEntity
            .ok()
            .body(libro);
    }

    @DeleteMapping("/libros/{id}")
    public ResponseEntity<Map<String, String>> deleteLibro(@PathVariable Integer id){

        String response = libroService.borrarLibro(id);

        return ResponseEntity
            .ok()
            .body(Collections.singletonMap("message", response));
    }

    private Sort.Direction getSortDirection(String direction) {
      if (direction.equals("asc")) {
        return Sort.Direction.ASC;
      } else if (direction.equals("desc")) {
        return Sort.Direction.DESC;
      }
  
      return Sort.Direction.ASC;
    }
    @GetMapping("/booksPage")
    public ResponseEntity<Map<String, Object>> getAllBooksPage(
      @RequestParam(required = false) String titulo,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "3") int size,
      @RequestParam(defaultValue = "id,desc") String[] sort) {
        try {
          List<Order> orders = new ArrayList<Order>();
          System.out.println("ja");
          if (sort[0].contains(",")) {
            // will sort more than 2 fields
            // sortOrder="field, direction"
            for (String sortOrder : sort) {
              String[] _sort = sortOrder.split(",");
              orders.add(new Order("asc"));
            }
          } else {
            System.out.println("ja");

            // sort=[field, direction]
            orders.add(new Order(getSortDirection(sort[1]), sort[0]));
          }

        List<Libro> libros = new ArrayList<Libro>();
        Pageable pagingSort = PageRequest.of(page, size, Sort.by(orders));

        Page<Libro> pageTuts;
        if (titulo == null){
          pageTuts = libroService.getRepository().findAll(pagingSort);
        }else{
          pageTuts = libroService.getRepository().findByTitulo(titulo, pagingSort);
        }
          libros = pageTuts.getContent();
        
        if (libros.isEmpty()) {
          return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("libros", libros);
        response.put("currentPage", pageTuts.getNumber());
        response.put("totalItems", pageTuts.getTotalElements());
        response.put("totalPages", pageTuts.getTotalPages());

        return new ResponseEntity<>(response, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

}