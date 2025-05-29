package SPA.Fernanda.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import SPA.Fernanda.model.Venta;

public interface VentaRepository extends JpaRepository<Venta, Long> {
	@Query("SELECT v FROM Venta v")
	List<Venta> obtenerVentasManual();
}

