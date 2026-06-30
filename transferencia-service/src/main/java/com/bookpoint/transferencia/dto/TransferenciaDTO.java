package com.bookpoint.transferencia.dto;
import lombok.*;
import java.time.LocalDate;
import com.bookpoint.transferencia.model.Transferencia;
@Data @NoArgsConstructor @AllArgsConstructor
public class TransferenciaDTO {
    private Long id;
    private Long productoId;
    private Long sucursalOrigenId;
    private Long sucursalDestinoId;
    private int cantidad;
    private String estado;
    private LocalDate fechaTransferencia;

    public static TransferenciaDTO fromEntity(Transferencia entity) {
        return new TransferenciaDTO(
            entity.getId(),
            entity.getProductoId(),
            entity.getSucursalOrigenId(),
            entity.getSucursalDestinoId(),
            entity.getCantidad(),
            entity.getEstado(),
            entity.getFechaTransferencia()
        );
    }
    public Transferencia toEntity() {
        return new Transferencia(id, productoId, sucursalOrigenId, sucursalDestinoId, cantidad, estado, fechaTransferencia);
    }
}
