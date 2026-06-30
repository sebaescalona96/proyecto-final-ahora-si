package com.bookpoint.notificacion.service;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bookpoint.notificacion.model.Notificacion;
import com.bookpoint.notificacion.repository.NotificacionRepository;
@Service
public class NotificacionService {
    @Autowired private NotificacionRepository notificacionRepository;

    public Notificacion guardarNotificacion(Notificacion notificacion) { return notificacionRepository.save(notificacion); }
    public List<Notificacion> listarNotificacions() { return notificacionRepository.findAll(); }
    public Optional<Notificacion> obtenerNotificacionPorId(Long id) { return notificacionRepository.findById(id); }
    public Notificacion actualizarNotificacion(Long id, Notificacion notificacion) {
        Notificacion existente = notificacionRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("No existe notificacion con id: " + id));
        existente.setUsuarioId(notificacion.getUsuarioId());
        existente.setMensaje(notificacion.getMensaje());
        existente.setTipo(notificacion.getTipo());
        existente.setLeida(notificacion.getLeida());
        existente.setFechaCreacion(notificacion.getFechaCreacion());
        return notificacionRepository.save(existente);
    }
    public void eliminarNotificacion(Long id) { notificacionRepository.deleteById(id); }
}
