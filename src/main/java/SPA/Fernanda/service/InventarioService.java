package SPA.Fernanda.service;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import SPA.Fernanda.model.Inventario;
import SPA.Fernanda.repository.InventarioRepository;
@Service
public class InventarioService {

    @Autowired
    private InventarioRepository inventarioRepository;

    public List<Inventario> obtenerTodos() {
        return inventarioRepository.findAll();
    }

    public Inventario guardar(Inventario inventario) {
        return inventarioRepository.save(inventario);
    }

    public Inventario actualizar(Long id, Inventario inventarioActualizado) {
        Optional<Inventario> inventarioExistente = inventarioRepository.findById(id);
        if (inventarioExistente.isPresent()) {
            Inventario inventario = inventarioExistente.get();
            inventario.setNombre(inventarioActualizado.getNombre());
            inventario.setCantidad(inventarioActualizado.getCantidad());
            inventario.setUnidadMedida(inventarioActualizado.getUnidadMedida());
            inventario.setDescripcion(inventarioActualizado.getDescripcion());
            return inventarioRepository.save(inventario);
        } else {
            throw new RuntimeException("Insumo no encontrado con ID: " + id);
        }
    }

    public void eliminar(Long id) {
        inventarioRepository.deleteById(id);
    }
}
