package com.steiner.hospital.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.steiner.hospital.domain.Medico;

import com.steiner.hospital.repository.MedicoRepository;
import com.steiner.hospital.web.rest.util.HeaderUtil;
import com.steiner.hospital.service.dto.MedicoDTO;
import com.steiner.hospital.service.mapper.MedicoMapper;
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
 * REST controller for managing Medico.
 */
@RestController
@RequestMapping("/api")
public class MedicoResource {

    private final Logger log = LoggerFactory.getLogger(MedicoResource.class);

    private static final String ENTITY_NAME = "medico";
        
    private final MedicoRepository medicoRepository;

    private final MedicoMapper medicoMapper;

    public MedicoResource(MedicoRepository medicoRepository, MedicoMapper medicoMapper) {
        this.medicoRepository = medicoRepository;
        this.medicoMapper = medicoMapper;
    }

    /**
     * POST  /medicos : Create a new medico.
     *
     * @param medicoDTO the medicoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new medicoDTO, or with status 400 (Bad Request) if the medico has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/medicos")
    @Timed
    public ResponseEntity<MedicoDTO> createMedico(@Valid @RequestBody MedicoDTO medicoDTO) throws URISyntaxException {
        log.debug("REST request to save Medico : {}", medicoDTO);
        if (medicoDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new medico cannot already have an ID")).body(null);
        }
        Medico medico = medicoMapper.medicoDTOToMedico(medicoDTO);
        medico = medicoRepository.save(medico);
        MedicoDTO result = medicoMapper.medicoToMedicoDTO(medico);
        return ResponseEntity.created(new URI("/api/medicos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /medicos : Updates an existing medico.
     *
     * @param medicoDTO the medicoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated medicoDTO,
     * or with status 400 (Bad Request) if the medicoDTO is not valid,
     * or with status 500 (Internal Server Error) if the medicoDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/medicos")
    @Timed
    public ResponseEntity<MedicoDTO> updateMedico(@Valid @RequestBody MedicoDTO medicoDTO) throws URISyntaxException {
        log.debug("REST request to update Medico : {}", medicoDTO);
        if (medicoDTO.getId() == null) {
            return createMedico(medicoDTO);
        }
        Medico medico = medicoMapper.medicoDTOToMedico(medicoDTO);
        medico = medicoRepository.save(medico);
        MedicoDTO result = medicoMapper.medicoToMedicoDTO(medico);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, medicoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /medicos : get all the medicos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of medicos in body
     */
    @GetMapping("/medicos")
    @Timed
    public List<MedicoDTO> getAllMedicos() {
        log.debug("REST request to get all Medicos");
        List<Medico> medicos = medicoRepository.findAll();
        return medicoMapper.medicosToMedicoDTOs(medicos);
    }

    /**
     * GET  /medicos/:id : get the "id" medico.
     *
     * @param id the id of the medicoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the medicoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/medicos/{id}")
    @Timed
    public ResponseEntity<MedicoDTO> getMedico(@PathVariable Long id) {
        log.debug("REST request to get Medico : {}", id);
        Medico medico = medicoRepository.findOne(id);
        MedicoDTO medicoDTO = medicoMapper.medicoToMedicoDTO(medico);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(medicoDTO));
    }

    /**
     * DELETE  /medicos/:id : delete the "id" medico.
     *
     * @param id the id of the medicoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/medicos/{id}")
    @Timed
    public ResponseEntity<Void> deleteMedico(@PathVariable Long id) {
        log.debug("REST request to delete Medico : {}", id);
        medicoRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
