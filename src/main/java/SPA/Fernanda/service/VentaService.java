package SPA.Fernanda.service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import SPA.Fernanda.model.DetalleVenta;
import SPA.Fernanda.model.Servicio;
import SPA.Fernanda.model.Venta;
import SPA.Fernanda.repository.DetalleVentaRepository;
import SPA.Fernanda.repository.VentaRepository;
import jakarta.transaction.Transactional;

@Service
public class VentaService {

	@Autowired
    private VentaRepository ventaRepository;
    
    @Autowired
    private ServicioService servicioService;
    
    @Autowired
    private DetalleVentaRepository detalleVentaRepository;


    public Optional<Venta> obtenerPorId(Long id) {
        return ventaRepository.findById(id);
    }

    public Venta guardar(Venta venta) {
        return ventaRepository.save(venta);
    }

    public Venta actualizar(Long id, Venta venta) {
        if (!ventaRepository.existsById(id)) {
            throw new RuntimeException("Venta no encontrada");
        }
        venta.setId(id);
        return ventaRepository.save(venta);
    }

    public void eliminar(Long id) {
        ventaRepository.deleteById(id);
    }
    @Transactional
    public void guardarVentaConDetalles(Venta venta, List<Long> servicioIds) {
 
        Venta ventaGuardada = ventaRepository.save(venta);
        
        List<DetalleVenta> detalles = servicioIds.stream()
            .map(servicioId -> {
                Servicio servicio = servicioService.obtenerPorId(servicioId)
                    .orElseThrow(() -> new RuntimeException("Servicio no encontrado"));
                
                DetalleVenta detalle = new DetalleVenta();
                detalle.setVenta(ventaGuardada);
                detalle.setServicio(servicio);
                detalle.setPrecioUnitario(servicio.getPrecio());
                detalle.setCantidad(1); 
                return detalle;
            })
            .collect(Collectors.toList());
        
        detalleVentaRepository.saveAll(detalles);
    }
    public List<Venta> obtenerTodas() {
        return ventaRepository.obtenerVentasManual();
      
    }
    
}