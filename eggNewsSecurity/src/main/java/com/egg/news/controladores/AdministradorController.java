package com.egg.news.controladores;

import com.egg.news.entidades.Noticia;
import com.egg.news.entidades.Periodista;
import com.egg.news.entidades.Usuario;
import com.egg.news.excepciones.MiException;
import com.egg.news.servicios.NoticiaServicio;
import com.egg.news.servicios.PeriodistaServicio;
import com.egg.news.servicios.UsuarioServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdministradorController {

    @Autowired
    private NoticiaServicio noticiaServicio;

    @Autowired
    private UsuarioServicio usuarioServicio;

    @Autowired
    private PeriodistaServicio periodistaServicio;

    @GetMapping("/dashboard")
    public String panelAdministrativo(ModelMap modelo) {

        List<Noticia> noticias = noticiaServicio.listarNoticias();

        modelo.addAttribute("noticias", noticias);

        return "panel.html";
    }

    @GetMapping("/usuarios")
    public String listar(ModelMap modelo) {
        List<Usuario> usuarios = usuarioServicio.listarUsuarios();
        modelo.addAttribute("usuarios", usuarios);

        return "usuario_list.html";
    }

    @GetMapping("/periodistas")
    public String listarPeriodistas(ModelMap modelo) {
        List<Periodista> periodistas = periodistaServicio.listarPeriodistas();
        modelo.addAttribute("periodistas", periodistas);

        return "periodista_list.html";
    }

    @GetMapping("/modificarRol/{id}")
    public String cambiarRol(@PathVariable String id) {
        usuarioServicio.cambiarRol(id);

        return "redirect:/admin/usuarios";
    }

    @GetMapping("/modificarActivo/{id}")
    public String cambiarActivo(@PathVariable String id) {
        usuarioServicio.cambiarActivo(id);

        return "redirect:/admin/usuarios";
    }

    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, ModelMap modelo) {

        modelo.put("usuario", usuarioServicio.getOne(id));

        return "usuario_modificar.html";
    }

    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, String nombre, String password, String password2, ModelMap modelo) {

        try {
            usuarioServicio.actualizar(id, nombre, password, password2);

            //Ver esta linea si funciona?
            modelo.put("exito", "El Usuario fue modificado correctamente!");

            return "redirect:/admin/usuarios";

        } catch (MiException ex) {

            modelo.put("error", ex.getMessage());

            return "usuario_modificar.html";
        }

    }

    @GetMapping("/modificar/periodista/{id}")
    public String modificarPeriodista(@PathVariable String id, ModelMap modelo) {

        modelo.put("periodista", periodistaServicio.getOne(id));

        return "admin_modificar.html";
    }

    @PostMapping("/modificar/periodista/{id}")
    public String modificarPeriodista(@PathVariable String id, String nombre, Integer sueldo, ModelMap modelo) {

        try {
            periodistaServicio.modificarSueldo(id, nombre, sueldo);

            //Ver esta linea si funciona?
            modelo.put("exito", "El Usuario fue modificado correctamente!");

            return "redirect:/admin/periodistas";

        } catch (MiException ex) {

            modelo.put("error", ex.getMessage());

            return "admin_modificar.html";
        }

    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable String id, ModelMap modelo) throws MiException {

        try {

            usuarioServicio.eliminarUsuario(id);

            return "redirect:../usuarios";

        } catch (MiException ex) {

            modelo.put("error", ex.getMessage());

            //return "noticia_eliminar.html";
            return "redirect:../usuarios";
        }

    }

}
