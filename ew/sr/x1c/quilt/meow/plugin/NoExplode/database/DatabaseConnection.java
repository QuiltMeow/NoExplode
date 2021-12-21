package ew.sr.x1c.quilt.meow.plugin.NoExplode.database;

import ew.sr.x1c.quilt.meow.plugin.NoExplode.Main;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.logging.Level;

public class DatabaseConnection {

    private static ThreadLocal<Connection> CON;

    private static String host, account, password, database;
    private static int port;

    public static void setConnectionInfo(String host, int port, String account, String password, String database) {
        DatabaseConnection.host = host;
        DatabaseConnection.port = port;

        DatabaseConnection.account = account;
        DatabaseConnection.password = password;
        DatabaseConnection.database = database;
    }

    public static Connection getConnection() {
        if (CON == null) {
            CON = new DatabaseConnection.ThreadLocalConnection();
        }
        return CON.get();
    }

    public static boolean hasConnection() {
        return CON != null;
    }

    public static void closeAll() throws SQLException {
        for (Connection connection : DatabaseConnection.ThreadLocalConnection.ALL_CONNECTION) {
            if (connection != null) {
                connection.close();
            }
        }
    }

    private static final class ThreadLocalConnection extends ThreadLocal<Connection> {

        private static final Collection<Connection> ALL_CONNECTION = new LinkedList<>();

        @Override
        protected final Connection initialValue() {
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (final ClassNotFoundException ex) {
                Main.getPlugin().getLogger().log(Level.WARNING, "找不到建立資料庫連線所需函式庫", ex);
            }

            try {
                Connection con = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "?autoReconnect=true&characterEncoding=UTF-8", account, password);
                ALL_CONNECTION.add(con);
                return con;
            } catch (SQLException ex) {
                Main.getPlugin().getLogger().log(Level.WARNING, "建立資料庫連線時發生例外狀況", ex);
                return null;
            }
        }
    }
}
