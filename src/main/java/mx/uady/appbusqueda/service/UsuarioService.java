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
        Usuario us = usuarioRepository.getOne(id);
        if (us != null) {
            return us;
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

        return usuarioRepository.saveAndFlush(usuario);
    }

    public Usuario getUsuario(String usuario) {
        Usuario user = usuarioRepository.findByUsuario(usuario);
        return user;
    }

    public Usuario editarUsuario(Integer id, UsuarioRequest request) {
        Usuario usuario =  usuarioRepository.getOne(id);
        if(usuario!=null){
            usuario.setUsuario(request.getUsuario());
            return usuarioRepository.saveAndFlush(usuario);
        }
        throw new NotFoundException("usuario");
    }
}
