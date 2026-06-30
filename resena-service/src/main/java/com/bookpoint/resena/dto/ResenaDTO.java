package com.bookpoint.resena.dto;
import lombok.*;
import java.time.LocalDate;
import com.bookpoint.resena.model.Resena;
@Data @NoArgsConstructor @AllArgsConstructor
public class ResenaDTO {
    private Long id;
    private Long usuarioId;
    private Long productoId;
    private String comentario;
    private int calificacion;
    private LocalDate fechaResena;

    public static ResenaDTO fromEntity(Resena entity) {
        return new ResenaDTO(
            entity.getId(),
            entity.getUsuarioId(),
            entity.getProductoId(),
            entity.getComentario(),
            entity.getCalificacion(),
            entity.getFechaResena()
        );
    }
    public Resena toEntity() {
        return new Resena(id, usuarioId, productoId, comentario, calificacion, fechaResena);
    }
}
