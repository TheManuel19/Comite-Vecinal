package utez.edu.mx.ComiteVecinal.controllers;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import utez.edu.mx.ComiteVecinal.models.*;
import utez.edu.mx.ComiteVecinal.services.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@RequestMapping(value = "/comite/presidente")
public class PresidenteController {

    @Autowired
    IncidenciaServiceImpl incidenciaService;

    @Autowired
    CategoriaServiceImpl categoriaService;

    @Autowired
    IntegranteServiceImpl integranteService;

    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private IComentarioService comentarioService;

    @Autowired
    private  IEnlaceService enlaceService;

    @Autowired
    private IComiteService comiteService;

    //------------------------------------------INCIDENCIAS--------------------------------------------
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','ENLACE','PRESIDENTE')")
    @GetMapping(value = {"/incidencias"})
    public String mostrarIncidencia(Model model, Authentication auth) {

        String username = auth.getName();
        Usuario usuario = usuarioService.buscarPorUsername(username);

        List<IntegranteComite> listaPresidentes = integranteService.listarIntegrantes().stream()
                .filter(IntegranteComite::isValidacionPresidente).toList();

            Optional<IntegranteComite> presiEncontrado = listaPresidentes.stream()
                    .filter(r -> r.getUsername().getUserName().equals(usuario.getUserName()))
                    .findFirst();

        if(presiEncontrado.isPresent()) {
            List<Incidencia> listaIncidencias = incidenciaService.listarIncidencia().stream()
                    .filter(i -> i.getIntegrante().getUsername().getUserName()
                            .equals(presiEncontrado.get().getUsername().getUserName())).toList();


            model.addAttribute("presidente", presiEncontrado.get());
            model.addAttribute("incidencias", incidenciaService.listarIncidencia());
            model.addAttribute("listaIncidencias", listaIncidencias);
        }else{
            Optional<Enlace> enlaceEncontrado = enlaceService.listarEnlaces().stream()
                    .filter(r -> r.getUsuario().getUserName().equals(usuario.getUserName()))
                    .findFirst();

            List<Incidencia> listaIncidencias = incidenciaService.listarIncidencia().stream()
                    .filter(i -> i.getIntegrante().getComite().getMunicipio().getNombre().equals(enlaceEncontrado.get()
                                    .getMunicipio().getNombre())).toList();

            model.addAttribute("listaIncidencias", listaIncidencias);
        }

        return "incidencia";
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR','PRESIDENTE')")
    @GetMapping(value = {"/incidencia/registrar"})
    public String mostrarRegistroIncidencia(Model model, Authentication auth) {

        String username = auth.getName();

        List<IntegranteComite> lista = integranteService.listarIntegrantes().stream()
                .filter(valor -> valor.getUsername() != null)
                .collect(Collectors.toList());

        Optional<IntegranteComite> integranteEncontrado = lista.stream()
                .filter(r -> r.getUsername().getUserName().equals(username))
                .findFirst();

        model.addAttribute("incidencias", new Incidencia());
        model.addAttribute("categorias", categoriaService.listarCategorias());
        model.addAttribute("integrantes", integranteEncontrado.get());

        return "registrarIncidenciaPresi";
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR','ENLACE','PRESIDENTE')")
    @PostMapping(value = {"/incidencia/registrar"})
    public String registroIncidencia(@Valid @ModelAttribute("incidencias") Incidencia incidencia, BindingResult result, Model model) {

        incidenciaService.guardarIncidencia(incidencia);

        return "redirect:/comite/presidente/incidencias";
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR','ENLACE','PRESIDENTE')")
    @GetMapping(value = {"/incidencia/actualizar/{id}"})
    public String mostrarActualizarIncidencia(@PathVariable Long id, Model model) {

        model.addAttribute("incidencias", incidenciaService.mostrarIncidenciaPorId(id));
        model.addAttribute("categorias", categoriaService.listarCategorias());
        model.addAttribute("integrantes", integranteService.listarIntegrantes());

        return "editarIncidencia";
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR','ENLACE','PRESIDENTE')")
    @PostMapping(value = {"/incidencia/actualizar/{id}"})
    public String actualizarIncidencia(@PathVariable Long id,@Valid @ModelAttribute("incidencias") Incidencia incidencia,
                                       BindingResult result, Model model) {

        Incidencia incidenciaExistente = incidenciaService.mostrarIncidenciaPorId(id);
        incidenciaExistente.setId(id);
        incidenciaExistente.setDescripcion(incidencia.getDescripcion());
        incidenciaExistente.setFechaRegistro(incidencia.getFechaRegistro());
        incidenciaExistente.setAnexo(incidencia.getAnexo());
        incidenciaExistente.setMontoCobro(incidencia.getMontoCobro());
        incidenciaExistente.setEstadoSolicitud(incidencia.getEstadoSolicitud());
        incidenciaExistente.setIntegranteComite(incidencia.getIntegrante());
        incidenciaExistente.setCategoria(incidencia.getCategoria());


        incidenciaService.actualizarIncidencia(incidenciaExistente);

        return "redirect:/comite/presidente/incidencias";
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR','ENLACE','PRESIDENTE')")
    @GetMapping(value = {"/incidencia/{id}"})
    public String mostrarEliminarIncidencia(@PathVariable Long id) {

        incidenciaService.eliminarIncidencia(id);

        return "redirect:/comite/presidente/incidencias";
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR','ENLACE','PRESIDENTE')")
    @GetMapping("/incidencia/comentar/{id}")
    public String incidenciaParaComentar(@PathVariable Long id, Model model) {
        Incidencia incidencia = incidenciaService.mostrarIncidenciaPorId(id);

        Comentario comentario = new Comentario();
        comentario.setIncidencia(incidencia);

        model.addAttribute("comentario", comentario);
        model.addAttribute("incidencia", incidencia);
        return "comentarioForm";
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR','ENLACE','PRESIDENTE')")
    @PostMapping("/incidencia/guardar-comentario")
    public String guardarComentario(Comentario comentario, Authentication auth,
                                    HttpSession session, RedirectAttributes flash) {

        String username = auth.getName();

        Usuario usuario = usuarioService.buscarPorUsername(username);

        comentario.setUsuario(usuario);

        comentarioService.guardarComentario(comentario);

        return "redirect:/comite/presidente/incidencias";
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR','ENLACE','PRESIDENTE')")
    @GetMapping("/ver-comentarios/{id}")
    public String peli(@PathVariable Long id, Model model, Authentication auth) {
        if(auth != null){
            String username = auth.getName();
            model.addAttribute("username", username);
        }
        Incidencia incidencia = incidenciaService.mostrarIncidenciaPorId(id);
        model.addAttribute("incidencia", incidencia);
        return "verComentarios";
    }
}
