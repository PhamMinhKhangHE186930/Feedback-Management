/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.*;
/**
 *
 * @author ADMIN
 */
public class FeedbackTypeDAO extends DBContext{
    public List<FeedbackType> getAllTypes(){
        List<FeedbackType> list = new ArrayList<>();
        String sql = "select * from FeedbackType";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                FeedbackType fbtype = new FeedbackType(rs.getInt(1), rs.getString(2));
                list.add(fbtype);
            }
        } catch (SQLException e) {
        }
        return list;
    }
    
    public static void main(String[] args) {
        FeedbackTypeDAO fbtDAO = new FeedbackTypeDAO();
        List<FeedbackType> list = fbtDAO.getAllTypes();
        System.out.println(list.size());
    }
}
