package com.bookpoint.inventario.model;
import jakarta.persistence.*;
import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor
@Entity @Table(name = "inventario")
public class Inventario {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "productoId")
    private Long productoId;

    @Column(name = "sucursalId")
    private Long sucursalId;

    @Column(name = "stock")
    private int stock;

    @Column(name = "stockMinimo")
    private int stockMinimo;
}
