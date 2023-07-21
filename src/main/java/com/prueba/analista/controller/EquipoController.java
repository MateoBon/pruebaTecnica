package com.prueba.analista.controller;

import com.prueba.analista.dto.EquipoDTO;
import com.prueba.analista.model.Equipo;
import com.prueba.analista.service.EquipoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/equipos")
@Tag(name = "Endpoints Equipo")
public class EquipoController {

    @Autowired
    EquipoService service;

    @Operation(
            description = "Crear un equipo.",
            responses = {
                    @ApiResponse(
                            description = "Created",
                            responseCode = "201"
                    )
            }
    )
    @PostMapping(value = "/createEquipo")
    public ResponseEntity<Equipo> createEquipo(@RequestBody Equipo equipo) {
        return service.createEquipo(equipo);
    }

    @Operation(
            description = "Traer todos los equipos.",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "No Content",
                            responseCode = "204",
                            content = @Content
                    )
            }
    )
    @GetMapping(value = "/getAllEquipos")
    public ResponseEntity<List<Equipo>> getAllEquipos() {
        return service.getAllEquipos();
    }

    @Operation(
            description = "Trae un equipo buscandolo por su numero serial, ademas, trae informacion " +
                    "sobre su depreciacion.",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Not Found",
                            responseCode = "404",
                            content = @Content
                    )
            }
    )
    @GetMapping(value = "/getEquipoBySerial/{serial}")
    public ResponseEntity<EquipoDTO> getEquipoBySerial(@PathVariable Long serial) {
        return service.getEquipoBySerial(serial);
    }

    @Operation(
            description = "Busca un equipo por su numero de serial y lo actualiza con " +
                    "la informacion suministrada por el usuario, se pueden actualizar todos" +
                    "los valores, menos su numero de serial.",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Not Found",
                            responseCode = "404",
                            content = @Content
                    )
            }
    )
    @PostMapping(value = "/updateEquipo/{serial}")
    public ResponseEntity<Equipo> updateEquipo(@PathVariable Long serial,
                                               @RequestBody Equipo equipo) {
        return service.updateEquipoBySerial(serial, equipo);
    }

    @Operation(
            description = "Busca un equipo por su numero serial y lo elimina.",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            content = @Content
                    ),
                    @ApiResponse(
                            description = "Not Found",
                            responseCode = "404",
                            content = @Content
                    )
            }
    )
    @DeleteMapping(value = "/deleteEquipoBySerial/{serial}")
    public ResponseEntity<HttpStatus> deleteEquipoBySerial(@PathVariable Long serial) {
        return service.deleteEquipoBySerial(serial);
    }
}
