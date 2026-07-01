package com.bookpoint.notificacion.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;
import java.time.LocalDate;

@Data @NoArgsConstructor @AllArgsConstructor
@Entity @Table(name = "notificaciones")
public class Notificacion {
 
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El ID del usuario es obligatorio")
    @Positive(message = "El ID del usuario debe ser un número positivo")
    @Column(name = "usuarioId", nullable = false)
    private Long usuarioId;

    @NotBlank(message = "El contenido del mensaje no puede estar vacío")
    @Size(min = 5, max = 1000, message = "El mensaje debe tener entre 5 y 1000 caracteres")
    @Column(name = "mensaje", nullable = false, length = 1000) // Mayor longitud para alertas extensas
    private String mensaje;

    @NotBlank(message = "El tipo de notificación es obligatorio")
    @Size(max = 50, message = "El tipo de notificación no puede superar los 50 caracteres")
    // Ejemplo de tipos: 'EMAIL', 'SMS', 'PUSH', 'ALERTA_SISTEMA'
    @Column(name = "tipo", nullable = false)
    private String tipo;

    @NotNull(message = "El estado de lectura (leída/no leída) es obligatorio")
    @Column(name = "leida", nullable = false)
    private Boolean leida;

    @NotNull(message = "La fecha de creación es obligatoria")
    @PastOrPresent(message = "La fecha de creación no puede ser una fecha futura")
    @Column(name = "fechaCreacion", nullable = false)
    private LocalDate fechaCreacion;
}
