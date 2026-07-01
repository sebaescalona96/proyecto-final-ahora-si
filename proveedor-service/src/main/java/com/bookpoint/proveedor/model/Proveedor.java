package com.bookpoint.proveedor.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor
@Entity @Table(name = "proveedores")
public class Proveedor {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre del proveedor no puede estar vacío ni en blanco")
    @Column(name = "nombre")
    private String nombre;

    @NotBlank(message = "El correo electrónico no puede estar vacío")
    @Email(message = "El formato del correo electrónico no es válido")
    @Column(name = "email")
    private String email;

    @NotBlank(message = "El teléfono no puede estar vacío ni en blanco")
    @Column(name = "telefono")
    private String telefono;

    @NotBlank(message = "La editorial no puede estar vacía ni en blanco")
    @Column(name = "editorial")
    private String editorial;

    // Al ser un objeto Boolean, validamos que no llegue nulo
    @NotNull(message = "El estado activo es obligatorio (true/false)")
    @Column(name = "activo")
    private Boolean activo;
}