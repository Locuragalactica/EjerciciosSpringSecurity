package com.estancias.controladores;

import com.estancias.excepciones.MiException;
import com.estancias.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/")
public class PortalControlador {

    @Autowired
    private UsuarioServicio usuarioservicio;

    @GetMapping("/")
    public String index() {

        return "index.html";
    }

    @GetMapping("/registrar")
    public String registrar() {
        return "registroUsuario.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, @RequestParam String calle, @RequestParam String numero, @RequestParam String codigoPostal, @RequestParam String ciudad, @RequestParam String pais, @RequestParam String email, @RequestParam String clave, @RequestParam String clave2, ModelMap modelo) throws MiException {

        try {
            usuarioservicio.registrar(nombre, calle, numero, codigoPostal, ciudad, pais, email, clave, clave2);

            modelo.put("exito", "Usuario registrado correctamente");

            return "index.html";
        } catch (MiException ex) {

            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("calle", calle);
            modelo.put("numero", numero);
            modelo.put("codigoPostal", codigoPostal);
            modelo.put("ciudad", ciudad);
            modelo.put("pais", pais);
            modelo.put("email", email);

            return "registroUsuario.html";
        }

    }

}
