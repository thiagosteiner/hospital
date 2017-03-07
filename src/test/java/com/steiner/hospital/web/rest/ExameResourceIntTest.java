package com.steiner.hospital.web.rest;

import com.steiner.hospital.HospitalApp;

import com.steiner.hospital.domain.Exame;
import com.steiner.hospital.repository.ExameRepository;
import com.steiner.hospital.service.dto.ExameDTO;
import com.steiner.hospital.service.mapper.ExameMapper;
import com.steiner.hospital.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.steiner.hospital.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ExameResource REST controller.
 *
 * @see ExameResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HospitalApp.class)
public class ExameResourceIntTest {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_LAUDO = "AAAAAAAAAA";
    private static final String UPDATED_LAUDO = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATA_SOLICITACAO = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATA_SOLICITACAO = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_DATA_LAUDO = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATA_LAUDO = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private ExameRepository exameRepository;

    @Autowired
    private ExameMapper exameMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restExameMockMvc;

    private Exame exame;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            ExameResource exameResource = new ExameResource(exameRepository, exameMapper);
        this.restExameMockMvc = MockMvcBuilders.standaloneSetup(exameResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Exame createEntity(EntityManager em) {
        Exame exame = new Exame()
                .nome(DEFAULT_NOME)
                .laudo(DEFAULT_LAUDO)
                .dataSolicitacao(DEFAULT_DATA_SOLICITACAO)
                .dataLaudo(DEFAULT_DATA_LAUDO);
        return exame;
    }

    @Before
    public void initTest() {
        exame = createEntity(em);
    }

    @Test
    @Transactional
    public void createExame() throws Exception {
        int databaseSizeBeforeCreate = exameRepository.findAll().size();

        // Create the Exame
        ExameDTO exameDTO = exameMapper.exameToExameDTO(exame);

        restExameMockMvc.perform(post("/api/exames")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(exameDTO)))
            .andExpect(status().isCreated());

        // Validate the Exame in the database
        List<Exame> exameList = exameRepository.findAll();
        assertThat(exameList).hasSize(databaseSizeBeforeCreate + 1);
        Exame testExame = exameList.get(exameList.size() - 1);
        assertThat(testExame.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testExame.getLaudo()).isEqualTo(DEFAULT_LAUDO);
        assertThat(testExame.getDataSolicitacao()).isEqualTo(DEFAULT_DATA_SOLICITACAO);
        assertThat(testExame.getDataLaudo()).isEqualTo(DEFAULT_DATA_LAUDO);
    }

    @Test
    @Transactional
    public void createExameWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = exameRepository.findAll().size();

        // Create the Exame with an existing ID
        Exame existingExame = new Exame();
        existingExame.setId(1L);
        ExameDTO existingExameDTO = exameMapper.exameToExameDTO(existingExame);

        // An entity with an existing ID cannot be created, so this API call must fail
        restExameMockMvc.perform(post("/api/exames")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingExameDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Exame> exameList = exameRepository.findAll();
        assertThat(exameList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = exameRepository.findAll().size();
        // set the field null
        exame.setNome(null);

        // Create the Exame, which fails.
        ExameDTO exameDTO = exameMapper.exameToExameDTO(exame);

        restExameMockMvc.perform(post("/api/exames")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(exameDTO)))
            .andExpect(status().isBadRequest());

        List<Exame> exameList = exameRepository.findAll();
        assertThat(exameList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllExames() throws Exception {
        // Initialize the database
        exameRepository.saveAndFlush(exame);

        // Get all the exameList
        restExameMockMvc.perform(get("/api/exames?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(exame.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].laudo").value(hasItem(DEFAULT_LAUDO.toString())))
            .andExpect(jsonPath("$.[*].dataSolicitacao").value(hasItem(sameInstant(DEFAULT_DATA_SOLICITACAO))))
            .andExpect(jsonPath("$.[*].dataLaudo").value(hasItem(sameInstant(DEFAULT_DATA_LAUDO))));
    }

    @Test
    @Transactional
    public void getExame() throws Exception {
        // Initialize the database
        exameRepository.saveAndFlush(exame);

        // Get the exame
        restExameMockMvc.perform(get("/api/exames/{id}", exame.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(exame.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.laudo").value(DEFAULT_LAUDO.toString()))
            .andExpect(jsonPath("$.dataSolicitacao").value(sameInstant(DEFAULT_DATA_SOLICITACAO)))
            .andExpect(jsonPath("$.dataLaudo").value(sameInstant(DEFAULT_DATA_LAUDO)));
    }

    @Test
    @Transactional
    public void getNonExistingExame() throws Exception {
        // Get the exame
        restExameMockMvc.perform(get("/api/exames/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateExame() throws Exception {
        // Initialize the database
        exameRepository.saveAndFlush(exame);
        int databaseSizeBeforeUpdate = exameRepository.findAll().size();

        // Update the exame
        Exame updatedExame = exameRepository.findOne(exame.getId());
        updatedExame
                .nome(UPDATED_NOME)
                .laudo(UPDATED_LAUDO)
                .dataSolicitacao(UPDATED_DATA_SOLICITACAO)
                .dataLaudo(UPDATED_DATA_LAUDO);
        ExameDTO exameDTO = exameMapper.exameToExameDTO(updatedExame);

        restExameMockMvc.perform(put("/api/exames")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(exameDTO)))
            .andExpect(status().isOk());

        // Validate the Exame in the database
        List<Exame> exameList = exameRepository.findAll();
        assertThat(exameList).hasSize(databaseSizeBeforeUpdate);
        Exame testExame = exameList.get(exameList.size() - 1);
        assertThat(testExame.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testExame.getLaudo()).isEqualTo(UPDATED_LAUDO);
        assertThat(testExame.getDataSolicitacao()).isEqualTo(UPDATED_DATA_SOLICITACAO);
        assertThat(testExame.getDataLaudo()).isEqualTo(UPDATED_DATA_LAUDO);
    }

    @Test
    @Transactional
    public void updateNonExistingExame() throws Exception {
        int databaseSizeBeforeUpdate = exameRepository.findAll().size();

        // Create the Exame
        ExameDTO exameDTO = exameMapper.exameToExameDTO(exame);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restExameMockMvc.perform(put("/api/exames")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(exameDTO)))
            .andExpect(status().isCreated());

        // Validate the Exame in the database
        List<Exame> exameList = exameRepository.findAll();
        assertThat(exameList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteExame() throws Exception {
        // Initialize the database
        exameRepository.saveAndFlush(exame);
        int databaseSizeBeforeDelete = exameRepository.findAll().size();

        // Get the exame
        restExameMockMvc.perform(delete("/api/exames/{id}", exame.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Exame> exameList = exameRepository.findAll();
        assertThat(exameList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Exame.class);
    }
}
