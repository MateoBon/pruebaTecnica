package com.prueba.analista.repository;

import com.prueba.analista.model.Equipo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class EquipoRepositoryTest {

    @Autowired
    private EquipoRepository equipoRepository;

    private Equipo equipo;

    @BeforeEach
    void setup() {
        equipo = Equipo.builder()
                .serial(5)
                .nombre("Equipo5")
                .fechaCompra(new Date())
                .valorCompra(100)
                .build();
    }

    @Test
    void createEquipoTest() {
        Equipo savedEquipo = equipoRepository.save(equipo);

        assertThat(savedEquipo).isNotNull();
    }

    @Test
    void getAllEquiposTest() {
        Equipo equipo1 = Equipo.builder()
                .serial(6)
                .nombre("Equipo6")
                .fechaCompra(new Date())
                .valorCompra(200)
                .build();
        equipoRepository.save(equipo1);
        equipoRepository.save(equipo);

        List<Equipo> equipoList = equipoRepository.findAll();

        assertThat(equipoList)
                .isNotNull()
                .hasSize(6);
    }

    @Test
    void getEquipoBySerial() {
        equipoRepository.save(equipo);

        Equipo getEquipo = equipoRepository.findById(equipo.getSerial()).get();

        assertThat(getEquipo).isNotNull();
    }

    @Test
    void updateEquipo() {
        equipoRepository.save(equipo);

        Equipo getEquipo = equipoRepository.findById(equipo.getSerial()).get();
        getEquipo.setValorCompra(2600);
        getEquipo.setNombre("Equipo32");

        Equipo updatedEquipo = equipoRepository.save(getEquipo);

        assertThat(updatedEquipo.getValorCompra()).isEqualTo(2600);
        assertThat(updatedEquipo.getNombre()).isEqualTo("Equipo32");
    }

    @Test
    void deleteEquipo() {
        equipoRepository.save(equipo);

        equipoRepository.deleteById(equipo.getSerial());
        Optional<Equipo> equipoOptional = equipoRepository.findById(equipo.getSerial());

        assertThat(equipoOptional).isEmpty();
    }
}
