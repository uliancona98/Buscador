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
        //AutorLibro autorLibroExistente = autorLibroRepository.findByIdLibroAndIdAutor(idLibro, idAutor).get(0);


        if (!autor.isPresent()) {
            throw new NotFoundException("El autor no está registrado");
        }else if(!libro.isPresent()){
            throw new NotFoundException("El libro no está registrado");
        }else{
            Libro libroB = libro.get();
            Autor autorB = autor.get();
            libroB.getAutores().add(autorB);
            libroRepository.save(libroB);  
            List<AutorLibro> autoresLibros = autorLibroRepository.findByLibroAndAutor(libroB, autorB);
            if (autoresLibros.size()==1) {
                return autoresLibros.get(0);
            }
        }
        throw new NotFoundException("El autor y el libro ya está registrado");
    }

    public AutorLibro getAutorLibro(Integer idAutor, Integer idLibro) {

        autorRepository.findById(idAutor)
            .orElseThrow(() -> new NotFoundException("El autor no está registrado"));

        libroRepository.findById(idLibro)
            .orElseThrow(() -> new NotFoundException("El libro no está registrado"));
            
        Optional<Autor> autor = autorRepository.findById(idAutor);
        Optional<Libro> libro = libroRepository.findById(idLibro);
        List<AutorLibro> autorLibroExistenteLista = autorLibroRepository.findByLibroAndAutor(libro.get(), autor.get());
        if(autorLibroExistenteLista.size()==1){
            return autorLibroExistenteLista.get(0);
        }else{
            throw new NotFoundException("El autor y el libro no se encuentra");
        }
    }

    public AutorLibro editarAutorLibro(Integer idLibro, Integer idAutor, AutorLibroRequest request) {

        autorRepository.findById(idAutor)
            .orElseThrow(() -> new NotFoundException("El autor no está registrado"));

        libroRepository.findById(idLibro)
            .orElseThrow(() -> new NotFoundException("El libro no está registrado"));

        Optional<Autor> autor = autorRepository.findById(idAutor);
        Optional<Libro> libro = libroRepository.findById(idLibro);

        List<AutorLibro> autorLibroExistenteLista = autorLibroRepository.findByLibroAndAutor(libro.get(), autor.get());

        if(autorLibroExistenteLista.size()==1){//check
            return autorLibroRepository.save(autorLibroExistenteLista.get(0));
        }
        throw new NotFoundException("El autor y el libro no se encuentra");
    }

    public void borrarAutorLibro(Integer idLibro, Integer idAutor) {

        libroRepository.findById(idLibro)
            .orElseThrow(() -> new NotFoundException("El libro no está registrado"));

        autorRepository.findById(idAutor)
            .orElseThrow(() -> new NotFoundException("El autor no está registrado"));

        Optional<Autor> autor = autorRepository.findById(idAutor);
        Optional<Libro> libro = libroRepository.findById(idLibro);
        List<AutorLibro> autorLibroExistenteLista = autorLibroRepository.findByLibroAndAutor(libro.get(), autor.get());
        if(autorLibroExistenteLista.size()==1){
            autorLibroRepository.deleteById(autorLibroExistenteLista.get(0).getId());
        }else{
            throw new NotFoundException("El autor y el libro no se encuentra");
        }
    }
    
}