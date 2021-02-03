package mx.uady.appbusqueda.repository;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import mx.uady.appbusqueda.model.Usuario;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, Integer> {
    // SELECT * WHERE Usuario.token = abc
    public Usuario findByUsuario(String usuario);

}