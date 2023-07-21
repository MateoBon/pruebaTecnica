package com.prueba.analista.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
@ToString
public class Equipo {

    @Id
    private long serial;
    private String nombre;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date fechaCompra;
    private double valorCompra;
}
