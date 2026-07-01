package com.bookpoint.sucursal.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor
@Entity @Table(name = "sucursales")
public class Sucursal {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre de la sucursal no puede estar vacío ni en blanco")
    @Column(name = "nombre")
    private String nombre;

    @NotBlank(message = "La dirección no puede estar vacía ni en blanco")
    @Column(name = "direccion")
    private String direccion;

    @NotBlank(message = "La ciudad no puede estar vacía ni en blanco")
    @Column(name = "ciudad")
    private String ciudad;

    @NotBlank(message = "El teléfono no puede estar vacío ni en blanco")
    @Column(name = "telefono")
    private String telefono;

    @NotBlank(message = "El horario no puede estar vacío ni en blanco")
    @Column(name = "horario")
    private String horario;
}
