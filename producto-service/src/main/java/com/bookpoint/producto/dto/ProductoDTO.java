package com.bookpoint.producto.dto;
import lombok.*;
import com.bookpoint.producto.model.Producto;
@Data @NoArgsConstructor @AllArgsConstructor
public class ProductoDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private Double precio;
    private String categoria;
    private String autor;
    private String editorial;

    public static ProductoDTO fromEntity(Producto entity) {
        return new ProductoDTO(
            entity.getId(),
            entity.getNombre(),
            entity.getDescripcion(),
            entity.getPrecio(),
            entity.getCategoria(),
            entity.getAutor(),
            entity.getEditorial()
        );
    }
    public Producto toEntity() {
        return new Producto(id, nombre, descripcion, precio, categoria, autor, editorial);
    }
}
