package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    // Busca las reservas hechas por un usuario específico
    List<Reserva> findByIdUsuario(Long idUsuario);
    List<Reserva> findByEstado(String estado);
}
