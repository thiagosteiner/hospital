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

import com.steiner.hospital.domain.enumeration.TipoEnfermeiro;

/**
 * A Enfermeiro.
 */
@Entity
@Table(name = "enfermeiro")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Enfermeiro implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "cre", nullable = false)
    private String cre;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_enfermeiro")
    private TipoEnfermeiro tipoEnfermeiro;

    @OneToMany(mappedBy = "enfermeiro")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Atendimento> atendimentos = new HashSet<>();

    @ManyToMany(mappedBy = "enfermeiros")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Paciente> pacientes = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCre() {
        return cre;
    }

    public Enfermeiro cre(String cre) {
        this.cre = cre;
        return this;
    }

    public void setCre(String cre) {
        this.cre = cre;
    }

    public String getNome() {
        return nome;
    }

    public Enfermeiro nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public TipoEnfermeiro getTipoEnfermeiro() {
        return tipoEnfermeiro;
    }

    public Enfermeiro tipoEnfermeiro(TipoEnfermeiro tipoEnfermeiro) {
        this.tipoEnfermeiro = tipoEnfermeiro;
        return this;
    }

    public void setTipoEnfermeiro(TipoEnfermeiro tipoEnfermeiro) {
        this.tipoEnfermeiro = tipoEnfermeiro;
    }

    public Set<Atendimento> getAtendimentos() {
        return atendimentos;
    }

    public Enfermeiro atendimentos(Set<Atendimento> atendimentos) {
        this.atendimentos = atendimentos;
        return this;
    }

    public Enfermeiro addAtendimento(Atendimento atendimento) {
        this.atendimentos.add(atendimento);
        atendimento.setEnfermeiro(this);
        return this;
    }

    public Enfermeiro removeAtendimento(Atendimento atendimento) {
        this.atendimentos.remove(atendimento);
        atendimento.setEnfermeiro(null);
        return this;
    }

    public void setAtendimentos(Set<Atendimento> atendimentos) {
        this.atendimentos = atendimentos;
    }

    public Set<Paciente> getPacientes() {
        return pacientes;
    }

    public Enfermeiro pacientes(Set<Paciente> pacientes) {
        this.pacientes = pacientes;
        return this;
    }

    public Enfermeiro addPaciente(Paciente paciente) {
        this.pacientes.add(paciente);
        paciente.getEnfermeiros().add(this);
        return this;
    }

    public Enfermeiro removePaciente(Paciente paciente) {
        this.pacientes.remove(paciente);
        paciente.getEnfermeiros().remove(this);
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
        Enfermeiro enfermeiro = (Enfermeiro) o;
        if (enfermeiro.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, enfermeiro.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Enfermeiro{" +
            "id=" + id +
            ", cre='" + cre + "'" +
            ", nome='" + nome + "'" +
            ", tipoEnfermeiro='" + tipoEnfermeiro + "'" +
            '}';
    }
}
