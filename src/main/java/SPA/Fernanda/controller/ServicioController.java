	package SPA.Fernanda.controller;
	
	import java.util.List;
	import java.util.Optional;
	
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.stereotype.Controller;  
	import org.springframework.ui.Model;
	import org.springframework.web.bind.annotation.*;
	import org.springframework.web.servlet.mvc.support.RedirectAttributes;
	
	import SPA.Fernanda.model.Servicio;
	import SPA.Fernanda.service.ServicioService;
	
	@Controller
	@RequestMapping("/servicios")
	public class ServicioController {
	
	    @Autowired
	    private ServicioService servicioService;
	    
	    // Mostrar listado de servicios
	    @GetMapping("/disponibles")
	    public String listarServicios(Model model) {
	        List<Servicio> servicios = servicioService.obtenerTodos();
	        model.addAttribute("servicios", servicios);
	        return "servicios/servicios_disponibles";  // Template: src/main/resources/templates/servicios/servicios_interfaz.html
	    }
	    
	    // Mostrar formulario para agregar un servicio
	    @GetMapping("/agregar")
	    public String mostrarFormularioAgregar(Model model) {
	        model.addAttribute("servicioForm", new Servicio());
	        return "servicios/form"; // Template: agregar_servicio.html
	    }
	    
	    // Procesar el formulario de agregar servicio
	    @PostMapping("/agregar")
	    public String agregarServicio(@ModelAttribute("servicioForm") Servicio servicio, RedirectAttributes redirectAttributes) {
	        try {
	            servicioService.guardar(servicio);
	            redirectAttributes.addFlashAttribute("exito", "Servicio agregado correctamente.");
	        } catch (Exception e) {
	            redirectAttributes.addFlashAttribute("error", "Error al agregar servicio: " + e.getMessage());
	        }
	        return "redirect:/servicios/disponibles";
	    }
	    
	    // Mostrar formulario para editar un servicio
	    @GetMapping("/editar/{id}")
	    public String mostrarFormularioEditar(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
	        Optional<Servicio> servicioOpt = servicioService.obtenerPorId(id);
	        if (servicioOpt.isPresent()) {
	            model.addAttribute("servicioForm", servicioOpt.get());
	            return "servicios/form"; 
	        } else {
	            redirectAttributes.addFlashAttribute("error", "Servicio no encontrado.");
	            return "redirect:/servicios/disponibles";
	        }
	    }
	    
	    // Procesar el formulario de edición
	    @PostMapping("/editar/{id}")
	    public String actualizarServicio(@PathVariable Long id, @ModelAttribute("servicioForm") Servicio servicio, RedirectAttributes redirectAttributes) {
	        try {
	            servicioService.actualizar(id, servicio);
	            redirectAttributes.addFlashAttribute("exito", "Servicio actualizado correctamente.");
	        } catch (Exception e) {
	            redirectAttributes.addFlashAttribute("error", "Error al actualizar el servicio: " + e.getMessage());
	            return "redirect:/interfaz/servicios/editar/" + id;
	        }
	        return "redirect:/servicios/disponibles";
	    }
	    
	    // Eliminar servicio
	    @PostMapping("/eliminar/{id}")
	    public String eliminarServicio(@PathVariable Long id, RedirectAttributes redirectAttributes) {
	        try {
	            servicioService.eliminar(id);
	            redirectAttributes.addFlashAttribute("exito", "Servicio eliminado correctamente.");
	        } catch (Exception e) {
	            redirectAttributes.addFlashAttribute("error", "Error al eliminar el servicio: " + e.getMessage());
	        }
	        return "redirect:/servicios/disponibles";
	    }
	}
