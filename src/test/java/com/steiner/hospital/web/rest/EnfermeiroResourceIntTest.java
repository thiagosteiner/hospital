package com.steiner.hospital.web.rest;

import com.steiner.hospital.HospitalApp;

import com.steiner.hospital.domain.Enfermeiro;
import com.steiner.hospital.repository.EnfermeiroRepository;
import com.steiner.hospital.service.dto.EnfermeiroDTO;
import com.steiner.hospital.service.mapper.EnfermeiroMapper;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.steiner.hospital.domain.enumeration.TipoEnfermeiro;
/**
 * Test class for the EnfermeiroResource REST controller.
 *
 * @see EnfermeiroResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HospitalApp.class)
public class EnfermeiroResourceIntTest {

    private static final String DEFAULT_CRE = "AAAAAAAAAA";
    private static final String UPDATED_CRE = "BBBBBBBBBB";

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final TipoEnfermeiro DEFAULT_TIPO_ENFERMEIRO = TipoEnfermeiro.BACHAREL;
    private static final TipoEnfermeiro UPDATED_TIPO_ENFERMEIRO = TipoEnfermeiro.TECNICO;

    @Autowired
    private EnfermeiroRepository enfermeiroRepository;

    @Autowired
    private EnfermeiroMapper enfermeiroMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEnfermeiroMockMvc;

    private Enfermeiro enfermeiro;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            EnfermeiroResource enfermeiroResource = new EnfermeiroResource(enfermeiroRepository, enfermeiroMapper);
        this.restEnfermeiroMockMvc = MockMvcBuilders.standaloneSetup(enfermeiroResource)
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
    public static Enfermeiro createEntity(EntityManager em) {
        Enfermeiro enfermeiro = new Enfermeiro()
                .cre(DEFAULT_CRE)
                .nome(DEFAULT_NOME)
                .tipoEnfermeiro(DEFAULT_TIPO_ENFERMEIRO);
        return enfermeiro;
    }

    @Before
    public void initTest() {
        enfermeiro = createEntity(em);
    }

    @Test
    @Transactional
    public void createEnfermeiro() throws Exception {
        int databaseSizeBeforeCreate = enfermeiroRepository.findAll().size();

        // Create the Enfermeiro
        EnfermeiroDTO enfermeiroDTO = enfermeiroMapper.enfermeiroToEnfermeiroDTO(enfermeiro);

        restEnfermeiroMockMvc.perform(post("/api/enfermeiros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(enfermeiroDTO)))
            .andExpect(status().isCreated());

        // Validate the Enfermeiro in the database
        List<Enfermeiro> enfermeiroList = enfermeiroRepository.findAll();
        assertThat(enfermeiroList).hasSize(databaseSizeBeforeCreate + 1);
        Enfermeiro testEnfermeiro = enfermeiroList.get(enfermeiroList.size() - 1);
        assertThat(testEnfermeiro.getCre()).isEqualTo(DEFAULT_CRE);
        assertThat(testEnfermeiro.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testEnfermeiro.getTipoEnfermeiro()).isEqualTo(DEFAULT_TIPO_ENFERMEIRO);
    }

    @Test
    @Transactional
    public void createEnfermeiroWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = enfermeiroRepository.findAll().size();

        // Create the Enfermeiro with an existing ID
        Enfermeiro existingEnfermeiro = new Enfermeiro();
        existingEnfermeiro.setId(1L);
        EnfermeiroDTO existingEnfermeiroDTO = enfermeiroMapper.enfermeiroToEnfermeiroDTO(existingEnfermeiro);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEnfermeiroMockMvc.perform(post("/api/enfermeiros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingEnfermeiroDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Enfermeiro> enfermeiroList = enfermeiroRepository.findAll();
        assertThat(enfermeiroList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCreIsRequired() throws Exception {
        int databaseSizeBeforeTest = enfermeiroRepository.findAll().size();
        // set the field null
        enfermeiro.setCre(null);

        // Create the Enfermeiro, which fails.
        EnfermeiroDTO enfermeiroDTO = enfermeiroMapper.enfermeiroToEnfermeiroDTO(enfermeiro);

        restEnfermeiroMockMvc.perform(post("/api/enfermeiros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(enfermeiroDTO)))
            .andExpect(status().isBadRequest());

        List<Enfermeiro> enfermeiroList = enfermeiroRepository.findAll();
        assertThat(enfermeiroList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = enfermeiroRepository.findAll().size();
        // set the field null
        enfermeiro.setNome(null);

        // Create the Enfermeiro, which fails.
        EnfermeiroDTO enfermeiroDTO = enfermeiroMapper.enfermeiroToEnfermeiroDTO(enfermeiro);

        restEnfermeiroMockMvc.perform(post("/api/enfermeiros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(enfermeiroDTO)))
            .andExpect(status().isBadRequest());

        List<Enfermeiro> enfermeiroList = enfermeiroRepository.findAll();
        assertThat(enfermeiroList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEnfermeiros() throws Exception {
        // Initialize the database
        enfermeiroRepository.saveAndFlush(enfermeiro);

        // Get all the enfermeiroList
        restEnfermeiroMockMvc.perform(get("/api/enfermeiros?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(enfermeiro.getId().intValue())))
            .andExpect(jsonPath("$.[*].cre").value(hasItem(DEFAULT_CRE.toString())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].tipoEnfermeiro").value(hasItem(DEFAULT_TIPO_ENFERMEIRO.toString())));
    }

    @Test
    @Transactional
    public void getEnfermeiro() throws Exception {
        // Initialize the database
        enfermeiroRepository.saveAndFlush(enfermeiro);

        // Get the enfermeiro
        restEnfermeiroMockMvc.perform(get("/api/enfermeiros/{id}", enfermeiro.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(enfermeiro.getId().intValue()))
            .andExpect(jsonPath("$.cre").value(DEFAULT_CRE.toString()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.tipoEnfermeiro").value(DEFAULT_TIPO_ENFERMEIRO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEnfermeiro() throws Exception {
        // Get the enfermeiro
        restEnfermeiroMockMvc.perform(get("/api/enfermeiros/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEnfermeiro() throws Exception {
        // Initialize the database
        enfermeiroRepository.saveAndFlush(enfermeiro);
        int databaseSizeBeforeUpdate = enfermeiroRepository.findAll().size();

        // Update the enfermeiro
        Enfermeiro updatedEnfermeiro = enfermeiroRepository.findOne(enfermeiro.getId());
        updatedEnfermeiro
                .cre(UPDATED_CRE)
                .nome(UPDATED_NOME)
                .tipoEnfermeiro(UPDATED_TIPO_ENFERMEIRO);
        EnfermeiroDTO enfermeiroDTO = enfermeiroMapper.enfermeiroToEnfermeiroDTO(updatedEnfermeiro);

        restEnfermeiroMockMvc.perform(put("/api/enfermeiros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(enfermeiroDTO)))
            .andExpect(status().isOk());

        // Validate the Enfermeiro in the database
        List<Enfermeiro> enfermeiroList = enfermeiroRepository.findAll();
        assertThat(enfermeiroList).hasSize(databaseSizeBeforeUpdate);
        Enfermeiro testEnfermeiro = enfermeiroList.get(enfermeiroList.size() - 1);
        assertThat(testEnfermeiro.getCre()).isEqualTo(UPDATED_CRE);
        assertThat(testEnfermeiro.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testEnfermeiro.getTipoEnfermeiro()).isEqualTo(UPDATED_TIPO_ENFERMEIRO);
    }

    @Test
    @Transactional
    public void updateNonExistingEnfermeiro() throws Exception {
        int databaseSizeBeforeUpdate = enfermeiroRepository.findAll().size();

        // Create the Enfermeiro
        EnfermeiroDTO enfermeiroDTO = enfermeiroMapper.enfermeiroToEnfermeiroDTO(enfermeiro);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEnfermeiroMockMvc.perform(put("/api/enfermeiros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(enfermeiroDTO)))
            .andExpect(status().isCreated());

        // Validate the Enfermeiro in the database
        List<Enfermeiro> enfermeiroList = enfermeiroRepository.findAll();
        assertThat(enfermeiroList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteEnfermeiro() throws Exception {
        // Initialize the database
        enfermeiroRepository.saveAndFlush(enfermeiro);
        int databaseSizeBeforeDelete = enfermeiroRepository.findAll().size();

        // Get the enfermeiro
        restEnfermeiroMockMvc.perform(delete("/api/enfermeiros/{id}", enfermeiro.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Enfermeiro> enfermeiroList = enfermeiroRepository.findAll();
        assertThat(enfermeiroList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Enfermeiro.class);
    }
}
