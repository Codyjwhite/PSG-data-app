package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

//Class to init embedded DB
public class DBManager {
    //Sets the filepath for the DB, if no path exists sqlite creates one.
    private static final String DB_URL = "jdbc:sqlite:data/psg_data.db";

    //Creates a connection to the embedded DB
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    //Method used to initialize the DB
    public static void initializeDatabase() {

        //Creates appointment table if no table exists
        String sql = """
            CREATE TABLE IF NOT EXISTS appointments (
                booking_id TEXT PRIMARY KEY,
                appointment_date TEXT,
                client_name TEXT,
                pet_name TEXT,
                service_size TEXT,
                gross_sales REAL,
                duration_minutes INTEGER,
                is_paid INTEGER,
                last_updated TEXT
            );
        """;

        //Makes a connection to the DB to execute SQL
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Database initialized");
        } catch (SQLException e) {
            e.printStackTrace(); // FIXME Handle this more gracefully in GUI version
        }
    }
}
