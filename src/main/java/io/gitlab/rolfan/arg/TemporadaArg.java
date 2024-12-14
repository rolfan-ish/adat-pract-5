package io.gitlab.rolfan.arg;

import io.gitlab.rolfan.jmenu.arg.ArgGetter;
import io.gitlab.rolfan.model.Olimpiada;

import java.util.Scanner;

public class TemporadaArg extends ArgGetter<Olimpiada.Temporada> {
    /**
     * Mandatory constructor for ALL subclasses of ArgGetter
     *
     * @param name Prompt to give to the user
     */
    public TemporadaArg(String name) {
        super(name);
    }

    @Override
    protected Olimpiada.Temporada getObject() {
        var s = new Scanner(System.in).next().toLowerCase();
        if ("winter".startsWith(s))
            return Olimpiada.Temporada.INVIERNO;
        if ("summer".startsWith(s))
            return Olimpiada.Temporada.VERANO;
        return null;
    }
}
