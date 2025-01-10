package com.reporte.reportes.Service;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reporte.reportes.Model.productos;
import com.reporte.reportes.Repository.ProductoRepository;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public List<productos> findAllProductos() {
        return productoRepository.findAll();
    }

    public productos findProductoById(Long id) {
        return productoRepository.findById(id).orElse(null);
    }

    public productos saveProducto(productos producto) {
        return productoRepository.save(producto);
    }

    public void deleteProducto(Long id) {
        productoRepository.deleteById(id);
    }

    // reporte utilizando jasper report
    public byte[] generarReporteProductos() throws JRException, FileNotFoundException {

        InputStream reporteStream = getClass().getResourceAsStream("/reportes/productos.jrxml");

        if (reporteStream != null) {
            return null;
        }

        JasperReport jasperReport = JasperCompileManager.compileReport(reporteStream);

        List<productos> productos = productoRepository.findAll();
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(productos);

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, dataSource);

        return JasperExportManager.exportReportToPdf(jasperPrint); 
    }

}
