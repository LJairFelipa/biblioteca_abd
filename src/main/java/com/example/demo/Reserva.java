package com.example.demo;
import jakarta.persistence.*;

@Entity
@Table(name = "RESERVAS")
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_RESERVA")
    private Long idReserva;

    @Column(name = "ID_USUARIO")
    private Long idUsuario;

    @Column(name = "ID_RECURSO")
    private Long idRecurso;

    @Column(name = "ESTADO")
    private String estado;

    public Long getIdReserva() { return idReserva; }
    public void setIdReserva(Long idReserva) { this.idReserva = idReserva; }
    public Long getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Long idUsuario) { this.idUsuario = idUsuario; }
    public Long getIdRecurso() { return idRecurso; }
    public void setIdRecurso(Long idRecurso) { this.idRecurso = idRecurso; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
