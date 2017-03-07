package com.steiner.hospital.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Medico.
 */
@Entity
@Table(name = "medico")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Medico implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "crm", nullable = false)
    private String crm;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "especialidade")
    private String especialidade;

    @OneToMany(mappedBy = "medico")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Atendimento> atendimentos = new HashSet<>();

    @OneToMany(mappedBy = "medico")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Exame> exames = new HashSet<>();

    @ManyToMany(mappedBy = "medicos")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Paciente> pacientes = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCrm() {
        return crm;
    }

    public Medico crm(String crm) {
        this.crm = crm;
        return this;
    }

    public void setCrm(String crm) {
        this.crm = crm;
    }

    public String getNome() {
        return nome;
    }

    public Medico nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public Medico especialidade(String especialidade) {
        this.especialidade = especialidade;
        return this;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public Set<Atendimento> getAtendimentos() {
        return atendimentos;
    }

    public Medico atendimentos(Set<Atendimento> atendimentos) {
        this.atendimentos = atendimentos;
        return this;
    }

    public Medico addAtendimento(Atendimento atendimento) {
        this.atendimentos.add(atendimento);
        atendimento.setMedico(this);
        return this;
    }

    public Medico removeAtendimento(Atendimento atendimento) {
        this.atendimentos.remove(atendimento);
        atendimento.setMedico(null);
        return this;
    }

    public void setAtendimentos(Set<Atendimento> atendimentos) {
        this.atendimentos = atendimentos;
    }

    public Set<Exame> getExames() {
        return exames;
    }

    public Medico exames(Set<Exame> exames) {
        this.exames = exames;
        return this;
    }

    public Medico addExame(Exame exame) {
        this.exames.add(exame);
        exame.setMedico(this);
        return this;
    }

    public Medico removeExame(Exame exame) {
        this.exames.remove(exame);
        exame.setMedico(null);
        return this;
    }

    public void setExames(Set<Exame> exames) {
        this.exames = exames;
    }

    public Set<Paciente> getPacientes() {
        return pacientes;
    }

    public Medico pacientes(Set<Paciente> pacientes) {
        this.pacientes = pacientes;
        return this;
    }

    public Medico addPaciente(Paciente paciente) {
        this.pacientes.add(paciente);
        paciente.getMedicos().add(this);
        return this;
    }

    public Medico removePaciente(Paciente paciente) {
        this.pacientes.remove(paciente);
        paciente.getMedicos().remove(this);
        return this;
    }

    public void setPacientes(Set<Paciente> pacientes) {
        this.pacientes = pacientes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Medico medico = (Medico) o;
        if (medico.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, medico.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Medico{" +
            "id=" + id +
            ", crm='" + crm + "'" +
            ", nome='" + nome + "'" +
            ", especialidade='" + especialidade + "'" +
            '}';
    }
}
