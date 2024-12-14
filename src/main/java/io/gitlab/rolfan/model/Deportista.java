package io.gitlab.rolfan.model;

import java.util.OptionalInt;

public class Deportista {
    public String nombre;
    public String sexo;
    public Integer peso;
    public Integer altura;
    public Medalla medalla;
    public enum Medalla {
        ORO,
        PLATA,
        BRONCE
    }
}
