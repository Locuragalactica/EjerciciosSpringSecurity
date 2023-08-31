package com.biblioteca.Biblioteca.servicios;

import com.biblioteca.Biblioteca.entidades.Autor;
import com.biblioteca.Biblioteca.entidades.Editorial;
import com.biblioteca.Biblioteca.entidades.Libro;
import com.biblioteca.Biblioteca.excepciones.MiException;
import com.biblioteca.Biblioteca.repositorios.AutorRepositorio;
import com.biblioteca.Biblioteca.repositorios.EditorialRepositorio;
import com.biblioteca.Biblioteca.repositorios.LibroRepositorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LibroServicio {

    @Autowired
    private LibroRepositorio libroRepositorio;
    @Autowired
    private AutorRepositorio autorRepositorio;
    @Autowired
    private EditorialRepositorio editorialRepositorio;

    @Transactional
    public void crearLibro(Long isbn, String titulo, Integer ejemplares, String idAutor, String idEditorial) throws MiException {

        validar(isbn, titulo, ejemplares, idAutor, idEditorial);

        Autor autor = autorRepositorio.findById(idAutor).get();
        Editorial editorial = editorialRepositorio.findById(idEditorial).get();

        Libro libro = new Libro();

        libro.setIsbn(isbn);
        libro.setTitulo(titulo);
        libro.setEjemplares(ejemplares);
        libro.setAlta(new Date());
        libro.setAutor(autor);
        libro.setEditorial(editorial);

        libroRepositorio.save(libro);
    }

    public List<Libro> listarLibros() {
        List<Libro> libros = new ArrayList();
        libros = libroRepositorio.findAll();

        return libros;
    }

    /*@RequestParam(required = false) Long isbn, @RequestParam String titulo,
            @RequestParam(required = false) Integer ejemplares, @RequestParam String idAutor,
            @RequestParam String idEditorial, ModelMap modelo*/
    //(Long isbn, String titulo, String idAutor, String idEditorial, Integer ejemplares)
    @Transactional
    public void modificarLibro(Long isbn, String titulo, String idAutor, String idEditorial, Integer ejemplares) throws MiException {

        validar(isbn, titulo, ejemplares, idAutor, idEditorial);

        Optional<Libro> respuesta = libroRepositorio.findById(isbn);

        if (respuesta.isPresent()) {
            Libro libro = respuesta.get();

            if (titulo != null && !titulo.isEmpty()) {
                libro.setTitulo(titulo);
            } else {
                throw new MiException("El Titulo no puede estar vacio");
            }
            if (ejemplares != null) {
                libro.setEjemplares(ejemplares);
            } else {
                throw new MiException("El Cantidad de ejemplares no puede estar vacia");
            }

            if (idAutor != null) {
                Optional<Autor> respuestaAutor = autorRepositorio.findById(idAutor);
                if (respuestaAutor.isPresent()) {
                    libro.setAutor(respuestaAutor.get());
                }
            }

            if (idEditorial != null) {
                Optional<Editorial> respuestaEditorial = editorialRepositorio.findById(idEditorial);
                if (respuestaEditorial.isPresent()) {
                    libro.setEditorial(respuestaEditorial.get());
                }
            }
            libroRepositorio.save(libro);

        }
    }

    private void validar(Long isbn, String titulo, Integer ejemplares, String idAutor, String idEditorial) throws MiException {
        if (isbn == null) {
            throw new MiException("El ISBN no puede ser nulo");
        }

        if (titulo == null || titulo.isEmpty()) {
            throw new MiException("El Titulo no puede ser nulo o estar vacio");
        }

        if (ejemplares == null) {
            throw new MiException("Los ejemplares no pueden ser nulos");
        }

        if (idAutor.isEmpty() || idAutor == null) {
            throw new MiException("El Autor no puede ser nulo o estar vacio");
        }

        if (idEditorial.isEmpty() || idEditorial == null) {
            throw new MiException("La Editorial no puede ser nulo o estar vacio");
        }
    }

    public Libro getOne(Long id) {
        return libroRepositorio.getOne(id);
    }

    @Transactional
    public void eliminarLibro(Long isbn) throws MiException {
        if (isbn == null) {
            throw new MiException("El ID de la noticia no puede ser nulo o estar vacio");
        }
        Optional<Libro> respuesta = libroRepositorio.findById(isbn);

        if (respuesta.isPresent()) {
            libroRepositorio.delete(respuesta.get());
        }
    }

}
