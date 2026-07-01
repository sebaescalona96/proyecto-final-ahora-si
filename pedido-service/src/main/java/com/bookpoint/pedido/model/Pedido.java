package com.bookpoint.pedido.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import java.time.LocalDate;

@Data @NoArgsConstructor @AllArgsConstructor
@Entity @Table(name = "pedidos")
public class Pedido {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El ID de usuario es obligatorio")
    @PositiveOrZero(message = "El ID de usuario no puede ser negativo")
    @Column(name = "usuarioId")
    private Long usuarioId;

    @NotNull(message = "El ID del producto es obligatorio")
    @PositiveOrZero(message = "El ID del producto no puede ser negativo")
    @Column(name = "productoId")
    private Long productoId;

    @Min(value = 1, message = "La cantidad debe ser como mínimo 1")
    @Column(name = "cantidad")
    private int cantidad;

    @NotBlank(message = "El estado del pedido no puede estar vacío ni en blanco")
    @Column(name = "estado")
    private String estado;

    @NotNull(message = "La fecha del pedido es obligatoria")
    @Column(name = "fechaPedido")
    private LocalDate fechaPedido;

    @NotBlank(message = "La dirección de entrega no puede estar vacía ni en blanco")
    @Column(name = "direccionEntrega")
    private String direccionEntrega;
}
