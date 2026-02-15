package com.tienda.AliFe.controller;
import org.springframework.http.ResponseEntity;
import java.util.Optional;

import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import java.util.List;
import com.tienda.AliFe.model.Producto;
import com.tienda.AliFe.repository.ProductoRepository;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ProductoController {

    private final ProductoRepository productoRepository;

    @GetMapping
    public List<Producto> listar() {
        return productoRepository.findAll();
    }

    @PostMapping
    public Producto guardar(@RequestBody Producto producto) {
        return productoRepository.save(producto);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        productoRepository.deleteById(id);
    }
    @PutMapping("/{id}")
    public Producto actualizar(@PathVariable Long id, @RequestBody Producto productoActualizado) {

        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        producto.setNombre(productoActualizado.getNombre());
        producto.setDescripcion(productoActualizado.getDescripcion());
        producto.setPrecio(productoActualizado.getPrecio());
        producto.setStock(productoActualizado.getStock());

        return productoRepository.save(producto);
    }
    //verificar el stock
    @PutMapping("/{id}/comprar")
    public ResponseEntity<?> comprarProducto(
            @PathVariable Long id,
            @RequestParam int cantidad) {

        Optional<Producto> optionalProducto = productoRepository.findById(id);

        if (optionalProducto.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Producto producto = optionalProducto.get();

        if (producto.getStock() < cantidad) {
            return ResponseEntity
                    .badRequest()
                    .body("Stock insuficiente");
        }

        producto.setStock(producto.getStock() - cantidad);
        productoRepository.save(producto);

        return ResponseEntity.ok(producto);
    }

}



