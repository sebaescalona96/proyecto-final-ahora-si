package com.bookpoint.descuento.dto;
import lombok.*;
import java.time.LocalDate;
import com.bookpoint.descuento.model.Descuento;
@Data @NoArgsConstructor @AllArgsConstructor
public class DescuentoDTO {
    private Long id;
    private String codigo;
    private String descripcion;
    private Double porcentaje;
    private String tipo;
    private Boolean activo;
    private LocalDate fechaExpiracion;

    public static DescuentoDTO fromEntity(Descuento entity) {
        return new DescuentoDTO(
            entity.getId(),
            entity.getCodigo(),
            entity.getDescripcion(),
            entity.getPorcentaje(),
            entity.getTipo(),
            entity.getActivo(),
            entity.getFechaExpiracion()
        );
    }
    public Descuento toEntity() {
        return new Descuento(id, codigo, descripcion, porcentaje, tipo, activo, fechaExpiracion);
    }
}
