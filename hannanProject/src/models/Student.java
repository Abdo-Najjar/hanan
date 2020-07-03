/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import database.Connect;
import javax.swing.JTable;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author abdo
 */
public class Student implements Model {

    private long id;
    private String first_name;
    private String last_name;
    private String phone;
    private String major;
    private String email;
    private final Connection con;

    public Student() {
        this.con = Connect.getConnect().getConnection();
    }

    @Override
    public Integer store() {
        String storeQuery = "insert into students ( first_name , last_name , phone , major , email) values (?,?,?,?,?)";
        try (PreparedStatement ps = con.prepareCall(storeQuery)) {
            ps.setString(1, this.getFirst_name());
            ps.setString(2, this.getLast_name());
            ps.setString(3, this.getPhone());
            ps.setString(4, this.getMajor());
            ps.setString(5, this.getEmail());
            return ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return null;
    }

    @Override
    public void all(JTable table) {

        String getQuery = "select * from students";
        try {
            PreparedStatement st = con.prepareCall(getQuery);
            st.executeQuery();
            ResultSet rs = st.executeQuery();
            table.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public void allRegiesteredStudents(JTable table) {

        String getQuery = "select c.name , c.code  , s.first_name , s.last_name  from course_student as cs " +
                           "INNER join courses as c on c.id = cs.course_id " +
                            "INNER join students as s on s.id = cs.student_id ";
        try {
            PreparedStatement st = con.prepareCall(getQuery);
            st.executeQuery();
            ResultSet rs = st.executeQuery();
            table.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
    
    
    public Integer regiesterToCourse(long courseId) {

        String deleteIfExists = "delete from course_student where student_id = ? and course_id = ? ";
        PreparedStatement ps;
        try {
            ps = con.prepareCall(deleteIfExists);
            ps.setLong(1, this.getId());
            ps.setLong(2, courseId);
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
        }

        String regiesterQuery = "insert into course_student set student_id = ? , course_id = ?";

        try {
            ps = con.prepareCall(regiesterQuery);
            ps.setLong(1, this.getId());
            ps.setLong(2, courseId);
            return ps.executeUpdate();
        } catch (SQLException ex) {

            System.out.println(ex);
        }
        return null;
    }

    public List<Student> get() {

        List<Student> students;

        String query = "select * from students";

        try (PreparedStatement ps = con.prepareCall(query)) {
            try (ResultSet rs = ps.executeQuery()) {
                students = new ArrayList<>();
                while (rs.next()) {
                    students.add(
                            new Student()
                                    .setId(rs.getLong("id"))
                                    .setFirst_name(rs.getString("first_name"))
                                    .setLast_name(rs.getString("last_name"))
                    );
                }
                return students;
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return null;
    }

    @Override
    public Integer delete() {

        String deleteQuery = "delete from students where id = ?";

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
    public Student setId(long id) {
        this.id = id;
        return this;
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
    public Student setFirst_name(String first_name) {
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
     * @return
     */
    public Student setLast_name(String last_name) {
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
    public Student setPhone(String phone) {
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
     */
    public Student setMajor(String major) {
        this.major = major;
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
    public Student setEmail(String email) {
        this.email = email;
        return this;
    }

    @Override
    public String toString() {
        return "Student{" + "id=" + id + ", first_name=" + first_name + ", last_name=" + last_name + ", phone=" + phone + ", major=" + major + ", email=" + email + '}';
    }

    public static void main(String[] args) {
        Student student = new Student()
                .setId(4);
        student.regiesterToCourse(2);

    }
}
