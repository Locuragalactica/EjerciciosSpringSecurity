package com.biblioteca.Biblioteca.repositorios;

import com.biblioteca.Biblioteca.entidades.Libro;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LibroRepositorio extends JpaRepository<Libro, Long> {

    @Query("SELECT l FROM Libro l WHERE l.titulo= :titulo")
    public Libro buscarPorTitulo(@Param("titulo") String titulo);

    @Query("SELECT l FROM Libro l WHERE l.autor.nombre = :nombre")
    public List<Libro> buscarPorAutor(@Param("nombre") String nombre);

    @Query("SELECT l FROM Libro l WHERE l.isbn = :isbn")
    public List<Libro> buscarPorISBN(@Param("isbn") Long isbn);

}
