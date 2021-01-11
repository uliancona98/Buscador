package mx.uady.appbusqueda.repository;

import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import mx.uady.appbusqueda.model.Autor;
import mx.uady.appbusqueda.model.Libro;
import mx.uady.appbusqueda.model.AutorLibro;

@Repository
public interface AutorLibroRepository extends JpaRepository<AutorLibro, Integer> {
    public List<AutorLibro> findByAutor(Autor autor);
    public List<AutorLibro> findByLibro(Libro libro);
    public List<AutorLibro> findByLibroAndAutor(Libro Libro, Autor autor);

}