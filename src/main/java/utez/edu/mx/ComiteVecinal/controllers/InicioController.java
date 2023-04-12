package utez.edu.mx.ComiteVecinal.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InicioController {
    @GetMapping("/")
    public String inicio(){
        return "index";
    }

    @GetMapping("/login")
    public String login(){
        return "inicioSesion";
    }
}
