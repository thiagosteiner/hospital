package com.steiner.hospital.repository;

import com.steiner.hospital.domain.Atendimento;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Atendimento entity.
 */
@SuppressWarnings("unused")
public interface AtendimentoRepository extends JpaRepository<Atendimento,Long> {

}
