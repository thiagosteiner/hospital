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
 * A Paciente.
 */
@Entity
@Table(name = "paciente")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Paciente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "cpf", nullable = false)
    private String cpf;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "plano_saude")
    private String planoSaude;

    @Column(name = "endereco")
    private String endereco;

    @OneToMany(mappedBy = "paciente")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Atendimento> atendimentos = new HashSet<>();

    @OneToMany(mappedBy = "paciente")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Exame> exames = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "paciente_medico",
               joinColumns = @JoinColumn(name="pacientes_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="medicos_id", referencedColumnName="id"))
    private Set<Medico> medicos = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "paciente_enfermeiro",
               joinColumns = @JoinColumn(name="pacientes_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="enfermeiros_id", referencedColumnName="id"))
    private Set<Enfermeiro> enfermeiros = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public Paciente cpf(String cpf) {
        this.cpf = cpf;
        return this;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public Paciente nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPlanoSaude() {
        return planoSaude;
    }

    public Paciente planoSaude(String planoSaude) {
        this.planoSaude = planoSaude;
        return this;
    }

    public void setPlanoSaude(String planoSaude) {
        this.planoSaude = planoSaude;
    }

    public String getEndereco() {
        return endereco;
    }

    public Paciente endereco(String endereco) {
        this.endereco = endereco;
        return this;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public Set<Atendimento> getAtendimentos() {
        return atendimentos;
    }

    public Paciente atendimentos(Set<Atendimento> atendimentos) {
        this.atendimentos = atendimentos;
        return this;
    }

    public Paciente addAtendimento(Atendimento atendimento) {
        this.atendimentos.add(atendimento);
        atendimento.setPaciente(this);
        return this;
    }

    public Paciente removeAtendimento(Atendimento atendimento) {
        this.atendimentos.remove(atendimento);
        atendimento.setPaciente(null);
        return this;
    }

    public void setAtendimentos(Set<Atendimento> atendimentos) {
        this.atendimentos = atendimentos;
    }

    public Set<Exame> getExames() {
        return exames;
    }

    public Paciente exames(Set<Exame> exames) {
        this.exames = exames;
        return this;
    }

    public Paciente addExame(Exame exame) {
        this.exames.add(exame);
        exame.setPaciente(this);
        return this;
    }

    public Paciente removeExame(Exame exame) {
        this.exames.remove(exame);
        exame.setPaciente(null);
        return this;
    }

    public void setExames(Set<Exame> exames) {
        this.exames = exames;
    }

    public Set<Medico> getMedicos() {
        return medicos;
    }

    public Paciente medicos(Set<Medico> medicos) {
        this.medicos = medicos;
        return this;
    }

    public Paciente addMedico(Medico medico) {
        this.medicos.add(medico);
        medico.getPacientes().add(this);
        return this;
    }

    public Paciente removeMedico(Medico medico) {
        this.medicos.remove(medico);
        medico.getPacientes().remove(this);
        return this;
    }

    public void setMedicos(Set<Medico> medicos) {
        this.medicos = medicos;
    }

    public Set<Enfermeiro> getEnfermeiros() {
        return enfermeiros;
    }

    public Paciente enfermeiros(Set<Enfermeiro> enfermeiros) {
        this.enfermeiros = enfermeiros;
        return this;
    }

    public Paciente addEnfermeiro(Enfermeiro enfermeiro) {
        this.enfermeiros.add(enfermeiro);
        enfermeiro.getPacientes().add(this);
        return this;
    }

    public Paciente removeEnfermeiro(Enfermeiro enfermeiro) {
        this.enfermeiros.remove(enfermeiro);
        enfermeiro.getPacientes().remove(this);
        return this;
    }

    public void setEnfermeiros(Set<Enfermeiro> enfermeiros) {
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
        Paciente paciente = (Paciente) o;
        if (paciente.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, paciente.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Paciente{" +
            "id=" + id +
            ", cpf='" + cpf + "'" +
            ", nome='" + nome + "'" +
            ", planoSaude='" + planoSaude + "'" +
            ", endereco='" + endereco + "'" +
            '}';
    }
}
