package io.gitlab.rolfan;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.query.Predicate;
import io.gitlab.rolfan.arg.MedallaArg;
import io.gitlab.rolfan.arg.TemporadaArg;
import io.gitlab.rolfan.jmenu.Entry;
import io.gitlab.rolfan.jmenu.Menu;
import io.gitlab.rolfan.jmenu.arg.Arg;
import io.gitlab.rolfan.jmenu.arg.IntArg;
import io.gitlab.rolfan.jmenu.arg.StringArg;
import io.gitlab.rolfan.model.*;

import java.util.List;

import static io.gitlab.rolfan.util.ListUtil.*;

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
        } catch (Exception e) {
            System.err.println("Valor invalido");
            return;
        }

        List<Olimpiada> olims = db.queryByExample(olim);
        forEachIndexed(olims, (i, o) -> System.out.printf("%d: %s\n", i, o.nombre));
        try {
            olim = olims.get(codigoOlim.get());
        } catch (Exception e) {
            System.err.println("Valor invalido");
            return;
        }

        forEachIndexed(olim.deportes, (i, e) -> System.out.printf("%d: %s\n", i, e.nombre));
        Deporte depor;
        try {
            depor = olim.deportes.get(codigoDepor.get());
        } catch (Exception e) {
            System.err.println("Valor invalido");
            return;
        }

        forEachIndexed(depor.eventos, (i, p) -> System.out.printf("%d: %s\n", i, p.nombre));
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
        evento.participaciones.forEach(d -> {
            System.out.println("Deportista:");
            System.out.println("\tNombre: " + d.deportista.nombre);
            System.out.println("\tAltura: " + d.deportista.altura);
            System.out.println("\tPeso: " + d.deportista.peso);
            System.out.println("\tEdad: " + d.edad);
            System.out.println("\tMedalla: " + d.medalla);
        });
    }

    @Entry(key = "2", desc = "Modificar medalla deportista", pos = 2)
    public void modificarMedalla(@Arg("Introduce el texto de busqueda:") StringArg textoBusqueda,
                                 @Arg("Introduce el código del deportista:") IntArg codigoDeportista,
                                 @Arg("Introduce el código del evento:") IntArg codigoEvento,
                                 @Arg("Introduce la nueva medalla (oro, plata, bronze, null):") MedallaArg medalla) {
        String busqueda;
        try {
            busqueda = textoBusqueda.get();
        } catch (Exception e) {
            System.err.println("Valor invalido");
            return;
        }
        var deportistas = db.query(new Predicate<Deportista>() {
            @Override
            public boolean match(Deportista o) {
                return o.nombre.contains(busqueda);
            }
        });

        forEachIndexed(deportistas, (i, d) -> System.out.printf("%d: %s\n", i, d.nombre));
        Deportista deportista;
        try {
            deportista = deportistas.get(codigoDeportista.get());
        } catch (Exception e) {
            System.err.println("Valor invalido");
            return;
        }

        forEachIndexed(deportista.participaciones, (i, p) -> System.out.printf("%d: %s\n", i, p.evento.nombre));
        Participacion participacion;
        try {
            participacion = deportista.participaciones.get(codigoEvento.get());
        } catch (Exception e) {
            System.err.println("Valor invalido");
            return;
        }

        try {
            participacion.medalla = medalla.get();
        } catch (Exception e) {
            System.err.println("Valor invalido");
            return;
        }
        db.store(participacion);
        db.commit();
    }
}
