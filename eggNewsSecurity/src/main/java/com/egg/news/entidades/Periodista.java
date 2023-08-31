package com.egg.news.entidades;

import com.egg.news.enumeraciones.Rol;
import java.util.Date;
import javax.persistence.Entity;

@Entity
public class Periodista extends Usuario {

    Integer sueldoMensual;

    public Periodista() {
    }

    public Periodista(Integer sueldoMensual, String id, String nombre, String password, Date fecha, Rol rol, Boolean activo) {
        super(id, nombre, password, fecha, rol, activo);
        this.sueldoMensual = sueldoMensual;
    }

    public Integer getSueldoMensual() {
        return sueldoMensual;
    }

    public void setSueldoMensual(Integer sueldoMensual) {
        this.sueldoMensual = sueldoMensual;
    }

}
