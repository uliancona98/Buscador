package mx.uady.appbusqueda.repository;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import mx.uady.appbusqueda.model.AutorLibro;

@Repository
public interface AutorLibroRepository extends JpaRepository<AutorLibro, Integer> {
    List<AutorLibro> findByIdAutor(Integer idAutor);
    List<AutorLibro> findByIdLibro(Integer idLibro);
    List<AutorLibro> findByIdLibroAndIdAutor(Integer idLibro, Integer idAutor);

}