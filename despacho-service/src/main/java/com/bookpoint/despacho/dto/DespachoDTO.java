package com.bookpoint.despacho.dto;
import lombok.*;
import java.time.LocalDate;
import com.bookpoint.despacho.model.Despacho;
@Data @NoArgsConstructor @AllArgsConstructor
public class DespachoDTO {
    private Long id;
    private Long pedidoId;
    private Long sucursalId;
    private String estado;
    private LocalDate fechaDespacho;
    private String direccionDestino;

    public static DespachoDTO fromEntity(Despacho entity) {
        return new DespachoDTO(
            entity.getId(),
            entity.getPedidoId(),
            entity.getSucursalId(),
            entity.getEstado(),
            entity.getFechaDespacho(),
            entity.getDireccionDestino()
        );
    }
    public Despacho toEntity() {
        return new Despacho(id, pedidoId, sucursalId, estado, fechaDespacho, direccionDestino);
    }
}
