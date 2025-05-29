package SPA.Fernanda.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import SPA.Fernanda.model.Servicio;

public interface ServicioRepository extends JpaRepository<Servicio, Long> {
	List<Servicio> findByActivoTrue();
	
}

