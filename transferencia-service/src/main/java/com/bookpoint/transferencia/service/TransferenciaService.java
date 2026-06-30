package com.bookpoint.transferencia.service;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bookpoint.transferencia.model.Transferencia;
import com.bookpoint.transferencia.repository.TransferenciaRepository;
@Service
public class TransferenciaService {
    @Autowired private TransferenciaRepository transferenciaRepository;

    public Transferencia guardarTransferencia(Transferencia transferencia) { return transferenciaRepository.save(transferencia); }
    public List<Transferencia> listarTransferencias() { return transferenciaRepository.findAll(); }
    public Optional<Transferencia> obtenerTransferenciaPorId(Long id) { return transferenciaRepository.findById(id); }
    public Transferencia actualizarTransferencia(Long id, Transferencia transferencia) {
        Transferencia existente = transferenciaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("No existe transferencia con id: " + id));
        existente.setProductoId(transferencia.getProductoId());
        existente.setSucursalOrigenId(transferencia.getSucursalOrigenId());
        existente.setSucursalDestinoId(transferencia.getSucursalDestinoId());
        existente.setCantidad(transferencia.getCantidad());
        existente.setEstado(transferencia.getEstado());
        existente.setFechaTransferencia(transferencia.getFechaTransferencia());
        return transferenciaRepository.save(existente);
    }
    public void eliminarTransferencia(Long id) { transferenciaRepository.deleteById(id); }
}
