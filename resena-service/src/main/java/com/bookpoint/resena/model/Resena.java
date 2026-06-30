package com.bookpoint.resena.model;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Data @NoArgsConstructor @AllArgsConstructor
@Entity @Table(name = "resenas")
public class Resena {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "usuarioId")
    private Long usuarioId;

    @Column(name = "productoId")
    private Long productoId;

    @Column(name = "comentario")
    private String comentario;

    @Column(name = "calificacion")
    private int calificacion;

    @Column(name = "fechaResena")
    private LocalDate fechaResena;
}
