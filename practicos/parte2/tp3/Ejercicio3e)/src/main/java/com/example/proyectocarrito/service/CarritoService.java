package com.example.proyectocarrito.service;

import com.example.proyectocarrito.domain.Articulo;
import com.example.proyectocarrito.domain.Carrito;
import com.example.proyectocarrito.domain.Detalle;
import com.example.proyectocarrito.repository.ArticuloRepo;
import com.example.proyectocarrito.repository.CarritoRepo;
import com.example.proyectocarrito.service.template.PagoTemplate;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class CarritoService {

    private final CarritoRepo carritoRepository;
    private final ArticuloRepo articuloRepository;
    private final PagoTemplate pagoEfectivo;
    private final PagoTemplate pagoMercadoPago;

    public CarritoService(CarritoRepo carritoRepository,
                          ArticuloRepo articuloRepository,
                          @Qualifier("pagoEfectivo") PagoTemplate pagoEfectivo,
                          @Qualifier("pagoMercadoPago") PagoTemplate pagoMercadoPago) {
        this.carritoRepository = carritoRepository;
        this.articuloRepository = articuloRepository;
        this.pagoEfectivo = pagoEfectivo;
        this.pagoMercadoPago = pagoMercadoPago;
    }

    @Transactional
    public Carrito agregarArticulo(String carritoId, String articuloId, int cantidad) {
        Carrito carrito = carritoRepository.findById(carritoId)
                .orElseThrow(() -> new IllegalArgumentException("Carrito no encontrado"));
        Articulo articulo = articuloRepository.findById(articuloId)
                .orElseThrow(() -> new IllegalArgumentException("Artículo no encontrado"));

        Detalle detalle = new Detalle();
        detalle.setArticulo(articulo);
        detalle.setCantidad(cantidad);
        detalle.setPrecioUnitario(articulo.getPrecio());
        detalle.setSubtotal(detalle.getPrecioUnitario() * cantidad);
        detalle.setCarrito(carrito);

        carrito.getDetalles().add(detalle);
        recalcularTotales(carrito);

        return carritoRepository.save(carrito);
    }

    @Transactional
    public Carrito eliminarDetalle(String carritoId, String detalleId) {
        Carrito carrito = carritoRepository.findById(carritoId)
                .orElseThrow(() -> new IllegalArgumentException("Carrito no encontrado"));

        boolean removed = carrito.getDetalles().removeIf(detalle -> detalle.getId().equals(detalleId));
        if (!removed) {
            throw new IllegalArgumentException("Detalle no encontrado en el carrito");
        }

        recalcularTotales(carrito);
        return carritoRepository.save(carrito);
    }

    @Transactional
    public CheckoutResult finalizarCompra(String carritoId) {
        return finalizarCompra(carritoId, "mercadopago");
    }

    @Transactional
    public CheckoutResult finalizarCompra(String carritoId, String metodoPago) {
        Carrito carrito = carritoRepository.findById(carritoId)
                .orElseThrow(() -> new IllegalArgumentException("Carrito no encontrado"));

        recalcularTotales(carrito);
        seleccionarPago(metodoPago).procesarPago(carrito);
        carritoRepository.save(carrito);

        return new CheckoutResult(carrito.getId(), carrito.getTotal());
    }

    private void recalcularTotales(Carrito carrito) {
        double total = carrito.getDetalles().stream()
                .mapToDouble(detalle -> {
                    detalle.setPrecioUnitario(detalle.getArticulo().getPrecio());
                    detalle.setSubtotal(detalle.getPrecioUnitario() * detalle.getCantidad());
                    return detalle.getSubtotal();
                })
                .sum();
        carrito.setTotal(total);
    }

    private PagoTemplate seleccionarPago(String metodoPago) {
        if (metodoPago == null || metodoPago.isBlank() || "mercadopago".equalsIgnoreCase(metodoPago)) {
            return pagoMercadoPago;
        }
        if ("efectivo".equalsIgnoreCase(metodoPago)) {
            return pagoEfectivo;
        }
        throw new IllegalArgumentException("Método de pago no soportado: " + metodoPago);
    }

    public record CheckoutResult(String carritoId, double total) {}
}
