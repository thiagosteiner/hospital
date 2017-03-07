package com.steiner.hospital.repository;

import com.steiner.hospital.domain.Exame;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Exame entity.
 */
@SuppressWarnings("unused")
public interface ExameRepository extends JpaRepository<Exame,Long> {

}
