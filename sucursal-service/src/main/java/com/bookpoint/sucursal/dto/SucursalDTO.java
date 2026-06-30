package com.bookpoint.sucursal.dto;
import lombok.*;
import com.bookpoint.sucursal.model.Sucursal;
@Data @NoArgsConstructor @AllArgsConstructor
public class SucursalDTO {
    private Long id;
    private String nombre;
    private String direccion;
    private String ciudad;
    private String telefono;
    private String horario;

    public static SucursalDTO fromEntity(Sucursal entity) {
        return new SucursalDTO(
            entity.getId(),
            entity.getNombre(),
            entity.getDireccion(),
            entity.getCiudad(),
            entity.getTelefono(),
            entity.getHorario()
        );
    }
    public Sucursal toEntity() {
        return new Sucursal(id, nombre, direccion, ciudad, telefono, horario);
    }
}
