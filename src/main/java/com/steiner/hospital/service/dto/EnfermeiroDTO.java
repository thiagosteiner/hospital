package com.steiner.hospital.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import com.steiner.hospital.domain.enumeration.TipoEnfermeiro;

/**
 * A DTO for the Enfermeiro entity.
 */
public class EnfermeiroDTO implements Serializable {

    private Long id;

    @NotNull
    private String cre;

    @NotNull
    private String nome;

    private TipoEnfermeiro tipoEnfermeiro;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getCre() {
        return cre;
    }

    public void setCre(String cre) {
        this.cre = cre;
    }
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    public TipoEnfermeiro getTipoEnfermeiro() {
        return tipoEnfermeiro;
    }

    public void setTipoEnfermeiro(TipoEnfermeiro tipoEnfermeiro) {
        this.tipoEnfermeiro = tipoEnfermeiro;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EnfermeiroDTO enfermeiroDTO = (EnfermeiroDTO) o;

        if ( ! Objects.equals(id, enfermeiroDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "EnfermeiroDTO{" +
            "id=" + id +
            ", cre='" + cre + "'" +
            ", nome='" + nome + "'" +
            ", tipoEnfermeiro='" + tipoEnfermeiro + "'" +
            '}';
    }
}
