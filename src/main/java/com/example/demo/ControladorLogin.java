package com.example.demo;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ControladorLogin {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/login")
    public String mostrarLogin() {
        return "login"; // Muestra login.html
    }

    @PostMapping("/login")
    public String procesarLogin(@RequestParam("correo") String correo, HttpSession session, Model model) {
        Usuario usuario = usuarioRepository.findByCorreo(correo);
        
        if (usuario != null) {
            // Guardamos el usuario en la sesión web activa
            session.setAttribute("usuarioLogueado", usuario);
            return "redirect:/"; // Redirige al inventario general
        }
        
        model.addAttribute("error", "Correo no encontrado en el sistema.");
        return "login";
    }

    @GetMapping("/logout")
    public String cerrarSesion(HttpSession session) {
        session.invalidate(); // Borra la sesión
        return "redirect:/login";
    }
}
