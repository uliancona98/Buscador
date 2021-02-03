package mx.uady.appbusqueda.service;
import java.util.UUID;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.time.LocalDate;
import java.io.File;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import org.apache.pdfbox.pdmodel.PDDocument;   
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import mx.uady.appbusqueda.exception.NotFoundException;
import java.util.Set;
import java.io.IOException;
import java.util.Iterator;
import java.util.HashSet;
import mx.uady.appbusqueda.model.request.AutorRequest;
import mx.uady.appbusqueda.model.request.LibroRequest;
import mx.uady.appbusqueda.model.Autor;
import mx.uady.appbusqueda.model.AutorLibro;
import mx.uady.appbusqueda.model.Libro;
import mx.uady.appbusqueda.model.Usuario;
import mx.uady.appbusqueda.repository.AutorRepository;
import mx.uady.appbusqueda.repository.LibroRepository;
import mx.uady.appbusqueda.repository.AutorLibroRepository;

@Service
public class LibroService {
    @Autowired
    private LibroRepository libroRepository;
    @Autowired
    private AutorRepository autorRepository;
    @Autowired
    private AutorLibroRepository autorLibroRepository;

    @Value("${solr.collection.text.pdf.savePath:null}") 
    private static String folderPath;

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
        Iterator<AutorRequest> iterator = autoresRequest.iterator();
	
        //simple iteration
        Set<Autor> autores = new HashSet<>();
        while(iterator.hasNext()){
            AutorRequest autorRequest = (AutorRequest)iterator.next();
            Autor autorBuscado=autorRepository.getOne(autorRequest.getId());
            if(autorBuscado!=null){
                autores.add(autorBuscado);
            }else{
                Autor autor = new Autor();
                autor.setNombre(autorRequest.getNombre());
                autor = autorRepository.saveAndFlush(autor); 
                autores.add(autor);
                autor = autorRepository.saveAndFlush(autor);
            }
        }
        libro.setAutores(autores);
        libro = libroRepository.saveAndFlush(libro); // INSERT
        return libro;
    }

    public Libro getLibro(Integer id) {
        Libro libro = libroRepository.getOne(id);
        if (libro != null) {
            return libro;
        }
        throw new NotFoundException("libro");
    }

    public Libro editarLibro(Integer id, LibroRequest request) {
        Libro libro = libroRepository.getOne(id);
        if (libro != null) {
            libro.setEditorial(request.getEditorial());
            libro.setFechaPublicacion(request.getFechaPublicacion());
            libro.setIsbn(request.getIsbn());
            libro.setAutor(request.getAutor());
            libro.setFechaUltimaModificacion();
            libro.setTitulo(request.getTitulo());
            return libroRepository.saveAndFlush(libro);        
        }
        throw new NotFoundException("libro");
    }

    public LibroRepository getRepository() {
        return libroRepository;
    }


    public String borrarLibro(Integer id) {
        Libro libro = libroRepository.getOne(id);
        List<AutorLibro> autoresLibro = autorLibroRepository.findByLibro(libro);

        if(autoresLibro.isEmpty()){
            libroRepository.deleteById(id);
            return "Libro Borrado";
        } else {
            return "Libro "+id+" No se pudo borrar ya que tiene autores asignadas";
        }
    }

    public List<Libro> saveCSV(MultipartFile file, Usuario usuario) {
      try {
            BufferedReader br;
            String line;
            InputStream is = file.getInputStream();
            List<Libro> libros = new ArrayList<Libro>();
            br = new BufferedReader(new InputStreamReader(is));

            while ((line = br.readLine()) != null) {
                String[] elements = line.split(",");

                Integer id =Integer.parseInt(elements[0]);
                String titulo = elements[1];
                String autoresString = elements[2];
                String fechaPublicacion = elements[3];
                String editorial = elements[4];
                String isbn = elements[5];
                LibroRequest libroRequest = new LibroRequest();
                libroRequest.setTitulo(titulo);
                libroRequest.setFechaPublicacion(LocalDate.parse(fechaPublicacion));
                libroRequest.setIsbn(isbn);
                libroRequest.setEditorial(editorial);
                libroRequest.setAutor(autoresString);

                Libro libroBuscado = libroRepository.getOne(id);

                if(libroBuscado == null){
                    //Se crea el libro
                    Libro libro = new Libro();
                    libro.setTitulo(libroRequest.getTitulo());
                    libro.setEditorial(libroRequest.getEditorial());
                    libro.setAutor(libroRequest.getAutor());
                    libro.setFechaPublicacion(libroRequest.getFechaPublicacion());
                    libro.setIsbn(libroRequest.getIsbn());
                    
                    /*Autores */
                    String[] autoresArray = autoresString.split("/");
                    Set<Autor> autores = new HashSet<>();
                    for(int i=0;i<autoresArray.length;i++){
                        Autor autorBuscado=autorRepository.findByNombre(autoresArray[i]);

                        if(autorBuscado!=null){//existe el autor
                            autores.add(autorBuscado);
                        }else{
                            AutorRequest autorRequest = new AutorRequest();
                            autorRequest.setNombre(autoresArray[i]);
                            libroRequest.setAutor(autorRequest);//ya se agrega a la tabla de autor libro

                            Autor autor = new Autor();
                            autor.setNombre(autoresArray[i]);
                            autor = autorRepository.saveAndFlush(autor); // INSERT
                            autores.add(autor);
                        }
                    }
                    libro.setAutor(autoresString);
                    libro.setAutores(autores);
                    libro.setUsuario(usuario); //se le envia el usuario
                    libroRepository.saveAndFlush(libro);
                    libros.add(libro);
                }else{
                    
                    libroBuscado.setTitulo(libroRequest.getTitulo());
                    libroBuscado.setEditorial(libroRequest.getEditorial());
                    libroBuscado.setAutor(libroRequest.getAutor());
                    libroBuscado.setFechaPublicacion(libroRequest.getFechaPublicacion());
                    libroBuscado.setIsbn(libroRequest.getIsbn());
                    libroBuscado.setFechaUltimaModificacion();
    
                    /*Autores */
                    String[] autoresArray = autoresString.split("/");
                    Set<Autor> autores = new HashSet<>();
                    for(int i=0;i<autoresArray.length;i++){
                        Autor autorBuscado=autorRepository.findByNombre(autoresArray[i]);
                        if(autorBuscado!=null){
                            autores.add(autorBuscado);
                        }else{
                            AutorRequest autorRequest = new AutorRequest();
                            autorRequest.setNombre(autoresArray[i]);
                            //libroRequest.setAutor(autorRequest);

                            Autor autor = new Autor();
                            autor.setNombre(autorRequest.getNombre());
                            autor = autorRepository.saveAndFlush(autor); // INSERT
                            autores.add(autor);
                        }
                    }
                    libroBuscado.setAutor(autoresString);
                    libroBuscado.setAutores(autores);
                    libroBuscado.setUsuario(usuario); //se le envia el usuario
                    libroRepository.saveAndFlush(libroBuscado);

                    libros.add(libroBuscado);


                }
            }
            return libros;
        //libroRepository.saveAll(libros);
      } catch (IOException e) {
        throw new RuntimeException("Error al guardar datos del CSV: " + e.getMessage());
      }
    }

    public Libro savePDF(MultipartFile file,Usuario usuario){
        try{
            String folderPathFile = "C:/xampp/htdocs/sicei/Buscador/files";
            //Primero lo guardo
            String fileName = file.getOriginalFilename();
            String uuid = UUID.randomUUID().toString();

            String filePath = folderPathFile + "/" + uuid+".pdf";
            System.out.println(filePath);
            Files.copy(file.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);


            //Loading an existing document   
            File fileType = new File(filePath);  
            PDDocument doc = PDDocument.load(fileType);  
        
            //Getting the PDDocumentInformation object  
            PDDocumentInformation pdd = doc.getDocumentInformation();  

            Libro libro = new Libro();
            if(pdd.getTitle()!=null){
                libro.setTitulo(pdd.getTitle());
            }else{
                libro.setTitulo("");
            }
            if(pdd.getTitle()!=null){
                libro.setAutor(pdd.getAuthor());
            }else{
                libro.setAutor("");
            }
            libro.setURL(filePath);
            libro.setUsuario(usuario); 
            libroRepository.saveAndFlush(libro);
            //Retrieving the info of a PDF document
            System.out.println("Author of the PDF document is :"+ pdd.getAuthor());  
            doc.close();  
            // Copies Spring's multipartfile inputStream to /sismed/temp/exames (absolute path)
            return libro;
        }catch(Exception e){
            throw new RuntimeException("Error al guardar el libro o al leerlo: " + e.getMessage());
        }
    }

    public boolean hasCSVFormat(MultipartFile file) {
        return TYPECSV.equals(file.getContentType());
    }

    public boolean hasPdfFormat(MultipartFile file) {
        return TYPEPDF.equals(file.getContentType());
    }
    
}