package net.mysticcloud.spigot.core.utils.sql;

import org.json2.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SQLUtils {

    private static final Map<String, IDatabase> databases = new HashMap<>();

    public static void createDatabase(String name, IDatabase db) {
        if (!db.init()) {
            System.out.println("There was an error registering database: " + name);
            return;
        }
        databases.put(name, db);
    }

    public static void createDatabase(String name, SQLDriver driver, String host, String database, Integer port,
                                      String username, String password) {
        createDatabase(name, new IDatabase(driver, host, database, port, username, password));

    }

    public static IDatabase getDatabase(String name) {
        return databases.getOrDefault(name, null);
    }

    public static String escape(String str) {

        String data = null;
        if (str != null && str.length() > 0) {
            str = str.replace("\\", "\\\\");
            str = str.replace("'", "\\'");
            str = str.replace("\0", "\\0");
            str = str.replace("\n", "\\n");
            str = str.replace("\r", "\\r");
            str = str.replace("\"", "\\\"");
            str = str.replace("\\x1a", "\\Z");
            data = str;
        }
        return data;

    }

    public enum SQLDriver {

        SQLITE("sqlite"),
        MYSQL("mysql");

        final String name;

        SQLDriver(String name) {
            this.name = name;
        }

        public String argname() {
            return name;
        }

    }

}