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
import jakarta.servlet.http.HttpSession;
import dal.*;
import java.net.URLEncoder;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import model.*;

/**
 *
 * @author ADMIN
 */
public class ResponseDisplayServlet extends HttpServlet {

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
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ResponseDisplayServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ResponseDisplayServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
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
//        processRequest(request, response);
        String gets = request.getParameter("get");
        String page_raw = request.getParameter("page");

        // create search data
        String search = request.getParameter("search");
        String receivefrom_raw = request.getParameter("receivefrom");
        String receiveto_raw = request.getParameter("receiveto");
        String responsefrom_raw = request.getParameter("responsefrom");
        String responseto_raw = request.getParameter("responseto");
        String type = request.getParameter("fbtype");
        String publicType = request.getParameter("publics");
        String privateType = request.getParameter("privates");
        String sortdate = request.getParameter("sortdate");
        String url_req = request.getParameter("url");

        // create key and value to append to the url of page
        LinkedHashMap<String, String> searchMap = new LinkedHashMap<>();
        searchMap.put("get", gets);
        searchMap.put("search", search);
        searchMap.put("receivefrom", receivefrom_raw);
        searchMap.put("receiveto", receiveto_raw);
        searchMap.put("responsefrom", responsefrom_raw);
        searchMap.put("responseto", responseto_raw);
        searchMap.put("fbtype", type);
        searchMap.put("publics", publicType);
        searchMap.put("privates", privateType);
        searchMap.put("sortdate", sortdate);

        String url = "responsedisplay?";

        for (String key : searchMap.keySet()) {
            if (searchMap.get(key) != null && !searchMap.get(key).equalsIgnoreCase("")) {
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

        if (sortdate == null || sortdate.equalsIgnoreCase("none")) {
            sortdate = null;
        }

        if (search == null || search.trim().equals("")) {
            search = null;
        }
        int typeid = 0;
        if (type != null) {
            typeid = Integer.parseInt(type);
        }
        HttpSession session = request.getSession(false);
        Account account = (Account) session.getAttribute("account");

        // create dao
        FeedbackDAO fDAO = new FeedbackDAO();
        ResponseContentDAO rDAO = new ResponseContentDAO();
        FeedbackTypeDAO feedbackTypeDAO = new FeedbackTypeDAO();

        List<FeedbackType> typeList = feedbackTypeDAO.getAllTypes();
        request.setAttribute("tList", typeList);

        Map<Feedback, List<ResponseContent>> map;
        int count = 0;
        int totalpage = 1;
        Date receivefrom = (receivefrom_raw != null && !receivefrom_raw.equals("")) ? Date.valueOf(receivefrom_raw) : null;
        Date receiveto = (receiveto_raw != null && !receiveto_raw.equals("")) ? Date.valueOf(receiveto_raw) : null;
        Date responsefrom = (responsefrom_raw != null && !responsefrom_raw.equals("")) ? Date.valueOf(responsefrom_raw) : null;
        Date responseto = (responseto_raw != null && !responseto_raw.equals("")) ? Date.valueOf(responseto_raw) : null;

        // set attribute to jsp to get current state of search continously
        request.setAttribute("current", page);
        request.setAttribute("search", search);
        request.setAttribute("receivefrom", receivefrom);
        request.setAttribute("receiveto", receiveto);
        request.setAttribute("responsefrom", responsefrom);
        request.setAttribute("responseto", responseto);
        request.setAttribute("type", type);
        request.setAttribute("publicType", publicType);
        request.setAttribute("privateType", privateType);
        request.setAttribute("sortdate", sortdate);
        if (gets != null && !gets.equalsIgnoreCase("")) {
            switch (gets) {
                case "all":
                    map = rDAO.searchResponseWithFeedback(page, 0, search, receivefrom, receiveto, responsefrom, responseto, typeid, checkPublic, sortdate);
                    count = rDAO.countSearch(0, search, receivefrom, receiveto, responsefrom, responseto, typeid, checkPublic, sortdate);
                    totalpage = (count % 5 == 0) ? (count / 5) : (count / 5 + 1);
                    request.setAttribute("totalpage", totalpage);
                    request.setAttribute("get", gets);

                    request.setAttribute("map", map);
                    request.getRequestDispatcher("./supporter/responsedisplay.jsp").forward(request, response);
                    break;
                case "user":
                    map = rDAO.searchResponseWithFeedback(page, account.getId(), search, receivefrom, receiveto, responsefrom, responseto, typeid, checkPublic, sortdate);
                    count = rDAO.countSearch(account.getId(), search, receivefrom, receiveto, responsefrom, responseto, typeid, checkPublic, sortdate);
                    totalpage = (count % 5 == 0) ? (count / 5) : (count / 5 + 1);
                    request.setAttribute("totalpage", totalpage);
                    request.setAttribute("get", gets);
                    request.setAttribute("responseof", "user");
                    request.setAttribute("map", map);
                    request.getRequestDispatcher("./supporter/responsedisplay.jsp").forward(request, response);
                    break;
                default:
                    response.sendRedirect("/StudentFeedback/supporthome");
            }
        } else {
            map = rDAO.searchResponseWithFeedback(page, 0, search, receivefrom, receiveto, responsefrom, responseto, typeid, checkPublic, sortdate);
            count = rDAO.countSearch(account.getId(), search, receivefrom, receiveto, responsefrom, responseto, typeid, checkPublic, sortdate);
            totalpage = (count % 5 == 0) ? (count / 5) : (count / 5 + 1);
            request.setAttribute("totalpage", totalpage);
            request.setAttribute("get", "all");
            request.setAttribute("responseof", "user");
            request.setAttribute("map", map);
            request.getRequestDispatcher("./supporter/responsedisplay.jsp").forward(request, response);
        }
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
//        processRequest(request, response);
        String gets = request.getParameter("get");

        // not use, each search need reset to first page, so that if take the current page can cause error if the search does not long page enough
        String page_raw = request.getParameter("page");

        String search = request.getParameter("search");
        String receivefrom_raw = request.getParameter("receivefrom");
        String receiveto_raw = request.getParameter("receiveto");
        String responsefrom_raw = request.getParameter("responsefrom");
        String responseto_raw = request.getParameter("responseto");
        String type = request.getParameter("fbtype");
        String publicType = request.getParameter("publics");
        String privateType = request.getParameter("privates");
        String sortdate = request.getParameter("sortdate");

        String encodeSearch = URLEncoder.encode(search, "UTF-8");

        LinkedHashMap<String, String> searchMap = new LinkedHashMap<>();
        searchMap.put("get", gets);
        searchMap.put("search", encodeSearch);
        searchMap.put("receivefrom", receivefrom_raw);
        searchMap.put("receiveto", receiveto_raw);
        searchMap.put("responsefrom", responsefrom_raw);
        searchMap.put("responseto", responseto_raw);
        searchMap.put("fbtype", type);
        searchMap.put("publics", publicType);
        searchMap.put("privates", privateType);
        searchMap.put("sortdate", sortdate);

        String url = "responsedisplay?";

        for (String key : searchMap.keySet()) {
            if (searchMap.get(key) != null && !searchMap.get(key).equalsIgnoreCase("")) {
                url += key + "=" + searchMap.get(key) + "&";
            }
        }
        response.sendRedirect(url);

//        // session
//        HttpSession session = request.getSession(false);
//        Account account = (Account) session.getAttribute("account");
//        // DAO
//        FeedbackDAO feedbackDAO = new FeedbackDAO();
//        ResponseContentDAO responseDAO = new ResponseContentDAO();
//
//        List<String> typeList = feedbackDAO.getAllTypeWithID(-1);
//        request.setAttribute("tList", typeList);
//
//        String checkPublic;
//        if ((publicType != null && privateType != null) || (publicType == null && privateType == null)) {
//            checkPublic = null;
//        } else if (publicType != null) {
//            checkPublic = "1";
//        } else {
//            checkPublic = "0";
//        }
//
//        if (sortdate.equalsIgnoreCase("none")) {
//            sortdate = null;
//        }
//
//        if (search == null || search.trim().equals("")) {
//            search = null;
//        }
//
//        try {
//            Date receivefrom = (receivefrom_raw != null && !receivefrom_raw.equals("")) ? Date.valueOf(receivefrom_raw) : null;
//            Date receiveto = (receiveto_raw != null && !receiveto_raw.equals("")) ? Date.valueOf(receiveto_raw) : null;
//            Date responsefrom = (responsefrom_raw != null && !responsefrom_raw.equals("")) ? Date.valueOf(responsefrom_raw) : null;
//            Date responseto = (responseto_raw != null && !responseto_raw.equals("")) ? Date.valueOf(responseto_raw) : null;
//            Map<Feedback, List<ResponseContent>> map;
//
//            int count = 0;
//            int totalpage = 1;
//            if (responseof != null && !responseof.equals("")) {
//                map = responseDAO.searchResponseWithFeedback(1, 0, search, receivefrom, receiveto, responsefrom, responseto, type, checkPublic, sortdate);
//                count = responseDAO.countSearch(0, search, receivefrom, receiveto, responsefrom, responseto, type, publicType, sortdate);
//                totalpage = (count % 5 == 0) ? (count / 5) : ((count / 5) + 1);
//                request.setAttribute("totalpage", totalpage);
//            } else {
//                map = responseDAO.searchResponseWithFeedback(1, account.getId(), search, receivefrom, receiveto, responsefrom, responseto, type, checkPublic, sortdate);
//                count = responseDAO.countSearch(account.getId(), search, receivefrom, receiveto, responsefrom, responseto, type, checkPublic, sortdate);
//                totalpage = (count % 5 == 0) ? (count / 5) : ((count / 5) + 1);
//                request.setAttribute("totalpage", totalpage);
//            }
//
//            AccountDAO accountDAO = new AccountDAO();
//            List<Account> aList = accountDAO.getAllAccounts();
//            // set current page to 1 to when ever user search with find from first page
//            request.setAttribute("current", "1");
//
//            request.setAttribute("search", search);
//            request.setAttribute("receivefrom", receivefrom);
//            request.setAttribute("receiveto", receiveto);
//            request.setAttribute("responsefrom", responsefrom);
//            request.setAttribute("responseto", responseto);
//            request.setAttribute("type", type);
//            request.setAttribute("publicType", publicType);
//            request.setAttribute("privateType", privateType);
//            request.setAttribute("responseof", responseof);
//            request.setAttribute("map", map);
//            request.setAttribute("sortdate", sortdate);
//            request.getRequestDispatcher("./supporter/responsedisplay.jsp").forward(request, response);
//        } catch (IllegalArgumentException e) {
//        }
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
