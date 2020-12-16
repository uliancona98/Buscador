package mx.uady.appbusqueda.service;
import java.util.Optional;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import mx.uady.appbusqueda.exception.NotFoundException;

import mx.uady.appbusqueda.model.Tutoria;
import mx.uady.appbusqueda.model.Alumno;
import mx.uady.appbusqueda.model.Profesor;
import mx.uady.appbusqueda.model.Tutoria.TutoriaId;
import mx.uady.appbusqueda.repository.TutoriaRepository;
import mx.uady.appbusqueda.repository.AlumnoRepository;
import mx.uady.appbusqueda.repository.ProfesorRepository;
import mx.uady.appbusqueda.model.request.TutoriaRequest;

@Service
public class TutoriaService {

    @Autowired
    private TutoriaRepository tutoriaRepository;
    @Autowired
    private AlumnoRepository alumnoRepository;
    @Autowired
    private ProfesorRepository profesorRepository;

    public List<Tutoria> obtenerTutorias() {

        List<Tutoria> tutorias = new LinkedList<>();
        tutoriaRepository.findAll().iterator().forEachRemaining(tutorias::add);

        return tutorias;
    }


    public Tutoria crearTutoria(TutoriaRequest request) {
        TutoriaId tutoriaId = new TutoriaId();
        Optional <Alumno> alumno= alumnoRepository.findById(request.getAlumnoId());
        Optional <Profesor> profesor= profesorRepository.findById(request.getProfesorId());

        Optional<Tutoria> tutoriaExistente = tutoriaRepository.findById(tutoriaId);
        if (!alumno.isPresent()) {
            throw new NotFoundException("El alumno no está registrado");
        }else if(!profesor.isPresent()){
            throw new NotFoundException("El profesor no está registrado");
        }else if (!tutoriaExistente.isPresent()) {
            Tutoria tutoria = new Tutoria();
            tutoriaId.setAlumnoId(request.getAlumnoId());
            tutoriaId.setProfesorId(request.getProfesorId());
            tutoria.setHoras(request.getHoras());
            tutoria.setId(tutoriaId);
            tutoria.setAlumno(alumno.get());
            tutoria.setProfesor(profesor.get());
            tutoria = tutoriaRepository.save(tutoria); // INSERT
            return tutoria;
        }
        throw new NotFoundException("La tutoría ya está registrada");
    }

    public Tutoria getTutoria(Integer alumnoId, Integer profesorId) {
        TutoriaId tutoriaId = new TutoriaId();

        alumnoRepository.findById(alumnoId)
            .orElseThrow(() -> new NotFoundException("El alumno no está registrado"));

        profesorRepository.findById(profesorId)
            .orElseThrow(() -> new NotFoundException("El profesor no está registrado"));

        tutoriaId.setAlumnoId(alumnoId);
        tutoriaId.setProfesorId(profesorId);

        return tutoriaRepository.findById(tutoriaId)
            .orElseThrow(() -> new NotFoundException("La tutoría no se encuentra"));
    }

    public Tutoria editarTutorias(Integer alumnoId, Integer profesorId, TutoriaRequest request) {
        TutoriaId tutoriaId = new TutoriaId();

        alumnoRepository.findById(alumnoId)
            .orElseThrow(() -> new NotFoundException("El alumno no está registrado"));

        profesorRepository.findById(profesorId)
            .orElseThrow(() -> new NotFoundException("El profesor no está registrado"));

        tutoriaId.setAlumnoId(alumnoId);
        tutoriaId.setProfesorId(profesorId);

        return tutoriaRepository.findById(tutoriaId)
        .map(tutoria -> {
            tutoria.setHoras(request.getHoras());
            return tutoriaRepository.save(tutoria);
        })
        .orElseThrow(() -> new NotFoundException("La tutoria no se encuentra"));
    }

    public void borrarTutoria(Integer alumnoId, Integer profesorId) {

        TutoriaId tutoriaId = new TutoriaId();

        alumnoRepository.findById(alumnoId)
            .orElseThrow(() -> new NotFoundException("El alumno no está registrado"));

        profesorRepository.findById(profesorId)
            .orElseThrow(() -> new NotFoundException("El profesor no está registrado"));

        tutoriaId.setAlumnoId(alumnoId);
        tutoriaId.setProfesorId(profesorId);
        
        
        tutoriaRepository.deleteById(tutoriaId);
    }
    
}