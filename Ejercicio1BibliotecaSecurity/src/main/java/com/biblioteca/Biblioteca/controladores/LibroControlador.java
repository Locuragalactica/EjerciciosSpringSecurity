package com.biblioteca.Biblioteca.controladores;

import com.biblioteca.Biblioteca.entidades.Autor;
import com.biblioteca.Biblioteca.entidades.Editorial;
import com.biblioteca.Biblioteca.entidades.Libro;
import com.biblioteca.Biblioteca.excepciones.MiException;
import com.biblioteca.Biblioteca.servicios.AutorServicio;
import com.biblioteca.Biblioteca.servicios.EditorialServicio;
import com.biblioteca.Biblioteca.servicios.LibroServicio;
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
@RequestMapping("/libro")
public class LibroControlador {

    @Autowired
    private LibroServicio libroServicio;
    @Autowired
    private AutorServicio autorServicio;
    @Autowired
    private EditorialServicio editorialServicio;

    @GetMapping("/registrar") //localhost:8080/libro/registrar
    public String registrar(ModelMap modelo) {
        List<Autor> autores = autorServicio.listarAutores();
        List<Editorial> editoriales = editorialServicio.listarEditoriales();

        modelo.addAttribute("autores", autores);
        modelo.addAttribute("editoriales", editoriales);

        return "libro_form.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam(required = false) Long isbn, @RequestParam String titulo,
            @RequestParam(required = false) Integer ejemplares, @RequestParam String idAutor,
            @RequestParam String idEditorial, ModelMap modelo) {
        try {

            libroServicio.crearLibro(isbn, titulo, ejemplares, idAutor, idEditorial);

            modelo.put("exito", "El Libro fue cargado correctamente!");

        } catch (MiException ex) {
            List<Autor> autores = autorServicio.listarAutores();
            List<Editorial> editoriales = editorialServicio.listarEditoriales();

            modelo.addAttribute("autores", autores);
            modelo.addAttribute("editoriales", editoriales);
            modelo.put("error", ex.getMessage());

            return "libro_form.html";  // volvemos a cargar el formulario.
        }
        return "index.html";
    }

    @GetMapping("/lista")
    public String listar(ModelMap modelo) {
        List<Libro> libros = libroServicio.listarLibros();
        modelo.addAttribute("libros", libros);

        return "libro_list";
    }

    @GetMapping("/modificar/{isbn}")
    public String modificar(@PathVariable Long isbn, ModelMap modelo) {

        modelo.put("libro", libroServicio.getOne(isbn));
        List<Autor> autores = autorServicio.listarAutores();
        List<Editorial> editoriales = editorialServicio.listarEditoriales();

        modelo.addAttribute("autores", autores);
        modelo.addAttribute("editoriales", editoriales);

        return "libro_modificar.html";
    }

    @PostMapping("/modificar/{isbn}")
    public String modificar(@PathVariable Long isbn, String titulo, Integer ejemplares, String idAutor, String idEditorial, ModelMap modelo) {
        try {
            if (idAutor == null || idEditorial == null || idAutor.isEmpty() || idEditorial.isEmpty()) {
                throw new MiException("Debes seleccionar un autor y una editorial.");
            }

            if (titulo == null || titulo.isEmpty()) {
                throw new MiException("El Titulo no puede estar vacio");
            }

            if (ejemplares == null) {
                throw new MiException("El Cantidad de ejemplares no puede estar vacia");
            }

            libroServicio.modificarLibro(isbn, titulo, idAutor, idEditorial, ejemplares);

            return "redirect:../lista";
        } catch (MiException ex) {
            List<Autor> autores = autorServicio.listarAutores();
            List<Editorial> editoriales = editorialServicio.listarEditoriales();

            modelo.addAttribute("autores", autores);
            modelo.addAttribute("editoriales", editoriales);
            modelo.put("error", ex.getMessage());

            Libro libro = libroServicio.getOne(isbn);
            modelo.addAttribute("libro", libro);

            return "libro_modificar.html";
        }
    }

    @GetMapping("/eliminar/{isbn}")
    public String eliminar(@PathVariable Long isbn, ModelMap modelo) throws MiException {

        modelo.put("libro", libroServicio.getOne(isbn));
        try {

            libroServicio.eliminarLibro(isbn);
            modelo.addAttribute("exito", "El libro fue borrado correctamente!");

            return "redirect:../lista";

        } catch (MiException ex) {

            modelo.put("error", ex.getMessage());

            return "redirect:../lista";
        }

    }

}
