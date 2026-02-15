package com.tienda.AliFe.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.tienda.AliFe.model.Producto;
import com.tienda.AliFe.repository.ProductoRepository;

@RestController
@RequestMapping("/api/cliente")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CompraController {

    private final ProductoRepository productoRepository;

    @PostMapping("/comprar/{id}/{cantidad}")
    public String comprar(@PathVariable Long id, @PathVariable int cantidad) {

        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        if (producto.getStock() < cantidad) {
            return "No hay suficiente stock";
        }

        producto.setStock(producto.getStock() - cantidad);
        productoRepository.save(producto);

        return "Compra realizada correctamente";
    }
}

