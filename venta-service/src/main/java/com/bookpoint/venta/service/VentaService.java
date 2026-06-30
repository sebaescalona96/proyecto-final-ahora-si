package com.bookpoint.venta.service;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.bookpoint.venta.dto.DescontarStockRequestDTO;
import com.bookpoint.venta.model.Venta;
import com.bookpoint.venta.model.VentaDetalle;
import com.bookpoint.venta.repository.VentaRepository;

@Service
public class VentaService {
    @Autowired private VentaRepository ventaRepository;
    @Autowired private RestTemplate restTemplate;

    @Value("${services.inventario-service.url:http://localhost:8085}")
    private String inventarioUrl;

    public Venta guardarVenta(Venta venta) {
        // Calcular subtotal de cada detalle y total de la venta
        double total = 0.0;
        for (VentaDetalle d : venta.getDetalles()) {
            double sub = d.getPrecioUnitario() * d.getCantidad();
            d.setSubtotal(sub);
            d.setVenta(venta);
            total += sub;
        }
        venta.setTotal(total);

        Venta guardada = ventaRepository.save(venta);

        // Descontar stock del inventario para cada producto vendido
        for (VentaDetalle d : guardada.getDetalles()) {
            try {
                DescontarStockRequestDTO req = new DescontarStockRequestDTO(
                    d.getProductoId(), guardada.getSucursalId(), d.getCantidad());
                restTemplate.put(inventarioUrl + "/api/v1/inventario/descontar", req);
            } catch (Exception e) {
                System.err.println("[venta-service] Aviso: no se pudo descontar stock para productoId="
                    + d.getProductoId() + " — " + e.getMessage());
            }
        }
        return guardada;
    }

    public List<Venta> listarVentas() { return ventaRepository.findAll(); }

    public Optional<Venta> obtenerVentaPorId(Long id) { return ventaRepository.findById(id); }

    public Venta actualizarVenta(Long id, Venta venta) {
        Venta existente = ventaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("No existe venta con id: " + id));
        existente.setUsuarioId(venta.getUsuarioId());
        existente.setSucursalId(venta.getSucursalId());
        existente.setFechaVenta(venta.getFechaVenta());
        existente.setTotal(venta.getTotal());
        return ventaRepository.save(existente);
    }

    public void eliminarVenta(Long id) { ventaRepository.deleteById(id); }
}
