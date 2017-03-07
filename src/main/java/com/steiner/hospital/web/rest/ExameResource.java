package com.steiner.hospital.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.steiner.hospital.domain.Exame;

import com.steiner.hospital.repository.ExameRepository;
import com.steiner.hospital.web.rest.util.HeaderUtil;
import com.steiner.hospital.service.dto.ExameDTO;
import com.steiner.hospital.service.mapper.ExameMapper;
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
 * REST controller for managing Exame.
 */
@RestController
@RequestMapping("/api")
public class ExameResource {

    private final Logger log = LoggerFactory.getLogger(ExameResource.class);

    private static final String ENTITY_NAME = "exame";
        
    private final ExameRepository exameRepository;

    private final ExameMapper exameMapper;

    public ExameResource(ExameRepository exameRepository, ExameMapper exameMapper) {
        this.exameRepository = exameRepository;
        this.exameMapper = exameMapper;
    }

    /**
     * POST  /exames : Create a new exame.
     *
     * @param exameDTO the exameDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new exameDTO, or with status 400 (Bad Request) if the exame has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/exames")
    @Timed
    public ResponseEntity<ExameDTO> createExame(@Valid @RequestBody ExameDTO exameDTO) throws URISyntaxException {
        log.debug("REST request to save Exame : {}", exameDTO);
        if (exameDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new exame cannot already have an ID")).body(null);
        }
        Exame exame = exameMapper.exameDTOToExame(exameDTO);
        exame = exameRepository.save(exame);
        ExameDTO result = exameMapper.exameToExameDTO(exame);
        return ResponseEntity.created(new URI("/api/exames/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /exames : Updates an existing exame.
     *
     * @param exameDTO the exameDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated exameDTO,
     * or with status 400 (Bad Request) if the exameDTO is not valid,
     * or with status 500 (Internal Server Error) if the exameDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/exames")
    @Timed
    public ResponseEntity<ExameDTO> updateExame(@Valid @RequestBody ExameDTO exameDTO) throws URISyntaxException {
        log.debug("REST request to update Exame : {}", exameDTO);
        if (exameDTO.getId() == null) {
            return createExame(exameDTO);
        }
        Exame exame = exameMapper.exameDTOToExame(exameDTO);
        exame = exameRepository.save(exame);
        ExameDTO result = exameMapper.exameToExameDTO(exame);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, exameDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /exames : get all the exames.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of exames in body
     */
    @GetMapping("/exames")
    @Timed
    public List<ExameDTO> getAllExames() {
        log.debug("REST request to get all Exames");
        List<Exame> exames = exameRepository.findAll();
        return exameMapper.examesToExameDTOs(exames);
    }

    /**
     * GET  /exames/:id : get the "id" exame.
     *
     * @param id the id of the exameDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the exameDTO, or with status 404 (Not Found)
     */
    @GetMapping("/exames/{id}")
    @Timed
    public ResponseEntity<ExameDTO> getExame(@PathVariable Long id) {
        log.debug("REST request to get Exame : {}", id);
        Exame exame = exameRepository.findOne(id);
        ExameDTO exameDTO = exameMapper.exameToExameDTO(exame);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(exameDTO));
    }

    /**
     * DELETE  /exames/:id : delete the "id" exame.
     *
     * @param id the id of the exameDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/exames/{id}")
    @Timed
    public ResponseEntity<Void> deleteExame(@PathVariable Long id) {
        log.debug("REST request to delete Exame : {}", id);
        exameRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
