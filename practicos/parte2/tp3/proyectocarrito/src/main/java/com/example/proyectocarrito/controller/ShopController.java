package com.example.proyectocarrito.controller;

import com.example.proyectocarrito.domain.Carrito;
import com.example.proyectocarrito.repository.ArticuloRepo;
import com.example.proyectocarrito.repository.CarritoRepo;
import com.example.proyectocarrito.service.CarritoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ShopController {

    private final ArticuloRepo articuloRepo;
    private final CarritoRepo carritoRepo;
    private final CarritoService carritoService;

    public ShopController(ArticuloRepo articuloRepo, CarritoRepo carritoRepo, CarritoService carritoService){
        this.articuloRepo = articuloRepo;
        this.carritoRepo = carritoRepo;
        this.carritoService = carritoService;
    }

    @ModelAttribute("carritoId")
    public String carritoId(HttpSession session) {
        String carritoId = (String) session.getAttribute("carritoId");
        if (carritoId != null && carritoRepo.existsById(carritoId)) return carritoId;
        String nuevo = carritoRepo.saveAndFlush(new Carrito()).getId();
        session.setAttribute("carritoId", nuevo);
        return nuevo;
    }

    @GetMapping("/")
    public String home(@ModelAttribute("carritoId") String carritoId, Model model){
        model.addAttribute("articulos", articuloRepo.findAll());
        model.addAttribute("carritoTotal",
                carritoRepo.findById(carritoId).map(Carrito::getTotal).orElse(0d));
        return "index";
    }

    @PostMapping("/carrito/agregar")
    public String agregar(@ModelAttribute("carritoId") String carritoId,
                          @RequestParam String articuloId,
                          @RequestParam(defaultValue = "1") int cantidad){
        carritoService.agregarArticulo(carritoId, articuloId, cantidad);
        return "redirect:/";
    }

    @GetMapping("/carrito")
    public String verCarrito(@ModelAttribute("carritoId") String carritoId, Model model){
        Carrito c = carritoRepo.findById(carritoId).orElseThrow();
        model.addAttribute("carrito", c);
        model.addAttribute("carritoTotal", c.getTotal());
        return "carrito";
    }

    @PostMapping("/carrito/eliminar")
    public String eliminar(@ModelAttribute("carritoId") String carritoId,
                           @RequestParam String detalleId){
        carritoService.eliminarDetalle(carritoId, detalleId);
        return "redirect:/carrito";
    }

    @PostMapping("/checkout")
    public String doCheckout(@ModelAttribute("carritoId") String carritoId,
                             @RequestParam(defaultValue = "mercadopago") String metodoPago,
                             Model model, HttpSession session){
        CarritoService.CheckoutResult resultado = carritoService.finalizarCompra(carritoId, metodoPago);
        model.addAttribute("total", resultado.total());
        // limpiar carrito de la sesión para próxima visita
        session.removeAttribute("carritoId");
        return "checkout_ok";
    }
}
