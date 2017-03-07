package com.steiner.hospital.service.mapper;

import com.steiner.hospital.domain.*;
import com.steiner.hospital.service.dto.MedicoDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Medico and its DTO MedicoDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MedicoMapper {

    MedicoDTO medicoToMedicoDTO(Medico medico);

    List<MedicoDTO> medicosToMedicoDTOs(List<Medico> medicos);

    @Mapping(target = "atendimentos", ignore = true)
    @Mapping(target = "exames", ignore = true)
    @Mapping(target = "pacientes", ignore = true)
    Medico medicoDTOToMedico(MedicoDTO medicoDTO);

    List<Medico> medicoDTOsToMedicos(List<MedicoDTO> medicoDTOs);
}
