/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author abdo
 */
public class Connect {
    private static Connect connect;
    private final String url;
    private final String uname;
    private final String pw;
    private Connection con;
    private final String db;
    private final String unicode ;

    private Connect() {
        unicode = "?useUnicode=yes&characterEncoding=UTF-8&serverTimezone=UTC";
        db = "java-haneen";
        url = "jdbc:mysql://localhost/" + db+unicode;
        uname = "root";
        pw = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, uname, pw);
        } catch (SQLException | ClassNotFoundException ex) {
            System.out.println(ex);
        }
    }

    public static Connect getConnect() {
        if (connect == null) {
            connect = new Connect();
        }
        return connect;
    }

    public Connection getConnection() {
        return con;
    }
    
}
