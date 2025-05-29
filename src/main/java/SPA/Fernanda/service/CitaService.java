package SPA.Fernanda.service;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import SPA.Fernanda.model.Cita;
import SPA.Fernanda.repository.CitaRepository;

@Service
public class CitaService {

    @Autowired
    private CitaRepository citaRepository;
    
    @Autowired
    private ServicioService ServicioService;

    @Autowired
    private UsuarioService UsuarioService;

    public List<Cita> obtenerTodas() {
        return citaRepository.findAll();
    }

    public Cita guardar(Cita cita) {
        return citaRepository.save(cita);
    }

    public Cita actualizar(Long id, Cita citaActualizada) {
        Optional<Cita> citaExistente = citaRepository.findById(id);
        if (citaExistente.isPresent()) {
            Cita cita = citaExistente.get();
            cita.setCliente(citaActualizada.getCliente());
            cita.setEmpleado(citaActualizada.getEmpleado());
            cita.setServicio(citaActualizada.getServicio());
            cita.setVenta(citaActualizada.getVenta());
            cita.setFechaCita(citaActualizada.getFechaCita());
            cita.setEstado(citaActualizada.getEstado());
            cita.setNotas(citaActualizada.getNotas());
            return citaRepository.save(cita);
        } else {
            throw new RuntimeException("Cita no encontrada con ID: " + id);
        }
    }

    public void eliminar(Long id) {
        citaRepository.deleteById(id);
    }
    public void agendarCita(Long servicioId, Long empleadoId, Long clienteId, LocalDateTime fechaCita, String notas) {
        Cita cita = new Cita();
        cita.setServicio(ServicioService.obtenerPorId(servicioId).orElseThrow());
        cita.setEmpleado(UsuarioService.obtenerPorId(empleadoId).orElseThrow());
        cita.setCliente(UsuarioService.obtenerPorId(clienteId).orElseThrow());
        cita.setFechaCita(fechaCita);
        cita.setNotas(notas);
        cita.setEstado(Cita.EstadoCita.Agendada);
        citaRepository.save(cita);
    }

    public List<Cita> obtenerCitasPorCliente(Long clienteId) {
        return citaRepository.findByCliente_Id(clienteId);
    }

    public List<Cita> obtenerCitasPorEmpleado(Long empleadoId) {
        return citaRepository.findByEmpleado_Id(empleadoId);
    }
    public void cancelarCita(Long id) {
        Optional<Cita> optional = citaRepository.findById(id);
        if (optional.isPresent()) {
            Cita cita = optional.get();
            cita.setEstado(Cita.EstadoCita.Cancelada);
            citaRepository.save(cita);
        }
    }
    public void eliminarCita(Long id) {
        citaRepository.deleteById(id);
    }

    public Optional<Cita> obtenerPorId(Long id) {
        return citaRepository.findById(id);
    }
}
