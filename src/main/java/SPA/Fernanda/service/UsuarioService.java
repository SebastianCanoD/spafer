package SPA.Fernanda.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import SPA.Fernanda.model.Usuario;
import SPA.Fernanda.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

	public List<Usuario> obtenerTodos() {
		return usuarioRepository.findAll();
	}

	public Optional<Usuario> obtenerPorId(Long id) {
		return usuarioRepository.findById(id);
	}

	public List<Usuario> findByRol(Usuario.Rol rol) {
		return usuarioRepository.findByRol(rol);
	}

	public Usuario obtenerPorUsername(String username) {
		return usuarioRepository.findByUsuario(username)
				.orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
	}

	public Optional<Usuario> buscarPorUsuario(String usuario) {
		return usuarioRepository.findByUsuario(usuario);
	}

	public void guardar(Usuario usuario) {
		// Encriptar la contraseña recibida
		String passwordEncriptada = passwordEncoder.encode(usuario.getContrasena());
		usuario.setContrasena(passwordEncriptada);

		// Se puede establecer otros valores por defecto si se requiere
		usuarioRepository.save(usuario);
	}

	public Usuario actualizar(Long id, Usuario usuarioActualizado) {
	    return usuarioRepository.findById(id).map(usuario -> {
	        usuario.setNombre(usuarioActualizado.getNombre());
	        usuario.setCorreo(usuarioActualizado.getCorreo());
	        usuario.setTelefono(usuarioActualizado.getTelefono());
	        usuario.setRol(usuarioActualizado.getRol());
	        
	        // Solo actualizamos la contraseña si se ingresa un nuevo valor
	        if (usuarioActualizado.getContrasena() != null && 
	            !usuarioActualizado.getContrasena().trim().isEmpty()) {
	            // Encriptamos la nueva contraseña antes de guardarla
	            usuario.setContrasena(passwordEncoder.encode(usuarioActualizado.getContrasena()));
	        }
	        return usuarioRepository.save(usuario);
	    }).orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
	}


	public void eliminar(Long id) {
		usuarioRepository.deleteById(id);
	}

	public List<Usuario> obtenerEmpleadosDisponibles() {
		return usuarioRepository.findByRol(Usuario.Rol.Empleado);
	}

	public static Usuario findById(Long clienteId) {

		return null;
	}

}