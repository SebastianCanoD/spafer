package SPA.Fernanda.model;
import jakarta.persistence.*;

@Entity
@Table(name = "Inventario_Servicio",
       uniqueConstraints = @UniqueConstraint(columnNames = {"servicio_id", "inventario_id"}))
public class InventarioServicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "servicio_id", nullable = false)
    private Servicio servicio;

    @ManyToOne
    @JoinColumn(name = "inventario_id", nullable = false)
    private Inventario inventario;

    @Column(nullable = false)
    private Double cantidadUsada;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Servicio getServicio() {
		return servicio;
	}

	public void setServicio(Servicio servicio) {
		this.servicio = servicio;
	}

	public Inventario getInventario() {
		return inventario;
	}

	public void setInventario(Inventario inventario) {
		this.inventario = inventario;
	}

	public Double getCantidadUsada() {
		return cantidadUsada;
	}

	public void setCantidadUsada(Double cantidadUsada) {
		this.cantidadUsada = cantidadUsada;
	}

	@Override
	public String toString() {
		return "InventarioServicio [id=" + id + ", servicio=" + servicio + ", inventario=" + inventario
				+ ", cantidadUsada=" + cantidadUsada + "]";
	}

    
}
