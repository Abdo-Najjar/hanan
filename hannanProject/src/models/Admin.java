/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import database.Connect;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JTable;
import java.sql.Connection;
import java.sql.ResultSet;
import javax.swing.ImageIcon;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author abdo
 */
public class Admin implements Model {

    private long id;
    private String first_name;
    private String last_name;
    private String phone;
    private String major;
    private String email;
    private String password;
    private String image;
    private final Connection con;

    public Admin() {
        this.con = Connect.getConnect().getConnection();
    }

    @Override
    public Integer store() {

        String storeQuery = "insert into admins ( first_name , last_name , phone , major , email , password , image) values (?,?,?,?,?,?,?)";
        try (PreparedStatement ps = con.prepareCall(storeQuery)) {
            ps.setString(1, this.getFirst_name());
            ps.setString(2, this.getLast_name());
            ps.setString(3, this.getPhone());
            ps.setString(4, this.getMajor());
            ps.setString(5, this.getEmail());
            ps.setString(6, this.getPassword());
            ps.setString(7,this.getImage());
            return ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return null;
    }

    @Override
    public void all(JTable table) {

        String getQuery = "select * from admins";

        try {
            PreparedStatement st = con.prepareCall(getQuery);
            st.executeQuery();
            ResultSet rs = st.executeQuery();
            table.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (SQLException ex) {
            System.out.println(ex);
        }

    }

    @Override
    public Integer delete() {

        String deleteQuery = "delete from admins where id = ?";

        try (PreparedStatement ps = con.prepareCall(deleteQuery)) {
            ps.setLong(1, this.id);
            return ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return null;

    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the first_name
     */
    public String getFirst_name() {
        return first_name;
    }

    /**
     * @param first_name the first_name to set
     * @return
     */
    public Admin setFirst_name(String first_name) {
        this.first_name = first_name;
        return this;
    }

    /**
     * @return the last_name
     */
    public String getLast_name() {
        return last_name;
    }

    /**
     * @param last_name the last_name to set
     */
    public Admin setLast_name(String last_name) {
        this.last_name = last_name;
        return this;
    }

    /**
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone the phone to set
     * @return
     */
    public Admin setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    /**
     * @return the major
     */
    public String getMajor() {
        return major;
    }

    /**
     * @param major the major to set
     * @return
     */
    public Admin setMajor(String major) {
        this.major = major;
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
     */
    public Admin setPassword(String password) {
        this.password = password;
        return this;
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
    public Admin setEmail(String email) {
        this.email = email;
        return this;
    }

    @Override
    public String toString() {
        return "Admin{" + "id=" + id + ", first_name=" + first_name + ", last_name=" + last_name + ", phone=" + phone + ", major=" + major + ", email=" + email + ", password=" + password + '}';
    }

    /**
     * @return the image
     */
    public String getImage() {
        return image;
    }

    /**
     * @param image the image to set
     * @return 
     */
    public Admin setImage(String image) {
        this.image = image;
        return this;
    }

    public static ImageIcon scaleImage(ImageIcon icon, int w, int h)
    {
        int nw = icon.getIconWidth();
        int nh = icon.getIconHeight();

        if(icon.getIconWidth() > w)
        {
          nw = w;
          nh = (nw * icon.getIconHeight()) / icon.getIconWidth();
        }

        if(nh > h)
        {
          nh = h;
          nw = (icon.getIconWidth() * nh) / icon.getIconHeight();
        }

        return new ImageIcon(icon.getImage().getScaledInstance(nw, nh, Image.SCALE_DEFAULT));
    }

}
