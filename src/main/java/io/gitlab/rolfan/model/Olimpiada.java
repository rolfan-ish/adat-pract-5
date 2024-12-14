package io.gitlab.rolfan.model;


import java.util.ArrayList;

public class Olimpiada {
    public String nombre;
    public int anio;
    public Temporada temporada;
    public String ciudad;
    public ArrayList<Deporte> deportes;
    public enum Temporada {
        INVIERNO,
        VERANO
    }
}
