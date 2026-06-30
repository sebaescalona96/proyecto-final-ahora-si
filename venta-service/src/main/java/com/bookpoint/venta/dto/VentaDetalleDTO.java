package com.bookpoint.venta.dto;
import lombok.*;
import com.bookpoint.venta.model.VentaDetalle;
@Data @NoArgsConstructor @AllArgsConstructor
public class VentaDetalleDTO {
    private Long id;
    private Long productoId;
    private Integer cantidad;
    private Double precioUnitario;
    private Double subtotal;

    public static VentaDetalleDTO fromEntity(VentaDetalle d) {
        return new VentaDetalleDTO(d.getId(), d.getProductoId(), d.getCantidad(), d.getPrecioUnitario(), d.getSubtotal());
    }
    public VentaDetalle toEntity() {
        VentaDetalle d = new VentaDetalle();
        d.setProductoId(productoId);
        d.setCantidad(cantidad);
        d.setPrecioUnitario(precioUnitario);
        d.setSubtotal(subtotal != null ? subtotal : (precioUnitario != null && cantidad != null ? precioUnitario * cantidad : 0.0));
        return d;
    }
}
