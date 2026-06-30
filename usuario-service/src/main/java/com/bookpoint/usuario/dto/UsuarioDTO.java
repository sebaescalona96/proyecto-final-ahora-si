package com.bookpoint.usuario.dto;
import lombok.*;
import com.bookpoint.usuario.model.Usuario;
@Data @NoArgsConstructor @AllArgsConstructor
public class UsuarioDTO {
    private Long id;
    private String nombre;
    private String email;
    private String password;
    private String rol;

    public static UsuarioDTO fromEntity(Usuario entity) {
        return new UsuarioDTO(
            entity.getId(),
            entity.getNombre(),
            entity.getEmail(),
            entity.getPassword(),
            entity.getRol()
        );
    }
    public Usuario toEntity() {
        return new Usuario(id, nombre, email, password, rol);
    }
}
