package com.bookpoint.proveedor.dto;
import lombok.*;
import com.bookpoint.proveedor.model.Proveedor;
@Data @NoArgsConstructor @AllArgsConstructor
public class ProveedorDTO {
    private Long id;
    private String nombre;
    private String email;
    private String telefono;
    private String editorial;
    private Boolean activo;

    public static ProveedorDTO fromEntity(Proveedor entity) {
        return new ProveedorDTO(
            entity.getId(),
            entity.getNombre(),
            entity.getEmail(),
            entity.getTelefono(),
            entity.getEditorial(),
            entity.getActivo()
        );
    }
    public Proveedor toEntity() {
        return new Proveedor(id, nombre, email, telefono, editorial, activo);
    }
}
