package com.bookpoint.transferencia.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import java.time.LocalDate;

@Data @NoArgsConstructor @AllArgsConstructor
@Entity @Table(name = "transferencias")
public class Transferencia {
      @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // No nulo y no puede ser negativo (0 o mayor)
    @NotNull(message = "El ID del producto es obligatorio")
    @PositiveOrZero(message = "El ID del producto no puede ser negativo")
    @Column(name = "productoId")
    private Long productoId;

    @NotNull(message = "La sucursal de origen es obligatoria")
    @PositiveOrZero(message = "El ID de sucursal de origen no puede ser negativo")
    @Column(name = "sucursalOrigenId")
    private Long sucursalOrigenId;

    @NotNull(message = "La sucursal de destino es obligatoria")
    @PositiveOrZero(message = "El ID de sucursal de destino no puede ser negativo")
    @Column(name = "sucursalDestinoId")
    private Long sucursalDestinoId;

    // Tipo primitivo 'int' no puede ser null, pero validamos que sea 0 o mayor
    @Min(value = 0, message = "La cantidad no puede ser negativa")
    @Column(name = "cantidad")
    private int cantidad;

    // @NotBlank valida que no sea nulo, no esté vacío y no tenga solo espacios en blanco

    @NotBlank(message = "El estado no puede estar vacío ni en blanco")
    @Column(name = "estado")
    private String estado;

    @NotNull(message = "La fecha de transferencia es obligatoria")
    @Column(name = "fechaTransferencia")
    private LocalDate fechaTransferencia;
}
