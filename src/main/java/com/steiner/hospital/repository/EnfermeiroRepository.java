package com.steiner.hospital.repository;

import com.steiner.hospital.domain.Enfermeiro;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Enfermeiro entity.
 */
@SuppressWarnings("unused")
public interface EnfermeiroRepository extends JpaRepository<Enfermeiro,Long> {

}
