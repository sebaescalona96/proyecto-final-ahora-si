package com.bookpoint.despacho.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;
import java.time.LocalDate;

@Data @NoArgsConstructor @AllArgsConstructor
@Entity @Table(name = "despachos")
public class Despacho {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El ID del pedido es obligatorio")
    @Positive(message = "El ID del pedido debe ser un número positivo")
    @Column(name = "pedidoId", nullable = false)
    private Long pedidoId;

    @NotNull(message = "El ID de la sucursal es obligatorio")
    @Positive(message = "El ID de la sucursal debe ser un número positivo")
    @Column(name = "sucursalId", nullable = false)
    private Long sucursalId;

    @NotBlank(message = "El estado del despacho no puede estar vacío")
    // Opcional: Podrías limitar el tamaño si usas estados cortos como 'PENDIENTE', 'EN_CAMINO'
    @Size(max = 30, message = "El estado no puede superar los 30 caracteres") 
    @Column(name = "estado", nullable = false)
    private String estado;

    @NotNull(message = "La fecha de despacho es obligatoria")
    // Nota: Aquí no usamos @Future porque a veces el despacho se registra el mismo día 
    // o necesitas registrar un despacho que ocurrió hoy muy temprano.
    @Column(name = "fechaDespacho", nullable = false)
    private LocalDate fechaDespacho;

    @NotBlank(message = "La dirección de destino no puede estar vacía")
    @Size(min = 10, max = 255, message = "La dirección debe tener entre 10 y 255 caracteres")
    @Column(name = "direccionDestino", nullable = false)
    private String direccionDestino;
}
