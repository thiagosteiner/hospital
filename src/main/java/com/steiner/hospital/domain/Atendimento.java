package com.steiner.hospital.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.steiner.hospital.domain.enumeration.TipoAtendimento;

import com.steiner.hospital.domain.enumeration.ClassificacaoRisco;

/**
 * A Atendimento.
 */
@Entity
@Table(name = "atendimento")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Atendimento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "data")
    private ZonedDateTime data;

    @NotNull
    @Column(name = "descricao", nullable = false)
    private String descricao;

    @Column(name = "prescricao")
    private String prescricao;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_atendimento")
    private TipoAtendimento tipoAtendimento;

    @Enumerated(EnumType.STRING)
    @Column(name = "classificacao_risco")
    private ClassificacaoRisco classificacaoRisco;

    @OneToMany(mappedBy = "atendimento")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Exame> exames = new HashSet<>();

    @ManyToOne
    private Medico medico;

    @ManyToOne
    private Enfermeiro enfermeiro;

    @ManyToOne
    private Paciente paciente;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getData() {
        return data;
    }

    public Atendimento data(ZonedDateTime data) {
        this.data = data;
        return this;
    }

    public void setData(ZonedDateTime data) {
        this.data = data;
    }

    public String getDescricao() {
        return descricao;
    }

    public Atendimento descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getPrescricao() {
        return prescricao;
    }

    public Atendimento prescricao(String prescricao) {
        this.prescricao = prescricao;
        return this;
    }

    public void setPrescricao(String prescricao) {
        this.prescricao = prescricao;
    }

    public TipoAtendimento getTipoAtendimento() {
        return tipoAtendimento;
    }

    public Atendimento tipoAtendimento(TipoAtendimento tipoAtendimento) {
        this.tipoAtendimento = tipoAtendimento;
        return this;
    }

    public void setTipoAtendimento(TipoAtendimento tipoAtendimento) {
        this.tipoAtendimento = tipoAtendimento;
    }

    public ClassificacaoRisco getClassificacaoRisco() {
        return classificacaoRisco;
    }

    public Atendimento classificacaoRisco(ClassificacaoRisco classificacaoRisco) {
        this.classificacaoRisco = classificacaoRisco;
        return this;
    }

    public void setClassificacaoRisco(ClassificacaoRisco classificacaoRisco) {
        this.classificacaoRisco = classificacaoRisco;
    }

    public Set<Exame> getExames() {
        return exames;
    }

    public Atendimento exames(Set<Exame> exames) {
        this.exames = exames;
        return this;
    }

    public Atendimento addExame(Exame exame) {
        this.exames.add(exame);
        exame.setAtendimento(this);
        return this;
    }

    public Atendimento removeExame(Exame exame) {
        this.exames.remove(exame);
        exame.setAtendimento(null);
        return this;
    }

    public void setExames(Set<Exame> exames) {
        this.exames = exames;
    }

    public Medico getMedico() {
        return medico;
    }

    public Atendimento medico(Medico medico) {
        this.medico = medico;
        return this;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public Enfermeiro getEnfermeiro() {
        return enfermeiro;
    }

    public Atendimento enfermeiro(Enfermeiro enfermeiro) {
        this.enfermeiro = enfermeiro;
        return this;
    }

    public void setEnfermeiro(Enfermeiro enfermeiro) {
        this.enfermeiro = enfermeiro;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public Atendimento paciente(Paciente paciente) {
        this.paciente = paciente;
        return this;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Atendimento atendimento = (Atendimento) o;
        if (atendimento.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, atendimento.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Atendimento{" +
            "id=" + id +
            ", data='" + data + "'" +
            ", descricao='" + descricao + "'" +
            ", prescricao='" + prescricao + "'" +
            ", tipoAtendimento='" + tipoAtendimento + "'" +
            ", classificacaoRisco='" + classificacaoRisco + "'" +
            '}';
    }
}
