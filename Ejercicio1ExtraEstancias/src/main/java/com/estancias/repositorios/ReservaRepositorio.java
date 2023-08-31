package com.estancias.repositorios;

import com.estancias.entidades.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservaRepositorio extends JpaRepository<Reserva, String> {

    @Query("SELECT r FROM Reserva r WHERE r.usuario.id = :id")
    public Reserva buscarPorIdUsuario(@Param("id") String id);

    @Query("SELECT r FROM Reserva r WHERE r.casa.id = :id")
    public Reserva buscarReservaPorCasa(@Param("id") String id);

}
