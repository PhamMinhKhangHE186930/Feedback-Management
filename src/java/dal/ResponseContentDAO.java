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
import java.sql.Timestamp;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import model.*;

/**
 *
 * @author ADMIN
 */
public class ResponseContentDAO extends DBContext {

    /**
     * get all response of all user
     *
     * @return
     */
    public List<ResponseContent> getAllResponse() {
        List<ResponseContent> list = new ArrayList<>();
        String sql = "select * from ResponseContent";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                ResponseContent response = new ResponseContent();
                response.setId(rs.getInt(1));
                response.setrTittle(rs.getString(2));
                response.setrContent(rs.getString(3));
                response.setResponseDate(rs.getTimestamp(4));
                response.setAccountid(rs.getInt(5));
                response.setFeedbackid(rs.getInt(6));
                response.setModifiedDate(rs.getTimestamp(7));

                list.add(response);
            }
        } catch (SQLException e) {
        }
        return list;
    }

    /**
     * get response of current user
     *
     * @param accountID
     * @return
     */
    public List<ResponseContent> getResponsByUserID(int accountID) {
        List<ResponseContent> list = new ArrayList<>();
        String sql = "select * from ResponseContent where accountid = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, accountID);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                ResponseContent response = new ResponseContent();
                response.setId(rs.getInt(1));
                response.setrTittle(rs.getString(2));
                response.setrContent(rs.getString(3));
                response.setResponseDate(rs.getTimestamp(4));
                response.setAccountid(rs.getInt(5));
                response.setFeedbackid(rs.getInt(6));
                response.setModifiedDate(rs.getTimestamp(7));

                list.add(response);
            }
        } catch (SQLException e) {
        }
        return list;
    }

    /**
     * get response which responded to specific feedback
     *
     * @param fbid
     * @return
     */
    public List<ResponseContent> getResponsByFeedbackID(int fbid) {
        List<ResponseContent> list = new ArrayList<>();
        String sql = "select * from ResponseContent where feedbackid = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, fbid);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                ResponseContent response = new ResponseContent();
                response.setId(rs.getInt(1));
                response.setrTittle(rs.getString(2));
                response.setrContent(rs.getString(3));
                response.setResponseDate(rs.getTimestamp(4));
                response.setAccountid(rs.getInt(5));
                response.setFeedbackid(rs.getInt(6));
                response.setModifiedDate(rs.getTimestamp(7));

                list.add(response);
            }

        } catch (SQLException e) {
        }
        return list;
    }

    /**
     * get response by id
     *
     * @param responseid
     * @return
     */
    public ResponseContent getResponsByID(int responseid) {
        String sql = "select * from ResponseContent where id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, responseid);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                ResponseContent response = new ResponseContent();
                response.setId(rs.getInt(1));
                response.setrTittle(rs.getString(2));
                response.setrContent(rs.getString(3));
                response.setResponseDate(rs.getTimestamp(4));
                response.setAccountid(rs.getInt(5));
                response.setFeedbackid(rs.getInt(6));
                response.setModifiedDate(rs.getTimestamp(7));

                return response;
            }

        } catch (SQLException e) {
        }
        return null;
    }

    /**
     * generate next response id base on highest response id
     *
     * @return
     */
    public int nextResponseID() {
        String sql = "select max(id) from ResponseContent";
        int id = 1;
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                id += rs.getInt(1);
            }
        } catch (SQLException e) {
        }
        return id;
    }

    /**
     * add response to database
     *
     * @param rtittle
     * @param rcontent
     * @param responseDate
     * @param userid
     * @param fbid
     * @return
     */
    public boolean addResponse(String rtittle, String rcontent, Timestamp responseDate, int userid, int fbid) {
        String sql = "insert into ResponseContent values(?,?,?,?,?,?,?)";
        String sql_update = "update Feedback set status = 1 where id = ?";
        boolean check = false;
        int id = nextResponseID();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            PreparedStatement statement_update = connection.prepareStatement(sql_update);
            statement.setInt(1, id);
            statement.setString(2, rtittle);
            statement.setString(3, rcontent);
            statement.setTimestamp(4, responseDate);
            statement.setInt(5, userid);
            statement.setInt(6, fbid);
            statement.setTimestamp(7, responseDate);

            statement_update.setInt(1, fbid);
            int icheck = statement.executeUpdate();
            int ucheck = statement_update.executeUpdate();
            if (icheck > 0 && ucheck > 0) {
                check = true;
            }
        } catch (SQLException e) {
            check = false;
        }
        return check;
    }

    /**
     *
     * @param offset
     * @param userid
     * @param freeSearch
     * @param receivefrom
     * @param receiveto
     * @param responsefrom
     * @param responseto
     * @param type
     * @param publictype
     * @param sortdate
     * @return
     */
    public Map<Feedback, List<ResponseContent>> searchResponseWithFeedback(int offset, int userid, String freeSearch, Date receivefrom, Date receiveto, Date responsefrom, Date responseto, int typeid, String publictype, String sortdate) {
        Map<Feedback, List<ResponseContent>> map = new LinkedHashMap<>();
        String sql = "Select r.id, f.id, r.rtittle, r.rcontent, f.createdate, r.responsedate, f.ftitle, f.typeid, f.[public], r.modifieddate, ft.typename from ResponseContent r, Feedback f, FeedbackType ft where r.feedbackid = f.id and f.typeid = ft.id";
        if (userid > 0) {
            sql += " and r.accountid = " + userid;
        }
        if (receivefrom != null) {
            sql += " and convert(date, f.createdate) >= '" + receivefrom + "'";
        }
        if (receiveto != null) {
            sql += " and convert(date, f.createdate) <= '" + receiveto + "'";
        }
        if (responsefrom != null) {
            sql += " and convert(date, r.responsedate) >= '" + responsefrom + "'";
        }
        if (responseto != null) {
            sql += " and convert(date, r.responsedate) <= '" + responseto + "'";
        }
        if (typeid > 0) {
            sql += " and f.typeid = " + typeid;
        }
        if (publictype != null) {
            sql += " and f.[public] = " + publictype;
        }
        if (freeSearch != null) {
            sql += " and (f.ftitle like N'%" + freeSearch + "%' or r.rtittle like N'%" + freeSearch + "%' or r.rcontent like N'%" + freeSearch + "%')";
        }
        if (sortdate != null && !sortdate.equalsIgnoreCase("")) {
            switch (sortdate) {
                case "newestReceive":
                    sql += " order by createdate desc";
                    break;
                case "oldestReceive":
                    sql += " order by createdate asc";
                    break;
                case "newestResponse":
                    sql += " order by responsedate desc";
                    break;
                case "oldestResponse":
                    sql += " order by responsedate asc";
                    break;
                default:
                    sql += " order by f.id";
                    break;
            }
        } else {
            sql += " order by f.id";
        }

        sql += " offset ? rows fetch next 5 rows only";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, (offset - 1) * 5);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Feedback feedback = new Feedback();
                ResponseContent response = new ResponseContent();
                FeedbackType feedbackType = new FeedbackType(rs.getInt(8), rs.getString(11));
                List<ResponseContent> rlist = new ArrayList<>();

                // create feedback object data
                feedback.setId(rs.getInt(2));
                feedback.setCreateDate(rs.getTimestamp(5));
                feedback.setfTitle(rs.getString(7));
                feedback.setFeedbackType(feedbackType);
                feedback.setCheckPublic(rs.getBoolean(9));

                // create response object data
                response.setId(rs.getInt(1));
                response.setrTittle(rs.getString(3));
                response.setrContent(rs.getString(4));
                response.setResponseDate(rs.getTimestamp(6));
                response.setModifiedDate(rs.getTimestamp(10));
                if (map.containsKey(feedback)) {
                    map.get(feedback).add(response); // Add response to existing feedback
                } else {
                    rlist.add(response);
                    map.put(feedback, rlist); // Create new feedback entry
                }
            }
        } catch (SQLException e) {
            System.out.println("loi sql " + e);
        }
        return map;
    }

    public int countSearch(int userid, String freeSearch, Date receivefrom, Date receiveto, Date responsefrom, Date responseto, int typeid, String publictype, String sortdate) {
        int count = 0;
        String sql = "Select count(*) from ResponseContent r, Feedback f, FeedbackType ft where f.typeid = ft.id and r.feedbackid = f.id ";
        if (userid > 0) {
            sql += " and r.accountid = " + userid;
        }
        if (receivefrom != null) {
            sql += " and convert(date, f.createdate) >= '" + receivefrom + "'";
        }
        if (receiveto != null) {
            sql += " and convert(date, f.createdate) <= '" + receiveto + "'";
        }
        if (responsefrom != null) {
            sql += " and convert(date, r.responsedate) >= '" + responsefrom + "'";
        }
        if (responseto != null) {
            sql += " and convert(date, r.responsedate) <= '" + responseto + "'";
        }
        if (typeid > 0) {
            sql += " and f.typeid = " + typeid;
        }
        if (publictype != null) {
            switch (publictype) {
                case "0":
                    sql += " and f.[public] = " + publictype;
                    break;
                case "1":
                    sql += " and f.[public] = " + publictype;
                    break;
                default:
                    break;
            }
        }
        if (freeSearch != null) {
            sql += " and (f.ftitle like N'%" + freeSearch + "%' or r.rtittle like N'%" + freeSearch + "%' or r.rcontent like N'%" + freeSearch + "%')";
        }
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("loi sql " + e);
        }
        return 0;
    }

    public boolean updateResponse(int id, String content) {
        boolean check = false;
        String sql = "update ResponseContent set rcontent = ?, modifieddate = ? where id = ?";
        LocalDateTime now = LocalDateTime.now();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, content);
            statement.setTimestamp(2, Timestamp.valueOf(now));
            statement.setInt(3, id);
            int rs = statement.executeUpdate();
            if (rs > 0) {
                check = true;
            }
        } catch (SQLException e) {
        }
        return check;
    }

    public boolean deleteResponse(int rpid, int fbid) {
        boolean check = false;
        String sql = "delete from ResponseContent where id = ?";
        String status_sql = "DECLARE @feedbackIdToCheck INT = ?\n"
                + "IF EXISTS (SELECT 1 FROM ResponseContent WHERE feedbackid = @feedbackIdToCheck)\n"
                + "    UPDATE Feedback SET [status] = 1 WHERE id = @feedbackIdToCheck;\n"
                + "ELSE\n"
                + "    UPDATE Feedback SET [status] = 0 WHERE id = @feedbackIdToCheck;";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            PreparedStatement status_statement = connection.prepareStatement(status_sql);
            statement.setInt(1, rpid);
            status_statement.setInt(1, fbid);
            int rs = statement.executeUpdate();
            int rs2 = status_statement.executeUpdate();
            if (rs > 0) {
                check = true;
            }
        } catch (SQLException e) {
        }
        return check;
    }

    public static void main(String[] args) {
        ResponseContentDAO dao = new ResponseContentDAO();

        LocalDate now = LocalDate.now();
        LocalDate before = now.minusDays(3);

        int count = dao.countSearch(0, null, null, null, null, null, 0, null, null);
        System.out.println(count);
        Map<Feedback, List<ResponseContent>> map = dao.searchResponseWithFeedback(1, 0, null, null, null, null, null, 0, null, "newestReceive");
        for (Feedback feedback : map.keySet()) {
            System.out.print(feedback.getfTitle() + " ");
            System.out.println();
        }
        
    }
}
