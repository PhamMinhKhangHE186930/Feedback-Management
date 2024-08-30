/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.*;
import dal.*;
import jakarta.servlet.http.HttpSession;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public class searchAjaxServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String gets = request.getParameter("get");
        String page_raw = request.getParameter("page");

        // get parameter of search for paging
        String search = request.getParameter("txt");
        String fromdate_raw = request.getParameter("fromdate");
        String todate_raw = request.getParameter("todate");
        String type = request.getParameter("fbtype");
        String publicType = request.getParameter("publics");
        String privateType = request.getParameter("privates");
        String sortdate = request.getParameter("sortdate");

        LinkedHashMap<String, String> searchMap = new LinkedHashMap<>() {
            {
                put("get", gets);
                put("fromdate", fromdate_raw);
                put("todate", todate_raw);
                put("fbtype", type);
                put("publics", publicType);
                put("privates", privateType);
                put("sortdate", sortdate);
            }
        };

        searchMap.put("search", search);

        // create uri to send back to doget of this servlet
        String url = "fbdisplayforsupport?";
        for (String key : searchMap.keySet()) {
            if (searchMap.get(key) != null && !searchMap.get(key).trim().equalsIgnoreCase("")) {
                url += key + "=" + searchMap.get(key) + "&";
            }
        }
        request.setAttribute("url", url);

        // check if page is null or < 1 will automatic is 1
        int page;
        if (page_raw == null || page_raw.equalsIgnoreCase("")) {
            page = 1;
        } else {
            try {
                page = Integer.parseInt(page_raw);
                if (page < 1) {
                    page = 1;
                }
            } catch (NumberFormatException e) {
                page = 1;
            }
        }

        String checkPublic;
        if ((publicType != null && privateType != null) || (publicType == null && privateType == null)) {
            checkPublic = null;
        } else if (publicType != null) {
            checkPublic = "1";
        } else {
            checkPublic = "0";
        }

        if (search == null || search.trim().equals("")) {
            search = null;
        }
        int typeid = 0;
        if (type != null) {
            typeid = Integer.parseInt(type);
        }
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");

        // DAO
        FeedbackDAO feedbackDAO = new FeedbackDAO();
        AccountDAO accountDAO = new AccountDAO();
        FeedbackTypeDAO feedbackTypeDAO = new FeedbackTypeDAO();

        List<FeedbackType> typeList = feedbackTypeDAO.getAllTypes();
        request.setAttribute("tList", typeList);

        int countSearch = 0;
        int total_page = 1;
        Date fromDate = (fromdate_raw != null && !fromdate_raw.equals("")) ? Date.valueOf(fromdate_raw) : null;
        Date toDate = (todate_raw != null && !todate_raw.equals("")) ? Date.valueOf(todate_raw) : null;

        List<Account> aList = accountDAO.getAllAccounts();
        List<Feedback> flist = new ArrayList<>();
        int numberperpage = 5;
        if (gets != null && !gets.trim().equals("")) {
            switch (gets) {
                case "all":
                    flist = feedbackDAO.searchFeedbacks(page, numberperpage, 0, null, fromDate, toDate, typeid, checkPublic, search, sortdate, null);
                    countSearch = feedbackDAO.countAfterSearch(page, null, fromDate, toDate, typeid, checkPublic, search, null);
                    total_page = (countSearch % numberperpage == 0) ? (countSearch / numberperpage) : (countSearch / numberperpage + 1);
                    break;
                case "notresponse":
                    flist = feedbackDAO.searchFeedbacks(page, numberperpage, 0, "0", fromDate, toDate, typeid, checkPublic, search, sortdate, null);
                    countSearch = feedbackDAO.countAfterSearch(page, "0", fromDate, toDate, typeid, checkPublic, search, null);
                    total_page = (countSearch % numberperpage == 0) ? (countSearch / numberperpage) : (countSearch / numberperpage + 1);
                    break;
                case "responded":
                    flist = feedbackDAO.searchFeedbacks(page, numberperpage, 0, "1", fromDate, toDate, typeid, checkPublic, search, sortdate, null);
                    countSearch = feedbackDAO.countAfterSearch(page, "1", fromDate, toDate, typeid, checkPublic, search, null);
                    total_page = (countSearch % numberperpage == 0) ? (countSearch / numberperpage) : (countSearch / numberperpage + 1);
                    break;
                case "last24":
                    LocalDate now = LocalDate.now();
                    LocalDate last24 = now.minusDays(1);
                    flist = feedbackDAO.searchFeedbacks(page, numberperpage, 0, null, Date.valueOf(last24), null, 0, null, null, null, null);
                    countSearch = feedbackDAO.countAfterSearch(page, null, Date.valueOf(last24), null, 0, null, null, null);
                    total_page = (countSearch % numberperpage == 0) ? (countSearch / numberperpage) : (countSearch / numberperpage + 1);
                    break;
                default:
                    response.sendRedirect("supporthome");
            }
        } else {
            flist = feedbackDAO.searchFeedbacks(page, numberperpage, 0, null, fromDate, toDate, typeid, checkPublic, search, sortdate, null);
            countSearch = feedbackDAO.countAfterSearch(page, null, fromDate, toDate, typeid, checkPublic, search, null);
            total_page = (countSearch % numberperpage == 0) ? (countSearch / numberperpage) : (countSearch / numberperpage + 1);

        }

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm");
        int count = 0;
        for (Feedback feedback : flist) {
            count++;
            String stdname = "";
            for (Account account1 : aList) {
                if (account1.getId() == feedback.getAccountID()) {
                    stdname = account1.getDisplayname();
                }
            }
            out.println("                                    <tr>\n"
                    + "                                        <td>" + count + "</td>\n"
                    + "                                        <td>\n" + stdname + "</td>\n"
                    + "                                        <td>" + feedback.getFeedbackType().getType_name() + "</td>\n"
                    + "                                        <td>" + feedback.getfTitle() + "</td>\n"
                    + "                                        <td style=\"width: 20%;\">" + feedback.getfContent() + "</td>\n"
                    + "                                        <td>" + formatter.format(feedback.getCreateDate()) + "</td>\n"
                    + "                                        <td>" + (feedback.isStatus() ? "responded" : "not response") + "</td>\n"
                    + "                                        <td>" + (feedback.isCheckPublic() ? "public" : "private") + "</td>\n"
                    + "                                        <td>\n"
                    + "                                            <a href=\"/StudentFeedback/createresponse?fbid=" + feedback.getId() + "\">\n"
                    + "                                                <input type=\"submit\" value=\"" + (feedback.isStatus() ? "Another Response" : "Create Resoponse") + "\" />\n"
                    + "                                            </a>\n"
                    + "                                            " + (feedback.isStatus()
                    ? "                                                <a href=\"/StudentFeedback/allresponsetofeedback?fbid=" + feedback.getId() + "\">\n"
                    + "                                                    <input type=\"submit\" value=\"See All Response\" />\n"
                    + "                                                </a>\n" : "")
                    + "                                        </td>\n"
                    + "                                    </tr>\n"
                    + "                                <nav aria-label=\"Page navigation example\">\n"
                    + "                                <ul class=\"pagination\">\n"
                    + "                                    <li class=\"page-item ${current == 1 ? 'disabled':''}\">\n"
                    + "                                        <a class=\"page-link\" href=\"${requestScope.url}page=${current > 1 ? (current-1):current}\" aria-label=\"Previous\">\n"
                    + "                                            <span aria-hidden=\"true\">&laquo;</span>\n"
                    + "                                        </a>\n"
                    + "                                    </li>\n"
                    + "                                    <c:forEach begin=\"1\" end=\"${requestScope.totalpage}\" var=\"page\">\n"
                    + "                                        <li class=\"page-item\"><a class=\"page-link ${requestScope.current eq page?'active':''}\" href=\"${requestScope.url}page=${page}\">${page}</a></li>\n"
                    + "                                        </c:forEach>\n"
                    + "                                    <li class=\"page-item ${current == requestScope.totalpage ? 'disabled':''}\">\n"
                    + "                                        <a class=\"page-link\" href=\"${requestScope.url}page=${current < requestScope.totalpage ? (current+1):current}\" aria-label=\"Next\">\n"
                    + "                                            <span aria-hidden=\"true\">&raquo;</span>\n"
                    + "                                        </a>\n"
                    + "                                    </li>\n"
                    + "                                </ul>\n"
                    + "                            </nav>");
        }
        if (count == 0) {
            out.println("There is no feedback match with condition");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
