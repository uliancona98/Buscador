package mx.uady.appbusqueda.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import mx.uady.appbusqueda.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    // SELECT * WHERE Usuario.token = abc
    public Usuario findByUsuario(String usuario);

}