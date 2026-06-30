package com.bookpoint.inventario.dto;
import lombok.*;
import com.bookpoint.inventario.model.Inventario;
@Data @NoArgsConstructor @AllArgsConstructor
public class InventarioDTO {
    private Long id;
    private Long productoId;
    private Long sucursalId;
    private int stock;
    private int stockMinimo;

    public static InventarioDTO fromEntity(Inventario entity) {
        return new InventarioDTO(
            entity.getId(),
            entity.getProductoId(),
            entity.getSucursalId(),
            entity.getStock(),
            entity.getStockMinimo()
        );
    }
    public Inventario toEntity() {
        return new Inventario(id, productoId, sucursalId, stock, stockMinimo);
    }
}
