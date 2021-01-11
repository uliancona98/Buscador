package mx.uady.appbusqueda.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

import mx.uady.appbusqueda.exception.NotFoundException;
import mx.uady.appbusqueda.model.Usuario;
import mx.uady.appbusqueda.model.request.UsuarioRequest;
import mx.uady.appbusqueda.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;


    public List<Usuario> getUsuarios() {
        List<Usuario> usuarios = new LinkedList<>();
        usuarioRepository.findAll().iterator().forEachRemaining(usuarios::add);
        return usuarios;
    }


    public Usuario getUsuario(Integer id) {

        Optional<Usuario> opt = usuarioRepository.findById(id);

        if (opt.isPresent()) {
            return opt.get();
        }
        throw new NotFoundException();
    }

    @Transactional
    public Usuario crearUsuario(UsuarioRequest request) {
        Usuario usuario = new Usuario();

        String secret = UUID.randomUUID().toString();
        usuario.setSecret(secret);
        usuario.setPassword(request.getPassword());
        usuario.setUsuario(request.getUsuario());

        return usuarioRepository.save(usuario);
    }

    public Usuario getUsuario(String usuario) {
        return usuarioRepository.findByUsuario(usuario);
    }

    public Usuario editarUsuario(Integer id, UsuarioRequest request) {
        return usuarioRepository.findById(id)
        .map(usuario -> {
            usuario.setUsuario(request.getUsuario());
            return usuarioRepository.save(usuario);
        })
        .orElseThrow(() -> new NotFoundException("No existe ese usuario"));
    }
}
