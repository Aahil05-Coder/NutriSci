package util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.stream.Collectors;

/**
 * Initializes and provides a shared JDBC Connection.
 * On first init(), it loads the driver, opens the DB, and executes schema SQL.
 */
public class DatabaseInitializer {
    private static Connection conn;
    private static final String JDBC_URL = "jdbc:h2:./nutrisci_db;AUTO_SERVER=TRUE";
    private static final String JDBC_USER = "sa";
    private static final String JDBC_PASS = "";
    private static final String SCHEMA_RESOURCE = "/schema.sql";

    /**
     * Load JDBC driver, open connection, and run schema SQL if needed.
     */
    public static void init() {
        if (conn != null) return;
        try {
            // 1) Load H2 JDBC driver
            Class.forName("org.h2.Driver");

            // 2) Open a connection
            conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);

            // 3) Execute DDL script to create tables
            runSchemaScript();
        } catch (Exception e) {
            throw new RuntimeException("Database initialization failed", e);
        }
    }

    /**
     * Returns the shared Connection, initializing if needed.
     */
    public static Connection getConnection() {
        if (conn == null) {
            init();
        }
        return conn;
    }

    /**
     * Reads and executes the SQL statements in /schema.sql on the classpath.
     */
    private static void runSchemaScript() throws SQLException {
        try (InputStream in = DatabaseInitializer.class.getResourceAsStream(SCHEMA_RESOURCE)) {
            if (in == null) {
                throw new RuntimeException("Schema resource not found: " + SCHEMA_RESOURCE);
            }
            String sql = new BufferedReader(new InputStreamReader(in))
                .lines()
                .collect(Collectors.joining(" "));
            // Split on semicolon, execute each statement
            try (Statement st = conn.createStatement()) {
                for (String stmt : sql.split(";")) {
                    if (!stmt.trim().isEmpty()) {
                        st.execute(stmt);
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to run schema script", e);
        }
    }
}

// End of MVC-aligned structure with DatabaseInitializer implemented
