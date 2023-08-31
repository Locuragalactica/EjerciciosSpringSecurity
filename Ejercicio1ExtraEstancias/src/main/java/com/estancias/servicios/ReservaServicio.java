package com.estancias.servicios;

import com.estancias.entidades.Casa;
import com.estancias.entidades.Familia;
import com.estancias.entidades.Reserva;
import com.estancias.entidades.Usuario;
import com.estancias.excepciones.MiException;
import com.estancias.repositorios.CasaRepositorio;
import com.estancias.repositorios.ReservaRepositorio;
import com.estancias.repositorios.UsuarioRepositorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReservaServicio {

    @Autowired
    private ReservaRepositorio reservaRepositorio;
    @Autowired
    private CasaRepositorio casaRepositorio;
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Transactional
    public void registrar(Date fechaDesde, Date fechaHasta, String idUsuario, String idCasa) throws MiException {

        validar(fechaDesde, fechaHasta);

        Reserva reserva = new Reserva();
        Casa casa = casaRepositorio.findById(idCasa).get();
        reserva.setCasa(casa);

        reserva.setFechaDesde(fechaDesde);
        reserva.setFechaHasta(fechaHasta);
        reserva.setPendiente(Boolean.TRUE);
        Usuario usuario = usuarioRepositorio.findById(idUsuario).get();
        reserva.setUsuario(usuario);

        reservaRepositorio.save(reserva);
    }

    @Transactional
    public void modificar(String idReserva, Date fechaDesde, Date fechaHasta) throws MiException {

        Reserva reserva = reservaRepositorio.findById(idReserva).get();

        reserva.setFechaDesde(fechaDesde);
        reserva.setFechaHasta(fechaHasta);

        reservaRepositorio.save(reserva);
    }

    @Transactional
    public void eliminarReserva(String idReserva) throws MiException {

        if (idReserva.isEmpty() || idReserva == null) {
            throw new MiException("El Id de la reserva no puede ser nula o estar vacia");
        }

        Optional<Reserva> respuesta = reservaRepositorio.findById(idReserva);

        if (respuesta.isPresent()) {
            reservaRepositorio.delete(respuesta.get());
        }
    }

    public Reserva getOne(String id) {
        return reservaRepositorio.getOne(id);
    }

    @Transactional(readOnly = true)
    public List<Reserva> listarReservas() {

        List<Reserva> reservas = new ArrayList();

        reservas = reservaRepositorio.findAll();

        return reservas;
    }

    private void validar(Date fechaDesde, Date fechaHasta) throws MiException {

        if (fechaDesde == null) {
            throw new MiException("La fecha desde no puede ser nula");
        }

        if (fechaHasta == null) {
            throw new MiException("La fecha hasta no puede ser nula");
        }
    }
}
