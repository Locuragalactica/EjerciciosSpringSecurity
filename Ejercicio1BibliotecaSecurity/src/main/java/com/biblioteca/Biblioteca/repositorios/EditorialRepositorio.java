package com.biblioteca.Biblioteca.repositorios;

import com.biblioteca.Biblioteca.entidades.Editorial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EditorialRepositorio extends JpaRepository <Editorial, String> {

}
