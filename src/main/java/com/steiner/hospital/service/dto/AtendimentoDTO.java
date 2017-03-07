package com.steiner.hospital.service.dto;


import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import com.steiner.hospital.domain.enumeration.TipoAtendimento;
import com.steiner.hospital.domain.enumeration.ClassificacaoRisco;

/**
 * A DTO for the Atendimento entity.
 */
public class AtendimentoDTO implements Serializable {

    private Long id;

    private ZonedDateTime data;

    @NotNull
    private String descricao;

    private String prescricao;

    private TipoAtendimento tipoAtendimento;

    private ClassificacaoRisco classificacaoRisco;

    private Long medicoId;

    private Long enfermeiroId;

    private Long pacienteId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public ZonedDateTime getData() {
        return data;
    }

    public void setData(ZonedDateTime data) {
        this.data = data;
    }
    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public String getPrescricao() {
        return prescricao;
    }

    public void setPrescricao(String prescricao) {
        this.prescricao = prescricao;
    }
    public TipoAtendimento getTipoAtendimento() {
        return tipoAtendimento;
    }

    public void setTipoAtendimento(TipoAtendimento tipoAtendimento) {
        this.tipoAtendimento = tipoAtendimento;
    }
    public ClassificacaoRisco getClassificacaoRisco() {
        return classificacaoRisco;
    }

    public void setClassificacaoRisco(ClassificacaoRisco classificacaoRisco) {
        this.classificacaoRisco = classificacaoRisco;
    }

    public Long getMedicoId() {
        return medicoId;
    }

    public void setMedicoId(Long medicoId) {
        this.medicoId = medicoId;
    }

    public Long getEnfermeiroId() {
        return enfermeiroId;
    }

    public void setEnfermeiroId(Long enfermeiroId) {
        this.enfermeiroId = enfermeiroId;
    }

    public Long getPacienteId() {
        return pacienteId;
    }

    public void setPacienteId(Long pacienteId) {
        this.pacienteId = pacienteId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AtendimentoDTO atendimentoDTO = (AtendimentoDTO) o;

        if ( ! Objects.equals(id, atendimentoDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "AtendimentoDTO{" +
            "id=" + id +
            ", data='" + data + "'" +
            ", descricao='" + descricao + "'" +
            ", prescricao='" + prescricao + "'" +
            ", tipoAtendimento='" + tipoAtendimento + "'" +
            ", classificacaoRisco='" + classificacaoRisco + "'" +
            '}';
    }
}
