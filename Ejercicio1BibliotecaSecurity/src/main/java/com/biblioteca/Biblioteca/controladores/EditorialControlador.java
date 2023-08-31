package com.biblioteca.Biblioteca.controladores;

import com.biblioteca.Biblioteca.entidades.Editorial;
import com.biblioteca.Biblioteca.excepciones.MiException;
import com.biblioteca.Biblioteca.servicios.EditorialServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/editorial")
public class EditorialControlador {

    @Autowired
    private EditorialServicio editorialServicio;

    @GetMapping("/registrar") //localhost:8080/autor/registrar
    public String registrar() {
        return "editorial_form.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, ModelMap modelo) {

        try {
            editorialServicio.crearEditorial(nombre);

            modelo.put("exito", "La Editorial fue registrada correctamente!");
        } catch (MiException ex) {

            modelo.put("error", ex.getMessage());
            return "editorial_form.html";
        }

        return "index.html";
    }

    @GetMapping("/lista")
    public String listar(ModelMap modelo) {

        List<Editorial> editoriales = editorialServicio.listarEditoriales();

        modelo.addAttribute("editoriales", editoriales);

        return "editorial_list.html";
    }

    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, ModelMap modelo) {
        modelo.put("editorial", editorialServicio.getOne(id));

        return "editorial_modificar.html";
    }

    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, String nombre, ModelMap modelo) {
        try {
            if (nombre != null && !nombre.isEmpty()) {
                editorialServicio.modificarEditorial(nombre, id);
                return "redirect:../lista";
            } else {
                modelo.put("error", "El nombre de la editorial  no puede estar vacío.");
                modelo.put("autor", editorialServicio.getOne(id));  // Agregar esto para mantener los datos en el formulario
                return "autor_modificar.html";
            }

        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            return "editorial_modificar.html";
        }

    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable String id, ModelMap modelo) throws MiException {

        modelo.put("libro", editorialServicio.getOne(id));
        try {

            editorialServicio.eliminarEditorial(id);
            modelo.addAttribute("exito", "La editorial fue borrada correctamente!");

            return "redirect:../lista";

        } catch (MiException ex) {

            modelo.put("error", ex.getMessage());

            return "redirect:../lista";
        }

    }

}
