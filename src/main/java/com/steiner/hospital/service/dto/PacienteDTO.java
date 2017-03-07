package com.steiner.hospital.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Paciente entity.
 */
public class PacienteDTO implements Serializable {

    private Long id;

    @NotNull
    private String cpf;

    @NotNull
    private String nome;

    private String planoSaude;

    private String endereco;

    private Set<MedicoDTO> medicos = new HashSet<>();

    private Set<EnfermeiroDTO> enfermeiros = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getPlanoSaude() {
        return planoSaude;
    }

    public void setPlanoSaude(String planoSaude) {
        this.planoSaude = planoSaude;
    }
    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public Set<MedicoDTO> getMedicos() {
        return medicos;
    }

    public void setMedicos(Set<MedicoDTO> medicos) {
        this.medicos = medicos;
    }

    public Set<EnfermeiroDTO> getEnfermeiros() {
        return enfermeiros;
    }

    public void setEnfermeiros(Set<EnfermeiroDTO> enfermeiros) {
        this.enfermeiros = enfermeiros;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PacienteDTO pacienteDTO = (PacienteDTO) o;

        if ( ! Objects.equals(id, pacienteDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PacienteDTO{" +
            "id=" + id +
            ", cpf='" + cpf + "'" +
            ", nome='" + nome + "'" +
            ", planoSaude='" + planoSaude + "'" +
            ", endereco='" + endereco + "'" +
            '}';
    }
}
