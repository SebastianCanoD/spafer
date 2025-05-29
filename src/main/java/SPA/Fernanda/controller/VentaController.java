package SPA.Fernanda.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import SPA.Fernanda.model.Usuario;
import SPA.Fernanda.model.Venta;
import SPA.Fernanda.service.ServicioService;
import SPA.Fernanda.service.UsuarioService;
import SPA.Fernanda.service.VentaService;

@Controller
@RequestMapping("/ventas")
public class VentaController {

    @Autowired
    private VentaService ventaService;
    
    @Autowired
    private UsuarioService usuarioService;
    
    @Autowired
    private ServicioService servicioService;

    @GetMapping("/venta")
    public String mostrarFormularioCrear(Model model) {
        model.addAttribute("venta", new Venta());
        model.addAttribute("clientes", usuarioService.findByRol(Usuario.Rol.Cliente));
        model.addAttribute("servicios", servicioService.findByActivoTrue());
        List<Venta> ventas = ventaService.obtenerTodas();
        System.out.println("Ventas obtenidas para la vista: " + ventas);
        model.addAttribute("ventas", ventas);
        return "ventas/venta";
    }

    @GetMapping("/lista")
    public String listarVentas(Model model) {
        List<Venta> ventas = ventaService.obtenerTodas();
        System.out.println("Ventas obtenidas para la vista: " + ventas);
        model.addAttribute("ventas", ventas);
        return "ventas/venta";
    }
    @PostMapping("/guardar")
    public String guardarVenta(
        @RequestParam Long clienteId,
        @RequestParam List<Long> servicioIds,
        @RequestParam Venta.MetodoPago metodoPago,
        @RequestParam(required = false) String notas,
        Principal principal) {
        
        String username = principal.getName();
        Usuario empleado = usuarioService.obtenerPorUsername(username);
        
        if(empleado == null) {
            throw new RuntimeException("Empleado no encontrado");
        }
        
        Venta venta = new Venta();
        venta.setCliente(usuarioService.obtenerPorId(clienteId)
            .orElseThrow(() -> new RuntimeException("Cliente no encontrado")));
        venta.setEmpleado(empleado);
        venta.setMetodoPago(metodoPago);
        venta.setObservaciones(notas);
  
        ventaService.guardarVentaConDetalles(venta, servicioIds);
        
        return "redirect:/ventas/lista";
    }
    
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Venta venta = ventaService.obtenerPorId(id)
            .orElseThrow(() -> new IllegalArgumentException("Venta no encontrada"));
        model.addAttribute("venta", venta);
        return "ventas/editar_venta";
    }
}
