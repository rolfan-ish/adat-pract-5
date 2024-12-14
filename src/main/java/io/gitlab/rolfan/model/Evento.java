package io.gitlab.rolfan.model;

import java.util.ArrayList;

public class Evento {
    public String nombre;
    public Olimpiada olimpiada;
    public Deporte deporte;
    public ArrayList<Participacion> participaciones;

    public Evento(String nombre, Olimpiada olimpiada, Deporte deporte) {
        this.nombre = nombre;
        this.olimpiada = olimpiada;
        this.deporte = deporte;
        this.participaciones = new ArrayList<>(); // Initialize the list
    }
}
