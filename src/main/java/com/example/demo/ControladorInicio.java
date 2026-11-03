package com.example.demo;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@Controller
public class ControladorInicio {
    
    @Autowired
    private RecursoRepository recursoRepository;
    
    @Autowired
    private ReservaRepository reservaRepository;
    
    @GetMapping("/")
    public String mostrarInicio(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario == null) {
            return "redirect:/login"; // Si no ha iniciado sesión, lo manda al login
        }
        
        model.addAttribute("usuario", usuario);
        List<Recurso> inventario = recursoRepository.findAll();
        model.addAttribute("recursos", inventario);
        return "index";
    }

    @PostMapping("/reservar")
    public String reservar(@RequestParam("id") Long idRecurso, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario == null) {
            return "redirect:/login";
        }

        Recurso recurso = recursoRepository.findById(idRecurso).orElse(null);
        
        if (recurso != null && "DISPONIBLE".equals(recurso.getEstado())) {
            recurso.setEstado("RESERVADO");
            recursoRepository.save(recurso);
            
            Reserva reserva = new Reserva();
            reserva.setIdUsuario(usuario.getIdUsuario()); // Usa el ID del usuario actual de la sesión
            reserva.setIdRecurso(idRecurso);
            reserva.setEstado("PENDIENTE");
            reservaRepository.save(reserva);
        }
        return "redirect:/";
    }
    @GetMapping("/mis-reservas")
    public String verMisReservas(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario == null) {
            return "redirect:/login";
        }
        
        model.addAttribute("usuario", usuario);
        // Buscamos solo las reservas de este usuario
        List<Reserva> misReservas = reservaRepository.findByIdUsuario(usuario.getIdUsuario());
        model.addAttribute("reservas", misReservas);
        
        return "mis-reservas"; // Carga el archivo mis-reservas.html
    }
}