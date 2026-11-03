package com.example.demo;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "PRESTAMOS")
public class Prestamo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PRESTAMO")
    private Long idPrestamo;
    @Column(name = "ID_RESERVA")
    private Long idReserva;
    @Column(name = "ID_USUARIO")
    private Long idUsuario;
    @Column(name = "ID_RECURSO")
    private Long idRecurso;
    @Column(name = "FECHA_PRESTAMO")
    private LocalDate fechaPrestamo;
    @Column(name = "FECHA_DEVOLUCION_LIMITE")
    private LocalDate fechaDevolucionLimite;
    @Column(name = "FECHA_DEVOLUCION_REAL")
    private LocalDate fechaDevolucionReal;
    @Column(name = "ESTADO")
    private String estado;

    // Getters y Setters
    public Long getIdPrestamo() { return idPrestamo; }
    public void setIdPrestamo(Long idPrestamo) { this.idPrestamo = idPrestamo; }
    public Long getIdReserva() { return idReserva; }
    public void setIdReserva(Long idReserva) { this.idReserva = idReserva; }
    public Long getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Long idUsuario) { this.idUsuario = idUsuario; }
    public Long getIdRecurso() { return idRecurso; }
    public void setIdRecurso(Long idRecurso) { this.idRecurso = idRecurso; }
    public LocalDate getFechaPrestamo() { return fechaPrestamo; }
    public void setFechaPrestamo(LocalDate fechaPrestamo) { this.fechaPrestamo = fechaPrestamo; }
    public LocalDate getFechaDevolucionLimite() { return fechaDevolucionLimite; }
    public void setFechaDevolucionLimite(LocalDate fechaDevolucionLimite) { this.fechaDevolucionLimite = fechaDevolucionLimite; }
    public LocalDate getFechaDevolucionReal() { return fechaDevolucionReal; }
    public void setFechaDevolucionReal(LocalDate fechaDevolucionReal) { this.fechaDevolucionReal = fechaDevolucionReal; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
