/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import database.Connect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author abdo
 */
public class Auth {

    private String email;
    private String password;
    private final Connection con;
    private static Admin currentAdmin;

    public Auth() {
        this.con = Connect.getConnect().getConnection();
    }

    public static Admin auth() {

        return Auth.currentAdmin;
    }

    public boolean login() {

        String loginQuery = "select password ,first_name , last_name , phone , major from admins where  email = ?";

        try (PreparedStatement ps = con.prepareCall(loginQuery)) {
            ps.setString(1, this.getEmail());
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                String rightPassword = rs.getString(1);
                if (rightPassword.equals(this.password)) {

                    Auth.currentAdmin = new Admin().
                            setEmail(this.getEmail())
                            .setFirst_name(rs.getString(2))
                            .setLast_name(rs.getString(3))
                            .setPhone(rs.getString(4))
                            .setMajor(rs.getString(5));

                    return true;
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return false;
    }

    public static void logout() {
        Auth.currentAdmin = null;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     * @return
     */
    public Auth setEmail(String email) {
        this.email = email;
        return this;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     * @return
     */
    public Auth setPassword(String password) {
        this.password = password;
        return this;
    }
}
