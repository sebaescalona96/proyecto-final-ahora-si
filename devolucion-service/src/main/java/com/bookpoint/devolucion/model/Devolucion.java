package com.bookpoint.devolucion.model;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Data @NoArgsConstructor @AllArgsConstructor
@Entity @Table(name = "devoluciones")
public class Devolucion {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "usuarioId")
    private Long usuarioId;

    @Column(name = "productoId")
    private Long productoId;

    @Column(name = "motivo")
    private String motivo;

    @Column(name = "estado")
    private String estado;

    @Column(name = "fechaDevolucion")
    private LocalDate fechaDevolucion;
}
