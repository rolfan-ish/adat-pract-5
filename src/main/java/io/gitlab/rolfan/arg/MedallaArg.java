package io.gitlab.rolfan.arg;

import io.gitlab.rolfan.jmenu.arg.ArgGetter;
import io.gitlab.rolfan.model.Participacion;

import java.util.Scanner;

public class MedallaArg extends ArgGetter<String> {

    /**
     * Mandatory constructor for ALL subclasses of ArgGetter
     *
     * @param name Prompt to give to the user
     */
    public MedallaArg(String name) {
        super(name);
    }

    @Override
    protected String getObject() throws Exception {
        var s = new Scanner(System.in).nextLine().toLowerCase();
        if ("oro".startsWith(s))
            return Participacion.ORO;
        if ("bronze".startsWith(s))
            return Participacion.BRONCE;
        if ("plata".startsWith(s))
            return Participacion.PLATA;
        if ("null".startsWith(s))
            return null;
        throw new Exception();
    }
}
