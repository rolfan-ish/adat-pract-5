package io.gitlab.rolfan.model;


import java.util.ArrayList;

public class Olimpiada {
    public String nombre;
    public int anio;
    public Temporada temporada; // "Summer" or "Winter"
    public String ciudad;
    public ArrayList<Deporte> deportes; // One-to-many relationship

    public Olimpiada(String nombre, int anio, Temporada temporada, String ciudad) {
        this.nombre = nombre;
        this.anio = anio;
        this.temporada = temporada;
        this.ciudad = ciudad;
        this.deportes = new ArrayList<>(); // Initialize the list
    }

    public Olimpiada() {

    }

    public enum Temporada {
        INVIERNO,
        VERANO
    }
}
