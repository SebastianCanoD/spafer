package SPA.Fernanda.repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import SPA.Fernanda.model.Cita;

public interface CitaRepository extends JpaRepository<Cita, Long> {
    List<Cita> findByCliente_Id(Long clienteId);
    List<Cita> findByEmpleado_Id(Long empleadoId);
    Optional<Cita> findById(Long id);
}
