package SPA.Fernanda.model;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Access(AccessType.FIELD)
@Table(name = "Usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String usuario;

    @Column(nullable = false)
    private String nombre;

    @Column(unique = true, nullable = false)
    private String correo;

    @Column(nullable = false)
    private String contrasena;

    @Column(nullable = false)
    private String telefono;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Rol rol;

    @Column(name = "fechaRegistro", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;

    @OneToMany(mappedBy = "cliente")
    private List<Venta> ventasComoCliente;

    @OneToMany(mappedBy = "empleado")
    private List<Venta> ventasComoEmpleado;

    @OneToMany(mappedBy = "cliente")
    private List<Cita> citasComoCliente;

    @OneToMany(mappedBy = "empleado")
    private List<Cita> citasComoEmpleado;

    @OneToMany(mappedBy = "cliente")
    private List<Carrito> carritos;

    public enum Rol {
        Administrador, Empleado, Cliente
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	public Date getFechaRegistro() {
	    return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
	    this.fechaRegistro = fechaRegistro;
	}

	public List<Venta> getVentasComoCliente() {
		return ventasComoCliente;
	}

	public void setVentasComoCliente(List<Venta> ventasComoCliente) {
		this.ventasComoCliente = ventasComoCliente;
	}

	public List<Venta> getVentasComoEmpleado() {
		return ventasComoEmpleado;
	}

	public void setVentasComoEmpleado(List<Venta> ventasComoEmpleado) {
		this.ventasComoEmpleado = ventasComoEmpleado;
	}

	public List<Cita> getCitasComoCliente() {
		return citasComoCliente;
	}

	public void setCitasComoCliente(List<Cita> citasComoCliente) {
		this.citasComoCliente = citasComoCliente;
	}

	public List<Cita> getCitasComoEmpleado() {
		return citasComoEmpleado;
	}

	public void setCitasComoEmpleado(List<Cita> citasComoEmpleado) {
		this.citasComoEmpleado = citasComoEmpleado;
	}

	public List<Carrito> getCarritos() {
		return carritos;
	}

	public void setCarritos(List<Carrito> carritos) {
		this.carritos = carritos;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", usuario=" + usuario + ", nombre=" + nombre + ", correo=" + correo
				+ ", contrasena=" + contrasena + ", telefono=" + telefono + ", rol=" + rol + ", fechaRegistro="
				+ fechaRegistro + ", ventasComoCliente=" + ventasComoCliente + ", ventasComoEmpleado="
				+ ventasComoEmpleado + ", citasComoCliente=" + citasComoCliente + ", citasComoEmpleado="
				+ citasComoEmpleado + ", carritos=" + carritos + "]";
	}
    
}
