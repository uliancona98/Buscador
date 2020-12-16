package mx.uady.appbusqueda.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import mx.uady.appbusqueda.exception.NotFoundException;
import mx.uady.appbusqueda.model.Alumno;
import mx.uady.appbusqueda.model.Equipo;
import mx.uady.appbusqueda.repository.EquipoRepository;
import mx.uady.appbusqueda.repository.AlumnoRepository;
import mx.uady.appbusqueda.model.request.EquipoRequest;

@Service
public class EquipoService {

    @Autowired
    private EquipoRepository equipoRepository;
    @Autowired
    private AlumnoRepository alumnoRepository;

    public List<Equipo> getEquipos() {

        List<Equipo> equipos = new LinkedList<>();
        equipoRepository.findAll().iterator().forEachRemaining(equipos::add);

        return equipos;
    }


    public Equipo crearEquipo(EquipoRequest request) {
        Equipo equipo = new Equipo();

        equipo.setModelo(request.getModelo());
        //equipo.setAlumnos(null);
        equipo = equipoRepository.save(equipo); // INSERT

        return equipo;
    }

    public Equipo getEquipo(Integer id) {

        return equipoRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("No existe ese equipo :v"));
    }

    public Equipo editarEquipos(Integer id, EquipoRequest request) {

        return equipoRepository.findById(id)
        .map(equipo -> {
            equipo.setModelo(request.getModelo());
            return equipoRepository.save(equipo);
        })
        .orElseThrow(() -> new NotFoundException("No existe ese equipo :v"));
    }

    public boolean borrarEquipo(Integer id) {

        Optional<Equipo> equipo = equipoRepository.findById(id);
        List<Alumno> alumnosConEquipo = alumnoRepository.findByEquipoId(equipo.get());
        if(alumnosConEquipo.size()>0){
            return false;
        }

        if (!equipo.isPresent()) {
            throw new NotFoundException("Equipo");
        }
        equipoRepository.deleteById(id);
        return true;
    }
    
}