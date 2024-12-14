package io.gitlab.rolfan.arg;

import io.gitlab.rolfan.jmenu.arg.ArgGetter;
import io.gitlab.rolfan.model.Olimpiada;

import java.util.Scanner;

public class TemporadaArg extends ArgGetter<String> {
    /**
     * Mandatory constructor for ALL subclasses of ArgGetter
     *
     * @param name Prompt to give to the user
     */
    public TemporadaArg(String name) {
        super(name);
    }

    @Override
    protected String getObject() throws Exception {
        var s = new Scanner(System.in).next().toLowerCase();
        if ("winter".startsWith(s))
            return Olimpiada.INVIERNO;
        if ("summer".startsWith(s))
            return Olimpiada.VERANO;
        throw new Exception();
    }
}
