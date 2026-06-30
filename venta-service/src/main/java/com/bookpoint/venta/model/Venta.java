package com.bookpoint.venta.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Entity @Table(name = "ventas")
public class Venta {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El ID del usuario es obligatorio")
    @Column(name = "usuario_id", nullable = false)
    private Long usuarioId;

    @NotNull(message = "El ID de la sucursal es obligatorio")
    @Column(name = "sucursal_id", nullable = false)
    private Long sucursalId;

    @NotNull(message = "La fecha de venta es obligatoria")
    @PastOrPresent(message = "La fecha de venta no puede ser en el futuro")
    @Column(name = "fecha_venta", nullable = false)
    private LocalDate fechaVenta;

    @NotNull(message = "El total de la venta es obligatorio")
    @PositiveOrZero(message = "El total debe ser mayor o igual a cero")
    @Column(name = "total", nullable = false)
    private Double total;

    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JsonManagedReference
    private List<VentaDetalle> detalles = new ArrayList<>();
}
