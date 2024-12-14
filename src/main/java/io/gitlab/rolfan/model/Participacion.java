package io.gitlab.rolfan.model;

public class Participacion {
    public int edad;
    public String medalla;
    public Evento evento;
    public Deportista deportista;
    public Equipo equipo;

    public Participacion(int edad, String medalla, Evento evento, Deportista deportista, Equipo equipo) {
        this.edad = edad;
        this.medalla = medalla;
        this.evento = evento;
        this.deportista = deportista;
        this.equipo = equipo;
    }

    public static final String ORO = "oro";
    public static final String PLATA = "plata";
    public static final String BRONCE = "bronce";
}
