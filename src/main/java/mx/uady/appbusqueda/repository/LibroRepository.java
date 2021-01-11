package mx.uady.appbusqueda.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import mx.uady.appbusqueda.model.Libro;
import mx.uady.appbusqueda.model.Usuario;


@Repository
public interface LibroRepository extends CrudRepository<Libro, Integer> {
    public List<Libro> findByIdUsuario(Usuario idUsuario);
    public Libro findByTitulo(String titulo);

}