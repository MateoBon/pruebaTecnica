package com.prueba.analista.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class EquipoDTO {

    private long serial;
    private String nombre;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date fechaCompra;
    private double valorCompra;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date fechaActual;
    private int annosTranscurridos;
    private double valorActual;
    private double depreciacion;

}
