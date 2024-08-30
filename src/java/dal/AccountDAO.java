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
public class AccountDAO extends DBContext {

    /**
     * check if user login with username and password is correct
     *
     * @param accountname username
     * @param password
     * @return true and user login success, false for wrong user name or
     * password
     */
    public boolean checkLogin(String accountname, String password) {
        boolean check = false;
        String sql = "select * from Account where accountname = ? and password = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, accountname);
            statement.setString(2, password);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                check = true;
            }
        } catch (SQLException e) {
        }
        return check;
    }

    public int getRole() {
        return 0;
    }

    /**
     * query account from database
     *
     * @param accountname user input user name
     * @param password user input password
     * @return return account object if username and password are true
     */
    public Account getUser(String accountname, String password) {
        String sql = "select * from Account where accountname = ? and password = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, accountname);
            statement.setString(2, password);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                FeedbackDAO dao = new FeedbackDAO();
                List<Feedback> list = dao.getFeedbackByAccount(rs.getInt("id"));
                ResponseContentDAO resDAO = new ResponseContentDAO();
                List<ResponseContent> rList = resDAO.getResponsByUserID(rs.getInt("id"));
                Account account = new Account();
                account.setId(rs.getInt("id"));
                account.setAccountName(accountname);
                account.setPassword(password);
                account.setRole(rs.getInt("role"));
                account.setDisplayname(rs.getString("displayname"));
                account.setFeedbackList(list);
                account.setResponseList(rList);
                return account;
            }
        } catch (SQLException e) {
        }
        return null;
    }

    /**
     * change password of corresponding username
     *
     * @param accountname user want to change password
     * @param newpassword new password that user want to change to
     * @return check if the change is successful or not
     */
    public boolean changePassword(String accountname, String newpassword) {
        boolean check = false;
        String sql = "update Account set password = ? where accountname = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, newpassword);
            statement.setString(2, accountname);
            int rs = statement.executeUpdate();
            if (rs != 0) {
                check = true;
            }
        } catch (SQLException e) {
        }
        return check;
    }

    /**
     *
     * @return
     */
    public List<Account> getAllAccounts() {
        List<Account> list = new ArrayList<>();
        String sql = "select * from Account";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Account acc = new Account();
                acc.setId(rs.getInt("id"));
                acc.setAccountName(rs.getString(2));
                acc.setDisplayname(rs.getString("displayname"));
                list.add(acc);
            }
        } catch (SQLException e) {
        }
        return list;
    }

    /**
     * get displayname by accountid
     *
     * @param id
     * @return
     */
    public String getDislpayName(int id) {
        String sql = "select displayname from Account where id = ?";
        String username = "";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                username = rs.getString(1);
            }
            return username;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }
    public String getAccountName(int id) {
        String sql = "select accountname from Account where id = ?";
        String username = "";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                username = rs.getString(1);
            }
            return username;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }
    
    public int nextAccountId(){
        int id = 1;
        String sql = "select max(id) from Account";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            if(rs.next()){
                id += rs.getInt(1);
            }
        } catch (SQLException e) {
        }
        return id;
    }

    public boolean createAccount(String username, String password, String fullname) {
        boolean check = false;
        int id = nextAccountId();
        String sql = "insert into Account(id, accountname, password, role, displayname, fullname) values(?,?,?,2,?,?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            statement.setString(2, username);
            statement.setString(3, password);
            statement.setString(4, fullname);
            statement.setString(5, fullname);
            int rs = statement.executeUpdate();
            if(rs>0){
                check = true;
            }
        } catch (SQLException e) {
        }
        return check;
    }

    public boolean deleteAccount() {
        boolean check = false;

        return check;
    }
    
    public boolean checkUserNameExist(String username){
        boolean check = false;
        String sql = "select * from Account where accountname = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();
            if(rs.next()){
                check = true;
            }
        } catch (SQLException e) {
        }
        return check;
    }

    public List<Account> getStudentPaging(int offset, String searchFreeForm) {
        List<Account> list = new ArrayList<>();
        String sql = "select * from Account where role = 2";
        if (searchFreeForm != null && !searchFreeForm.trim().equalsIgnoreCase("")) {
            sql += " and displayname like N'%" + searchFreeForm + "%')";
        }
        sql += " order by id";

        sql += " offset ? rows fetch next 5 rows only";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, (offset - 1) * 5);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                FeedbackDAO dao = new FeedbackDAO();
                List<Feedback> fblist = dao.getFeedbackByAccount(rs.getInt("id"));
                ResponseContentDAO resDAO = new ResponseContentDAO();
                Account account = new Account();
                account.setId(rs.getInt("id"));
                account.setAccountName(rs.getString(2));
                account.setRole(rs.getInt("role"));
                account.setDisplayname(rs.getString("displayname"));
                account.setFeedbackList(fblist);
                account.setResponseList(null);

                list.add(account);
            }
        } catch (SQLException e) {
        }
        return list;
    }
    
    public List<Account> getStudentPaging2(int offset, String sortdate, String searchFreeForm) {
        List<Account> list = new ArrayList<>();
        String sql = "WITH LatestFeedback AS (\n"
                + "    SELECT\n"
                + "        f.accountid,\n"
                + "        MAX(f.createdate) AS latest_feedback_date\n"
                + "    FROM Feedback f\n"
                + "    GROUP BY f.accountid\n"
                + ")\n"
                + "SELECT a.*\n"
                + "FROM Account a\n"
                + "LEFT JOIN LatestFeedback lf ON a.id = lf.accountid\n"
                + "WHERE a.role = 2";
        if (searchFreeForm != null && !searchFreeForm.trim().equalsIgnoreCase("")) {
            sql += " and a.displayname like N'%" + searchFreeForm + "%'";
        }
        if (sortdate != null && !sortdate.equalsIgnoreCase("")) {
            switch (sortdate) {
                case "Newest":
                    sql += " ORDER BY COALESCE(lf.latest_feedback_date, '9999-12-31') DESC";
                    break;
                case "Oldest":
                    sql += " ORDER BY COALESCE(lf.latest_feedback_date, '9999-12-31') asc";
                    break;
                default:
                    sql += " order by a.id";
                    break;
            }
        } else {
            sql += " order by a.id";
        }

        sql += " offset ? rows fetch next 5 rows only";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, (offset - 1) * 5);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                FeedbackDAO dao = new FeedbackDAO();
                List<Feedback> fblist = dao.getFeedbackByAccount(rs.getInt("id"));
                ResponseContentDAO resDAO = new ResponseContentDAO();
                Account account = new Account();
                account.setId(rs.getInt("id"));
                account.setAccountName(rs.getString(2));
                account.setRole(rs.getInt("role"));
                account.setDisplayname(rs.getString("displayname"));
                account.setFeedbackList(fblist);
                account.setResponseList(null);

                list.add(account);
            }
        } catch (SQLException e) {
        }
        return list;
    }

    public int countStudentPaging(String searchFreeForm) {
        String sql = "select * from Account where role = 2";
        if (searchFreeForm != null && !searchFreeForm.trim().equalsIgnoreCase("")) {
            sql += " and (ftitle like N'%" + searchFreeForm + "%' or fcontent like N'%" + searchFreeForm + "%')";
        }

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
        }
        return 0;
    }

    public static void main(String[] args) {
        AccountDAO dao = new AccountDAO();
        List<Account> list = dao.getStudentPaging2(1, "Oldest", "a");
        System.out.println(list.size());
        System.out.println(dao.getAccountName(4));
    }
}
