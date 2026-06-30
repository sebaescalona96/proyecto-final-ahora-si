package com.bookpoint.notificacion.model;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Data @NoArgsConstructor @AllArgsConstructor
@Entity @Table(name = "notificaciones")
public class Notificacion {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "usuarioId")
    private Long usuarioId;

    @Column(name = "mensaje")
    private String mensaje;

    @Column(name = "tipo")
    private String tipo;

    @Column(name = "leida")
    private Boolean leida;

    @Column(name = "fechaCreacion")
    private LocalDate fechaCreacion;
}
