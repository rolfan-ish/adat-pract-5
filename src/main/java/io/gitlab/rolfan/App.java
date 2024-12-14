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

import static io.gitlab.rolfan.util.ListUtil.forEachIndexed;

public class App {
    private final ObjectContainer db;

    public App(ObjectContainer db) {
        this.db = db;
    }

    public static void main(String[] args) {
        new Menu(new App(Db4oEmbedded.openFile("db.db4o"))).runMenuForever();
    }

    public Evento buscarEvento(TemporadaArg temporada, IntArg codigoOlim, IntArg codigoDepor, IntArg codigoEven) {
        Olimpiada olim;
        try {
            olim = new Olimpiada(temporada.get());
        } catch (Exception e) {
            return null;
        }
        List<Olimpiada> olims = db.queryByExample(olim);
        forEachIndexed(olims, (i, o) -> System.out.printf("%d: %s\n", i, o.nombre));
        try {
            olim = olims.get(codigoOlim.get());
        } catch (Exception e) {
            return null;
        }

        forEachIndexed(olim.deportes, (i, e) -> System.out.printf("%d: %s\n", i, e.nombre));
        Deporte depor;
        try {
            depor = olim.deportes.get(codigoDepor.get());
        } catch (Exception e) {
            return null;
        }

        forEachIndexed(depor.eventos, (i, p) -> System.out.printf("%d: %s\n", i, p.nombre));
        try {
            return depor.eventos.get(codigoEven.get());
        } catch (Exception e) {
            return null;
        }
    }

    public List<Deportista> buscarDeportistas(StringArg textoBusqueda) {
        String busqueda;
        try {
            busqueda = textoBusqueda.get();
        } catch (Exception e) {
            return null;
        }
        return db.query(new Predicate<>() {
            @Override
            public boolean match(Deportista o) {
                return o.nombre.contains(busqueda);
            }
        });
    }

    public Deportista buscarDeportista(List<Deportista> deportistas, IntArg codigo) {
        forEachIndexed(deportistas, (i, d) -> System.out.printf("%d: %s\n", i, d.nombre));
        try {
            return deportistas.get(codigo.get());
        } catch (Exception e) {
            return null;
        }
    }

    public Participacion buscarParticipacion(StringArg textoBusqueda, IntArg codigoDeportista, IntArg codigoParticipacion) {
        var deportistas = buscarDeportistas(textoBusqueda);
        if (deportistas.isEmpty())
            return null;
        var deportista = buscarDeportista(deportistas, codigoDeportista);
        if (deportista == null)
            return null;

        forEachIndexed(deportista.participaciones, (i, p) -> System.out.printf("%d: %s\n", i, p.evento.nombre));
        try {
            return deportista.participaciones.get(codigoParticipacion.get());
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * @param temporada Temporada de las ediciones olímpicas a buscar
     * @param codigoOlim Índice de la edición olímpica de los resultados encontrados
     * @param codigoDepor Índice del deporte de los deportes de la edición seleccionada
     * @param codigoEven Índice del evento del deporte seleccionado
     */
    @Entry(key = "1", desc = "Listado de deportistas participantes", pos = 1)
    public void listado(@Arg("Introduce la temporada (winter/summer):") TemporadaArg temporada,
                        @Arg("Introduce el código de la edición olímpica:") IntArg codigoOlim,
                        @Arg("Introduce el código del deporte:") IntArg codigoDepor,
                        @Arg("Introduce el código del evento:") IntArg codigoEven) {
        var evento = buscarEvento(temporada, codigoOlim, codigoDepor, codigoEven);
        if (evento == null) {
            System.out.println("Valor invalido");
            return;
        }

        System.out.println("Temporada: " + evento.olimpiada.temporada);
        System.out.println("Edición olímpica: " + evento.olimpiada.nombre);
        System.out.println("Deporte: " + evento.deporte.nombre);
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

    /**
     * @param textoBusqueda Texto con el que localizar al deportista
     * @param codigoDeportista Índice del deportista de los resultados obtenidos
     * @param codigoParticipacion Índice de la participación de las participaciones del deportista seleccionado
     * @param medalla Valor de la nueva medalla
     */
    @Entry(key = "2", desc = "Modificar medalla deportista", pos = 2)
    public void modificarMedalla(@Arg("Introduce el texto de búsqueda:") StringArg textoBusqueda,
                                 @Arg("Introduce el código del deportista:") IntArg codigoDeportista,
                                 @Arg("Introduce el código del evento:") IntArg codigoParticipacion,
                                 @Arg("Introduce la nueva medalla (oro, plata, bronze, null):") MedallaArg medalla) {
        var participacion = buscarParticipacion(textoBusqueda, codigoDeportista, codigoParticipacion);
        if (participacion == null) {
            System.out.println("Valor invalido");
            return;
        }
        try {
            participacion.medalla = medalla.get();
        } catch (Exception e) {
            System.out.println("Valor invalido");
            return;
        }
        db.store(participacion);
        db.commit();
    }

    /**
     * @param textoBusqueda Texto con el que localizar al deportista
     * @param nombreDeportista Nombre del deportista en caso de que se tenga que crear
     * @param codigoDeportista Índice del deportista de los resultados obtenidos
     * @param temporada Temporada de las ediciones olímpicas a buscar
     * @param codigoOlim Índice de la edición olímpica de los resultados encontrados
     * @param codigoDeporte Índice del deporte de los deportes de la edición seleccionada
     * @param codigoEvento Índice del evento del deporte seleccionado
     */
    @Entry(key = "3", desc = "Añadir deportista/participación", pos = 3)
    public void aniadirDeportista(@Arg("Introduce el texto de búsqueda:") StringArg textoBusqueda,
                                  @Arg("Introduce el nombre del nuevo deportista:") StringArg nombreDeportista,
                                  @Arg("Introduce el código del deportista:") IntArg codigoDeportista,
                                  @Arg("Introduce la temporada (winter/summer):") TemporadaArg temporada,
                                  @Arg("Introduce el código de la olimpiada:") IntArg codigoOlim,
                                  @Arg("Introduce el código del deporte:") IntArg codigoDeporte,
                                  @Arg("Introduce el código del evento:") IntArg codigoEvento) {
        var deportistas = buscarDeportistas(textoBusqueda);
        var creado = false;
        Deportista deportista;
        if (deportistas.isEmpty()) {
            try {
                deportista = new Deportista(nombreDeportista.get());
                creado = true;
            } catch (Exception e) {
                deportista = null;
            }
        } else {
            deportista = buscarDeportista(deportistas, codigoDeportista);
        }
        if (deportista == null) {
            System.out.println("Valor invalido");
            return;
        }

        var evento = buscarEvento(temporada, codigoOlim, codigoDeporte, codigoEvento);
        if (evento == null) {
            System.out.println("Valor invalido");
            return;
        }
        var part = new Participacion(0, null, evento, deportista, null);
        if (creado) {
            deportista.participaciones.add(part);
            db.store(deportista);
        }
        evento.participaciones.add(part);

        db.store(evento);
        db.store(part);
        db.commit();
    }

    /**
     * @param textoBusqueda Texto con el que localizar al deportista
     * @param codigoDeportista Índice del deportista de los resultados obtenidos
     * @param codigoParticipacion Índice de la participación de las participaciones del deportista seleccionado
     */
    @Entry(key = "4", desc = "Eliminar participación", pos = 4)
    public void eliminarParticipacion(@Arg("Introduce el texto de búsqueda:") StringArg textoBusqueda,
                                      @Arg("Introduce el código del deportista:") IntArg codigoDeportista,
                                      @Arg("Introduce el código de la participación:") IntArg codigoParticipacion) {
        var participacion = buscarParticipacion(textoBusqueda, codigoDeportista, codigoParticipacion);
        if (participacion == null) {
            System.out.println("Valor invalido");
            return;
        }
        participacion.deportista.participaciones.remove(participacion);
        participacion.evento.participaciones.remove(participacion);
        participacion.equipo.participaciones.remove(participacion);
        if (participacion.deportista.participaciones.isEmpty()) {
            db.delete(participacion.deportista);
        } else {
            db.store(participacion.deportista);
        }
        db.delete(participacion);
        db.commit();
    }

    @Entry(key = "5", desc = "Crear datos de prueba", pos = 5)
    public void crearDatosPrueba() {
        var olimpiada = new Olimpiada("Tokyo 2020", 2020, Olimpiada.VERANO, "Tokyo");
        var atletismo = new Deporte("Atletismo");
        var evento100m = new Evento("100m", olimpiada, atletismo);

        var deportista = new Deportista("Usain Bolt", "M", 94, 195);
        var equipo = new Equipo("Jamaica", "JAM");
        var participacion = new Participacion(34, Participacion.ORO, evento100m, deportista, equipo);

        evento100m.participaciones.add(participacion);
        deportista.participaciones.add(participacion);
        equipo.participaciones.add(participacion);

        atletismo.eventos.add(evento100m);
        olimpiada.deportes.add(atletismo);

        db.store(olimpiada);
        db.store(atletismo);
        db.store(evento100m);
        db.store(deportista);
        db.store(equipo);
        db.store(participacion);

        db.commit();
    }

    @Entry(key = "0", desc = "Salir", pos = -1)
    public void salir() {
        db.close();
        System.exit(0);
    }
}
