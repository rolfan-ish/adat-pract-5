package io.gitlab.rolfan.model;

import java.util.ArrayList;

public class Deportista {
    public String nombre;
    public String sexo;
    public Integer peso;
    public Integer altura;
    public ArrayList<Participacion> participaciones;

    public Deportista(String nombre, String sexo, Integer peso, Integer altura) {
        this.nombre = nombre;
        this.sexo = sexo;
        this.peso = peso;
        this.altura = altura;
        this.participaciones = new ArrayList<>();
    }

    public Deportista(String nombre) {
        this.nombre = nombre;
        this.participaciones = new ArrayList<>();
    }
}
