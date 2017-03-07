package com.steiner.hospital.service.mapper;

import com.steiner.hospital.domain.*;
import com.steiner.hospital.service.dto.ExameDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Exame and its DTO ExameDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ExameMapper {

    @Mapping(source = "atendimento.id", target = "atendimentoId")
    @Mapping(source = "paciente.id", target = "pacienteId")
    @Mapping(source = "medico.id", target = "medicoId")
    ExameDTO exameToExameDTO(Exame exame);

    List<ExameDTO> examesToExameDTOs(List<Exame> exames);

    @Mapping(source = "atendimentoId", target = "atendimento")
    @Mapping(source = "pacienteId", target = "paciente")
    @Mapping(source = "medicoId", target = "medico")
    Exame exameDTOToExame(ExameDTO exameDTO);

    List<Exame> exameDTOsToExames(List<ExameDTO> exameDTOs);

    default Atendimento atendimentoFromId(Long id) {
        if (id == null) {
            return null;
        }
        Atendimento atendimento = new Atendimento();
        atendimento.setId(id);
        return atendimento;
    }

    default Paciente pacienteFromId(Long id) {
        if (id == null) {
            return null;
        }
        Paciente paciente = new Paciente();
        paciente.setId(id);
        return paciente;
    }

    default Medico medicoFromId(Long id) {
        if (id == null) {
            return null;
        }
        Medico medico = new Medico();
        medico.setId(id);
        return medico;
    }
}
