package com.bookpoint.pedido.model;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Data @NoArgsConstructor @AllArgsConstructor
@Entity @Table(name = "pedidos")
public class Pedido {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "usuarioId")
    private Long usuarioId;

    @Column(name = "productoId")
    private Long productoId;

    @Column(name = "cantidad")
    private int cantidad;

    @Column(name = "estado")
    private String estado;

    @Column(name = "fechaPedido")
    private LocalDate fechaPedido;

    @Column(name = "direccionEntrega")
    private String direccionEntrega;
}
