/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.*;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.*;

/**
 *
 * @author ADMIN
 */
public class StdDetailsFeedbServlet extends HttpServlet {

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
            out.println("<title>Servlet StdDetailsFeedbServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet StdDetailsFeedbServlet at " + request.getContextPath() + "</h1>");
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

        // get parameter of search for paging
        String search = request.getParameter("search");
        String fromdate_raw = request.getParameter("fromdate");
        String todate_raw = request.getParameter("todate");
        String type = request.getParameter("fbtype");
        String publicType = request.getParameter("publics");
        String privateType = request.getParameter("privates");
        String sortdate = request.getParameter("sortdate");
        String stdid_raw = request.getParameter("stdid");

        LinkedHashMap<String, String> searchMap = new LinkedHashMap<>() {
            {
                put("get", gets);
                put("fromdate", fromdate_raw);
                put("todate", todate_raw);
                put("fbtype", type);
                put("publics", publicType);
                put("privates", privateType);
                put("sortdate", sortdate);
                put("stdid", stdid_raw);
            }
        };

        searchMap.put("search", search);

        // create uri to send back to doget of this servlet
        String url = "stddetailsfeedb?";
        for (String key : searchMap.keySet()) {
            if (searchMap.get(key) != null && !searchMap.get(key).isEmpty()) {
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
        int stdid = 0;
        try {
            stdid = Integer.parseInt(stdid_raw);
            if (stdid <= 0) {
                throw new Exception();
            }
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Student is invalid");
            request.getRequestDispatcher("./supporter/stddetailsfeedb.jsp").forward(request, response);
        } catch (Exception ex) {
            request.setAttribute("error", "Student is invalid");
            request.getRequestDispatcher("./supporter/stddetailsfeedb.jsp").forward(request, response);
        }

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
        request.setAttribute("stdid", stdid);

        int numberperpage = 5;
        if (gets != null && !gets.trim().equals("")) {
            switch (gets) {
                case "all":
                    flist = feedbackDAO.searchFeedbacks(page, numberperpage, stdid, null, fromDate, toDate, typeid, checkPublic, search, sortdate, "own");
                    countSearch = feedbackDAO.countAfterSearch(stdid, null, fromDate, toDate, typeid, checkPublic, search, "own");
                    total_page = (countSearch % numberperpage == 0) ? (countSearch / numberperpage) : (countSearch / numberperpage + 1);
                    request.setAttribute("totalpage", total_page);
                    request.setAttribute("get", gets);
                    request.setAttribute("fbList", flist);
                    request.getRequestDispatcher("./supporter/stddetailsfeedb.jsp").forward(request, response);
                    break;
                case "notresponse":
                    flist = feedbackDAO.searchFeedbacks(page, numberperpage, stdid, "0", fromDate, toDate, typeid, checkPublic, search, sortdate, "own");
                    countSearch = feedbackDAO.countAfterSearch(stdid, "0", fromDate, toDate, typeid, checkPublic, search, "own");
                    total_page = (countSearch % numberperpage == 0) ? (countSearch / numberperpage) : (countSearch / numberperpage + 1);
                    request.setAttribute("totalpage", total_page);
                    request.setAttribute("get", gets);
                    request.setAttribute("fbList", flist);
                    request.getRequestDispatcher("./supporter/stddetailsfeedb.jsp").forward(request, response);
                    break;
                case "responded":
                    flist = feedbackDAO.searchFeedbacks(page, numberperpage, stdid, "1", fromDate, toDate, typeid, checkPublic, search, sortdate, "own");
                    countSearch = feedbackDAO.countAfterSearch(stdid, "1", fromDate, toDate, typeid, checkPublic, search, "own");
                    total_page = (countSearch % numberperpage == 0) ? (countSearch / numberperpage) : (countSearch / numberperpage + 1);
                    request.setAttribute("totalpage", total_page);
                    request.setAttribute("get", gets);
                    request.setAttribute("fbList", flist);
                    request.getRequestDispatcher("./supporter/stddetailsfeedb.jsp").forward(request, response);
                    break;
                default:
                    flist = feedbackDAO.searchFeedbacks(page, numberperpage, stdid, null, fromDate, toDate, typeid, checkPublic, search, sortdate, "own");
                    countSearch = feedbackDAO.countAfterSearch(stdid, null, fromDate, toDate, typeid, checkPublic, search, "own");
                    total_page = (countSearch % numberperpage == 0) ? (countSearch / numberperpage) : (countSearch / numberperpage + 1);
                    request.setAttribute("totalpage", total_page);
                    request.setAttribute("get", gets);
                    request.setAttribute("fbList", flist);
                    request.getRequestDispatcher("./supporter/stddetailsfeedb.jsp").forward(request, response);
                    break;
            }
        } else {
            flist = feedbackDAO.searchFeedbacks(page, numberperpage, stdid, null, fromDate, toDate, typeid, checkPublic, search, sortdate, "own");
            countSearch = feedbackDAO.countAfterSearch(stdid, null, fromDate, toDate, typeid, checkPublic, search, "own");
            total_page = (countSearch % numberperpage == 0) ? (countSearch / numberperpage) : (countSearch / numberperpage + 1);
            request.setAttribute("totalpage", total_page);
            request.setAttribute("get", gets);
            request.setAttribute("fbList", flist);
            request.getRequestDispatcher("./supporter/stddetailsfeedb.jsp").forward(request, response);
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
