package com.bookpoint.pedido.service;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bookpoint.pedido.model.Pedido;
import com.bookpoint.pedido.repository.PedidoRepository;
@Service
public class PedidoService {
    @Autowired private PedidoRepository pedidoRepository;

    public Pedido guardarPedido(Pedido pedido) { return pedidoRepository.save(pedido); }
    public List<Pedido> listarPedidos() { return pedidoRepository.findAll(); }
    public Optional<Pedido> obtenerPedidoPorId(Long id) { return pedidoRepository.findById(id); }
    public Pedido actualizarPedido(Long id, Pedido pedido) {
        Pedido existente = pedidoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("No existe pedido con id: " + id));
        existente.setUsuarioId(pedido.getUsuarioId());
        existente.setProductoId(pedido.getProductoId());
        existente.setCantidad(pedido.getCantidad());
        existente.setEstado(pedido.getEstado());
        existente.setFechaPedido(pedido.getFechaPedido());
        existente.setDireccionEntrega(pedido.getDireccionEntrega());
        return pedidoRepository.save(existente);
    }
    public void eliminarPedido(Long id) { pedidoRepository.deleteById(id); }
}
