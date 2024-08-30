package controller;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
import dal.*;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import model.*;

/**
 *
 * @author ADMIN
 */
public class FBDisplayForSupportServlet extends HttpServlet {

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
            out.println("<title>Servlet FeedBackDPforSupportServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet FeedBackDPforSupportServlet at " + request.getContextPath() + "</h1>");
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
        request.setAttribute("tList", typeList);

        List<Feedback> flist;
        int countSearch = 0;
        int total_page = 1;
        Date fromDate = (fromdate_raw != null && !fromdate_raw.equals("")) ? Date.valueOf(fromdate_raw) : null;
        Date toDate = (todate_raw != null && !todate_raw.equals("")) ? Date.valueOf(todate_raw) : null;

        List<Account> aList = accountDAO.getAllAccounts();
        request.setAttribute("aList", aList);
        request.setAttribute("current", page);
        request.setAttribute("search", search);
        request.setAttribute("fromdate", fromDate);
        request.setAttribute("todate", toDate);
        request.setAttribute("type", type);
        request.setAttribute("publicType", publicType);
        request.setAttribute("privateType", privateType);
        request.setAttribute("sortdate", sortdate);
        
        int numberperpage = 5;
        if (gets != null && !gets.trim().equals("")) {
            switch (gets) {
                case "all":
                    flist = feedbackDAO.searchFeedbacks(page, numberperpage, 0, null, fromDate, toDate, typeid, checkPublic, search, sortdate, null);
                    countSearch = feedbackDAO.countAfterSearch(page, null, fromDate, toDate, typeid, checkPublic, search, null);
                    total_page = (countSearch % numberperpage == 0) ? (countSearch / numberperpage) : (countSearch / numberperpage + 1);
                    request.setAttribute("totalpage", total_page);
                    request.setAttribute("get", gets);
                    request.setAttribute("fbList", flist);
                    request.getRequestDispatcher("./supporter/fbdisplayforsupport.jsp").forward(request, response);
                    break;
                case "notresponse":
                    flist = feedbackDAO.searchFeedbacks(page, numberperpage, 0, "0", fromDate, toDate, typeid, checkPublic, search, sortdate, null);
                    countSearch = feedbackDAO.countAfterSearch(page, "0", fromDate, toDate, typeid, checkPublic, search, null);
                    total_page = (countSearch % numberperpage == 0) ? (countSearch / numberperpage) : (countSearch / numberperpage + 1);
                    request.setAttribute("totalpage", total_page);
                    request.setAttribute("get", gets);
                    request.setAttribute("fbList", flist);
                    request.getRequestDispatcher("./supporter/fbdisplayforsupport.jsp").forward(request, response);
                    break;
                case "responded":
                    flist = feedbackDAO.searchFeedbacks(page, numberperpage, 0, "1", fromDate, toDate, typeid, checkPublic, search, sortdate, null);
                    countSearch = feedbackDAO.countAfterSearch(page, "1", fromDate, toDate, typeid, checkPublic, search, null);
                    total_page = (countSearch % numberperpage == 0) ? (countSearch / numberperpage) : (countSearch / numberperpage + 1);
                    request.setAttribute("totalpage", total_page);
                    request.setAttribute("get", gets);
                    request.setAttribute("fbList", flist);
                    request.getRequestDispatcher("./supporter/fbdisplayforsupport.jsp").forward(request, response);
                    break;
                case "last24":
                    LocalDate now = LocalDate.now();
                    LocalDate last24 = now.minusDays(1);
                    flist = feedbackDAO.searchFeedbacks(page, numberperpage, 0, null, Date.valueOf(last24), null, 0, null, null, null, null);
                    countSearch = feedbackDAO.countAfterSearch(page, null, Date.valueOf(last24), null, 0, null, null, null);
                    total_page = (countSearch % numberperpage == 0) ? (countSearch / numberperpage) : (countSearch / numberperpage + 1);
                    request.setAttribute("totalpage", total_page);
                    request.setAttribute("get", gets);
                    request.setAttribute("fromdate", last24);
                    request.setAttribute("fbList", flist);
                    request.getRequestDispatcher("./supporter/fbdisplayforsupport.jsp").forward(request, response);
                    break;
                default:
                    response.sendRedirect("supporthome");
            }
        } else {
            flist = feedbackDAO.searchFeedbacks(page, numberperpage, 0, null, fromDate, toDate, typeid, checkPublic, search, sortdate, null);
            countSearch = feedbackDAO.countAfterSearch(page, null, fromDate, toDate, typeid, checkPublic, search, null);
            total_page = (countSearch % numberperpage == 0) ? (countSearch / numberperpage) : (countSearch / numberperpage + 1);
            request.setAttribute("totalpage", total_page);
            request.setAttribute("get", gets);
            request.setAttribute("fbList", flist);
            request.getRequestDispatcher("./supporter/fbdisplayforsupport.jsp").forward(request, response);
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
//        String gets = request.getParameter("get");
//        String search = request.getParameter("search");
//        String fromdate_raw = request.getParameter("fromdate");
//        String todate_raw = request.getParameter("todate");
//        String type = request.getParameter("fbtype");
//        String publicType = request.getParameter("publics");
//        String privateType = request.getParameter("privates");
//        String sortdate = request.getParameter("sortdate");
//
//        LinkedHashMap<String, String> searchMap = new LinkedHashMap<>() {
//            {
//                put("get", gets);
//                put("search", search);
//                put("fromdate", fromdate_raw);
//                put("todate", todate_raw);
//                put("fbtype", type);
//                put("publics", publicType);
//                put("privates", privateType);
//                put("sortdate", sortdate);
//            }
//        };
//
//        // create uri to send back to doget of this servlet
//        String url = "fbdisplayforsupport?";
//        for (String key : searchMap.keySet()) {
//            if (searchMap.get(key) != null && !searchMap.get(key).equalsIgnoreCase("")) {
//                url += key + "=" + searchMap.get(key) + "&";
//            }
//        }
//
//        response.sendRedirect(url);
//        FeedbackDAO feedbackDAO = new FeedbackDAO();
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
//        if (search == null || search.trim().equals("")) {
//            search = null;
//        }
//
//        try {
//            Date fromDate = (fromdate_raw != null && !fromdate_raw.equals("")) ? Date.valueOf(fromdate_raw) : null;
//            Date toDate = (todate_raw != null && !todate_raw.equals("")) ? Date.valueOf(todate_raw) : null;
//            List<Feedback> list;
//            if (status != null && !status.equals("")) {
//                list = feedbackDAO.searchFeedbacks(-1, status, fromDate, toDate, type, checkPublic, search, false);
//            } else {
//                list = feedbackDAO.searchFeedbacks(-1, null, fromDate, toDate, type, checkPublic, search, false);
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
//            AccountDAO accountDAO = new AccountDAO();
//            List<Account> aList = accountDAO.getAllAccounts();
//            request.setAttribute("aList", aList);
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
//            request.getRequestDispatcher("./supporter/fbdisplayforsupport.jsp").forward(request, response);
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
