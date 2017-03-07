package com.steiner.hospital.service.mapper;

import com.steiner.hospital.domain.*;
import com.steiner.hospital.service.dto.PacienteDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Paciente and its DTO PacienteDTO.
 */
@Mapper(componentModel = "spring", uses = {MedicoMapper.class, EnfermeiroMapper.class, })
public interface PacienteMapper {

    PacienteDTO pacienteToPacienteDTO(Paciente paciente);

    List<PacienteDTO> pacientesToPacienteDTOs(List<Paciente> pacientes);

    @Mapping(target = "atendimentos", ignore = true)
    @Mapping(target = "exames", ignore = true)
    Paciente pacienteDTOToPaciente(PacienteDTO pacienteDTO);

    List<Paciente> pacienteDTOsToPacientes(List<PacienteDTO> pacienteDTOs);

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
}
