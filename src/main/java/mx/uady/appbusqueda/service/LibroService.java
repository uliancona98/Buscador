package mx.uady.appbusqueda.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.uady.appbusqueda.exception.NotFoundException;

import mx.uady.appbusqueda.model.request.LibroRequest;
import mx.uady.appbusqueda.model.AutorLibro;
import mx.uady.appbusqueda.model.Libro;
import mx.uady.appbusqueda.model.Usuario;
import mx.uady.appbusqueda.repository.LibroRepository;
import mx.uady.appbusqueda.repository.AutorLibroRepository;
import mx.uady.appbusqueda.repository.UsuarioRepository;

@Service
public class LibroService {

    @Autowired
    private LibroRepository libroRepository;
    @Autowired
    private AutorLibroRepository autorLibroRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Libro> obtenerLibros() {

        List<Libro> libros = new LinkedList<>();
        libroRepository.findAll().iterator().forEachRemaining(libros::add); // SELECT(id, nombre)
        return libros;
    }

    public Libro crearLibro(LibroRequest request) {
        Libro libro = new Libro();
        libro.setContenido(request.getContenido());
        libro.setEditorial(request.getEditorial());
        libro.setFechaPublicacion(request.getFechaPublicacion());
        libro.setIsbn(request.getIsbn());
        libro.setNombreArchivo(request.getNombreArchivo());
        libro.setTitulo(request.getTitulo());
        Usuario usuario = usuarioRepository.findByUsuario(request.getUsuario());
        libro.setUsuario(usuario); //se le envia el usuario
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
            libro.setContenido(request.getContenido());
            libro.setEditorial(request.getEditorial());
            libro.setFechaPublicacion(request.getFechaPublicacion());
            libro.setIsbn(request.getIsbn());
            libro.setNombreArchivo(request.getNombreArchivo());
            libro.setTitulo(request.getTitulo());
            return libroRepository.save(libro);
        })
        .orElseThrow(() -> new NotFoundException("No existe ese libro"));
    }

    public String borrarLibro(Integer id) {

        List<Libro> libros = new LinkedList<>();
        libroRepository.findAll().iterator().forEachRemaining(libros::add);
        if(libros.size() < id || id <= 0){
            throw new NotFoundException("No existe ese libro");
        }
        Optional<Libro> libro = libroRepository.findById(id);
        List<AutorLibro> autoresLibro = autorLibroRepository.findByLibro(libro.get());

        if(autoresLibro.size()==0){
            libroRepository.deleteById(id);
            return "Libro Borrado";
        } else {
            return "Libro "+id+" No se pudo borrar ya que tiene autores asignadas";
        }
    }
    
}