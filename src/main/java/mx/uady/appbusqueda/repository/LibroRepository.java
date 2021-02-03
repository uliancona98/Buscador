package mx.uady.appbusqueda.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import mx.uady.appbusqueda.model.Libro;
import mx.uady.appbusqueda.model.Usuario;



@Repository
public interface LibroRepository extends JpaRepository<Libro, Integer> {
    public List<Libro> findByIdUsuario(Usuario idUsuario);
    public Libro findByTitulo(String titulo);
    Page<Libro> findByTitulo(String titulo, Pageable pageable);

}