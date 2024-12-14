package io.gitlab.rolfan.model;


import java.util.ArrayList;

public class Olimpiada {
    public String nombre;
    public int anio;
    public String temporada;
    public String ciudad;
    public ArrayList<Deporte> deportes;

    public Olimpiada(String nombre, int anio, String temporada, String ciudad) {
        this.nombre = nombre;
        this.anio = anio;
        this.temporada = temporada;
        this.ciudad = ciudad;
        this.deportes = new ArrayList<>();
    }

    public Olimpiada(String temporada) {
        this.temporada = temporada;
    }

    public static final String VERANO = "verano";
    public static final String INVIERNO = "invierno";
}
