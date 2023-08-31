package com.biblioteca.Biblioteca.servicios;

import com.biblioteca.Biblioteca.entidades.Editorial;
import com.biblioteca.Biblioteca.excepciones.MiException;
import com.biblioteca.Biblioteca.repositorios.EditorialRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EditorialServicio {

    @Autowired
    EditorialRepositorio editorialRepositorio;

    @Transactional
    public void crearEditorial(String nombre) throws MiException {
        if (nombre.isEmpty() || nombre == null) {
            throw new MiException("El nombre de la editorial no puede ser nulo o estar vacio");
        }
        Editorial editorial = new Editorial();
        editorial.setNombre(nombre);

        editorialRepositorio.save(editorial);
    }

    public List<Editorial> listarEditoriales() {
        List<Editorial> editoriales = new ArrayList();

        editoriales = editorialRepositorio.findAll();

        return editoriales;
    }

    @Transactional
    public void modificarEditorial(String nombre, String id) throws MiException {
        validarEditorial(nombre, id);
        Optional<Editorial> respuesta = editorialRepositorio.findById(id);

        if (respuesta.isPresent()) {
            Editorial editorial = respuesta.get();

            if (editorial.getNombre() != null && !editorial.getNombre().isEmpty()) {
                editorial.setNombre(nombre);
                editorialRepositorio.save(editorial);
            } else {
                throw new MiException("El Nombre no puede estar vacio");
            }

        }
    }

    private void validarEditorial(String nombre, String id) throws MiException {
        if (nombre.isEmpty() || nombre == null) {
            throw new MiException("El nombre no puede ser nulo o estar vacio");
        }

        if (id.isEmpty() || id == null) {
            throw new MiException("El id no puede ser nulo o estar vacio");
        }
    }

    public Editorial getOne(String id) {
        return editorialRepositorio.getOne(id);
    }

    @Transactional
    public void eliminarEditorial(String id) throws MiException {
        if (id == null || id.isEmpty()) {
            throw new MiException("El ID de la Editorial no puede ser nulo o estar vacio");
        }
        Optional<Editorial> respuesta = editorialRepositorio.findById(id);

        if (respuesta.isPresent()) {
            editorialRepositorio.delete(respuesta.get());
        }
    }

}
