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
import java.util.List;
import java.sql.Date;
import dal.*;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import model.*;

/**
 *
 * @author ADMIN
 */
public class FeedBackDisplayServlet extends HttpServlet {

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
            out.println("<title>Servlet FeedBackDisplayServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet FeedBackDisplayServlet at " + request.getContextPath() + "</h1>");
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
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        String gets = request.getParameter("get");
        String page_raw = request.getParameter("page");

        // get parameter of search for paging
        String search = request.getParameter("search");
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
        String url = "feedbackdisplay?";
        for (String key : searchMap.keySet()) {
            if (searchMap.get(key) != null && !searchMap.get(key).equalsIgnoreCase("")) {
                url += key + "=" + searchMap.get(key) + "&";
            }
        }
        request.setAttribute("url", url);
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

        FeedbackDAO feedbackDAO = new FeedbackDAO();
        FeedbackTypeDAO feedbackTypeDAO = new FeedbackTypeDAO();
        List<FeedbackType> typeList = feedbackTypeDAO.getAllTypes();

        request.setAttribute("tList", typeList);

        List<Feedback> flist;
        int countSearch = 0;
        int total_page = 1;
        Date fromDate = (fromdate_raw != null && !fromdate_raw.equals("")) ? Date.valueOf(fromdate_raw) : null;
        Date toDate = (todate_raw != null && !todate_raw.equals("")) ? Date.valueOf(todate_raw) : null;

        request.setAttribute("current", page);
        request.setAttribute("search", search);
        request.setAttribute("fromdate", fromDate);
        request.setAttribute("todate", toDate);
        request.setAttribute("type", type);
        request.setAttribute("publicType", publicType);
        request.setAttribute("privateType", privateType);
        request.setAttribute("sortdate", sortdate);
        
        int numberperpage = 4;
        if (gets != null && !gets.trim().equals("")) {
            switch (gets) {
                case "all":
                    flist = feedbackDAO.searchFeedbacks(page, numberperpage, account.getId(), null, fromDate, toDate, typeid, checkPublic, search, sortdate, "own");
                    
                    countSearch = feedbackDAO.countAfterSearch(account.getId(), null, fromDate, toDate, typeid, checkPublic, search, "own");
                    total_page = (countSearch % numberperpage == 0) ? (countSearch / numberperpage) : (countSearch / numberperpage + 1);
                    request.setAttribute("totalpage", total_page);
                    request.setAttribute("get", gets);
                    request.setAttribute("fbList", flist);
                    request.getRequestDispatcher("./student/feedbackdisplay.jsp").forward(request, response);
                    break;
                case "notresponse":
                    flist = feedbackDAO.searchFeedbacks(page, numberperpage, account.getId(), "0", fromDate, toDate, typeid, checkPublic, search, sortdate, "own");
                    countSearch = feedbackDAO.countAfterSearch(account.getId(), "0", fromDate, toDate, typeid, checkPublic, search, "own");
                    total_page = (countSearch % numberperpage == 0) ? (countSearch / numberperpage) : (countSearch / numberperpage + 1);
                    request.setAttribute("totalpage", total_page);
                    request.setAttribute("get", gets);
                    request.setAttribute("fbList", flist);
                    request.getRequestDispatcher("./student/feedbackdisplay.jsp").forward(request, response);
                    break;
                case "responded":
                    flist = feedbackDAO.searchFeedbacks(page, numberperpage, account.getId(), "1", fromDate, toDate, typeid, checkPublic, search, sortdate, "own");
                    countSearch = feedbackDAO.countAfterSearch(account.getId(), "1", fromDate, toDate, typeid, checkPublic, search, "own");
                    total_page = (countSearch % numberperpage == 0) ? (countSearch / numberperpage) : (countSearch / numberperpage + 1);
                    request.setAttribute("totalpage", total_page);
                    request.setAttribute("get", gets);
                    request.setAttribute("fbList", flist);
                    request.getRequestDispatcher("./student/feedbackdisplay.jsp").forward(request, response);
                    break;
                default:
                    flist = feedbackDAO.searchFeedbacks(page, numberperpage, account.getId(), null, fromDate, toDate, typeid, checkPublic, search, sortdate, "own");
                    countSearch = feedbackDAO.countAfterSearch(account.getId(), null, fromDate, toDate, typeid, checkPublic, search, "own");
                    total_page = (countSearch % numberperpage == 0) ? (countSearch / numberperpage) : (countSearch / numberperpage + 1);
                    request.setAttribute("totalpage", total_page);
                    request.setAttribute("get", gets);
                    request.setAttribute("fbList", flist);
                    request.getRequestDispatcher("./student/feedbackdisplay.jsp").forward(request, response);
                    break;
            }
        } else {
            flist = feedbackDAO.searchFeedbacks(page, numberperpage, account.getId(), null, fromDate, toDate, typeid, checkPublic, search, sortdate, "own");
            countSearch = feedbackDAO.countAfterSearch(account.getId(), null, fromDate, toDate, typeid, checkPublic, search, "own");
            total_page = (countSearch % numberperpage == 0) ? (countSearch / numberperpage) : (countSearch / numberperpage + 1);
            request.setAttribute("totalpage", total_page);
            request.setAttribute("get", gets);
            request.setAttribute("fbList", flist);
            request.getRequestDispatcher("./student/feedbackdisplay.jsp").forward(request, response);
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
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        String gets = request.getParameter("get");
        String search = request.getParameter("search");
        String fromdate_raw = request.getParameter("fromdate");
        String todate_raw = request.getParameter("todate");
        String type = request.getParameter("fbtype");
        String publicType = request.getParameter("publics");
        String privateType = request.getParameter("privates");
        String sortdate = request.getParameter("sortdate");

        String encodeSearch = URLEncoder.encode(search, "UTF-8");

        LinkedHashMap<String, String> searchMap = new LinkedHashMap<>() {
            {
                put("get", gets);
                put("search", encodeSearch);
                put("fromdate", fromdate_raw);
                put("todate", todate_raw);
                put("fbtype", type);
                put("publics", publicType);
                put("privates", privateType);
                put("sortdate", sortdate);
            }
        };

        // create uri to send back to doget of this servlet
        String url = "feedbackdisplay?";
        for (String key : searchMap.keySet()) {
            if (searchMap.get(key) != null && !searchMap.get(key).equalsIgnoreCase("")) {
                url += key + "=" + searchMap.get(key) + "&";
            }
        }

        response.sendRedirect(url);
//        FeedbackDAO feedbackDAO = new FeedbackDAO();
//
//        List<String> typeList = feedbackDAO.getAllTypeWithID(account.getId());
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
//        if (search == null || search.trim().equals("")) {
//            search = null;
//        }
//        try {
//            Date fromDate = (fromdate_raw != null && !fromdate_raw.equals("")) ? Date.valueOf(fromdate_raw) : null;
//            Date toDate = (todate_raw != null && !todate_raw.equals("")) ? Date.valueOf(todate_raw) : null;
//            List<Feedback> list;
//            if (status != null && !status.equals("")) {
//                list = feedbackDAO.searchFeedbacks(account.getId(), status, fromDate, toDate, type, checkPublic, search, false);
//            } else {
//                list = feedbackDAO.searchFeedbacks(account.getId(), null, fromDate, toDate, type, checkPublic, search, false);
//            }
//
//            if (sortdate.equalsIgnoreCase("oldest")) {
//                Collections.sort(list, new Comparator<Feedback>() {
//                    @Override
//                    public int compare(Feedback o1, Feedback o2) {
//                        return o1.getCreateDate().compareTo(o2.getCreateDate());
//                    }
//                });
//            } else if (sortdate.equalsIgnoreCase("newest")) {
//                Collections.sort(list, new Comparator<Feedback>() {
//                    @Override
//                    public int compare(Feedback o1, Feedback o2) {
//                        return -o1.getCreateDate().compareTo(o2.getCreateDate());
//                    }
//                });
//            }
//
//            request.setAttribute("search", search);
//            request.setAttribute("fromdate", fromDate);
//            request.setAttribute("todate", toDate);
//            request.setAttribute("type", type);
//            request.setAttribute("publicType", publicType);
//            request.setAttribute("privateType", privateType);
//            request.setAttribute("status", status);
//            request.setAttribute("fbList", list);
//            request.setAttribute("sortdate", sortdate);
//            request.getRequestDispatcher("./student/feedbackdisplay.jsp").forward(request, response);
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
