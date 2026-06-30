package com.bookpoint.transferencia.model;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Data @NoArgsConstructor @AllArgsConstructor
@Entity @Table(name = "transferencias")
public class Transferencia {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "productoId")
    private Long productoId;

    @Column(name = "sucursalOrigenId")
    private Long sucursalOrigenId;

    @Column(name = "sucursalDestinoId")
    private Long sucursalDestinoId;

    @Column(name = "cantidad")
    private int cantidad;

    @Column(name = "estado")
    private String estado;

    @Column(name = "fechaTransferencia")
    private LocalDate fechaTransferencia;
}
