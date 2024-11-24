package io.gitlab.rolfan.database;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;

public class Database {
    private static ObjectContainer database;

    public static void initialize() {
        database = Db4oEmbedded.openFile("database.db4o");
    }

    public static ObjectContainer database() {
        return database;
    }
}
