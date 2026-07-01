package com.bookpoint.devolucion.service;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bookpoint.devolucion.model.Devolucion;
import com.bookpoint.devolucion.repository.DevolucionRepository;
@Service
public class DevolucionService {
    @Autowired private DevolucionRepository devolucionRepository;

    public Devolucion guardarDevolucion(Devolucion devolucion) { return devolucionRepository.save(devolucion); }
    public List<Devolucion> listarDevoluciones() { return devolucionRepository.findAll(); }
    public Optional<Devolucion> obtenerDevolucionPorId(Long id) { return devolucionRepository.findById(id); }
    public Devolucion actualizarDevolucion(Long id, Devolucion devolucion) {
        Devolucion existente = devolucionRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("No existe devolucion con id: " + id));
        existente.setUsuarioId(devolucion.getUsuarioId());
        existente.setProductoId(devolucion.getProductoId());
        existente.setMotivo(devolucion.getMotivo());
        existente.setEstado(devolucion.getEstado());
        existente.setFechaDevolucion(devolucion.getFechaDevolucion());
        return devolucionRepository.save(existente);
    }
    public void eliminarDevolucion(Long id) { devolucionRepository.deleteById(id); }
}
