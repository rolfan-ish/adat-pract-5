package io.gitlab.rolfan.model;

public class Participacion {
    public int edad;
    public Medalla medalla;
    public Evento evento;
    public Deportista deportista;
    public Equipo equipo;

    public Participacion(int edad, Medalla medalla, Evento evento, Deportista deportista, Equipo equipo) {
        this.edad = edad;
        this.medalla = medalla;
        this.evento = evento;
        this.deportista = deportista;
        this.equipo = equipo;
    }

    public enum Medalla {
        ORO, PLATA, BRONCE
    }
}
