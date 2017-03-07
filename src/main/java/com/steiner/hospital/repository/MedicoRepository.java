package com.steiner.hospital.repository;

import com.steiner.hospital.domain.Medico;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Medico entity.
 */
@SuppressWarnings("unused")
public interface MedicoRepository extends JpaRepository<Medico,Long> {

}
