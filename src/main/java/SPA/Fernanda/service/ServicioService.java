package SPA.Fernanda.service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import SPA.Fernanda.model.Servicio;
import SPA.Fernanda.repository.ServicioRepository;

@Service
public class ServicioService {

    @Autowired
    private ServicioRepository servicioRepository;

    public List<Servicio> obtenerTodos() {
        return servicioRepository.findAll();
    }

    public Optional<Servicio> obtenerPorId(Long id) {
        return servicioRepository.findById(id);
    }

    public Servicio guardar(Servicio servicio) {
        return servicioRepository.save(servicio);
    }

    public Servicio actualizar(Long id, Servicio servicio) {
        if (!servicioRepository.existsById(id)) {
            throw new RuntimeException("Servicio no encontrado");
        }
        servicio.setId(id);
        return servicioRepository.save(servicio);
    }

    public void eliminar(Long id) {
        servicioRepository.deleteById(id);
    }
    public List<Servicio> listarServiciosActivos() {
        return servicioRepository.findByActivoTrue();
    }
    public List<Servicio> findByActivoTrue() {
        return servicioRepository.findAll().stream()
                .filter(Servicio::getActivo)
                .collect(Collectors.toList());
    }
}