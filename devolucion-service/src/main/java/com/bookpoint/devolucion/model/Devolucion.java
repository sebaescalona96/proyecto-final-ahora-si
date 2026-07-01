package com.bookpoint.devolucion.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;
import java.time.LocalDate;

@Data @NoArgsConstructor @AllArgsConstructor
@Entity @Table(name = "devoluciones")
public class Devolucion {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El ID del usuario es obligatorio")
    @Positive(message = "El ID del usuario debe ser un número positivo")
    @Column(name = "usuarioId", nullable = false)
    private Long usuarioId;

    @NotNull(message = "El ID del producto es obligatorio")
    @Positive(message = "El ID del producto debe ser un número positivo")
    @Column(name = "productoId", nullable = false)
    private Long productoId;

    @NotBlank(message = "El motivo de la devolución es obligatorio")
    @Size(min = 10, max = 500, message = "El motivo debe tener entre 10 y 500 caracteres")
    @Column(name = "motivo", nullable = false, length = 500) // Ajustamos el tamaño en BD también
    private String motivo;

    @NotBlank(message = "El estado de la devolución no puede estar vacío")
    @Size(max = 30, message = "El estado no puede superar los 30 caracteres")
    @Column(name = "estado", nullable = false)
    private String estado;

    @NotNull(message = "La fecha de devolución es obligatoria")
    @PastOrPresent(message = "La fecha de devolución no puede ser una fecha futura")
    @Column(name = "fechaDevolucion", nullable = false)
    private LocalDate fechaDevolucion;
}
