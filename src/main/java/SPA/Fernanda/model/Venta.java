package SPA.Fernanda.model;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Ventas")
public class Venta {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "cliente_id", nullable = false)
	private Usuario cliente;

	@ManyToOne
	@JoinColumn(name = "empleado_id")
	private Usuario empleado;

	@Column(nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date fecha_venta = new Date();

	@Enumerated(EnumType.STRING)
	@Column(name = "metodo_pago", nullable = false)
	private MetodoPago metodoPago;

	@Enumerated(EnumType.STRING)
	@Column(name = "Estado", nullable = false)
	private EstadoVenta estado;

	private String observaciones;

	@OneToMany(mappedBy = "venta", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<DetalleVenta> detalles;

	@OneToMany(mappedBy = "venta")
	private List<Cita> citas;

	public enum MetodoPago {
		Efectivo, Tarjeta, Transferencia, Otro
	}

	public enum EstadoVenta {
		Completada, Cancelada, Pendiente
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Usuario getCliente() {
		return cliente;
	}

	public void setCliente(Usuario cliente) {
		this.cliente = cliente;
	}

	public Usuario getEmpleado() {
		return empleado;
	}

	public void setEmpleado(Usuario empleado) {
		this.empleado = empleado;
	}

	public Date getFechaVenta() {
		return fecha_venta;
	}

	public void setFechaVenta(Date fechaVenta) {
		this.fecha_venta = fechaVenta;
	}

	public MetodoPago getMetodoPago() {
		return metodoPago;
	}

	public void setMetodoPago(MetodoPago metodoPago) {
		this.metodoPago = metodoPago;
	}

	public EstadoVenta getEstado() {
		return estado;
	}

	public void setEstado(EstadoVenta estado) {
		this.estado = estado;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public List<DetalleVenta> getDetalles() {
		return detalles;
	}

	public void setDetalles(List<DetalleVenta> detalles) {
		this.detalles = detalles;
	}

	public List<Cita> getCitas() {
		return citas;
	}

	public void setCitas(List<Cita> citas) {
		this.citas = citas;
	}

	@Override
	public String toString() {
		return "Venta [id=" + id + ", clienteId=" + (cliente != null ? cliente.getId() : null) + ", empleadoId="
				+ (empleado != null ? empleado.getId() : null) + ", fechaVenta=" + fecha_venta + ", metodoPago="
				+ metodoPago + ", estado=" + estado + ", observaciones=" + observaciones + "]";
	}

}
