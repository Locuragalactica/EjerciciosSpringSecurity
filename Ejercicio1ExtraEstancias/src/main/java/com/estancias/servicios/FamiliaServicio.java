package com.estancias.servicios;

import com.estancias.entidades.Casa;
import com.estancias.entidades.Familia;
import com.estancias.entidades.Reserva;
import com.estancias.entidades.Usuario;
import com.estancias.excepciones.MiException;
import com.estancias.repositorios.CasaRepositorio;
import com.estancias.repositorios.FamiliaRepositorio;
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
public class FamiliaServicio {

    @Autowired
    private FamiliaRepositorio familiaRepositorio;
    @Autowired
    private ReservaRepositorio reservaRepositorio;
    @Autowired
    private ReservaServicio reservaServicio;
    @Autowired
    private CasaServicio casaServicio;
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    @Autowired
    private CasaRepositorio casaRepositorio;

    @Transactional
    public void registrar(String idUsuario, Integer edadMin, Integer edadMax, Integer numHijos) throws MiException {

        validar(edadMin, edadMax, numHijos);

        Familia familia = new Familia();
        Usuario user = usuarioRepositorio.findById(idUsuario).get();
        familia.setUsuario(user);
        familia.setNombre(user.getNombre());
        familia.setEmail(user.getEmail());
        familia.setEdadMin(edadMin);
        familia.setEdadMax(edadMax);
        familia.setNumHijos(numHijos);
//      usuario.setPassword(new BCryptPasswordEncoder().encode(password));

        familiaRepositorio.save(familia);
    }

    @Transactional
    public void actualizar(String idFamilia, String nombre, String email, Integer edadMin, Integer edadMax, Integer numHijos) throws MiException {

        Optional<Familia> respuesta = familiaRepositorio.findById(idFamilia);
        if (respuesta.isPresent()) {

            Familia familia = respuesta.get();

            familia.setNombre(nombre);
            familia.setEmail(email);
            familia.setEdadMin(edadMin);
            familia.setEdadMax(edadMax);
            familia.setNumHijos(numHijos);
// usuario.setPassword(new BCryptPasswordEncoder().encode(password));

            familiaRepositorio.save(familia);
        }

    }

    @Transactional
    public void registrarCasa(String idFamilia, String nombre, String email, Integer edadMin, Integer edadMax, Integer numHijos, String idCasa) throws MiException {

        Optional<Familia> respuesta = familiaRepositorio.findById(idFamilia);
        if (respuesta.isPresent()) {

            Familia familia = respuesta.get();
            Casa casa = casaRepositorio.findById(idCasa).get();
            familia.setCasa(casa);
// usuario.setPassword(new BCryptPasswordEncoder().encode(password));

            familiaRepositorio.save(familia);
        }

    }

    @Transactional
    public void eliminarFamilia(String idFamilia) throws MiException {

        if (idFamilia.isEmpty() || idFamilia == null) {
            throw new MiException("El Id de la familia no puede ser nula o estar vacia");
        }

        Optional<Familia> respuesta = familiaRepositorio.findById(idFamilia);

        if (respuesta.isPresent()) {
            Reserva reserva = reservaRepositorio.buscarReservaPorCasa(respuesta.get().getCasa().getId());
            reservaServicio.eliminarReserva(reserva.getId()); //Eliminación de la reserva
            casaServicio.eliminarCasa(respuesta.get().getCasa().getId()); //Eliminación de la casa
            familiaRepositorio.delete(respuesta.get()); //Eliminación de la familia
        }
    }

    public Familia getOne(String id) {
        return familiaRepositorio.getOne(id);
    }

    public List<Familia> listarFamilias() {

        List<Familia> familias = new ArrayList();

        familias = familiaRepositorio.findAll();

        return familias;
    }

    private void validar(Integer edadMin, Integer edadMax, Integer numHijos) throws MiException {

        if (edadMin < 0 || edadMin == null) {
            throw new MiException("La edad no puede ser menor a cero o estar vacía");
        }

        if (edadMax == null || edadMax < edadMin) {
            throw new MiException("La edad debe ser mayor o igual a la edad minima y no puede estar vacía");
        }

        if (numHijos < 0) {
            throw new MiException("La cantidad de hijos puede ser menor a cero");
        }

    }

//    @Override
//    public UserDetails loadUserByUsername(String nombre) throws UsernameNotFoundException {
//
//        Usuario usuario = usuarioRepositorio.buscarPorNombre(nombre);
//
//        if (usuario != null) {
//
//            List<GrantedAuthority> permisos = new ArrayList();
//
//            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + usuario.getRol().toString()); //ROLE_USER por ejemplo
//
//            permisos.add(p);
//
//            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
//
//            HttpSession session = attr.getRequest().getSession(true);
//
//            session.setAttribute("usuariosession", usuario);
//
//            return new User(usuario.getNombre(), usuario.getPassword(), permisos);
//        } else {
//            return null;
//        }
//    }
}
