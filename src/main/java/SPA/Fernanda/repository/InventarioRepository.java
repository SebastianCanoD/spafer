package SPA.Fernanda.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import SPA.Fernanda.model.Inventario;

public interface InventarioRepository extends JpaRepository<Inventario, Long> {
}
