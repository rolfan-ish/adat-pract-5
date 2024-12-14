package io.gitlab.rolfan;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import io.gitlab.rolfan.arg.TemporadaArg;
import io.gitlab.rolfan.jmenu.Entry;
import io.gitlab.rolfan.jmenu.Menu;
import io.gitlab.rolfan.jmenu.arg.Arg;
import io.gitlab.rolfan.jmenu.arg.IntArg;
import io.gitlab.rolfan.model.Deporte;
import io.gitlab.rolfan.model.Evento;
import io.gitlab.rolfan.model.Olimpiada;

public class App {
    private final ObjectContainer db;

    public App(ObjectContainer db) {
        this.db = db;
    }

    public static void main(String[] args) {
        new Menu(new App(Db4oEmbedded.openFile("db.db4o"))).runMenuForever();
    }

    @Entry(key = "1", desc = "Listado de deportistas participantes", pos = 1)
    public void listado(@Arg("Introduce la temporada:") TemporadaArg temporada,
                        @Arg("Introduce el código de la edición olímpica:") IntArg codigoOlim,
                        @Arg("Introduce el código del deporte:") IntArg codigoDepor,
                        @Arg("Introduce el código del evento:") IntArg codigoEven) {
        var olim = new Olimpiada();
        try {
            olim.temporada = temporada.get();
            if (olim.temporada == null) throw new Exception();
        } catch (Exception e) {
            System.err.println("Valor invalido");
            return;
        }

        db.queryByExample(olim)
                .stream()
                .map(r -> (Olimpiada)r)
                .forEach(r -> System.out.printf("%d: %s %s\n", r.anio, r.nombre, r.ciudad));

        try {
            olim.anio = codigoOlim.get();
            olim = (Olimpiada) db.queryByExample(olim).getFirst();
            if (olim == null) throw new Exception();
        } catch (Exception e) {
            System.err.println("Valor invalido");
            return;
        }

        for (int i = 0; i < olim.deportes.size(); i++) {
            System.out.printf("%d: %s\n", i, olim.deportes.get(i).nombre);
        }
        Deporte depor;
        try {
            depor = olim.deportes.get(codigoDepor.get());
        } catch (Exception e) {
            System.err.println("Valor invalido");
            return;
        }

        for (int i = 0; i < depor.eventos.size(); i++) {
            System.out.printf("%d: %s\n", i, depor.eventos.get(i).nombre);
        }
        Evento evento;
        try {
            evento = depor.eventos.get(codigoEven.get());
        } catch (Exception e) {
            System.err.println("Valor invalido");
            return;
        }

        System.out.println("Temporada: " + olim.temporada);
        System.out.println("Edición olímpica: " + olim.nombre);
        System.out.println("Deporte: " + depor.nombre);
        System.out.println("Evento: " + evento.nombre);
        evento.deportistas.forEach(d -> {
            System.out.println("Deportista:");
            System.out.println("\tNombre: " + d.nombre);
            System.out.println("\tAltura: " + d.altura);
            System.out.println("\tPeso: " + d.peso);
            System.out.println("\tMedalla: " + d.medalla);
        });
    }
}
