package mx.uady.appbusqueda.service;

import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;

import java.util.Optional;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.uady.appbusqueda.exception.NotFoundException;

import mx.uady.appbusqueda.model.request.AutorRequest;
import mx.uady.appbusqueda.model.AutorLibro;
import mx.uady.appbusqueda.model.Autor;
import mx.uady.appbusqueda.model.Libro;

import mx.uady.appbusqueda.repository.AutorRepository;
import mx.uady.appbusqueda.repository.AutorLibroRepository;

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
        autor = autorRepository.saveAndFlush(autor); // INSERT
        return autor;
    }

    public Autor getAutor(Integer idAutor) {
       Autor autor =  autorRepository.getOne(idAutor);
        if (autor == null) {
            throw new NotFoundException("autor");
        }
        return autor;
    }

    public List<Libro> getAutorLibros(Integer idAutor){
        Autor autor = autorRepository.getOne(idAutor);
        List<AutorLibro> autoresLibros = autorLibroRepository.findByAutor(autor);
        List<Libro> libros = new ArrayList<>();
        for(int i=0;i<autoresLibros.size();i++){
            libros.add(autoresLibros.get(i).getLibro());
        }
        return libros;
    }

    public Autor editarAutor(Integer idAutor, AutorRequest request) {

        Autor autor =  autorRepository.getOne(idAutor);
        if(autor!=null){
            autor.setNombre(request.getNombre());
            return autorRepository.saveAndFlush(autor);
        }
        throw new NotFoundException("autor");
    }

    public String borrarAutor(Integer id) {
        Autor autor = autorRepository.getOne(id);
        List<AutorLibro> autoresLibro = autorLibroRepository.findByAutor(autor);
        if(autoresLibro.size()==0){
            autorRepository.deleteById(id);
            return "Autor Borrado";
        } else {
            return "Autor "+id+" No se pudo borrar ya que tiene libros asignadas";
        }
    }
    
}