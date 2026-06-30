package com.bookpoint.venta.dto;
import lombok.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import com.bookpoint.venta.model.Venta;
import com.bookpoint.venta.model.VentaDetalle;
@Data @NoArgsConstructor @AllArgsConstructor
public class VentaDTO {
    private Long id;
    private Long usuarioId;
    private Long sucursalId;
    private LocalDate fechaVenta;
    private Double total;
    private List<VentaDetalleDTO> detalles;

    public static VentaDTO fromEntity(Venta v) {
        List<VentaDetalleDTO> dtos = v.getDetalles() == null ? new ArrayList<>() :
            v.getDetalles().stream().map(VentaDetalleDTO::fromEntity).collect(Collectors.toList());
        return new VentaDTO(v.getId(), v.getUsuarioId(), v.getSucursalId(), v.getFechaVenta(), v.getTotal(), dtos);
    }
    public Venta toEntity() {
        Venta v = new Venta();
        v.setUsuarioId(usuarioId);
        v.setSucursalId(sucursalId);
        v.setFechaVenta(fechaVenta);
        v.setTotal(total);
        if (detalles != null) {
            List<VentaDetalle> lista = detalles.stream().map(VentaDetalleDTO::toEntity).collect(Collectors.toList());
            lista.forEach(d -> d.setVenta(v));
            v.setDetalles(lista);
        }
        return v;
    }
}
