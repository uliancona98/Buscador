package mx.uady.appbusqueda.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import mx.uady.appbusqueda.exception.NotFoundException;
import java.util.Set;
import java.io.IOException;
import java.util.Iterator;
import java.util.HashSet;
import mx.uady.appbusqueda.model.request.LibroRequest;
import mx.uady.appbusqueda.model.request.AutorLibroRequest;
import mx.uady.appbusqueda.model.request.AutorRequest;
import mx.uady.appbusqueda.model.Autor;
import mx.uady.appbusqueda.model.AutorLibro;
import mx.uady.appbusqueda.model.Libro;
import mx.uady.appbusqueda.model.Usuario;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import mx.uady.appbusqueda.repository.AutorRepository;
import mx.uady.appbusqueda.repository.LibroRepository;
import mx.uady.appbusqueda.repository.AutorLibroRepository;
import mx.uady.appbusqueda.repository.UsuarioRepository;
import org.apache.commons.csv.CSVRecord;

@Service
public class LibroService {
    @Autowired
    private LibroRepository libroRepository;
    @Autowired
    private AutorRepository autorRepository;
    @Autowired
    private AutorLibroRepository autorLibroRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    public static String TYPECSV = "text/csv";
    public static String TYPEPDF = "application/pdf";

    public List<Libro> obtenerLibros() {

        List<Libro> libros = new LinkedList<>();
        libroRepository.findAll().iterator().forEachRemaining(libros::add); // SELECT(id, nombre)
        return libros;
    }

    public Libro crearLibro(LibroRequest request, Usuario usuario) {
        Libro libro = new Libro();
        libro.setEditorial(request.getEditorial());
        libro.setFechaPublicacion(request.getFechaPublicacion());
        libro.setIsbn(request.getIsbn());
        libro.setTitulo(request.getTitulo());
        libro.setAutor(request.getAutor());
        libro.setUsuario(usuario); //se le envia el usuario

        Set<AutorRequest> autoresRequest = request.getAutores();
        Iterator iterator = autoresRequest.iterator();
	
        //simple iteration
        Set<Autor> autores = new HashSet<>();
        while(iterator.hasNext()){
            AutorRequest autorRequest = (AutorRequest)iterator.next();
            Autor autorBuscado=autorRepository.findByNombre(autorRequest.getNombre());
            if(autorBuscado!=null){
                autores.add(autorBuscado);
            }else{
                Autor autor = new Autor();
                autor.setNombre(autorRequest.getNombre());
                autor = autorRepository.save(autor); // INSERT
                autores.add(autor);

            }
        }
        libro.setAutores(autores);
        libro = libroRepository.save(libro); // INSERT
        return libro;
    }

    public Libro getLibro(Integer id) {
        return libroRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("No existe el libro"));
    }

    public Libro editarLibro(Integer id, LibroRequest request) {
        return libroRepository.findById(id)
        .map(libro -> {
            libro.setEditorial(request.getEditorial());
            libro.setFechaPublicacion(request.getFechaPublicacion());
            libro.setIsbn(request.getIsbn());
            libro.setAutor(request.getAutor());
            libro.setTitulo(request.getTitulo());
            return libroRepository.save(libro);
        })
        .orElseThrow(() -> new NotFoundException("No existe ese libro"));
    }

    public String borrarLibro(Integer id) {
        Optional<Libro> libro = libroRepository.findById(id);
        List<AutorLibro> autoresLibro = autorLibroRepository.findByLibro(libro.get());

        if(autoresLibro.size()==0){
            libroRepository.deleteById(id);
            return "Libro Borrado";
        } else {
            return "Libro "+id+" No se pudo borrar ya que tiene autores asignadas";
        }
    }


  
    public List<Libro> saveCSV(MultipartFile file, Usuario usuario) {
      try {
            BufferedReader br;
            List<String> result = new ArrayList<>();
            String line;
            InputStream is = file.getInputStream();
            List<Libro> libros = new ArrayList<Libro>();
            br = new BufferedReader(new InputStreamReader(is));
            int contador = 0;
            while ((line = br.readLine()) != null) {
                String[] elements = line.split(",");
                String titulo = elements[0];
                String autoresString = elements[1];
                String fechaPublicacion = elements[2];
                String editorial = elements[3];
                String isbn = elements[4];

                Libro libro = new Libro();
                libro.setTitulo(titulo);
                libro.setEditorial(editorial);
                libro.setAutor(autoresString);
                libro.setFechaPublicacion(LocalDate.parse(fechaPublicacion));
                libro.setIsbn(isbn);

                /*Autores */
                String[] autoresArray = autoresString.split("/");
                Set<Autor> autores = new HashSet<>();
                for(int i=0;i<autoresArray.length;i++){
                    Autor autorBuscado=autorRepository.findByNombre(autoresArray[i]);
                    if(autorBuscado!=null){
                        autores.add(autorBuscado);
                    }else{
                        Autor autor = new Autor();
                        autor.setNombre(autoresArray[i]);
                        autor = autorRepository.save(autor); // INSERT
                        autores.add(autor);
                    }
                }
                libro.setAutores(autores);
                libro.setUsuario(usuario); //se le envia el usuario
                libroRepository.save(libro);
                libros.add(libro);
            }
            return libros;
        //libroRepository.saveAll(libros);
      } catch (IOException e) {
        throw new RuntimeException("fail to store csv data: " + e.getMessage());
      }
    }

    public void savePDF(MultipartFile file){
        try{
            String fileType = "pdf";
            String fileName = file.getOriginalFilename();
            String folderPath = "C:/xampp/htdocs/sicei/Buscador/files";
            String filePath = folderPath + "/" + fileName;
            // Copies Spring's multipartfile inputStream to /sismed/temp/exames (absolute path)
            Files.copy(file.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
        }catch(Exception e){
            throw new RuntimeException("fail to store pdf file: " + e.getMessage());
        }
    }

    public boolean hasCSVFormat(MultipartFile file) {

        if (!TYPECSV.equals(file.getContentType())) {
          return false;
        }
    
        return true;
    }

    public boolean hasPdfFormat(MultipartFile file) {

        if (!TYPEPDF.equals(file.getContentType())) {
          return false;
        }
    
        return true;
    }
    
}