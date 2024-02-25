package com.formations.service_formation.mapper;

import com.formations.service_formation.Entity.Formation;
import com.formations.service_formation.dto.FormationDto;

public class FormationMapper {

    public static FormationDto mapfromFormationToFormationDto(Formation formation){
        return new FormationDto(
                formation.getId(),
                formation.getNomFormation(),
                formation.getObjectif(),
                formation.getCollaborateurs(),
                formation.getDuree(),
                formation.getDate()
        );
    }
}
