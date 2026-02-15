package com.tienda.AliFe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.tienda.AliFe.model.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
}
