package com.steiner.hospital.web.rest;

import com.steiner.hospital.HospitalApp;

import com.steiner.hospital.domain.Atendimento;
import com.steiner.hospital.repository.AtendimentoRepository;
import com.steiner.hospital.service.dto.AtendimentoDTO;
import com.steiner.hospital.service.mapper.AtendimentoMapper;
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

import com.steiner.hospital.domain.enumeration.TipoAtendimento;
import com.steiner.hospital.domain.enumeration.ClassificacaoRisco;
/**
 * Test class for the AtendimentoResource REST controller.
 *
 * @see AtendimentoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HospitalApp.class)
public class AtendimentoResourceIntTest {

    private static final ZonedDateTime DEFAULT_DATA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATA = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String DEFAULT_PRESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_PRESCRICAO = "BBBBBBBBBB";

    private static final TipoAtendimento DEFAULT_TIPO_ATENDIMENTO = TipoAtendimento.EMERGENCIA;
    private static final TipoAtendimento UPDATED_TIPO_ATENDIMENTO = TipoAtendimento.INTERNACAO;

    private static final ClassificacaoRisco DEFAULT_CLASSIFICACAO_RISCO = ClassificacaoRisco.VERMELHO;
    private static final ClassificacaoRisco UPDATED_CLASSIFICACAO_RISCO = ClassificacaoRisco.LARANJA;

    @Autowired
    private AtendimentoRepository atendimentoRepository;

    @Autowired
    private AtendimentoMapper atendimentoMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAtendimentoMockMvc;

    private Atendimento atendimento;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            AtendimentoResource atendimentoResource = new AtendimentoResource(atendimentoRepository, atendimentoMapper);
        this.restAtendimentoMockMvc = MockMvcBuilders.standaloneSetup(atendimentoResource)
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
    public static Atendimento createEntity(EntityManager em) {
        Atendimento atendimento = new Atendimento()
                .data(DEFAULT_DATA)
                .descricao(DEFAULT_DESCRICAO)
                .prescricao(DEFAULT_PRESCRICAO)
                .tipoAtendimento(DEFAULT_TIPO_ATENDIMENTO)
                .classificacaoRisco(DEFAULT_CLASSIFICACAO_RISCO);
        return atendimento;
    }

    @Before
    public void initTest() {
        atendimento = createEntity(em);
    }

    @Test
    @Transactional
    public void createAtendimento() throws Exception {
        int databaseSizeBeforeCreate = atendimentoRepository.findAll().size();

        // Create the Atendimento
        AtendimentoDTO atendimentoDTO = atendimentoMapper.atendimentoToAtendimentoDTO(atendimento);

        restAtendimentoMockMvc.perform(post("/api/atendimentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(atendimentoDTO)))
            .andExpect(status().isCreated());

        // Validate the Atendimento in the database
        List<Atendimento> atendimentoList = atendimentoRepository.findAll();
        assertThat(atendimentoList).hasSize(databaseSizeBeforeCreate + 1);
        Atendimento testAtendimento = atendimentoList.get(atendimentoList.size() - 1);
        assertThat(testAtendimento.getData()).isEqualTo(DEFAULT_DATA);
        assertThat(testAtendimento.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testAtendimento.getPrescricao()).isEqualTo(DEFAULT_PRESCRICAO);
        assertThat(testAtendimento.getTipoAtendimento()).isEqualTo(DEFAULT_TIPO_ATENDIMENTO);
        assertThat(testAtendimento.getClassificacaoRisco()).isEqualTo(DEFAULT_CLASSIFICACAO_RISCO);
    }

    @Test
    @Transactional
    public void createAtendimentoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = atendimentoRepository.findAll().size();

        // Create the Atendimento with an existing ID
        Atendimento existingAtendimento = new Atendimento();
        existingAtendimento.setId(1L);
        AtendimentoDTO existingAtendimentoDTO = atendimentoMapper.atendimentoToAtendimentoDTO(existingAtendimento);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAtendimentoMockMvc.perform(post("/api/atendimentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingAtendimentoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Atendimento> atendimentoList = atendimentoRepository.findAll();
        assertThat(atendimentoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = atendimentoRepository.findAll().size();
        // set the field null
        atendimento.setDescricao(null);

        // Create the Atendimento, which fails.
        AtendimentoDTO atendimentoDTO = atendimentoMapper.atendimentoToAtendimentoDTO(atendimento);

        restAtendimentoMockMvc.perform(post("/api/atendimentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(atendimentoDTO)))
            .andExpect(status().isBadRequest());

        List<Atendimento> atendimentoList = atendimentoRepository.findAll();
        assertThat(atendimentoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAtendimentos() throws Exception {
        // Initialize the database
        atendimentoRepository.saveAndFlush(atendimento);

        // Get all the atendimentoList
        restAtendimentoMockMvc.perform(get("/api/atendimentos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(atendimento.getId().intValue())))
            .andExpect(jsonPath("$.[*].data").value(hasItem(sameInstant(DEFAULT_DATA))))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].prescricao").value(hasItem(DEFAULT_PRESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].tipoAtendimento").value(hasItem(DEFAULT_TIPO_ATENDIMENTO.toString())))
            .andExpect(jsonPath("$.[*].classificacaoRisco").value(hasItem(DEFAULT_CLASSIFICACAO_RISCO.toString())));
    }

    @Test
    @Transactional
    public void getAtendimento() throws Exception {
        // Initialize the database
        atendimentoRepository.saveAndFlush(atendimento);

        // Get the atendimento
        restAtendimentoMockMvc.perform(get("/api/atendimentos/{id}", atendimento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(atendimento.getId().intValue()))
            .andExpect(jsonPath("$.data").value(sameInstant(DEFAULT_DATA)))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()))
            .andExpect(jsonPath("$.prescricao").value(DEFAULT_PRESCRICAO.toString()))
            .andExpect(jsonPath("$.tipoAtendimento").value(DEFAULT_TIPO_ATENDIMENTO.toString()))
            .andExpect(jsonPath("$.classificacaoRisco").value(DEFAULT_CLASSIFICACAO_RISCO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAtendimento() throws Exception {
        // Get the atendimento
        restAtendimentoMockMvc.perform(get("/api/atendimentos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAtendimento() throws Exception {
        // Initialize the database
        atendimentoRepository.saveAndFlush(atendimento);
        int databaseSizeBeforeUpdate = atendimentoRepository.findAll().size();

        // Update the atendimento
        Atendimento updatedAtendimento = atendimentoRepository.findOne(atendimento.getId());
        updatedAtendimento
                .data(UPDATED_DATA)
                .descricao(UPDATED_DESCRICAO)
                .prescricao(UPDATED_PRESCRICAO)
                .tipoAtendimento(UPDATED_TIPO_ATENDIMENTO)
                .classificacaoRisco(UPDATED_CLASSIFICACAO_RISCO);
        AtendimentoDTO atendimentoDTO = atendimentoMapper.atendimentoToAtendimentoDTO(updatedAtendimento);

        restAtendimentoMockMvc.perform(put("/api/atendimentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(atendimentoDTO)))
            .andExpect(status().isOk());

        // Validate the Atendimento in the database
        List<Atendimento> atendimentoList = atendimentoRepository.findAll();
        assertThat(atendimentoList).hasSize(databaseSizeBeforeUpdate);
        Atendimento testAtendimento = atendimentoList.get(atendimentoList.size() - 1);
        assertThat(testAtendimento.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testAtendimento.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testAtendimento.getPrescricao()).isEqualTo(UPDATED_PRESCRICAO);
        assertThat(testAtendimento.getTipoAtendimento()).isEqualTo(UPDATED_TIPO_ATENDIMENTO);
        assertThat(testAtendimento.getClassificacaoRisco()).isEqualTo(UPDATED_CLASSIFICACAO_RISCO);
    }

    @Test
    @Transactional
    public void updateNonExistingAtendimento() throws Exception {
        int databaseSizeBeforeUpdate = atendimentoRepository.findAll().size();

        // Create the Atendimento
        AtendimentoDTO atendimentoDTO = atendimentoMapper.atendimentoToAtendimentoDTO(atendimento);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAtendimentoMockMvc.perform(put("/api/atendimentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(atendimentoDTO)))
            .andExpect(status().isCreated());

        // Validate the Atendimento in the database
        List<Atendimento> atendimentoList = atendimentoRepository.findAll();
        assertThat(atendimentoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAtendimento() throws Exception {
        // Initialize the database
        atendimentoRepository.saveAndFlush(atendimento);
        int databaseSizeBeforeDelete = atendimentoRepository.findAll().size();

        // Get the atendimento
        restAtendimentoMockMvc.perform(delete("/api/atendimentos/{id}", atendimento.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Atendimento> atendimentoList = atendimentoRepository.findAll();
        assertThat(atendimentoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Atendimento.class);
    }
}
