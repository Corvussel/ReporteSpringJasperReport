package com.reporte.reportes.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.reporte.reportes.Model.productos;

public interface ProductoRepository extends JpaRepository<productos, Long> {

}
