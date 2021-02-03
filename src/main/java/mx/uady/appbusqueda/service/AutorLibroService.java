package mx.uady.appbusqueda.service;
import java.util.Optional;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jdk.javadoc.internal.doclets.formats.html.SourceToHTMLConverter;
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

    public Libro crearAutorLibro(AutorLibroRequest request) {
        Autor autor = autorRepository.getOne(request.getIdAutor());
        Libro libro = libroRepository.getOne(request.getIdLibro());
        Integer idLibro = request.getIdLibro();
        Integer idAutor = request.getIdAutor();


        if (autor == null) {
            throw new NotFoundException("autor");
        }else if(libro == null){
            throw new NotFoundException("libro");
        }else{
            Libro libroB = libro;
            Autor autorB = autor;
            libroB.getAutores().add(autorB);
            libroRepository.saveAndFlush(libroB);
            return libroB;
        }
    }

    public AutorLibro getAutorLibro(Integer idAutor, Integer idLibro) {
        Autor autor = autorRepository.getOne(idAutor);
        Libro libro = libroRepository.getOne(idLibro);


        if (autor == null) {
            throw new NotFoundException("autor");
        }else if(libro == null){
            throw new NotFoundException("libro");
        }

        List<AutorLibro> autorLibroExistenteLista = autorLibroRepository.findByLibroAndAutor(libro, autor);
        if(autorLibroExistenteLista.size()==1){
            return autorLibroExistenteLista.get(0);
        }
        throw new NotFoundException("autor y libro solicitado");
        
    }

    public void borrarAutorLibro(Integer idLibro, Integer idAutor) {

        Autor autor = autorRepository.getOne(idAutor);
        Libro libro = libroRepository.getOne(idLibro);
        if (autor == null) {
            throw new NotFoundException("autor");
        }else if(libro == null){
            throw new NotFoundException("libro");
        }
        List<AutorLibro> autorLibroExistenteLista = autorLibroRepository.findByLibroAndAutor(libro, autor);
        if(autorLibroExistenteLista.size()==1){
            autorLibroRepository.deleteById(autorLibroExistenteLista.get(0).getId());
        }else{
            throw new NotFoundException("autor y libro solicitada");
        }
    }
    
}