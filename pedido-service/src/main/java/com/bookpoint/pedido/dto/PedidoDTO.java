package com.bookpoint.pedido.dto;
import lombok.*;
import java.time.LocalDate;
import com.bookpoint.pedido.model.Pedido;
@Data @NoArgsConstructor @AllArgsConstructor
public class PedidoDTO {
    private Long id;
    private Long usuarioId;
    private Long productoId;
    private int cantidad;
    private String estado;
    private LocalDate fechaPedido;
    private String direccionEntrega;

    public static PedidoDTO fromEntity(Pedido entity) {
        return new PedidoDTO(
            entity.getId(),
            entity.getUsuarioId(),
            entity.getProductoId(),
            entity.getCantidad(),
            entity.getEstado(),
            entity.getFechaPedido(),
            entity.getDireccionEntrega()
        );
    }
    public Pedido toEntity() {
        return new Pedido(id, usuarioId, productoId, cantidad, estado, fechaPedido, direccionEntrega);
    }
}
