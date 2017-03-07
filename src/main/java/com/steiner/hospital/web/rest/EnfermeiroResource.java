package com.steiner.hospital.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.steiner.hospital.domain.Enfermeiro;

import com.steiner.hospital.repository.EnfermeiroRepository;
import com.steiner.hospital.web.rest.util.HeaderUtil;
import com.steiner.hospital.service.dto.EnfermeiroDTO;
import com.steiner.hospital.service.mapper.EnfermeiroMapper;
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
 * REST controller for managing Enfermeiro.
 */
@RestController
@RequestMapping("/api")
public class EnfermeiroResource {

    private final Logger log = LoggerFactory.getLogger(EnfermeiroResource.class);

    private static final String ENTITY_NAME = "enfermeiro";
        
    private final EnfermeiroRepository enfermeiroRepository;

    private final EnfermeiroMapper enfermeiroMapper;

    public EnfermeiroResource(EnfermeiroRepository enfermeiroRepository, EnfermeiroMapper enfermeiroMapper) {
        this.enfermeiroRepository = enfermeiroRepository;
        this.enfermeiroMapper = enfermeiroMapper;
    }

    /**
     * POST  /enfermeiros : Create a new enfermeiro.
     *
     * @param enfermeiroDTO the enfermeiroDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new enfermeiroDTO, or with status 400 (Bad Request) if the enfermeiro has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/enfermeiros")
    @Timed
    public ResponseEntity<EnfermeiroDTO> createEnfermeiro(@Valid @RequestBody EnfermeiroDTO enfermeiroDTO) throws URISyntaxException {
        log.debug("REST request to save Enfermeiro : {}", enfermeiroDTO);
        if (enfermeiroDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new enfermeiro cannot already have an ID")).body(null);
        }
        Enfermeiro enfermeiro = enfermeiroMapper.enfermeiroDTOToEnfermeiro(enfermeiroDTO);
        enfermeiro = enfermeiroRepository.save(enfermeiro);
        EnfermeiroDTO result = enfermeiroMapper.enfermeiroToEnfermeiroDTO(enfermeiro);
        return ResponseEntity.created(new URI("/api/enfermeiros/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /enfermeiros : Updates an existing enfermeiro.
     *
     * @param enfermeiroDTO the enfermeiroDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated enfermeiroDTO,
     * or with status 400 (Bad Request) if the enfermeiroDTO is not valid,
     * or with status 500 (Internal Server Error) if the enfermeiroDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/enfermeiros")
    @Timed
    public ResponseEntity<EnfermeiroDTO> updateEnfermeiro(@Valid @RequestBody EnfermeiroDTO enfermeiroDTO) throws URISyntaxException {
        log.debug("REST request to update Enfermeiro : {}", enfermeiroDTO);
        if (enfermeiroDTO.getId() == null) {
            return createEnfermeiro(enfermeiroDTO);
        }
        Enfermeiro enfermeiro = enfermeiroMapper.enfermeiroDTOToEnfermeiro(enfermeiroDTO);
        enfermeiro = enfermeiroRepository.save(enfermeiro);
        EnfermeiroDTO result = enfermeiroMapper.enfermeiroToEnfermeiroDTO(enfermeiro);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, enfermeiroDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /enfermeiros : get all the enfermeiros.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of enfermeiros in body
     */
    @GetMapping("/enfermeiros")
    @Timed
    public List<EnfermeiroDTO> getAllEnfermeiros() {
        log.debug("REST request to get all Enfermeiros");
        List<Enfermeiro> enfermeiros = enfermeiroRepository.findAll();
        return enfermeiroMapper.enfermeirosToEnfermeiroDTOs(enfermeiros);
    }

    /**
     * GET  /enfermeiros/:id : get the "id" enfermeiro.
     *
     * @param id the id of the enfermeiroDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the enfermeiroDTO, or with status 404 (Not Found)
     */
    @GetMapping("/enfermeiros/{id}")
    @Timed
    public ResponseEntity<EnfermeiroDTO> getEnfermeiro(@PathVariable Long id) {
        log.debug("REST request to get Enfermeiro : {}", id);
        Enfermeiro enfermeiro = enfermeiroRepository.findOne(id);
        EnfermeiroDTO enfermeiroDTO = enfermeiroMapper.enfermeiroToEnfermeiroDTO(enfermeiro);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(enfermeiroDTO));
    }

    /**
     * DELETE  /enfermeiros/:id : delete the "id" enfermeiro.
     *
     * @param id the id of the enfermeiroDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/enfermeiros/{id}")
    @Timed
    public ResponseEntity<Void> deleteEnfermeiro(@PathVariable Long id) {
        log.debug("REST request to delete Enfermeiro : {}", id);
        enfermeiroRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
