package com.bookpoint.resena.service;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bookpoint.resena.model.Resena;
import com.bookpoint.resena.repository.ResenaRepository;
@Service
public class ResenaService {
    @Autowired private ResenaRepository resenaRepository;

    public Resena guardarResena(Resena resena) { return resenaRepository.save(resena); }
    public List<Resena> listarResenas() { return resenaRepository.findAll(); }
    public Optional<Resena> obtenerResenaPorId(Long id) { return resenaRepository.findById(id); }
    public Resena actualizarResena(Long id, Resena resena) {
        Resena existente = resenaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("No existe resena con id: " + id));
        existente.setUsuarioId(resena.getUsuarioId());
        existente.setProductoId(resena.getProductoId());
        existente.setComentario(resena.getComentario());
        existente.setCalificacion(resena.getCalificacion());
        existente.setFechaResena(resena.getFechaResena());
        return resenaRepository.save(existente);
    }
    public void eliminarResena(Long id) { resenaRepository.deleteById(id); }
}
