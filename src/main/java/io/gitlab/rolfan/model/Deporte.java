package io.gitlab.rolfan.model;

import java.util.ArrayList;

public class Deporte {
    public String nombre;
    public ArrayList<Evento> eventos;

    public Deporte(String nombre) {
        this.nombre = nombre;
        this.eventos = new ArrayList<>();
    }
}

