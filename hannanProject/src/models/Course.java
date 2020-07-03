/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.sql.Connection;
import javax.swing.JTable;
import database.Connect;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author abdo
 */
public class Course implements Model {

    private long id;
    private String name;
    private String code;
    private double price;
    private int numberOfHours;
    private final Connection con;

    public Course() {
        this.con = Connect.getConnect().getConnection();
    }

    @Override
    public Integer store() {

        String storeQuery = "insert into courses ( name , code , price , number_of_hours ) values (?,?,?,?)";

        try (PreparedStatement ps = con.prepareCall(storeQuery)) {
            ps.setString(1, this.getName());
            ps.setString(2, this.getCode());
            ps.setDouble(3, this.getPrice());
            ps.setInt(4, this.getNumberOfHours());
            return ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return null;
    }

    @Override
    public void all(JTable table) {

        String getQuery = "select * from courses";

        try {
            PreparedStatement st = con.prepareCall(getQuery);
            st.executeQuery();
            ResultSet rs = st.executeQuery();
            table.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public List<Course> get() {

        List<Course> courses;

        String query = "select * from courses";

        try (PreparedStatement ps = con.prepareCall(query)) {
            try (ResultSet rs = ps.executeQuery()) {
                courses = new ArrayList<>();
                while (rs.next()) {
                    courses.add(
                            new Course()
                                    .setId(rs.getLong("id"))
                                    .setCode(rs.getString("code"))
                                    .setName(rs.getString("name"))
                    );
                }
                return courses;
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return null;
    }

    @Override
    public Integer delete() {

        String deleteQuery = "delete from courses where id = ?";

        try (PreparedStatement ps = con.prepareCall(deleteQuery)) {
            ps.setLong(1, this.id);
            return ps.executeUpdate();
        } catch (SQLException ex) {
            return -1;
        }
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     * @return
     */
    public Course setId(long id) {
        this.id = id;
        return this;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     * @return
     */
    public Course setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code the code to set
     * @return
     */
    public Course setCode(String code) {
        this.code = code;
        return this;
    }

    /**
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     * @return
     */
    public Course setPrice(double price) {
        this.price = price;
        return this;
    }

    /**
     * @return the numberOfHours
     */
    public int getNumberOfHours() {
        return numberOfHours;
    }

    /**
     * @param numberOfHours the numberOfHours to set
     * @return
     */
    public Course setNumberOfHours(int numberOfHours) {
        this.numberOfHours = numberOfHours;
        return this;
    }

    @Override
    public String toString() {
        return "Course{" + "id=" + id + ", name=" + name + ", code=" + code + ", price=" + price + ", numberOfHours=" + numberOfHours + '}';
    }
    
}
