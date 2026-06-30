package com.bookpoint.devolucion.dto;
import lombok.*;
import java.time.LocalDate;
import com.bookpoint.devolucion.model.Devolucion;
@Data @NoArgsConstructor @AllArgsConstructor
public class DevolucionDTO {
    private Long id;
    private Long usuarioId;
    private Long productoId;
    private String motivo;
    private String estado;
    private LocalDate fechaDevolucion;

    public static DevolucionDTO fromEntity(Devolucion entity) {
        return new DevolucionDTO(
            entity.getId(),
            entity.getUsuarioId(),
            entity.getProductoId(),
            entity.getMotivo(),
            entity.getEstado(),
            entity.getFechaDevolucion()
        );
    }
    public Devolucion toEntity() {
        return new Devolucion(id, usuarioId, productoId, motivo, estado, fechaDevolucion);
    }
}
