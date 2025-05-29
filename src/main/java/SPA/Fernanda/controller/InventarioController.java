package SPA.Fernanda.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import SPA.Fernanda.model.Inventario;
import SPA.Fernanda.service.InventarioService;

@Controller
@RequestMapping("/inventario")
public class InventarioController {

    @Autowired
    private InventarioService inventarioService;

    @GetMapping
    public String verInventario(Model model) {
        model.addAttribute("listaInventario", inventarioService.obtenerTodos());
        return "inventario/inventario"; 
    }

    @ResponseBody
    @GetMapping("/api")
    public List<Inventario> listarInventario() {
        return inventarioService.obtenerTodos();
    }

    @ResponseBody
    @PostMapping("/api")
    public Inventario agregarInsumo(@RequestBody Inventario inventario) {
        return inventarioService.guardar(inventario);
    }

    @ResponseBody
    @PutMapping("/api/{id}")
    public Inventario actualizarInsumo(@PathVariable Long id, @RequestBody Inventario inventario) {
        return inventarioService.actualizar(id, inventario);
    }

    @ResponseBody
    @DeleteMapping("/api/{id}")
    public void eliminarInsumo(@PathVariable Long id) {
        inventarioService.eliminar(id);
    }
}
