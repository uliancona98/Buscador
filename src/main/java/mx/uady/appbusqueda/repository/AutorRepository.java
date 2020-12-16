package mx.uady.appbusqueda.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import mx.uady.appbusqueda.model.Autor;

@Repository
public interface AutorRepository extends CrudRepository<Autor, Integer> {
    public Autor findByNombre(String nombre);

}