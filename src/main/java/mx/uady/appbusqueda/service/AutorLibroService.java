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
        Optional <Autor> autor = autorRepository.findById(request.getIdAutor());
        Optional <Libro> libro = libroRepository.findById(request.getIdLibro());
        Integer idLibro = request.getIdLibro();
        Integer idAutor = request.getIdAutor();
        //AutorLibro autorLibroExistente = autorLibroRepository.findByIdLibroAndIdAutor(idLibro, idAutor).get(0);


        if (!autor.isPresent()) {
            throw new NotFoundException("autor");
        }else if(!libro.isPresent()){
            throw new NotFoundException("libro");
        }else{
            Libro libroB = libro.get();
            Autor autorB = autor.get();
            libroB.getAutores().add(autorB);
            libroRepository.save(libroB);
            return libroB;
        }
    }

    public AutorLibro getAutorLibro(Integer idAutor, Integer idLibro) {

        autorRepository.findById(idAutor)
            .orElseThrow(() -> new NotFoundException("autor"));

        libroRepository.findById(idLibro)
            .orElseThrow(() -> new NotFoundException("libro"));
            
        Optional<Autor> autor = autorRepository.findById(idAutor);
        Optional<Libro> libro = libroRepository.findById(idLibro);
        List<AutorLibro> autorLibroExistenteLista = autorLibroRepository.findByLibroAndAutor(libro.get(), autor.get());
        if(autorLibroExistenteLista.size()==1){
            return autorLibroExistenteLista.get(0);
        }else{
            throw new NotFoundException("autor y libro solicitado");
        }
    }

 /*   public AutorLibro editarAutorLibro(Integer idLibro, Integer idAutor, AutorLibroRequest request) {

        autorRepository.findById(idAutor)
            .orElseThrow(() -> new NotFoundException("autor"));

        libroRepository.findById(idLibro)
            .orElseThrow(() -> new NotFoundException("libro"));

        Optional<Autor> autor = autorRepository.findById(idAutor);
        Optional<Libro> libro = libroRepository.findById(idLibro);

        List<AutorLibro> autorLibroExistenteLista = autorLibroRepository.findByLibroAndAutor(libro.get(), autor.get());

        if(autorLibroExistenteLista.size()==1){//check
            return autorLibroRepository.save(autorLibroExistenteLista.get(0));
        }
        throw new NotFoundException("autor y libro solicitada");
    }
*/
    public void borrarAutorLibro(Integer idLibro, Integer idAutor) {

        libroRepository.findById(idLibro)
            .orElseThrow(() -> new NotFoundException("libro"));

        autorRepository.findById(idAutor)
            .orElseThrow(() -> new NotFoundException("autor"));

        Optional<Autor> autor = autorRepository.findById(idAutor);
        Optional<Libro> libro = libroRepository.findById(idLibro);
        List<AutorLibro> autorLibroExistenteLista = autorLibroRepository.findByLibroAndAutor(libro.get(), autor.get());
        if(autorLibroExistenteLista.size()==1){
            autorLibroRepository.deleteById(autorLibroExistenteLista.get(0).getId());
        }else{
            throw new NotFoundException("autor y libro solicitada");
        }
    }
    
}