package utez.edu.mx.ComiteVecinal.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
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
import java.util.*;

@Controller
//@SessionAttributes({"municipioParaColonia","municipios", "roles", "usuarioParaEnlace"})
public class AdminController {
	
	@Autowired
	IEnlaceService enlaceService;

	@Autowired
	IColoniaService coloniaService;

	@Autowired
	IMunicipioService municipioService;

	@Autowired
	IUsuarioService usuarioService;

	@Autowired
	ICategoriaService categoriaService;

	@Autowired
	IRolService rolService;

	@Autowired
	IIntegranteService integranteService;

	@Autowired
	PasswordEncoder passwordEncoder;

	@PreAuthorize("hasRole('ADMINISTRADOR')")
	@GetMapping(value = {"/default"})
	public String mostrarInicio(Model model) {

		return "default";
	}

	@PreAuthorize("hasRole('ADMINISTRADOR')")
	@GetMapping("/usuario/registrar")
	public String mostrarRegistroEnlace(Model model, Usuario usuario) {
		Usuario usuario1 = new Usuario();
		model.addAttribute("enlaces", new Enlace());
		model.addAttribute("municipios", municipioService.listarMunicipios());
		model.addAttribute("usuarios", usuario1);
		return "registrarUsuario";
	}

	@PreAuthorize("hasAnyRole('ADMINISTRADOR','ENLACE')")
    @GetMapping("/usuario-presidente/registrar")
    public String mostrarRegistroPresidente(@ModelAttribute("idIntegrante") Long id,Model model, @ModelAttribute("usuarios") Usuario usuario) {
		IntegranteComite integrante = integranteService.mostrarIntegrantePorId(id);
        model.addAttribute("integrante", integrante);
        model.addAttribute("usuarioParaPresi", usuario);

        return "registrarPresidente";
    }
	@PreAuthorize("hasRole('ADMINISTRADOR')")
	@PostMapping("/usuario/registrar")
	public String registroUsuario(@ModelAttribute("usuarios") Usuario usuario, @RequestParam String userName, @RequestParam String password,
								   RedirectAttributes flash, BindingResult result) {

		Usuario u = new Usuario();
		u.setUserName(userName);
		u.setPassword(passwordEncoder.encode(password));

		String rolEncontrado="";
		Set<Rol> roles = new HashSet<>();
		for (Rol r: usuario.getRoles()) {
			rolEncontrado = r.getTipo();
		}
		System.out.println(rolEncontrado);
		Rol rol = rolService.buscarPorTipo(rolEncontrado);
		System.out.println(rol);
		roles.add(rol);

		u.setRoles(roles);

		usuarioService.guardarUsuario(u);
		flash.addAttribute("usuario", u);

		return "redirect:/enlace/registrar";
	}

	@PreAuthorize("hasAnyRole('ADMINISTRADOR','ENLACE')")
	@PostMapping("/usuario-presi/registrar")
	public String registroUsuarioPresi(@RequestParam("idPresidente") Long idPresi, @RequestParam String userName,
									   @RequestParam String password, Usuario usuario,
									   RedirectAttributes flash, BindingResult result) {

		Usuario u = new Usuario();
		u.setUserName(userName);
		u.setPassword(passwordEncoder.encode(password));

		String rolEncontrado = "";
		Set<Rol> roles = new HashSet<>();
		for (Rol r: usuario.getRoles()) {
			rolEncontrado = r.getTipo();
		}
		Rol rol = rolService.buscarPorTipo(rolEncontrado);
		System.out.println(rol);
		roles.add(rol);

		u.setRoles(roles);
		usuarioService.guardarUsuario(u);
		flash.addAttribute("usuario", u);
		flash.addAttribute("idIntegrante", idPresi);

		return "redirect:/usuario-presidente/registrar";
	}

	//---------------------ENLACES-------------------------
	@PreAuthorize("hasRole('ADMINISTRADOR')")
	@GetMapping("/enlaces")
	public String mostrarEnlace(Model model, @ModelAttribute("enla") Usuario usuario) {
		model.addAttribute("enlaces", enlaceService.listarEnlaces());
		return "enlace";
	}

	@PreAuthorize("hasRole('ADMINISTRADOR')")
	@GetMapping(value = {"/enlace/registrar"})
	public String mostrarRegistroEnlace(Model model) {
		Enlace enlace = new Enlace();
		model.addAttribute("enlaces", enlace);
		model.addAttribute("usuarios", new Usuario());
		model.addAttribute("roles", rolService.listarRoles());
		model.addAttribute("municipios", municipioService.listarMunicipios());

		return "registrarEnlace";
	}

	@PreAuthorize("hasRole('ADMINISTRADOR')")
	@PostMapping(value = {"/enlace/registrar"})
	public String registroEnlace(@RequestParam(name = "foto", required = true) MultipartFile foto,
								 @Valid @ModelAttribute("enlaces") Enlace enlace,
								 BindingResult result, RedirectAttributes flash, @ModelAttribute("usuarioParaEnlace") Usuario usuario) {

		Usuario usuario1 = new Usuario();
		Rol rol = new Rol();
//		if (!foto.isEmpty()) {
//			String ruta = "C://Temp//uploads";
//			String nombreUnico = UUID.randomUUID() + " " + foto.getOriginalFilename();
//			try {
//				byte[] bytes = foto.getBytes();
//				Path rutaAbsoluta = Paths.get(ruta + "//" + nombreUnico);
//				Files.write(rutaAbsoluta, bytes);
//				System.out.println("Enlace guardado correctamente");
//			} catch (Exception e) {
//				e.getCause().getMessage();
//			}
//		}
		for (int i=0;i<usuarioService.listarUsuarios().size();i++)  {
			usuario1 = usuarioService.listarUsuarios().get(i);
		}

		for (int i=0;i<rolService.listarRoles().size();i++)  {

			if (rolService.listarRoles().get(i).getTipo().equals("ROLE_ENLACE")){
				rol = rolService.listarRoles().get(i);
			}
			rol = rolService.listarRoles().get(i);
		}

		usuario1.setRoles((Set<Rol>) rol);
		enlace.setUsuario(usuario1);
		enlace.setFotografia("nombreUnico");
		enlaceService.guardarEnlaces(enlace);
		flash.addAttribute("enla", usuario);

		return "redirect:/enlaces";
	}
	@PreAuthorize("hasRole('ADMINISTRADOR')")
	@GetMapping(value = {"/enlace/actualizar/{id}"})
	public String mostrarActualizarEnlace(@PathVariable Long id, Model model) {

		model.addAttribute("enlaces", enlaceService.mostrarEnlacePorId(id));
		model.addAttribute("municipios", municipioService.listarMunicipios());

		return "editarEnlace";
	}
	@PreAuthorize("hasRole('ADMINISTRADOR')")
	@PostMapping(value = {"/enlace/actualizar/{id}"})
	public String actualizarEnlace(@PathVariable Long id,@Valid @ModelAttribute("enlaces") Enlace enlace,
								 BindingResult result, Model model) {

		Enlace enlaceExistente = enlaceService.mostrarEnlacePorId(id);
		enlaceExistente.setId(id);
		enlaceExistente.setNombreCompleto(enlace.getNombreCompleto());
		enlaceExistente.setNumeroEmpleado(enlace.getNumeroEmpleado());
		enlaceExistente.setFotografia(enlace.getFotografia());
		enlaceExistente.setTelefono(enlace.getTelefono());
		enlaceExistente.setCorreo(enlace.getCorreo());
		enlaceExistente.setMunicipio(enlace.getMunicipio());
		enlaceExistente.setUsuario(enlace.getUsuario());

		enlaceService.actualizarEnlace(enlaceExistente);

		return "redirect:/enlaces";
	}
	@PreAuthorize("hasRole('ADMINISTRADOR')")
	@GetMapping(value = {"/enlace/{id}"})
	public String mostrarEliminarEnlace(@PathVariable Long id) {

		enlaceService.eliminarEnlace(id);

		return "redirect:/enlaces";
	}

	//---------------------MUNICIPIOS-------------------------
	@PreAuthorize("hasRole('ADMINISTRADOR')")
	@GetMapping(value = {"/municipios"})
	public String mostrarMunicipio(Model model) {

		model.addAttribute("municipios", municipioService.listarMunicipios());

		return "municipio";
	}

	@PreAuthorize("hasRole('ADMINISTRADOR')")
	@GetMapping(value = {"/municipio/registrar"})
	public String mostrarRegistroMunicipio(Model model) {
		Municipio municipio = new Municipio();
		model.addAttribute("municipios", municipio);

		return "registrarMunicipio";
	}

	@PreAuthorize("hasRole('ADMINISTRADOR')")
	@PostMapping(value = {"/municipio/registrar"})
	public String registroMunicipio(@Valid @ModelAttribute("municipios") Municipio municipio,
								 BindingResult result) {

		if(result.hasErrors()){
			return "registrarMunicipio";
		}
		municipioService.guardarMunicipio(municipio);

		return "redirect:/municipios";
	}

	@PreAuthorize("hasRole('ADMINISTRADOR')")
	@GetMapping(value = {"/municipio/actualizar/{id}"})
	public String mostrarActualizarMunicipio(@PathVariable Long id, Model model) {

		model.addAttribute("municipios", municipioService.mostrarMunicipioPorId(id));

		return "editarMunicipio";
	}

	@PreAuthorize("hasRole('ADMINISTRADOR')")
	@PostMapping(value = {"/municipio/actualizar/{id}"})
	public String actualizarMunicipio(@PathVariable Long id,@Valid @ModelAttribute("municipios") Municipio municipio,
								   BindingResult result, Model model) {

		Municipio municipioExistente = municipioService.mostrarMunicipioPorId(id);
		municipioExistente.setId(id);
		municipioExistente.setNombre(municipio.getNombre());

		municipioService.actualizarMunicipio(municipioExistente);

		return "redirect:/municipios";
	}

	@PreAuthorize("hasRole('ADMINISTRADOR')")
	@GetMapping(value = {"/municipio/{id}"})
	public String mostrarEliminarMunicipio(@PathVariable Long id) {

		municipioService.eliminarMunicipio(id);

		return "redirect:/municipios";
	}

	//---------------------CATEGORIAS-------------------------
	@PreAuthorize("hasRole('ADMINISTRADOR')")
	@GetMapping(value = {"/categorias"})
	public String mostrarCategorias(Model model) {

		model.addAttribute("categorias", categoriaService.listarCategorias());

		return "categoria";
	}

	@PreAuthorize("hasRole('ADMINISTRADOR')")
	@GetMapping(value = {"/categoria/registrar"})
	public String mostrarRegistroCategoria(Model model) {
		Categoria categoria = new Categoria();
		model.addAttribute("categorias", categoria);

		return "registrarCategoria";
	}

	@PreAuthorize("hasRole('ADMINISTRADOR')")
	@PostMapping(value = {"/categoria/registrar"})
	public String registroCategoria(@Valid @ModelAttribute("categorias") Categoria categoria,
									BindingResult result) {

		if(result.hasErrors()){
			return "registrarCategoria";
		}
		categoriaService.guardarCategoria(categoria);
		return "redirect:/categorias";
	}

	@PreAuthorize("hasRole('ADMINISTRADOR')")
	@GetMapping(value = {"/categoria/actualizar/{id}"})
	public String mostrarActualizarCategoria(@PathVariable Long id, Model model) {

		model.addAttribute("categorias", categoriaService.mostrarCategoriaPorId(id));

		return "editarCategoria";
	}

	@PreAuthorize("hasRole('ADMINISTRADOR')")
	@PostMapping(value = {"/categoria/actualizar/{id}"})
	public String actualizarCategoria(@PathVariable Long id,@Valid @ModelAttribute("categorias") Categoria categoria,
									  BindingResult result) {

		Categoria categoriaExistente = categoriaService.mostrarCategoriaPorId(id);
		categoriaExistente.setId(id);
		categoriaExistente.setNombre(categoria.getNombre());

		categoriaService.actualizarCategoria(categoriaExistente);

		return "redirect:/categorias";
	}

	@PreAuthorize("hasRole('ADMINISTRADOR')")
	@GetMapping(value = {"/categoria/{id}"})
	public String mostrarEliminarCategoria(@PathVariable Long id) {

		categoriaService.eliminarCategoria(id);

		return "redirect:/categorias";
	}
}
