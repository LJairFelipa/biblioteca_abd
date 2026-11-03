package com.example.demo;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.time.LocalDate;

@Controller
public class ControladorBibliotecario {

    @Autowired
    private ReservaRepository reservaRepository;
    @Autowired
    private PrestamoRepository prestamoRepository;
    @Autowired
    private RecursoRepository recursoRepository;

    @GetMapping("/panel")
    public String verPanel(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario == null || !"BIBLIOTECARIO".equals(usuario.getRol())) return "redirect:/"; 
        
        model.addAttribute("usuario", usuario);
        model.addAttribute("reservas", reservaRepository.findByEstado("PENDIENTE"));
        model.addAttribute("prestamos", prestamoRepository.findByEstado("ACTIVO"));
        return "panel-bibliotecario";
    }

    @PostMapping("/entregar-prestamo")
    public String entregarPrestamo(@RequestParam("idReserva") Long idReserva, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario == null || !"BIBLIOTECARIO".equals(usuario.getRol())) return "redirect:/"; 

        Reserva reserva = reservaRepository.findById(idReserva).orElse(null);
        if (reserva != null && "PENDIENTE".equals(reserva.getEstado())) {
            reserva.setEstado("COMPLETADA");
            reservaRepository.save(reserva);

            Recurso recurso = recursoRepository.findById(reserva.getIdRecurso()).orElse(null);
            if (recurso != null) {
                recurso.setEstado("PRESTADO");
                recursoRepository.save(recurso);
            }

            Prestamo prestamo = new Prestamo();
            prestamo.setIdReserva(idReserva);
            prestamo.setIdUsuario(reserva.getIdUsuario());
            prestamo.setIdRecurso(reserva.getIdRecurso());
            prestamo.setFechaPrestamo(LocalDate.now());
            prestamo.setFechaDevolucionLimite(LocalDate.now().plusDays(7)); 
            prestamo.setEstado("ACTIVO");
            prestamoRepository.save(prestamo);
        }
        return "redirect:/panel";
    }

    @PostMapping("/devolver-prestamo")
    public String devolverPrestamo(@RequestParam("idPrestamo") Long idPrestamo, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario == null || !"BIBLIOTECARIO".equals(usuario.getRol())) return "redirect:/"; 

        Prestamo prestamo = prestamoRepository.findById(idPrestamo).orElse(null);
        if (prestamo != null && "ACTIVO".equals(prestamo.getEstado())) {
            prestamo.setEstado("DEVUELTO");
            prestamo.setFechaDevolucionReal(LocalDate.now());
            prestamoRepository.save(prestamo);

            Recurso recurso = recursoRepository.findById(prestamo.getIdRecurso()).orElse(null);
            if (recurso != null) {
                recurso.setEstado("DISPONIBLE");
                recursoRepository.save(recurso);
            }
        }
        return "redirect:/panel";
    }

    // --- NUEVAS RUTAS PARA EL CRUD DE INVENTARIO ---

    @GetMapping("/gestion-recursos")
    public String gestionarRecursos(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario == null || !"BIBLIOTECARIO".equals(usuario.getRol())) return "redirect:/";
        model.addAttribute("usuario", usuario);
        model.addAttribute("recursos", recursoRepository.findAll());
        return "gestion-recursos";
    }

    @GetMapping("/nuevo-recurso")
    public String nuevoRecurso(HttpSession session, Model model) {
        if (session.getAttribute("usuarioLogueado") == null) return "redirect:/";
        model.addAttribute("recurso", new Recurso()); // Enviamos un recurso vacío al formulario
        return "formulario-recurso";
    }

    @GetMapping("/editar-recurso")
    public String editarRecurso(@RequestParam("id") Long id, HttpSession session, Model model) {
        if (session.getAttribute("usuarioLogueado") == null) return "redirect:/";
        Recurso recurso = recursoRepository.findById(id).orElse(null);
        model.addAttribute("recurso", recurso); // Enviamos los datos actuales al formulario
        return "formulario-recurso";
    }

    @PostMapping("/guardar-recurso")
    public String guardarRecurso(Recurso recurso, HttpSession session) {
        if (session.getAttribute("usuarioLogueado") == null) return "redirect:/";
        // Si es un recurso nuevo, forzamos que inicie DISPONIBLE
        if (recurso.getEstado() == null || recurso.getEstado().isEmpty()) {
            recurso.setEstado("DISPONIBLE");
        }
        recursoRepository.save(recurso);
        return "redirect:/gestion-recursos";
    }

   @GetMapping("/eliminar-recurso")
public String eliminarRecurso(@RequestParam("id") Long id, HttpSession session) {
    if (session.getAttribute("usuarioLogueado") == null) return "redirect:/";
    
    // Aplicamos una "Baja Lógica" en lugar de un DELETE físico para proteger el historial
    Recurso recurso = recursoRepository.findById(id).orElse(null);
    if (recurso != null) {
        recurso.setEstado("EN_MANTENIMIENTO");
        recursoRepository.save(recurso);
    }
    
    return "redirect:/gestion-recursos";
}
}