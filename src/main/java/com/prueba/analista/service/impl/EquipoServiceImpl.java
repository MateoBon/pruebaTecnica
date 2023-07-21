package com.prueba.analista.service.impl;

import com.prueba.analista.dto.EquipoDTO;
import com.prueba.analista.model.Equipo;
import com.prueba.analista.repository.EquipoRepository;
import com.prueba.analista.service.EquipoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class EquipoServiceImpl implements EquipoService {

    @Autowired
    EquipoRepository repository;

    @Override
    public ResponseEntity<Equipo> createEquipo(Equipo equipo) {
        Optional<Equipo> equipoOptional = repository.findById(equipo.getSerial());

        if (equipoOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(repository.save(equipo), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<List<Equipo>> getAllEquipos() {

        List<Equipo> equipoList = repository.findAll();

        if (equipoList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(equipoList, HttpStatus.OK);

    }

    @Override
    public ResponseEntity<EquipoDTO> getEquipoBySerial(Long serial) {
        Optional<Equipo> equipo = repository.findById(serial);

        if (equipo.isPresent()) {
            EquipoDTO equipoDTO = calculateEquipoDepreciation(equipo.get());
            return new ResponseEntity<>(equipoDTO, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<Equipo> updateEquipoBySerial(Long serial, Equipo equipo) {
        Optional<Equipo> oldEquipo = repository.findById(serial);

        if (oldEquipo.isPresent()) {
            Equipo updatedEquipo = oldEquipo.get();
            updatedEquipo.setNombre(equipo.getNombre());
            updatedEquipo.setFechaCompra(equipo.getFechaCompra());
            updatedEquipo.setValorCompra(equipo.getValorCompra());

            repository.save(updatedEquipo);
            return new ResponseEntity<>(updatedEquipo, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<HttpStatus> deleteEquipoBySerial(Long serial) {
        Optional<Equipo> equipo = repository.findById(serial);

        if (equipo.isPresent()) {
            repository.delete(equipo.get());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    private EquipoDTO calculateEquipoDepreciation(Equipo equipo) {

        ModelMapper modelMapper = new ModelMapper();
        EquipoDTO equipoDTO = modelMapper.map(equipo, EquipoDTO.class);

        Instant instant = Instant.now();
        long diffInYears = ChronoUnit.YEARS.between(equipo.getFechaCompra().toInstant().atZone(ZoneId.systemDefault()), instant.atZone(ZoneId.systemDefault()));
        double valorCompra = equipo.getValorCompra();
        double annualDepreciation = valorCompra * 0.04;
        double finalDepreciation = annualDepreciation * diffInYears;

        equipoDTO.setFechaActual(new Date());
        equipoDTO.setAnnosTranscurridos((int) diffInYears);
        equipoDTO.setDepreciacion(finalDepreciation);
        equipoDTO.setValorActual(valorCompra-finalDepreciation);
        return equipoDTO;
    }
}
