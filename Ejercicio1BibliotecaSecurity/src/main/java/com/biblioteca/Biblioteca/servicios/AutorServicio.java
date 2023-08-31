package com.biblioteca.Biblioteca.servicios;

import com.biblioteca.Biblioteca.entidades.Autor;
import com.biblioteca.Biblioteca.excepciones.MiException;
import com.biblioteca.Biblioteca.repositorios.AutorRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AutorServicio {

    @Autowired
    AutorRepositorio autorRepositorio;

    @Transactional
    public void crearAutor(String nombre) throws MiException {
        if (nombre.isEmpty() || nombre == null) {
            throw new MiException("El Nombre del autor no puede ser nulo o estar vacio");
        }

        Autor autor = new Autor();
        autor.setNombre(nombre);
        autorRepositorio.save(autor);
    }

    public List<Autor> listarAutores() {
        List<Autor> autores = new ArrayList();
        autores = autorRepositorio.findAll();
        return autores;
    }

    @Transactional
    public void modificarAutor(String nombre, String id) throws MiException {
        validarAutor(nombre, id);
        Optional<Autor> respuesta = autorRepositorio.findById(id);

        if (respuesta.isPresent()) {
            Autor autor = respuesta.get();

            if (autor.getNombre() != null && !autor.getNombre().isEmpty()) {
                autor.setNombre(nombre);
                autorRepositorio.save(autor);
            } else {

                throw new MiException("El Nombre no puede estar vacio");

            }

        }

    }

    private void validarAutor(String nombre, String id) throws MiException {
        if (nombre.isEmpty() || nombre == null) {
            throw new MiException("El nombre no puede ser nulo o estar vacio");
        }

        if (id.isEmpty() || id == null) {
            throw new MiException("El id no puede ser nulo o estar vacio");
        }
    }

    public Autor getOne(String id) {
        return autorRepositorio.getOne(id);
    }

    @Transactional
    public void eliminarAutor(String id) throws MiException {
        if (id == null || id.isEmpty()) {
            throw new MiException("El ID del Autor no puede ser nulo o estar vacio");
        }
        Optional<Autor> respuesta = autorRepositorio.findById(id);

        if (respuesta.isPresent()) {
            autorRepositorio.delete(respuesta.get());
        }
    }

}
