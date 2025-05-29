package SPA.Fernanda.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import SPA.Fernanda.model.Usuario;
import SPA.Fernanda.service.UsuarioService;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;

	@GetMapping("/usuario")
	public String listaUsuarios(Model model) {
		model.addAttribute("usuarios", usuarioService.obtenerTodos());
		return "usuarios/usuario";
	}

	@GetMapping("/")
	public List<Usuario> obtenerTodos() {
		return usuarioService.obtenerTodos();
	}

	@GetMapping("/perfil")
	public String perfilUsuario(Model model, Principal principal) {
		if (principal != null) {
			Usuario usuario = usuarioService.obtenerPorUsername(principal.getName());
			model.addAttribute("usuario", usuario);
		}
		return "usuarios/perfil";
	}

	@GetMapping("/editar/{id}")
	public String mostrarFormularioEditar(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
		Optional<Usuario> usuarioOpt = usuarioService.obtenerPorId(id);
		if (usuarioOpt.isPresent()) {
			model.addAttribute("usuarioForm", usuarioOpt.get());
			return "usuarios/editar_usuario";
		} else {
			redirectAttributes.addFlashAttribute("error", "Usuario no encontrado.");
			return "redirect:/usuarios/usuario";
		}
	}

	@PostMapping("/editar/{id}")
	public String actualizarUsuario(@PathVariable Long id, @ModelAttribute("usuarioForm") Usuario usuario,
			RedirectAttributes redirectAttributes) {
		try {
			usuarioService.actualizar(id, usuario);
			redirectAttributes.addFlashAttribute("exito", "Usuario actualizado correctamente.");
		} catch (RuntimeException e) {
			redirectAttributes.addFlashAttribute("error", "Error al actualizar el usuario: " + e.getMessage());
			return "redirect:/usuarios/editar/" + id;
		}
		return "redirect:/usuarios/usuario";
	}

	@PostMapping("/eliminar/{id}")
	public String eliminarUsuario(@PathVariable Long id, RedirectAttributes redirectAttributes) {
		try {
			usuarioService.eliminar(id);
			redirectAttributes.addFlashAttribute("exito", "Usuario eliminado exitosamente.");
		} catch (RuntimeException e) {
			redirectAttributes.addFlashAttribute("error", "Error al eliminar el usuario: " + e.getMessage());
		}
		return "redirect:/usuarios/usuario	";
	}

	@GetMapping("/buscar/{usuario}")
	public ResponseEntity<Usuario> buscarPorUsuario(@PathVariable String usuario) {
		Optional<Usuario> resultado = usuarioService.buscarPorUsuario(usuario);
		return resultado.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@GetMapping("/registro")
	public String mostrarFormularioRegistro(Model model) {
		model.addAttribute("nuevoUsuario", new Usuario());
		return "usuarios/registro"; // O la ruta de tu template correspondiente.
	}

	@PostMapping("/registro")
	public String procesarFormularioRegistro(@ModelAttribute("nuevoUsuario") Usuario usuario,
			RedirectAttributes redirectAttributes) {
		usuario.setRol(Usuario.Rol.Cliente);

		if (usuarioService.buscarPorUsuario(usuario.getUsuario()).isPresent()) {
			redirectAttributes.addFlashAttribute("error", "El nombre de usuario ya está en uso.");
			return "redirect:/usuarios/registro";
		}

		usuarioService.guardar(usuario);
		redirectAttributes.addFlashAttribute("exito", "¡Usuario registrado exitosamente!");
		return "redirect:/login";
	}

}
