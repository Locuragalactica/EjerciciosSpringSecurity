package com.biblioteca.Biblioteca.repositorios;

import com.biblioteca.Biblioteca.entidades.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AutorRepositorio extends JpaRepository <Autor, String> {

}
