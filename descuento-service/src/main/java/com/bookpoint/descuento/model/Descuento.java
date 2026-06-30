package com.bookpoint.descuento.model;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Data @NoArgsConstructor @AllArgsConstructor
@Entity @Table(name = "descuentos")
public class Descuento {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo")
    private String codigo;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "porcentaje")
    private Double porcentaje;

    @Column(name = "tipo")
    private String tipo;

    @Column(name = "activo")
    private Boolean activo;

    @Column(name = "fechaExpiracion")
    private LocalDate fechaExpiracion;
}
