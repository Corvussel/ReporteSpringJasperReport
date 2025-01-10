package com.reporte.reportes.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import com.reporte.reportes.Model.productos;
import com.reporte.reportes.Service.ProductoService;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping("/")
    public String index(Model model) {

        model.addAttribute("producto", new productos());
        model.addAttribute("productos", productoService.findAllProductos());
        return "index";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model) {
        model.addAttribute("producto", productoService.findProductoById(id));
        return "Producto";
    }

    // este metodo se llama cuando se envia el formulario
    @PostMapping("/save")
    public String save(@ModelAttribute productos producto) {
        productoService.saveProducto(producto);
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        productoService.deleteProducto(id);
        return "redirect:/";
    }

 @GetMapping("/reporte/pdf")
 public ResponseEntity<byte[]> generarReporteProductos(){
     try {

        byte[] reporte = productoService.generarReporteProductos();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "reporte.pdf");

        return ResponseEntity.ok().headers(headers).body(reporte);

     } catch (Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(500).build();
     }
 }
}
