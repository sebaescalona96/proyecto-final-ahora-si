package com.bookpoint.descuento.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;
import java.time.LocalDate;

@Data @NoArgsConstructor @AllArgsConstructor
@Entity @Table(name = "descuentos")
public class Descuento {
   @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El código no puede estar vacío")
    @Size(min = 3, max = 50, message = "El código debe tener entre 3 y 50 caracteres")
    @Column(name = "codigo", nullable = false, unique = true) // nullable a nivel BD también
    private String codigo;

    @NotBlank(message = "La descripción no puede estar vacía")
    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @NotNull(message = "El porcentaje es obligatorio")
    @Positive(message = "El porcentaje debe ser un número positivo")
    @Max(value = 100, message = "El porcentaje no puede ser mayor a 100")
    @Column(name = "porcentaje", nullable = false)
    private Double porcentaje;

    @NotBlank(message = "El tipo de descuento es obligatorio")
    @Column(name = "tipo", nullable = false)
    private String tipo;

    @NotNull(message = "El estado activo/inactivo es obligatorio")
    @Column(name = "activo", nullable = false)
    private Boolean activo;

    @NotNull(message = "La fecha de expiración es obligatoria")
    @FutureOrPresent(message = "La fecha de expiración debe ser hoy o una fecha futura")
    @Column(name = "fechaExpiracion", nullable = false)
    private LocalDate fechaExpiracion;
}
