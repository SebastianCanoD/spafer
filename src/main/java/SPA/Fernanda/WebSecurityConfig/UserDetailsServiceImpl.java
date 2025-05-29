package SPA.Fernanda.WebSecurityConfig;

import SPA.Fernanda.model.Usuario;
import SPA.Fernanda.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Collections;

@Configuration
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	Usuario usuario = usuarioRepository.findByUsuario(username)
    	        .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));


    	return new User(
    		    usuario.getUsuario(),
    		    usuario.getContrasena(),
    		    Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + usuario.getRol().name()))
    		);
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
