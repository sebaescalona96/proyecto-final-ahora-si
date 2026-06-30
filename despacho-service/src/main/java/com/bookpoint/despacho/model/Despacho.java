package com.bookpoint.despacho.model;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Data @NoArgsConstructor @AllArgsConstructor
@Entity @Table(name = "despachos")
public class Despacho {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "pedidoId")
    private Long pedidoId;

    @Column(name = "sucursalId")
    private Long sucursalId;

    @Column(name = "estado")
    private String estado;

    @Column(name = "fechaDespacho")
    private LocalDate fechaDespacho;

    @Column(name = "direccionDestino")
    private String direccionDestino;
}
