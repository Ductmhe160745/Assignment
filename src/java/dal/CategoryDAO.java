/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import model.Category;

/**
 *
 * @author tranm
 */
public class CategoryDAO extends DBContext{
    
    public boolean insertCategory(Category c) {
        String sql = "insert into Category(CategoryName) values(?)";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, c.getCategoryName());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e);
        }

        return false;
    }
    
}
