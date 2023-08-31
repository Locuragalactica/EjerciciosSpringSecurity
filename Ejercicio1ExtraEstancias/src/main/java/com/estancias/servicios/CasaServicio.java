package com.estancias.servicios;

import com.estancias.entidades.Casa;
import com.estancias.entidades.Familia;
import com.estancias.entidades.Reserva;
import com.estancias.entidades.Usuario;
import com.estancias.excepciones.MiException;
import com.estancias.repositorios.CasaRepositorio;
import com.estancias.repositorios.FamiliaRepositorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CasaServicio {

    @Autowired
    private CasaRepositorio casaRepositorio;

    @Transactional
    public void registrar(String calle, String codPostal, String ciudad, String pais, Integer numero, Integer maxDias, Integer minDias, Double precio, Date fechaDesde, Date fechaHasta) throws MiException {

        validar(calle, codPostal, ciudad, pais, numero, maxDias, minDias, precio, fechaDesde, fechaHasta);

        Casa casa = new Casa();

        casa.setCalle(calle);
        casa.setCodPostal(codPostal);
        casa.setCiudad(ciudad);
        casa.setPais(pais);
        casa.setNumero(numero);
        casa.setMaxDias(maxDias);
        casa.setMinDias(minDias);
        casa.setPrecio(precio);
        casa.setFechaDesde(fechaDesde);
        casa.setFechaHasta(fechaHasta);

        casaRepositorio.save(casa);
    }

    @Transactional
    public void actualizar(String idCasa, String calle, String codPostal, String ciudad, String pais, Integer numero, Integer maxDias, Integer minDias, Double precio, Date fechaDesde, Date fechaHasta) throws MiException {

        Optional<Casa> respuesta = casaRepositorio.findById(idCasa);
        if (respuesta.isPresent()) {

            Casa casa = respuesta.get();

            casa.setCalle(calle);
            casa.setCodPostal(codPostal);
            casa.setCiudad(ciudad);
            casa.setPais(pais);
            casa.setNumero(numero);
            casa.setMaxDias(maxDias);
            casa.setMinDias(minDias);
            casa.setPrecio(precio);
            casa.setFechaDesde(fechaDesde);
            casa.setFechaHasta(fechaHasta);

            casaRepositorio.save(casa);
        }

    }

    @Transactional
    public void eliminarCasa(String idCasa) throws MiException {

        if (idCasa.isEmpty() || idCasa == null) {
            throw new MiException("El Id de la casa no puede ser nula o estar vacia");
        }

        Optional<Casa> respuesta = casaRepositorio.findById(idCasa);

        if (respuesta.isPresent()) {
            casaRepositorio.delete(respuesta.get());
        }
    }

    public Casa getOne(String id) {
        return casaRepositorio.getOne(id);
    }

    public List<Casa> listarCasas() {

        List<Casa> casas = new ArrayList();

        casas = casaRepositorio.findAll();

        return casas;
    }

    private void validar(String calle, String codPostal, String ciudad, String pais, Integer numero, Integer maxDias, Integer minDias, Double precio, Date fechaDesde, Date fechaHasta) throws MiException {

        if (calle.isEmpty() || calle == null) {
            throw new MiException("La calle no puede ser nula o estar vacía");
        }

        if (codPostal.isEmpty() || codPostal == null) {
            throw new MiException("El Codigo Postal no puede ser nulo o estar vacío");
        }

        if (ciudad.isEmpty() || ciudad == null) {
            throw new MiException("La ciudad no puede ser nula o estar vacía");
        }

        if (pais.isEmpty() || pais == null) {
            throw new MiException("El país Postal no puede ser nulo o estar vacío");
        }

        if (minDias < 0 || minDias == null) {
            throw new MiException("El minimo de dias no puede ser menor a cero o estar vacía");
        }

        if (maxDias < minDias || maxDias == null) {
            throw new MiException("El Maximo de dias no puede ser menor al minimo de días o estar vacía");
        }

        if (numero == null || numero < 0) {
            throw new MiException("El numero no puede estar vacío o ser menor a cero");
        }

        if (precio < 0 || precio == null) {
            throw new MiException("El precio no puede ser nulo o ser menor a cero");
        }

        if (fechaDesde == null) {
            throw new MiException("La fecha desde no puede ser nula");
        }

        if (fechaHasta == null) {
            throw new MiException("La fecha hasta no puede ser nula");
        }
    }
}
