package mx.uady.appbusqueda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.uady.appbusqueda.model.Autor;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Integer> {
    public Autor findByNombre(String nombre);

}