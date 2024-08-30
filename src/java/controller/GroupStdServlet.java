/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.AccountDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import model.Account;

/**
 *
 * @author ADMIN
 */
public class GroupStdServlet extends HttpServlet {

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
            out.println("<title>Servlet GroupStdServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet GroupStdServlet at " + request.getContextPath() + "</h1>");
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
        String search = request.getParameter("search");
        String sortdate = request.getParameter("sortdate");
        String page_raw = request.getParameter("page");
        AccountDAO aDAO = new AccountDAO();

        LinkedHashMap<String, String> searchMap = new LinkedHashMap<>() {
            {
                put("sortdate", sortdate);
            }
        };

        searchMap.put("search", search);
        String url = "fbdisplayforsupport?";
        for (String key : searchMap.keySet()) {
            if (searchMap.get(key) != null && !searchMap.get(key).trim().equalsIgnoreCase("")) {
                url += key + "=" + searchMap.get(key) + "&";
            }
        }
        request.setAttribute("url", url);

        // check if page is null or < 1 will automatic is 1
        int page;
        int countSearch;
        int total_page;
        if (page_raw == null || page_raw.trim().equalsIgnoreCase("")) {
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
        List<Account> aList = aDAO.getStudentPaging2(page, sortdate, search);
        
        countSearch = aDAO.countStudentPaging(search);
        total_page = (countSearch % 5 == 0) ? (countSearch / 5) : (countSearch / 5 + 1);
        request.setAttribute("totalpage", total_page);
        request.setAttribute("current", page);
        request.setAttribute("sortdate", sortdate);
        request.setAttribute("search", search);
        request.setAttribute("alist", aList);
        request.getRequestDispatcher("./supporter/groupstd.jsp").forward(request, response);
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
