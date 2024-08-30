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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import model.*;

/**
 *
 * @author ADMIN
 */
public class AllResponseToFBServlet extends HttpServlet {

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
            out.println("<title>Servlet AllResponseToFBServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AllResponseToFBServlet at " + request.getContextPath() + "</h1>");
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
        String fbid_raw = request.getParameter("fbid");

        FeedbackDAO fbDAO = new FeedbackDAO();
        AccountDAO accDAO = new AccountDAO();
        ResponseContentDAO resDAO = new ResponseContentDAO();
        int fbid;
        try {
            fbid = Integer.parseInt(fbid_raw);

            Feedback fb = fbDAO.getFeedbackByfbID(fbid);
            
            String name = accDAO.getDislpayName(fb.getAccountID());
            String filename = fbDAO.getFileNameByFBid(fbid);
            List<ResponseContent> rList = resDAO.getResponsByFeedbackID(fbid);
            
            Collections.sort(rList, new Comparator<ResponseContent>() {
                @Override
                public int compare(ResponseContent o1, ResponseContent o2) {
                    return -o1.getResponseDate().compareTo(o2.getResponseDate());
                }
            });
            List<Account> aList = accDAO.getAllAccounts();
            
            request.setAttribute("filename", filename);
            request.setAttribute("stdid", fb.getAccountID());
            request.setAttribute("aList", aList);
            request.setAttribute("rList", rList);
            request.setAttribute("name", name);
            request.setAttribute("feedback", fb);
            request.getRequestDispatcher("./supporter/allresponsetofeedback.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            request.setAttribute("format_error", "Feedback id is invalid");
            request.getRequestDispatcher("./supporter/allresponsetofeedback.jsp").forward(request, response);
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
