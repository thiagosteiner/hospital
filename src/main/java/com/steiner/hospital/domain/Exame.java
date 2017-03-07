package com.steiner.hospital.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Exame.
 */
@Entity
@Table(name = "exame")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Exame implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "laudo")
    private String laudo;

    @Column(name = "data_solicitacao")
    private ZonedDateTime dataSolicitacao;

    @Column(name = "data_laudo")
    private ZonedDateTime dataLaudo;

    @ManyToOne
    private Atendimento atendimento;

    @ManyToOne
    private Paciente paciente;

    @ManyToOne
    private Medico medico;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public Exame nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLaudo() {
        return laudo;
    }

    public Exame laudo(String laudo) {
        this.laudo = laudo;
        return this;
    }

    public void setLaudo(String laudo) {
        this.laudo = laudo;
    }

    public ZonedDateTime getDataSolicitacao() {
        return dataSolicitacao;
    }

    public Exame dataSolicitacao(ZonedDateTime dataSolicitacao) {
        this.dataSolicitacao = dataSolicitacao;
        return this;
    }

    public void setDataSolicitacao(ZonedDateTime dataSolicitacao) {
        this.dataSolicitacao = dataSolicitacao;
    }

    public ZonedDateTime getDataLaudo() {
        return dataLaudo;
    }

    public Exame dataLaudo(ZonedDateTime dataLaudo) {
        this.dataLaudo = dataLaudo;
        return this;
    }

    public void setDataLaudo(ZonedDateTime dataLaudo) {
        this.dataLaudo = dataLaudo;
    }

    public Atendimento getAtendimento() {
        return atendimento;
    }

    public Exame atendimento(Atendimento atendimento) {
        this.atendimento = atendimento;
        return this;
    }

    public void setAtendimento(Atendimento atendimento) {
        this.atendimento = atendimento;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public Exame paciente(Paciente paciente) {
        this.paciente = paciente;
        return this;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Medico getMedico() {
        return medico;
    }

    public Exame medico(Medico medico) {
        this.medico = medico;
        return this;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Exame exame = (Exame) o;
        if (exame.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, exame.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Exame{" +
            "id=" + id +
            ", nome='" + nome + "'" +
            ", laudo='" + laudo + "'" +
            ", dataSolicitacao='" + dataSolicitacao + "'" +
            ", dataLaudo='" + dataLaudo + "'" +
            '}';
    }
}
