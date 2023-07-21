package com.prueba.analista.service;

import com.prueba.analista.dto.EquipoDTO;
import com.prueba.analista.model.Equipo;
import com.prueba.analista.repository.EquipoRepository;
import com.prueba.analista.service.impl.EquipoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EquipoServiceTest {

    @Mock
    private EquipoRepository equipoRepository;

    @InjectMocks
    private EquipoServiceImpl equipoService;

    private Equipo equipo;

    @BeforeEach
    void setup() {
        equipo = Equipo.builder()
                .serial(1)
                .nombre("Equipo1")
                .fechaCompra(new Date())
                .valorCompra(100)
                .build();
    }


    @Test
    void createEquipoTestSuccess() {
        given(equipoRepository.findById(equipo.getSerial()))
                .willReturn(Optional.empty());
        given(equipoRepository.save(equipo)).willReturn(equipo);

        ResponseEntity<Equipo> response = equipoService.createEquipo(equipo);

        assertThat(response.getBody()).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    void createEquipoTestBadRequest() {
        given(equipoRepository.findById(equipo.getSerial()))
                .willReturn(Optional.of(equipo));

        ResponseEntity<Equipo> response = equipoService.createEquipo(equipo);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        verify(equipoRepository, never()).save(any(Equipo.class));
    }

    @Test
    void getAllEquipos() {
        Equipo equipo1 = Equipo.builder()
                .serial(2)
                .nombre("Equipo2")
                .fechaCompra(new Date())
                .valorCompra(200)
                .build();
        given(equipoRepository.findAll()).willReturn(List.of(equipo, equipo1));

        ResponseEntity<List<Equipo>> equipoList = equipoService.getAllEquipos();

        assertThat(equipoList.getBody())
                .isNotNull()
                .hasSize(2);
        assertThat(equipoList.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void getAllEquiposNoContent() {
        given(equipoRepository.findAll()).willReturn(Collections.emptyList());

        ResponseEntity<List<Equipo>> equipoList = equipoService.getAllEquipos();

        assertThat(equipoList.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    void getEquipoBySerial() {
        given(equipoRepository.findById(equipo.getSerial())).willReturn(Optional.of(equipo));

        ResponseEntity<EquipoDTO> response = equipoService.getEquipoBySerial(equipo.getSerial());

        assertThat(response.getBody()).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void getEquipoBySerialNotFound() {
        given(equipoRepository.findById(equipo.getSerial())).willReturn(Optional.empty());

        ResponseEntity<EquipoDTO> response = equipoService.getEquipoBySerial(equipo.getSerial());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void updateEquipo() {
        given(equipoRepository.save(equipo)).willReturn(equipo);
        given(equipoRepository.findById(equipo.getSerial())).willReturn(Optional.of(equipo));
        equipo.setNombre("Equipo32");
        equipo.setValorCompra(2600);

        ResponseEntity<Equipo> response = equipoService.updateEquipoBySerial(equipo.getSerial(), equipo);

        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getNombre()).isEqualTo("Equipo32");
        assertThat(response.getBody().getValorCompra()).isEqualTo(2600);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void updateEquipoNotFound() {
        given(equipoRepository.findById(equipo.getSerial())).willReturn(Optional.empty());
        equipo.setNombre("Equipo32");
        equipo.setValorCompra(2600);

        ResponseEntity<Equipo> response = equipoService.updateEquipoBySerial(equipo.getSerial(), equipo);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void deleteEquipo() {
        given(equipoRepository.findById(equipo.getSerial())).willReturn(Optional.of(equipo));

        ResponseEntity<HttpStatus> response = equipoService.deleteEquipoBySerial(equipo.getSerial());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void deleteEquipoNotFound() {
        given(equipoRepository.findById(equipo.getSerial())).willReturn(Optional.empty());

        ResponseEntity<HttpStatus> response = equipoService.deleteEquipoBySerial(equipo.getSerial());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
