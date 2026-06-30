package com.bookpoint.despacho.service;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bookpoint.despacho.model.Despacho;
import com.bookpoint.despacho.repository.DespachoRepository;
@Service
public class DespachoService {
    @Autowired private DespachoRepository despachoRepository;

    public Despacho guardarDespacho(Despacho despacho) { return despachoRepository.save(despacho); }
    public List<Despacho> listarDespachos() { return despachoRepository.findAll(); }
    public Optional<Despacho> obtenerDespachoPorId(Long id) { return despachoRepository.findById(id); }
    public Despacho actualizarDespacho(Long id, Despacho despacho) {
        Despacho existente = despachoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("No existe despacho con id: " + id));
        existente.setPedidoId(despacho.getPedidoId());
        existente.setSucursalId(despacho.getSucursalId());
        existente.setEstado(despacho.getEstado());
        existente.setFechaDespacho(despacho.getFechaDespacho());
        existente.setDireccionDestino(despacho.getDireccionDestino());
        return despachoRepository.save(existente);
    }
    public void eliminarDespacho(Long id) { despachoRepository.deleteById(id); }
}
