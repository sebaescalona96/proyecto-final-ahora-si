package com.bookpoint.inventario.service;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bookpoint.inventario.model.Inventario;
import com.bookpoint.inventario.repository.InventarioRepository;
@Service
public class InventarioService {
    @Autowired private InventarioRepository inventarioRepository;

    public Inventario guardarInventario(Inventario inventario) { return inventarioRepository.save(inventario); }
    public List<Inventario> listarInventarios() { return inventarioRepository.findAll(); }
    public Optional<Inventario> obtenerInventarioPorId(Long id) { return inventarioRepository.findById(id); }
    public Inventario actualizarInventario(Long id, Inventario inventario) {
        Inventario existente = inventarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("No existe inventario con id: " + id));
        existente.setProductoId(inventario.getProductoId());
        existente.setSucursalId(inventario.getSucursalId());
        existente.setStock(inventario.getStock());
        existente.setStockMinimo(inventario.getStockMinimo());
        return inventarioRepository.save(existente);
    }
    public void eliminarInventario(Long id) { inventarioRepository.deleteById(id); }

    /** Descuenta stock al vender. Llamado desde venta-service. */
    public void descontarStock(Long productoId, Long sucursalId, Integer cantidad) {
        Inventario inv = inventarioRepository.findByProductoIdAndSucursalId(productoId, sucursalId)
            .orElseThrow(() -> new RuntimeException(
                "No hay inventario para productoId=" + productoId + " sucursalId=" + sucursalId));
        if (inv.getStock() < cantidad) {
            throw new RuntimeException("Stock insuficiente. Disponible: " + inv.getStock() + ", requerido: " + cantidad);
        }
        inv.setStock(inv.getStock() - cantidad);
        inventarioRepository.save(inv);
    }
}
