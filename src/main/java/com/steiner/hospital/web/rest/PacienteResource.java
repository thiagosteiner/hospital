package com.steiner.hospital.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.steiner.hospital.domain.Paciente;

import com.steiner.hospital.repository.PacienteRepository;
import com.steiner.hospital.web.rest.util.HeaderUtil;
import com.steiner.hospital.service.dto.PacienteDTO;
import com.steiner.hospital.service.mapper.PacienteMapper;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing Paciente.
 */
@RestController
@RequestMapping("/api")
public class PacienteResource {

    private final Logger log = LoggerFactory.getLogger(PacienteResource.class);

    private static final String ENTITY_NAME = "paciente";
        
    private final PacienteRepository pacienteRepository;

    private final PacienteMapper pacienteMapper;

    public PacienteResource(PacienteRepository pacienteRepository, PacienteMapper pacienteMapper) {
        this.pacienteRepository = pacienteRepository;
        this.pacienteMapper = pacienteMapper;
    }

    /**
     * POST  /pacientes : Create a new paciente.
     *
     * @param pacienteDTO the pacienteDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pacienteDTO, or with status 400 (Bad Request) if the paciente has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/pacientes")
    @Timed
    public ResponseEntity<PacienteDTO> createPaciente(@Valid @RequestBody PacienteDTO pacienteDTO) throws URISyntaxException {
        log.debug("REST request to save Paciente : {}", pacienteDTO);
        if (pacienteDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new paciente cannot already have an ID")).body(null);
        }
        Paciente paciente = pacienteMapper.pacienteDTOToPaciente(pacienteDTO);
        paciente = pacienteRepository.save(paciente);
        PacienteDTO result = pacienteMapper.pacienteToPacienteDTO(paciente);
        return ResponseEntity.created(new URI("/api/pacientes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pacientes : Updates an existing paciente.
     *
     * @param pacienteDTO the pacienteDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pacienteDTO,
     * or with status 400 (Bad Request) if the pacienteDTO is not valid,
     * or with status 500 (Internal Server Error) if the pacienteDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/pacientes")
    @Timed
    public ResponseEntity<PacienteDTO> updatePaciente(@Valid @RequestBody PacienteDTO pacienteDTO) throws URISyntaxException {
        log.debug("REST request to update Paciente : {}", pacienteDTO);
        if (pacienteDTO.getId() == null) {
            return createPaciente(pacienteDTO);
        }
        Paciente paciente = pacienteMapper.pacienteDTOToPaciente(pacienteDTO);
        paciente = pacienteRepository.save(paciente);
        PacienteDTO result = pacienteMapper.pacienteToPacienteDTO(paciente);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, pacienteDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pacientes : get all the pacientes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of pacientes in body
     */
    @GetMapping("/pacientes")
    @Timed
    public List<PacienteDTO> getAllPacientes() {
        log.debug("REST request to get all Pacientes");
        List<Paciente> pacientes = pacienteRepository.findAllWithEagerRelationships();
        return pacienteMapper.pacientesToPacienteDTOs(pacientes);
    }

    /**
     * GET  /pacientes/:id : get the "id" paciente.
     *
     * @param id the id of the pacienteDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pacienteDTO, or with status 404 (Not Found)
     */
    @GetMapping("/pacientes/{id}")
    @Timed
    public ResponseEntity<PacienteDTO> getPaciente(@PathVariable Long id) {
        log.debug("REST request to get Paciente : {}", id);
        Paciente paciente = pacienteRepository.findOneWithEagerRelationships(id);
        PacienteDTO pacienteDTO = pacienteMapper.pacienteToPacienteDTO(paciente);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(pacienteDTO));
    }

    /**
     * DELETE  /pacientes/:id : delete the "id" paciente.
     *
     * @param id the id of the pacienteDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/pacientes/{id}")
    @Timed
    public ResponseEntity<Void> deletePaciente(@PathVariable Long id) {
        log.debug("REST request to delete Paciente : {}", id);
        pacienteRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
