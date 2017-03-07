package com.steiner.hospital.service.mapper;

import com.steiner.hospital.domain.*;
import com.steiner.hospital.service.dto.EnfermeiroDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Enfermeiro and its DTO EnfermeiroDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EnfermeiroMapper {

    EnfermeiroDTO enfermeiroToEnfermeiroDTO(Enfermeiro enfermeiro);

    List<EnfermeiroDTO> enfermeirosToEnfermeiroDTOs(List<Enfermeiro> enfermeiros);

    @Mapping(target = "atendimentos", ignore = true)
    @Mapping(target = "pacientes", ignore = true)
    Enfermeiro enfermeiroDTOToEnfermeiro(EnfermeiroDTO enfermeiroDTO);

    List<Enfermeiro> enfermeiroDTOsToEnfermeiros(List<EnfermeiroDTO> enfermeiroDTOs);
}
