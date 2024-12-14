package io.gitlab.rolfan.model;

import java.util.ArrayList;

public class Equipo {
    public String nombre;
    public String iniciales;
    public ArrayList<Participacion> participaciones;

    public Equipo(String nombre, String iniciales) {
        this.nombre = nombre;
        this.iniciales = iniciales;
        this.participaciones = new ArrayList<>();
    }
}