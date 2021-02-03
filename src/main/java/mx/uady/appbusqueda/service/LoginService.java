package mx.uady.appbusqueda.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.uady.appbusqueda.model.Usuario;
import mx.uady.appbusqueda.model.TokenBlacklist;
import mx.uady.appbusqueda.model.request.LoginRequest;
import mx.uady.appbusqueda.repository.UsuarioRepository;
import mx.uady.appbusqueda.repository.TokenRepository;
import mx.uady.appbusqueda.config.JwtTokenUtil;


@Service
public class LoginService {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private TokenRepository tokenRepository;
    
    public String loginUsuario(LoginRequest request) {
        Usuario usuario = usuarioRepository.findByUsuario(request.getUsuario());
        
        if (usuario == null) {
            return null;
        }

        if (!usuario.getPassword().equals(request.getPassword())) {
            return null;
        }
        usuarioRepository.saveAndFlush(usuario);
        String jwt = jwtTokenUtil.generateToken(usuario);
        return jwt;
    }
    
     public void logout(String auth) {
        TokenBlacklist token = new TokenBlacklist(auth);
        tokenRepository.saveAndFlush(token);
    }
}
