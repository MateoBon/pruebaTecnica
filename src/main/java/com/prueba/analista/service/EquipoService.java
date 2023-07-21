package com.prueba.analista.service;

import com.prueba.analista.dto.EquipoDTO;
import com.prueba.analista.model.Equipo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface EquipoService {

    ResponseEntity<Equipo> createEquipo(Equipo equipo);

    ResponseEntity<List<Equipo>> getAllEquipos();

    ResponseEntity<EquipoDTO> getEquipoBySerial(Long serial);

    ResponseEntity<Equipo> updateEquipoBySerial(Long serial, Equipo equipo);

    ResponseEntity<HttpStatus> deleteEquipoBySerial(Long serial);
}
