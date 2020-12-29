package mx.uady.appbusqueda.service;
import java.util.Optional;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import mx.uady.appbusqueda.exception.NotFoundException;

import mx.uady.appbusqueda.model.AutorLibro;
import mx.uady.appbusqueda.model.Autor;
import mx.uady.appbusqueda.model.Libro;
import mx.uady.appbusqueda.repository.AutorLibroRepository;
import mx.uady.appbusqueda.repository.AutorRepository;
import mx.uady.appbusqueda.repository.LibroRepository;
import mx.uady.appbusqueda.model.request.AutorLibroRequest;

@Service
public class AutorLibroService {

    @Autowired
    private AutorLibroRepository autorLibroRepository;
    @Autowired
    private AutorRepository autorRepository;
    @Autowired
    private LibroRepository libroRepository;

    public List<AutorLibro> obtenerAutorLibro() {
        List<AutorLibro> autoresLibros = new LinkedList<>();
        autorLibroRepository.findAll().iterator().forEachRemaining(autoresLibros::add);
        return autoresLibros;
    }

    public AutorLibro crearAutorLibro(AutorLibroRequest request) {
        Optional <Autor> autor = autorRepository.findById(request.getIdAutor());
        Optional <Libro> libro = libroRepository.findById(request.getIdLibro());
        Integer idLibro = request.getIdLibro();
        Integer idAutor = request.getIdAutor();
        Optional<AutorLibro> autorLibroExistente = autorLibroRepository.findByIdLibroAndIdAutor(idLibro, idAutor);
        if (!autor.isPresent()) {
            throw new NotFoundException("El autor no está registrado");
        }else if(!libro.isPresent()){
            throw new NotFoundException("El libro no está registrado");
        }else if (!autorLibroExistente.isPresent()) {
            AutorLibro autorLibro = new AutorLibro();
            autorLibro.setAutor(autor.get());
            autorLibro.setLibro(libro.get());
            autorLibro = autorLibroRepository.save(autorLibro); // INSERT
            return autorLibro;
        }
        throw new NotFoundException("El autor y el libro ya está registrado");
    }

    public AutorLibro getAutorLibro(Integer idAutor, Integer idLibro) {

        autorRepository.findById(idAutor)
            .orElseThrow(() -> new NotFoundException("El autor no está registrado"));

        libroRepository.findById(idLibro)
            .orElseThrow(() -> new NotFoundException("El libro no está registrado"));

        return autorLibroRepository.findByIdLibroAndIdAutor(idLibro, idAutor)
            .orElseThrow(() -> new NotFoundException("El autor y el libro no se encuentra"));
    }

    public AutorLibro editarAutorLibro(Integer id, Integer idAutor, Integer idLibro, AutorLibroRequest request) {

        autorRepository.findById(idAutor)
            .orElseThrow(() -> new NotFoundException("El autor no está registrado"));

        libroRepository.findById(idLibro)
            .orElseThrow(() -> new NotFoundException("El libro no está registrado"));

        return autorLibroRepository.findByIdLibroAndIdAutor(idLibro,idAutor)
        .map(autorLibro -> {
            return autorLibroRepository.save(autorLibro);
        })
        .orElseThrow(() -> new NotFoundException("El autor y el libro no se encuentra"));
    }

    public void borrarAutorLibro(Integer id, Integer idLibro, Integer idAutor) {

        libroRepository.findById(idLibro)
            .orElseThrow(() -> new NotFoundException("El libro no está registrado"));

        autorRepository.findById(idAutor)
            .orElseThrow(() -> new NotFoundException("El autor no está registrado"));

        AutorLibro autorLibroExistente = autorLibroRepository.findByIdLibroAndIdAutor(idLibro, idAutor);
        //checar
        if (autorLibroExistente !=null) {
            autorLibroRepository.deleteById(autorLibroExistente.getId());
        }
        throw new NotFoundException("No se encontró al libro y al autor");
    }
    
}