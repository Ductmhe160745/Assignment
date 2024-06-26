/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Customer;

/**
 *
 * @author tranm
 */
public class CustomerDAO {

    private static final Logger logger = Logger.getLogger(CustomerDAO.class.getName());
    public static final CustomerDAO INSTANCE = new CustomerDAO();
    private Connection con;

    public CustomerDAO() {

        con = new DBContext().connection;

    }

    public int insertCustomer(Customer c) {

        String sql = "Insert into Customer(FirstName, LastName, Email, Phone, Address, Gender) values(?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, c.getFirstName());
            ps.setString(2, c.getLastName());
            ps.setString(3, c.getEmail());
            ps.setString(4, c.getPhone());
            ps.setString(5, c.getAddress());
            ps.setString(6, c.getGender());
            ps.executeUpdate();
            String sql1 = "select top 1 CustomerID from Customer order by CustomerID desc";
            PreparedStatement ps1 = con.prepareStatement(sql1);
            rs = ps1.executeQuery();
            if (rs.next()) {
                int cusId = rs.getInt(1);
                return cusId;
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "", e);

        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "", e);
            }

        }

        return 0;
    }

    public List<Customer> getAllCustomers() {
        List<Customer> list = new ArrayList<>();
        String sql = "select * from Customer c left join Account a ON c.CustomerID = a.CustomerID";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Customer c = new Customer();
                c.setCustomerId(rs.getInt("CustomerID"));
                c.setFirstName(rs.getString("FirstName"));
                c.setLastName(rs.getString("LastName"));
                c.setEmail(rs.getString("Email"));
                c.setPhone(rs.getString("Phone"));
                c.setAddress(rs.getString("Address"));
                c.setGender(rs.getString("Gender"));
                list.add(c);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "", e);

        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "", e);
            }

        }
        return list;
    }

    public boolean updateCustomerInfo(Customer c) {
        String sql = "Update Customer SET FirstName = ?, LastName = ?, Email = ?, Phone = ?, Address = ?, Gender = ? Where CustomerID = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, c.getFirstName());
            ps.setString(2, c.getLastName());
            ps.setString(3, c.getEmail());
            ps.setString(4, c.getPhone());
            ps.setString(5, c.getAddress());
            ps.setString(6, c.getGender());
            ps.setInt(7, c.getCustomerId());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "", e);

        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "", e);
            }

        }
        return false;
    }

    public Customer getCustomerById(int cusId) {
        String sql = "select * from Customer where CustomerId = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
             ps = con.prepareStatement(sql);
            ps.setInt(1, cusId);
             rs = ps.executeQuery();
            if (rs.next()) {
                Customer c = new Customer();
                c.setCustomerId(rs.getInt("CustomerID"));
                c.setFirstName(rs.getString("FirstName"));
                c.setLastName(rs.getString("LastName"));
                c.setEmail(rs.getString("Email"));
                c.setPhone(rs.getString("Phone"));
                c.setAddress(rs.getString("Address"));
                c.setGender(rs.getString("Gender"));
                return c;
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "", e);

        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "", e);
            }

        }

        return null;
    }

    public void deleteCusById(int id) {
        String sql1 = "UPDATE [Order]\n"
                + "SET CustomerID = NULL\n"
                + "WHERE CustomerID = ?";
        PreparedStatement ps1 = null;
        ResultSet rs = null;
        try {
            ps1 = con.prepareStatement(sql1);
            ps1.setInt(1, id);
            ps1.executeUpdate();
            String sql2 = "UPDATE Account SET CustomerID = NULL WHERE CustomerID = ?";
            PreparedStatement ps2 = con.prepareStatement(sql2);
            ps2.setInt(1, id);
            ps2.executeUpdate();
            String sql3 = "DELETE FROM Customer WHERE CustomerID = ?";
            PreparedStatement ps3 = con.prepareStatement(sql3);
            ps3.setInt(1, id);
            ps3.executeUpdate();

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "", e);

        } finally {
            try {
                if (ps1 != null) {
                    ps1.close();
                }
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "", e);
            }

        }
    }

    public static void main(String[] args) {
        CustomerDAO.INSTANCE.deleteCusById(10);
    }

}
