package com.bookpoint.notificacion.dto;
import lombok.*;
import java.time.LocalDate;
import com.bookpoint.notificacion.model.Notificacion;
@Data @NoArgsConstructor @AllArgsConstructor
public class NotificacionDTO {
    private Long id;
    private Long usuarioId;
    private String mensaje;
    private String tipo;
    private Boolean leida;
    private LocalDate fechaCreacion;

    public static NotificacionDTO fromEntity(Notificacion entity) {
        return new NotificacionDTO(
            entity.getId(),
            entity.getUsuarioId(),
            entity.getMensaje(),
            entity.getTipo(),
            entity.getLeida(),
            entity.getFechaCreacion()
        );
    }
    public Notificacion toEntity() {
        return new Notificacion(id, usuarioId, mensaje, tipo, leida, fechaCreacion);
    }
}
