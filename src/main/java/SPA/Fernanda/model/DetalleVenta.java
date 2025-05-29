package SPA.Fernanda.model;
import jakarta.persistence.*;

@Entity
@Table(name = "Detalle_Venta_Servicios")
public class DetalleVenta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "venta_id", nullable = false)
    private Venta venta;

    @ManyToOne
    @JoinColumn(name = "servicio_id", nullable = false)
    private Servicio servicio;

    @Column(nullable = false)
    private Integer cantidad = 1;

    @Column(nullable = false)
    private Double precioUnitario;

    @Column(nullable = false)
    private Double descuento = 0.0;

    @Column(nullable = false)
    private Double subtotal;

    @PrePersist
    @PreUpdate
    private void calcularSubtotal() {
        this.subtotal = (precioUnitario - descuento) * cantidad;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Venta getVenta() {
		return venta;
	}

	public void setVenta(Venta venta) {
		this.venta = venta;
	}

	public Servicio getServicio() {
		return servicio;
	}

	public void setServicio(Servicio servicio) {
		this.servicio = servicio;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public Double getPrecioUnitario() {
		return precioUnitario;
	}

	public void setPrecioUnitario(Double precioUnitario) {
		this.precioUnitario = precioUnitario;
	}

	public Double getDescuento() {
		return descuento;
	}

	public void setDescuento(Double descuento) {
		this.descuento = descuento;
	}

	public Double getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(Double subtotal) {
		this.subtotal = subtotal;
	}

	@Override
	public String toString() {
		return "DetalleVenta [id=" + id + ", venta=" + venta + ", servicio=" + servicio + ", cantidad=" + cantidad
				+ ", precioUnitario=" + precioUnitario + ", descuento=" + descuento + ", subtotal=" + subtotal + "]";
	}
    
}
