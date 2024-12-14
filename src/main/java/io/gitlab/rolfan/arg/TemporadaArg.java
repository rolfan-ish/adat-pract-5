package io.gitlab.rolfan.arg;

import io.gitlab.rolfan.jmenu.arg.ArgGetter;
import io.gitlab.rolfan.model.Olimpiada.Temporada;

import java.util.Scanner;

public class TemporadaArg extends ArgGetter<Temporada> {
    /**
     * Mandatory constructor for ALL subclasses of ArgGetter
     *
     * @param name Prompt to give to the user
     */
    public TemporadaArg(String name) {
        super(name);
    }

    @Override
    protected Temporada getObject() throws Exception {
        var s = new Scanner(System.in).next().toLowerCase();
        if ("winter".startsWith(s))
            return Temporada.INVIERNO;
        if ("summer".startsWith(s))
            return Temporada.VERANO;
        throw new Exception();
    }
}
