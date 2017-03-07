package com.steiner.hospital.service.dto;


import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Exame entity.
 */
public class ExameDTO implements Serializable {

    private Long id;

    @NotNull
    private String nome;

    private String laudo;

    private ZonedDateTime dataSolicitacao;

    private ZonedDateTime dataLaudo;

    private Long atendimentoId;

    private Long pacienteId;

    private Long medicoId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getLaudo() {
        return laudo;
    }

    public void setLaudo(String laudo) {
        this.laudo = laudo;
    }
    public ZonedDateTime getDataSolicitacao() {
        return dataSolicitacao;
    }

    public void setDataSolicitacao(ZonedDateTime dataSolicitacao) {
        this.dataSolicitacao = dataSolicitacao;
    }
    public ZonedDateTime getDataLaudo() {
        return dataLaudo;
    }

    public void setDataLaudo(ZonedDateTime dataLaudo) {
        this.dataLaudo = dataLaudo;
    }

    public Long getAtendimentoId() {
        return atendimentoId;
    }

    public void setAtendimentoId(Long atendimentoId) {
        this.atendimentoId = atendimentoId;
    }

    public Long getPacienteId() {
        return pacienteId;
    }

    public void setPacienteId(Long pacienteId) {
        this.pacienteId = pacienteId;
    }

    public Long getMedicoId() {
        return medicoId;
    }

    public void setMedicoId(Long medicoId) {
        this.medicoId = medicoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ExameDTO exameDTO = (ExameDTO) o;

        if ( ! Objects.equals(id, exameDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ExameDTO{" +
            "id=" + id +
            ", nome='" + nome + "'" +
            ", laudo='" + laudo + "'" +
            ", dataSolicitacao='" + dataSolicitacao + "'" +
            ", dataLaudo='" + dataLaudo + "'" +
            '}';
    }
}
