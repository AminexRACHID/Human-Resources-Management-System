package com.formations.service_formation.dto;

import com.formations.service_formation.Entity.Formation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CollaborateursDto {

    private String collaborateurs;
    private List<Formation> formations;
}
