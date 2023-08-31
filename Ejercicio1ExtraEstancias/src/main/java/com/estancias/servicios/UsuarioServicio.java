package com.estancias.servicios;

import com.estancias.entidades.Reserva;
import com.estancias.entidades.Usuario;
import com.estancias.excepciones.MiException;
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
public class UsuarioServicio {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private ReservaRepositorio reservaRepositorio;

    @Autowired
    private ReservaServicio reservaServicio;

    @Transactional
    public void registrar(String nombre, String calle, String numero, String codigoPostal, String ciudad, String pais, String email, String clave, String clave2) throws MiException {

        validar(nombre, calle, numero, codigoPostal, ciudad, pais, email, clave, clave2);

        Usuario usuario = new Usuario();

        usuario.setNombre(nombre);
        usuario.setCalle(calle);
        usuario.setNumero(numero);
        usuario.setCodigoPostal(codigoPostal);
        usuario.setCiudad(ciudad);
        usuario.setPais(pais);
        usuario.setEmail(email);
        usuario.setClave(clave);
//      usuario.setPassword(new BCryptPasswordEncoder().encode(password));

        usuario.setFechaAlta(new Date());

        usuarioRepositorio.save(usuario);
    }

    @Transactional
    public void actualizar(String idUsuario, String nombre, String calle, String numero, String codigoPostal, String ciudad, String pais, String email, String clave, String clave2) throws MiException {

        Optional<Usuario> respuesta = usuarioRepositorio.findById(idUsuario);
        if (respuesta.isPresent()) {

            Usuario usuario = respuesta.get();

            usuario.setNombre(nombre);
            usuario.setCalle(calle);
            usuario.setNumero(numero);
            usuario.setCodigoPostal(codigoPostal);
            usuario.setCiudad(ciudad);
            usuario.setPais(pais);
            usuario.setEmail(email);
            usuario.setClave(clave);
            usuario.setFechaAlta(usuario.getFechaAlta());
// usuario.setPassword(new BCryptPasswordEncoder().encode(password));

            usuarioRepositorio.save(usuario);
        }

    }

    @Transactional
    public void eliminarUsuario(String id) throws MiException {

        if (id.isEmpty() || id == null) {
            throw new MiException("El Id del Usuario no puede ser nulo o estar vacio");
        }

        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);

        if (respuesta.isPresent()) {
            Reserva reserva = reservaRepositorio.buscarPorIdUsuario(id);
            reservaServicio.eliminarReserva(reserva.getId());
            usuarioRepositorio.delete(respuesta.get());
        }
    }

    public Usuario getOne(String id) {
        return usuarioRepositorio.getOne(id);
    }

    @Transactional(readOnly = true)
    public List<Usuario> listarUsuarios() {

        List<Usuario> usuarios = new ArrayList();

        usuarios = usuarioRepositorio.findAll();

        return usuarios;
    }

    private void validar(String nombre, String calle, String numero, String codigoPostal, String ciudad, String pais, String email, String clave, String clave2) throws MiException {

        if (nombre.isEmpty() || nombre == null) {
            throw new MiException("El nombre no puede ser nulo o estar vacío");
        }

        if (calle.isEmpty() || calle == null) {
            throw new MiException("La calle no puede ser nula o estar vacía");
        }

        if (numero.isEmpty() || numero == null) {
            throw new MiException("El numero no puede ser nulo o estar vacío");
        }

        if (codigoPostal.isEmpty() || codigoPostal == null) {
            throw new MiException("El Codigo Postal no puede ser nulo o estar vacío");
        }

        if (ciudad.isEmpty() || ciudad == null) {
            throw new MiException("La ciudad no puede ser nula o estar vacía");
        }

        if (pais.isEmpty() || pais == null) {
            throw new MiException("El País no puede ser nulo o estar vacío");
        }

        if (email.isEmpty() || email == null) {
            throw new MiException("El Email no puede ser nulo o estar vacío");
        }

        if (clave.isEmpty() || clave == null || clave.length() <= 5) {
            throw new MiException("La contraseña no puede estar vacía, y debe tener más de 5 dígitos");
        }

        if (!clave.equals(clave2)) {
            throw new MiException("Las contraseñas ingresadas deben ser iguales");
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
