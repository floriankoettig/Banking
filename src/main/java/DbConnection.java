import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DbConnection {
    static Connection conn = null;

    public static Connection getConnection() {
        if (conn == null) {
            try {
                var constr = "jdbc:postgresql://kdb.sh:6082/fmkoettig";
                var user = System.getenv("DB_USER");
                var password = System.getenv("DB_PASSWORD");

                var props = new Properties();
                props.setProperty("user", user);
                props.setProperty("password", password);

                conn = DriverManager.getConnection(constr, props);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return conn;
    }
}
