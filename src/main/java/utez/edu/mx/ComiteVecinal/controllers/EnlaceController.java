package utez.edu.mx.ComiteVecinal.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import utez.edu.mx.ComiteVecinal.models.*;
import utez.edu.mx.ComiteVecinal.services.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
@SessionAttributes({"comites","integrantes","comiteParaIntegrante"})
public class EnlaceController{

    @Autowired
    IColoniaService coloniaService;

    @Autowired
    IComiteService comiteService;

    @Autowired
    IMunicipioService municipioService;

    @Autowired
    IIncidenciaService incidenciaService;

    @Autowired
    ICategoriaService categoriaService;

    @Autowired
    IIntegranteService integranteService;

    @Autowired
    IRolService rolService;

    @Autowired
    IUsuarioService usuarioService;

    @Autowired
    IEnlaceService enlaceService;




    //------------------------------------------COMITE--------------------------------------------
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','ENLACE')")
    @GetMapping(value = {"/comites"})
    public String mostrarComite(Model model, Authentication auth) {

        String userName = auth.getName();
        Usuario queUsuario = usuarioService.buscarPorUsername(userName);
        Boolean esRolAdmin=false;
        for (Rol r: queUsuario.getRoles()) {
            if(r.getTipo().equals("ROLE_ADMINISTRADOR")){
                esRolAdmin=true;
            }
        }

        if(comiteService.listarComites().size() > 0 && !esRolAdmin) {
        model.addAttribute("comites", comiteService.listarComites());
        String username = auth.getName();
        Usuario usuario = usuarioService.buscarPorUsername(username);
        Optional<Enlace> enlaceEncontrado = enlaceService.listarEnlaces().stream()
                .filter(r -> r.getUsuario().getId().equals(usuario.getId()))
                .findFirst();
        model.addAttribute("enlace", enlaceEncontrado.get());
        System.out.println(enlaceEncontrado.get().getMunicipio().getNombre());


        List<Comite> comiteEncontrado = comiteService.listarComites().stream()
                    .filter(r -> r.getMunicipio().getId().equals(enlaceEncontrado.get().getMunicipio().getId())).toList();

        System.out.println(comiteEncontrado);
        model.addAttribute("comites", comiteEncontrado);
        } else if (comiteService.listarComites().size() > 0 && esRolAdmin) {
            model.addAttribute("comites", comiteService.listarComites());
        } else{
            model.addAttribute("comites", comiteService.listarComites());
        }
        return "comite";
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR','ENLACE')")
    @GetMapping(value = {"/comite/registrar"})
    public String mostrarRegistroComite(Model model, Authentication auth) {
        Comite comite = new Comite();
        model.addAttribute("comites", comite);
        model.addAttribute("municipios", municipioService.listarMunicipios());
        //model.addAttribute("colonias", coloniaService.listarColonias());
        String username = auth.getName();
        Usuario usuario = usuarioService.buscarPorUsername(username);
        Optional<Enlace> enlaceEncontrado = enlaceService.listarEnlaces().stream()
                .filter(r -> r.getUsuario().getId().equals(usuario.getId()))
                .findFirst();
        model.addAttribute("enlace", enlaceEncontrado.get());

        return "registrarComite";
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR','ENLACE')")
    @PostMapping("/comite/registrar")
    public String registroComite(Comite comite, BindingResult result, RedirectAttributes flash) {

        comiteService.guardarComite(comite);
        flash.addAttribute("comiteParaForm", comite);

        return "redirect:/integrante/registrar";
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR','ENLACE')")
    @GetMapping(value = {"/comite/actualizar/{id}"})
    public String mostrarActualizarComite(@PathVariable Long id, Model model) {

        model.addAttribute("comites", comiteService.mostrarComitePorId(id));
        model.addAttribute("municipios", municipioService.listarMunicipios());
        model.addAttribute("colonias", coloniaService.listarColonias());

        return "editarComite";
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR','ENLACE')")
    @PostMapping(value = {"/comite/actualizar/{id}"})
    public String actualizarComite(@PathVariable Long id,@Valid @ModelAttribute("comites") Comite comite,
                                   BindingResult result, Model model) {

        Comite comiteExistente = comiteService.mostrarComitePorId(id);
        comiteExistente.setId(id);
        comiteExistente.setMunicipio(comite.getMunicipio());
        comiteExistente.setColonias(comite.getColonias());

        comiteService.actualizarComite(comiteExistente);

        return "redirect:/comites";
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR','ENLACE')")
    @GetMapping(value = {"/comite/{id}"})
    public String mostrarEliminarComite(@PathVariable Long id) {

        comiteService.eliminarComite(id);

        return "redirect:/comites";
    }

    //------------------------------------------INTEGRANTES--------------------------------------------
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','ENLACE')")
    @GetMapping(value = {"/integrantes"})
    public String mostrarIntegrantes(Model model) {

        model.addAttribute("integrantes", integranteService.listarIntegrantes());

        return "integrantes";
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR','ENLACE')")
    @GetMapping("/integrante/registrar")
    public String mostrarRegistroIntegrantes(Model model, @ModelAttribute("comiteParaForm") Comite comite) {

        model.addAttribute("integrantes", new IntegranteComite());
        model.addAttribute("comites", comite);
        Boolean integrante = comite.getIntegrantes().stream().anyMatch(inte -> inte.isValidacionPresidente() == true);
        model.addAttribute("integrante", integrante);

        return "registrarIntegrante";
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR','ENLACE')")
    @PostMapping("/integrante/registrar")
    public String registroIntegrante(IntegranteComite integrante, BindingResult result, RedirectAttributes flash,
                                     @ModelAttribute("comites") Comite comite,
                                     @RequestParam(name = "imagen", required = true) MultipartFile foto) {
        if (!foto.isEmpty()) {
            String ruta = "C://Temp//uploads";
            String nombreUnico = UUID.randomUUID() + " " + foto.getOriginalFilename();
            try {
                byte[] bytes = foto.getBytes();
                Path rutaAbsoluta = Paths.get(ruta + "//" + nombreUnico);
                Files.write(rutaAbsoluta, bytes);
                integrante.setImagen(nombreUnico);
                integranteService.guardarIntegrante(integrante);
                System.out.println("Integrante guardado correctamente");
            } catch (Exception e) {
                e.getCause().getMessage();
            }
        }
        flash.addAttribute("comiteParaForm", comite);

        return "redirect:/integrante/registrar";
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR','ENLACE')")
    @GetMapping("/agregar-integrantes/{id}")
    public String agregarIntegrantes(@PathVariable Long id, Model model){
        Comite comite = comiteService.mostrarComitePorId(id);
        Boolean integrantes = comite.getIntegrantes().stream().anyMatch(inte -> inte.isValidacionPresidente() == true);

        model.addAttribute("integrante", new IntegranteComite());
        model.addAttribute("integrantes", integrantes);
        model.addAttribute("comiteParaIntegrante", comite);
        return "agregarIntegrantesForm";
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR','ENLACE')")
    @GetMapping("/editar-integrantes/{id}")
    public String editarIntegrantes(@PathVariable Long id, Model model){
        Comite comite = comiteService.mostrarComitePorId(id);
        model.addAttribute("comite", comite);
        return "/editarIntegrantes";
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR','ENLACE')")
    @PostMapping("/guardar-integrante")
    public String guardarIntegrante(@RequestParam(name = "imagen", required = true) MultipartFile foto,
                                    @Valid @ModelAttribute("integrante") IntegranteComite integrante,
                                 BindingResult result, RedirectAttributes flash, Model model) {

        if (!foto.isEmpty()) {
            String ruta = "C://Temp//uploads";
            String nombreUnico = UUID.randomUUID() + " " + foto.getOriginalFilename();
            try {
                byte[] bytes = foto.getBytes();
                Path rutaAbsoluta = Paths.get(ruta + "//" + nombreUnico);
                Files.write(rutaAbsoluta, bytes);
                integrante.setImagen(nombreUnico);
                integranteService.guardarIntegrante(integrante);
                System.out.println("Integrante guardado correctamente");
            } catch (Exception e) {
                e.getCause().getMessage();
            }
        }
        flash.addAttribute("presidenteParaForm", integrante);

        return "redirect:/comites";
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR','ENLACE')")
    @GetMapping("/eliminar-integrante/{id}")
    public String eliminarIntegrante(@PathVariable Long id, RedirectAttributes flash){
        integranteService.eliminarIntegrante(id);
        return "redirect:/comites";
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR','ENLACE')")
    @GetMapping("/cargar-integrante/{id}")
    public String cargarIntegrante(@PathVariable Long id, Model model){
        IntegranteComite integrante = integranteService.mostrarIntegrantePorId(id);
        model.addAttribute("integrante", integrante);
        Comite comite = comiteService.mostrarComitePorId(integrante.getComite().getId());
        model.addAttribute("comite", comite);
        return "editarIntegranteForm";
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR','ENLACE')")
    @GetMapping(value = {"/presidente/registrar/{id}"})
    public String mostrarRegistroEnlace(Model model, @PathVariable Long id) {
        model.addAttribute("usuarios", new Usuario());
        model.addAttribute("roles", rolService.listarRoles());
        model.addAttribute("idPresi", id);

        return "registrarUsuarioPresi";
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR','ENLACE')")
    @GetMapping("/editar-incidencia-enlace/{id}")
    public String editarIncidenciaEnlace(@PathVariable Long id, Model model){
        Incidencia incidencia = incidenciaService.mostrarIncidenciaPorId(id);
        model.addAttribute("incidencia", incidencia);
        return "/editarIncidenciaEnlace";
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR','ENLACE')")
    @PostMapping("/editar-incidencia-enlace")
    public String editarIncidenciaEnlacePost(Incidencia incidencia){

        incidenciaService.guardarIncidencia(incidencia);

        return "redirect:/comite/presidente/incidencias";
    }

    //------------------------------------------COLONIAS--------------------------------------------
    @PreAuthorize("hasAnyRole('ADMINISTRADOR','ENLACE')")
    @GetMapping(value = {"/colonias"})
    public String mostrarColonia(Model model) {

        model.addAttribute("colonias", coloniaService.listarColonias());

        return "colonia";
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR','ENLACE')")
    @GetMapping(value = {"/colonia/registrar"})
    public String mostrarRegistroColonia(Model model) {
        Colonia colonia = new Colonia();
        model.addAttribute("colonias", colonia);
        model.addAttribute("municipios", municipioService.listarMunicipios());

        return "registrarColonia";
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR','ENLACE')")
    @GetMapping(value = {"/colonia/registrar/{id}"})
    public String mostrarRegistro(@PathVariable Long id, Model model) {
        Municipio municipio = municipioService.mostrarMunicipioPorId(id);
        System.out.println(municipio.getId());
        System.out.println(municipio.getNombre());
        Colonia colonia = new Colonia();
        model.addAttribute("municipioParaColonia", municipio);
        model.addAttribute("colonias", colonia);

        return "registrarColonias";
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR','ENLACE')")

    @PostMapping("/guardar-colonia")
    public String guardarColonia(@Valid @ModelAttribute("colonias")Colonia colonia, BindingResult result,
                                 RedirectAttributes flash) {

        if(result.hasErrors()){
            return "registrarColonias";
        }
        coloniaService.guardarColonia(colonia);
        return "redirect:/colonias";
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR','ENLACE')")
    @PostMapping(value = {"/colonia/registrar"})
    public String registroColonia(@Valid @ModelAttribute("colonias")Colonia colonia,
                                  BindingResult result, Model model) {

        if(result.hasErrors()){
            return "registrarColonia";
        }
        coloniaService.guardarColonia(colonia);

        model.addAttribute("colonias", coloniaService.listarColonias());
        model.addAttribute("municipios", municipioService.listarMunicipios());

        return "redirect:/colonias";
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR','ENLACE')")
    @GetMapping(value = {"/colonia/actualizar/{id}"})
    public String mostrarActualizarColonia(@PathVariable Long id, Model model) {

        model.addAttribute("colonias", coloniaService.mostrarColoniaPorId(id));
        model.addAttribute("municipios", municipioService.listarMunicipios());

        return "editarColonia";
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR','ENLACE')")
    @PostMapping(value = {"/colonia/actualizar/{id}"})
    public String actualizarColonia(@PathVariable Long id,@Valid @ModelAttribute("colonias") Colonia colonia,
                                    BindingResult result, Model model) {

        Colonia coloniaExistente = coloniaService.mostrarColoniaPorId(id);
        coloniaExistente.setId(id);
        coloniaExistente.setNombre(colonia.getNombre());
        coloniaExistente.setCodigoPostal(colonia.getCodigoPostal());
        coloniaExistente.setMunicipio(colonia.getMunicipio());

        coloniaService.actualizarColonia(coloniaExistente);

        return "redirect:/colonias";
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR','ENLACE')")
    @GetMapping(value = {"/colonia/{id}"})
    public String mostrarEliminarColonia(@PathVariable Long id) {

        coloniaService.eliminarColonia(id);

        return "redirect:/colonias";
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR','ENLACE')")
    @GetMapping("/colonias-por-municipio")
    public String obtenerColoniasPorMunicipio(@RequestParam("municipio") Long municipioId, Model model) {
        List<Colonia> colonias = coloniaService.buscarPorMunicipio(municipioId);

        model.addAttribute("coloniasDelMunicipio", colonias);
        return "registrarComite";
    }
}
