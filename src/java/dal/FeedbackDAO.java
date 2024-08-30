/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import model.*;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public class FeedbackDAO extends DBContext {

    /**
     * get all feedback exist in database
     *
     * @return list of feedback
     */
    public List<Feedback> getAllFeedbacks() {
        List<Feedback> list = new ArrayList<>();
        String sql = "select * from FeedBack f, FeedbackType ft where f.typeid = ft.id";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                FeedbackType feedbackType = new FeedbackType(rs.getInt(10), rs.getString(11));

                Feedback feedback = new Feedback();
                feedback.setId(rs.getInt(1));
                feedback.setFeedbackType(feedbackType);
                feedback.setfTitle(rs.getString(3));
                feedback.setfContent(rs.getString(4));
                feedback.setStatus(rs.getBoolean(5));
                feedback.setCreateDate(rs.getTimestamp(6));
                feedback.setCheckPublic(rs.getBoolean(7));
                feedback.setAccountID(rs.getInt(8));
                feedback.setFeedbackFile(rs.getString(9));

                list.add(feedback);
            }
        } catch (SQLException e) {
        }
        return list;
    }

    /**
     * get feed back that was send by one user
     *
     * @param accountid name of user send feedback
     * @return list of feedback correspond to user
     */
    public List<Feedback> getFeedbackByAccount(int accountid) {
        List<Feedback> list = new ArrayList<>();
        String sql = "select * from Feedback f, FeedbackType ft where f.typeid = ft.id and f.accountid = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, accountid);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                FeedbackType feedbackType = new FeedbackType(rs.getInt(10), rs.getString(11));

                Feedback feedback = new Feedback();
                feedback.setId(rs.getInt(1));
                feedback.setFeedbackType(feedbackType);
                feedback.setfTitle(rs.getString(3));
                feedback.setfContent(rs.getString(4));
                feedback.setStatus(rs.getBoolean(5));
                feedback.setCreateDate(rs.getTimestamp(6));
                feedback.setCheckPublic(rs.getBoolean(7));
                feedback.setAccountID(rs.getInt(8));
                feedback.setFeedbackFile(rs.getString(9));

                list.add(feedback);
            }
        } catch (SQLException e) {
        }
        return list;
    }

    /**
     * get all feedback with status between responded = 1 (true) and not
     * responded = 0 (false)
     *
     * @param status true or false of which feedback was responded
     * @return list
     */
    public List<Feedback> getFeedbackByStatus(boolean status) {
        List<Feedback> list = new ArrayList<>();
        String sql = "select * from Feedback f, FeedbackType ft where f.typeid = ft.id and f.status = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setBoolean(1, status);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                FeedbackType feedbackType = new FeedbackType(rs.getInt(10), rs.getString(11));

                Feedback feedback = new Feedback();
                feedback.setId(rs.getInt(1));
                feedback.setFeedbackType(feedbackType);
                feedback.setfTitle(rs.getString(3));
                feedback.setfContent(rs.getString(4));
                feedback.setStatus(rs.getBoolean(5));
                feedback.setCreateDate(rs.getTimestamp(6));
                feedback.setCheckPublic(rs.getBoolean(7));
                feedback.setAccountID(rs.getInt(8));
                feedback.setFeedbackFile(rs.getString(9));

                list.add(feedback);
            }
        } catch (SQLException e) {
        }
        return list;
    }

    /**
     *
     * @param status
     * @param accountID
     * @return
     */
    public List<Feedback> getFeedbackByStatusAndID(boolean status, int accountID) {
        List<Feedback> list = new ArrayList<>();
        String sql = "select * from Feedback f, FeedbackType ft where f.typeid = ft.id and status = ? and accountid = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setBoolean(1, status);
            statement.setInt(2, accountID);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                FeedbackType feedbackType = new FeedbackType(rs.getInt(10), rs.getString(11));

                Feedback feedback = new Feedback();
                feedback.setId(rs.getInt(1));
                feedback.setFeedbackType(feedbackType);
                feedback.setfTitle(rs.getString(3));
                feedback.setfContent(rs.getString(4));
                feedback.setStatus(rs.getBoolean(5));
                feedback.setCreateDate(rs.getTimestamp(6));
                feedback.setCheckPublic(rs.getBoolean(7));
                feedback.setAccountID(rs.getInt(8));
                feedback.setFeedbackFile(rs.getString(9));

                list.add(feedback);
            }
        } catch (SQLException e) {
        }
        return list;
    }

    /**
     * get feedback base on feedback id
     *
     * @param fbID
     * @return
     */
    public Feedback getFeedbackByfbID(int fbID) {
        String sql = "select * from Feedback f, FeedbackType ft where f.typeid = ft.id and f.id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, fbID);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                FeedbackType feedbackType = new FeedbackType(rs.getInt(10), rs.getString(11));

                Feedback feedback = new Feedback();
                feedback.setId(rs.getInt(1));
                feedback.setFeedbackType(feedbackType);
                feedback.setfTitle(rs.getString(3));
                feedback.setfContent(rs.getString(4));
                feedback.setStatus(rs.getBoolean(5));
                feedback.setCreateDate(rs.getTimestamp(6));
                feedback.setCheckPublic(rs.getBoolean(7));
                feedback.setAccountID(rs.getInt(8));
                feedback.setFeedbackFile(rs.getString(9));

                return feedback;
            }
        } catch (SQLException e) {
        }
        return null;
    }

    public List<Feedback> getFeedbackExceptID(int accountID) {
        List<Feedback> list = new ArrayList<>();
        String sql = "select * from Feedback f, FeedbackType ft where f.typeid = ft.id and f.accountid <> ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, accountID);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                FeedbackType feedbackType = new FeedbackType(rs.getInt(10), rs.getString(11));

                Feedback feedback = new Feedback();
                feedback.setId(rs.getInt(1));
                feedback.setFeedbackType(feedbackType);
                feedback.setfTitle(rs.getString(3));
                feedback.setfContent(rs.getString(4));
                feedback.setStatus(rs.getBoolean(5));
                feedback.setCreateDate(rs.getTimestamp(6));
                feedback.setCheckPublic(rs.getBoolean(7));
                feedback.setAccountID(rs.getInt(8));
                feedback.setFeedbackFile(rs.getString(9));

                list.add(feedback);
                return list;
            }
        } catch (SQLException e) {
        }
        return null;
    }

    public List<Feedback> getNextTopFeedback(int offset, int top) {
        List<Feedback> list = new ArrayList<>();
        String sql = "select * from FeedBack f, FeedbackType ft where f.typeid = ft.id\n"
                + "order by f.id\n"
                + "offset ? rows\n"
                + "fetch next ? rows only";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, offset);
            statement.setInt(2, top);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                FeedbackType feedbackType = new FeedbackType(rs.getInt(10), rs.getString(11));

                Feedback feedback = new Feedback();
                feedback.setId(rs.getInt(1));
                feedback.setFeedbackType(feedbackType);
                feedback.setfTitle(rs.getString(3));
                feedback.setfContent(rs.getString(4));
                feedback.setStatus(rs.getBoolean(5));
                feedback.setCreateDate(rs.getTimestamp(6));
                feedback.setCheckPublic(rs.getBoolean(7));
                feedback.setAccountID(rs.getInt(8));
                feedback.setFeedbackFile(rs.getString(9));

                list.add(feedback);
            }
        } catch (SQLException e) {
        }
        return list;
    }

    public List<Feedback> getFeedbackByType(String type) {
        List<Feedback> list = new ArrayList<>();
        String sql = "select * from Feedback f, FeedbackType ft where f.typeid = ft.id and f.type = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, type);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                FeedbackType feedbackType = new FeedbackType(rs.getInt(10), rs.getString(11));

                Feedback feedback = new Feedback();
                feedback.setId(rs.getInt(1));
                feedback.setFeedbackType(feedbackType);
                feedback.setfTitle(rs.getString(3));
                feedback.setfContent(rs.getString(4));
                feedback.setStatus(rs.getBoolean(5));
                feedback.setCreateDate(rs.getTimestamp(6));
                feedback.setCheckPublic(rs.getBoolean(7));
                feedback.setAccountID(rs.getInt(8));
                feedback.setFeedbackFile(rs.getString(9));

                list.add(feedback);
            }
        } catch (SQLException e) {
        }
        return list;
    }

    public List<Feedback> getFeedbackByDate(Date fromDate, Date toDate) {
        List<Feedback> list = new ArrayList<>();
        String sql = "select * from Feedback f, FeedbackType ft where f.typeid = ft.id and convert(date, createdate) = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setDate(1, fromDate);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                FeedbackType feedbackType = new FeedbackType(rs.getInt(10), rs.getString(11));

                Feedback feedback = new Feedback();
                feedback.setId(rs.getInt(1));
                feedback.setFeedbackType(feedbackType);
                feedback.setfTitle(rs.getString(3));
                feedback.setfContent(rs.getString(4));
                feedback.setStatus(rs.getBoolean(5));
                feedback.setCreateDate(rs.getTimestamp(6));
                feedback.setCheckPublic(rs.getBoolean(7));
                feedback.setAccountID(rs.getInt(8));
                feedback.setFeedbackFile(rs.getString(9));

                list.add(feedback);
            }
        } catch (SQLException e) {
        }
        return list;
    }

    /**
     * get number of feedback was send in last 24 hours
     *
     * @return number
     */
    public int countNumOfFBinRecentDate() {
        int count = 0;
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime last24h = now.minusHours(24);
        String sql = "select count(*) from Feedback where createdate >= ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setTimestamp(1, Timestamp.valueOf(last24h));
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
        }
        return count;
    }

    /**
     * list of specific account feedback with multiple search type
     *
     * @param offset
     * @param numberPerPage
     * @param userID
     * @param status
     * @param fromDate
     * @param toDate
     * @param typeid
     * @param checkPublic
     * @param searchFreeForm
     * @param sortdate
     * @param other check take other feedback or user feedback
     * @return list of search
     */
    public List<Feedback> searchFeedbacks(int offset, int numberPerPage, int userID, String status, Date fromDate, Date toDate, int typeid, String checkPublic, String searchFreeForm, String sortdate, String other) {
        List<Feedback> list = new ArrayList<>();
        String sql = "select * from Feedback f, FeedbackType ft where f.typeid = ft.id";
        if (other != null && !other.trim().equalsIgnoreCase("")) {
            if (other.equalsIgnoreCase("own")) {
                if (userID > 0) {
                    sql += " and accountid = " + userID;
                }
            } else if (other.equalsIgnoreCase("other")) {
                if (userID > 0) {
                    sql += " and accountid <> " + userID;
                }
            }
        }

        if (status != null) {
            sql += " and status = " + status;
        }
        if (fromDate != null) {
            sql += " and convert(date, createdate) >= '" + fromDate + "'";
        }
        if (toDate != null) {
            sql += " and convert(date, createdate) <= '" + toDate + "'";
        }
        if (typeid > 0) {
            sql += " and typeid = " + typeid;
        }
        if (checkPublic != null) {
            switch (checkPublic) {
                case "0":
                    sql += " and [public] = " + checkPublic;
                    break;
                case "1":
                    sql += " and [public] = " + checkPublic;
                    break;
                default:
                    break;
            }
        }
        if (searchFreeForm != null) {
            sql += " and (ftitle like N'%" + searchFreeForm + "%' or fcontent like N'%" + searchFreeForm + "%')";
        }
        if (sortdate != null && !sortdate.equalsIgnoreCase("")) {
            switch (sortdate) {
                case "Newest":
                    sql += " order by createdate desc";
                    break;
                case "Oldest":
                    sql += " order by createdate asc";
                    break;
                default:
                    sql += " order by f.id";
                    break;
            }
        } else {
            sql += " order by f.id";
        }

        sql += " offset ? rows fetch next " + numberPerPage + " rows only";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, (offset - 1) * numberPerPage);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                FeedbackType feedbackType = new FeedbackType(rs.getInt(2), rs.getString(11));

                Feedback feedback = new Feedback();
                feedback.setId(rs.getInt(1));
                feedback.setFeedbackType(feedbackType);
                feedback.setfTitle(rs.getString(3));
                feedback.setfContent(rs.getString(4));
                feedback.setStatus(rs.getBoolean(5));
                feedback.setCreateDate(rs.getTimestamp(6));
                feedback.setCheckPublic(rs.getBoolean(7));
                feedback.setAccountID(rs.getInt(8));
                feedback.setFeedbackFile(rs.getString(9));

                list.add(feedback);
            }
        } catch (SQLException e) {
        }
        return list;
    }

    public int countAfterSearch(int userID, String status, Date fromDate, Date toDate, int typeid, String checkPublic, String searchFreeForm, String other) {
        String sql = "select count(*) from Feedback f, FeedbackType ft where f.typeid = ft.id";
        if (other != null && !other.trim().isEmpty()) {
            if (other.equalsIgnoreCase("own") || other.equalsIgnoreCase("other")) {
                sql += " and accountid " + (other.equalsIgnoreCase("own") ? "=" : "<>") + userID;
            }
        }

        if (status != null) {
            sql += " and status = " + status;
        }
        if (fromDate != null) {
            sql += " and convert(date, createdate) >= '" + fromDate + "'";
        }
        if (toDate != null) {
            sql += " and convert(date, createdate) <= '" + toDate + "'";
        }
        if (typeid > 0) {
            sql += " and typeid = " + typeid;
        }
        if (checkPublic != null) {
            switch (checkPublic) {
                case "0":
                    sql += " and [public] = " + checkPublic;
                    break;
                case "1":
                    sql += " and [public] = " + checkPublic;
                    break;
                default:
                    break;
            }
        }
        if (searchFreeForm != null) {
            sql += " and (ftitle like N'%" + searchFreeForm + "%' or fcontent like N'%" + searchFreeForm + "%')";
        }

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return 0;
    }

    /**
     * choose the highest feedback id and then create a new id feedback
     *
     * @return new feedback id
     */
    public int nextFbId() {
        String sql = "select max(id) from Feedback";
        int nextID = 1;
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                nextID += rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return nextID;
    }

    /**
     * add feedback to the database
     *
     * @param type
     * @param ftitle
     * @param fcontent
     * @param status
     * @param createdate
     * @param checkpublic
     * @param accountid
     * @return return check if add is success or not
     */
    public boolean addFeedback(int type, String ftitle, String fcontent, boolean status, Timestamp createdate, boolean checkpublic, int accountid, String fileName) {
        boolean check = false;
        String sql = "insert into Feedback values(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        int id = nextFbId();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            statement.setInt(2, type);
            statement.setString(3, ftitle);
            statement.setString(4, fcontent);
            statement.setBoolean(5, status);
            statement.setTimestamp(6, createdate);
            statement.setBoolean(7, checkpublic);
            statement.setInt(8, accountid);
            statement.setString(9, fileName);

            int i = statement.executeUpdate();
            if (i != 0) {
                check = true;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return check;
    }

    /**
     * get file name with by feedback id
     *
     * @param fbid
     * @return
     */
    public String getFileNameByFBid(int fbid) {
        String sql = "Select filename from Feedback where id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, fbid);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException e) {
        }
        return null;
    }

    public boolean checkFileExistWithUser(String fileName, int userID) {
        boolean check = false;
        String sql = "Select count(*) from Feedback where accountid = ? and [filename] = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, userID);
            statement.setString(2, fileName);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                if (rs.getInt(1) > 0) {
                    check = true;
                }
            }
        } catch (SQLException e) {
        }
        return check;
    }

    /**
     * get count number of status by its status
     *
     * @param status (all = null, responded = 1, not response = 2)
     * @return
     */
    public int getTotalFeedbackByStatus(String status) {
        int count = 0;
        String sql = "select count(*) from Feedback where 0=0";
        if (status != null) {
            switch (status) {
                case "1":
                    sql += " and status = " + status;
                    break;
                case "0":
                    sql += " and status = " + status;
                    break;
                default:
                    break;
            }
        }
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
        }
        return count;
    }

    public static void main(String[] args) {
        FeedbackDAO dao = new FeedbackDAO();
        LocalDateTime now = LocalDateTime.now();

        Timestamp time = Timestamp.valueOf(now);

//        dao.addFeedback("type1", "tiếng việt ở đây", "tiếng việt ở đây", false, time, false, 2);
        List<Feedback> list = dao.searchFeedbacks(1, 5, 0, null, null, null, 0, null, null, "Oldest", null);
        int count = dao.countAfterSearch(0, null, null, null, 0, null, null, null);
        List<Feedback> listtop5 = dao.getNextTopFeedback(5, 5);
        System.out.println(list.get(1).getFeedbackType().getType_name());
        System.out.println(count);

        System.out.println(dao.getFileNameByFBid(11));

    }
}
