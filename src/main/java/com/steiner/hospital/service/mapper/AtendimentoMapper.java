package com.steiner.hospital.service.mapper;

import com.steiner.hospital.domain.*;
import com.steiner.hospital.service.dto.AtendimentoDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Atendimento and its DTO AtendimentoDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AtendimentoMapper {

    @Mapping(source = "medico.id", target = "medicoId")
    @Mapping(source = "enfermeiro.id", target = "enfermeiroId")
    @Mapping(source = "paciente.id", target = "pacienteId")
    AtendimentoDTO atendimentoToAtendimentoDTO(Atendimento atendimento);

    List<AtendimentoDTO> atendimentosToAtendimentoDTOs(List<Atendimento> atendimentos);

    @Mapping(target = "exames", ignore = true)
    @Mapping(source = "medicoId", target = "medico")
    @Mapping(source = "enfermeiroId", target = "enfermeiro")
    @Mapping(source = "pacienteId", target = "paciente")
    Atendimento atendimentoDTOToAtendimento(AtendimentoDTO atendimentoDTO);

    List<Atendimento> atendimentoDTOsToAtendimentos(List<AtendimentoDTO> atendimentoDTOs);

    default Medico medicoFromId(Long id) {
        if (id == null) {
            return null;
        }
        Medico medico = new Medico();
        medico.setId(id);
        return medico;
    }

    default Enfermeiro enfermeiroFromId(Long id) {
        if (id == null) {
            return null;
        }
        Enfermeiro enfermeiro = new Enfermeiro();
        enfermeiro.setId(id);
        return enfermeiro;
    }

    default Paciente pacienteFromId(Long id) {
        if (id == null) {
            return null;
        }
        Paciente paciente = new Paciente();
        paciente.setId(id);
        return paciente;
    }
}
