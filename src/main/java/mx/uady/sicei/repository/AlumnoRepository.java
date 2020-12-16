package mx.uady.sicei.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import mx.uady.sicei.model.Alumno;
import mx.uady.sicei.model.Equipo;

@Repository
public interface AlumnoRepository extends CrudRepository<Alumno, Integer> {
    public List<Alumno> findByEquipoId(Equipo equipoId);

}