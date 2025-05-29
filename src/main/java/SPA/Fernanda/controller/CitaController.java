package SPA.Fernanda.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import SPA.Fernanda.model.Cita;
import SPA.Fernanda.model.Servicio;
import SPA.Fernanda.model.Usuario;
import SPA.Fernanda.service.CitaService;
import SPA.Fernanda.service.ServicioService;
import SPA.Fernanda.service.UsuarioService;

@Controller
@RequestMapping("/citas")
public class CitaController {

	@Autowired
	private CitaService citaService;

	@Autowired
	private ServicioService servicioService;

	@Autowired
	private UsuarioService usuarioService;

	@GetMapping("/agendar/{servicioId}")
	public String mostrarFormularioAgendar(@PathVariable Long servicioId, Model model) {
		Optional<Servicio> optionalServicio = servicioService.obtenerPorId(servicioId);
		Servicio servicioSeleccionado = optionalServicio.orElse(null);

		model.addAttribute("servicios", servicioService.listarServiciosActivos());
		model.addAttribute("empleados", usuarioService.obtenerEmpleadosDisponibles());
		model.addAttribute("servicioSeleccionado", servicioSeleccionado);

		return "citas/agendar_cita";
	}

	@PostMapping("/agendar")
	public String guardarCita(@RequestParam Long servicioId, @RequestParam Long empleadoId,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaCita,
			@RequestParam(required = false) String notas, RedirectAttributes redirectAttributes, Principal principal) {
		Usuario cliente = usuarioService.obtenerPorUsername(principal.getName());
		citaService.agendarCita(servicioId, empleadoId, cliente.getId(), fechaCita, notas);
		redirectAttributes.addFlashAttribute("exito", "¡Cita agendada correctamente!");
		return "redirect:/citas/historial";
	}

	@GetMapping("/historial")
	public String verHistorial(Model model, Principal principal) {
		String username = principal.getName();
		Usuario usuario = usuarioService.obtenerPorUsername(username);

		List<Cita> citas;
		if (usuario.getRol() == Usuario.Rol.Cliente) {
			citas = citaService.obtenerCitasPorCliente(usuario.getId());
		} else if (usuario.getRol() == Usuario.Rol.Empleado) {
			citas = citaService.obtenerCitasPorEmpleado(usuario.getId());
		} else {
			citas = citaService.obtenerTodas();
		}

		model.addAttribute("citas", citas);
		model.addAttribute("principal", principal);
		return "citas/historial_citas";
	}

	@PostMapping("/cancelar/{id}")
	public String cancelarCita(@PathVariable Long id, RedirectAttributes redirect) {
		citaService.cancelarCita(id);
		redirect.addFlashAttribute("exito", "Cita cancelada correctamente");
		return "redirect:/citas/historial";
	}

	@PostMapping("/eliminar/{id}")
	public String eliminarCita(@PathVariable Long id, RedirectAttributes redirect) {
		citaService.eliminarCita(id);
		redirect.addFlashAttribute("exito", "Cita eliminada del historial");
		return "redirect:/citas/historial";
	}

	@GetMapping("/detalle/{id}")
	public String verDetalleCita(@PathVariable Long id, Model model) {
		Cita cita = citaService.obtenerPorId(id).orElseThrow(() -> new RuntimeException("Cita no encontrada"));
		model.addAttribute("cita", cita);
		return "citas/detalle_cita";
	}

	@GetMapping("/editar/{id}")
	public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
		Cita cita = citaService.obtenerPorId(id).orElseThrow(() -> new RuntimeException("Cita no encontrada"));
		model.addAttribute("cita", cita);
		model.addAttribute("servicios", servicioService.listarServiciosActivos());
		model.addAttribute("empleados", usuarioService.obtenerEmpleadosDisponibles());
		return "citas/editar_cita";
	}

	@PostMapping("/editar/{id}")
	public String editarCita(@PathVariable Long id, @RequestParam Long servicioId, @RequestParam Long empleadoId,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaCita,
			@RequestParam(required = false) String notas, RedirectAttributes redirectAttributes) {
		Cita cita = citaService.obtenerPorId(id).orElseThrow();
		cita.setServicio(servicioService.obtenerPorId(servicioId).orElseThrow());
		cita.setEmpleado(usuarioService.obtenerPorId(empleadoId).orElseThrow());
		cita.setFechaCita(fechaCita);
		cita.setNotas(notas);
		citaService.guardar(cita);

		redirectAttributes.addFlashAttribute("exito", "¡Cita actualizada correctamente!");
		return "redirect:/citas/historial";
	}

}
