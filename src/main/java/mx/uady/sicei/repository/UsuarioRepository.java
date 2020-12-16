package mx.uady.sicei.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import mx.uady.sicei.model.Alumno;
import mx.uady.sicei.model.Usuario;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, Integer> {
    // SELECT * WHERE Usuario.token = abc
    Usuario findByUsuario(String usuario);
}