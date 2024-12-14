package io.gitlab.rolfan.model;


import java.util.ArrayList;

public class Olimpiada {
    public String nombre;
    public int anio;
    public Temporada temporada;
    public String ciudad;
    public ArrayList<Deporte> deportes;

    public Olimpiada(String nombre, int anio, Temporada temporada, String ciudad) {
        this.nombre = nombre;
        this.anio = anio;
        this.temporada = temporada;
        this.ciudad = ciudad;
        this.deportes = new ArrayList<>();
    }

    public Olimpiada(Temporada temporada) {
        this.temporada = temporada;
    }

    public enum Temporada {
        INVIERNO,
        VERANO
    }
}
