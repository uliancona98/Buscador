package mx.uady.sicei.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.uady.sicei.exception.NotFoundException;
import javax.transaction.Transactional;
import java.util.UUID;
import mx.uady.sicei.model.Alumno;
import mx.uady.sicei.model.Usuario;
import mx.uady.sicei.config.JwtTokenUtil;
import mx.uady.sicei.model.request.AlumnoRequest;
import mx.uady.sicei.model.Tutoria;
import mx.uady.sicei.model.Equipo;

import mx.uady.sicei.repository.AlumnoRepository;
import mx.uady.sicei.repository.TutoriaRepository;
import mx.uady.sicei.repository.UsuarioRepository;
import mx.uady.sicei.repository.EquipoRepository;

@Service
public class AlumnoSerivce {

    @Autowired
    private AlumnoRepository alumnoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private TutoriaRepository tutoriaRepository;
    @Autowired
    private EquipoRepository equipoRepository;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    public List<Alumno> obtenerAlumnos() {

        List<Alumno> alumnos = new LinkedList<>();
        alumnoRepository.findAll().iterator().forEachRemaining(alumnos::add); // SELECT(id, nombre)
        
        return alumnos;
    }

    public Alumno crearAlumno(AlumnoRequest request) {

        Usuario usuarioExistente = usuarioRepository.findByUsuario(request.getUsuario());
        Alumno alumno = new Alumno();

        if (usuarioExistente == null) {
            alumno.setNombre(request.getNombre());
            alumno.setLicenciatura(request.getLicenciatura());
            alumno.setUsuario(crearUsuario(request)); // Relacionar 2 entidades
            Equipo equipo = equipoRepository.findById(request.getEquipoId()).get();
            equipoRepository.save(equipo);
            alumno.setEquipo(equipo);
            alumno = alumnoRepository.save(alumno); // INSERT
            return alumno;
        }
        return null;
    }

    @Transactional
    private Usuario crearUsuario(AlumnoRequest request) {
        Usuario usuario = new Usuario();

        String secret = UUID.randomUUID().toString();
        usuario.setSecret(secret);
        usuario.setPassword(request.getPassword());
        usuario.setUsuario(request.getUsuario());

        return usuarioRepository.save(usuario);
    }

    public Alumno getAlumno(Integer id) {

        return alumnoRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("No existe este alumno"));
    }

    public Alumno editarAlumno(Integer id, AlumnoRequest request) {
        return alumnoRepository.findById(id)
        .map(alumno -> {
            alumno.setNombre(request.getNombre());
            alumno.setLicenciatura(request.getLicenciatura());

            Equipo equipo = equipoRepository.findById(request.getEquipoId()).get();
            equipoRepository.save(equipo);
            alumno.setEquipo(equipo);

            return alumnoRepository.save(alumno);
        })
        .orElseThrow(() -> new NotFoundException("No existe este alumno :v"));
    }

    public String borrarAlumno(Integer id) {
        Optional<Alumno> alumno = alumnoRepository.findById(id);
        if (!alumno.isPresent()) {
            throw new NotFoundException("Alumno");
        }
        List<Tutoria> tutorias = tutoriaRepository.findByAlumnoId(id);

        if(tutorias.isEmpty()){
            alumnoRepository.deleteById(id);
            usuarioRepository.deleteById(id);
            return "Alumno Borrado";
        } else {
            return "Alumno "+id+" No se pudo borrar ya que tiene tutorias asignadas";
        }


    }
    
}
