package com.steiner.hospital.repository;

import com.steiner.hospital.domain.Paciente;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Paciente entity.
 */
@SuppressWarnings("unused")
public interface PacienteRepository extends JpaRepository<Paciente,Long> {

    @Query("select distinct paciente from Paciente paciente left join fetch paciente.medicos left join fetch paciente.enfermeiros")
    List<Paciente> findAllWithEagerRelationships();

    @Query("select paciente from Paciente paciente left join fetch paciente.medicos left join fetch paciente.enfermeiros where paciente.id =:id")
    Paciente findOneWithEagerRelationships(@Param("id") Long id);

}
