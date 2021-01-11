package mx.uady.appbusqueda.service;

import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.uady.appbusqueda.exception.NotFoundException;

import mx.uady.appbusqueda.model.request.AutorRequest;
import mx.uady.appbusqueda.model.request.AutorLibroRequest;
import mx.uady.appbusqueda.model.AutorLibro;
import mx.uady.appbusqueda.model.Autor;
import mx.uady.appbusqueda.model.Libro;

import mx.uady.appbusqueda.repository.AutorRepository;
import mx.uady.appbusqueda.repository.AutorLibroRepository;
import mx.uady.appbusqueda.repository.UsuarioRepository;

@Service
public class AutorService {

    @Autowired
    private AutorRepository autorRepository;
    @Autowired
    private AutorLibroRepository autorLibroRepository;


    public List<Autor> obtenerAutores() {
        
        List<Autor> autores = new LinkedList<>();
        autorRepository.findAll().iterator().forEachRemaining(autores::add);
        return autores;
    }

    public Autor crearAutor(AutorRequest request) {
        Autor autor = new Autor();
        autor.setNombre(request.getNombre());
        autor = autorRepository.save(autor); // INSERT
        return autor;
    }

    public Autor getAutor(Integer idAutor) {
        return autorRepository.findById(idAutor)
            .orElseThrow(() -> new NotFoundException("No existe el autor"));
    }

    public List<Libro> getAutorLibros(Integer idAutor){
        Optional<Autor> autor = autorRepository.findById(idAutor);
        List<AutorLibro> autoresLibros = autorLibroRepository.findByAutor(autor.get());
        List<Libro> libros = new ArrayList();
        for(int i=0;i<autoresLibros.size();i++){
            libros.add(autoresLibros.get(i).getLibro());
        }
        return libros;
    }

    public Autor editarAutor(Integer idAutor, AutorRequest request) {
        return autorRepository.findById(idAutor)
        .map(autor -> {
            autor.setNombre(request.getNombre());
            return autorRepository.save(autor);
        })
        .orElseThrow(() -> new NotFoundException("No existe ese autor"));
    }

    public String borrarAutor(Integer id) {
        Optional<Autor> autor = autorRepository.findById(id);
        List<AutorLibro> autoresLibro = autorLibroRepository.findByAutor(autor.get());
        if(autoresLibro.size()==0){
            autorRepository.deleteById(id);
            return "Autor Borrado";
        } else {
            return "Autor "+id+" No se pudo borrar ya que tiene libros asignadas";
        }
    }
    
}