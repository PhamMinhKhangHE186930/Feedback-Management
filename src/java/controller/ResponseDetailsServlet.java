/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.*;
import dal.ResponseContentDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.*;

/**
 *
 * @author ADMIN
 */
public class ResponseDetailsServlet extends HttpServlet {

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
            out.println("<title>Servlet ResponseDetailsServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ResponseDetailsServlet at " + request.getContextPath() + "</h1>");
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
        String responseid_raw = request.getParameter("rid");

        FeedbackDAO fbDAO = new FeedbackDAO();
        AccountDAO accDAO = new AccountDAO();
        ResponseContentDAO resDAO = new ResponseContentDAO();

        int responseid;
        try {
            responseid = Integer.parseInt(responseid_raw);
            ResponseContent responseContent = resDAO.getResponsByID(responseid);
            if (responseContent == null) {
                throw new Exception();
            } else {
                Feedback fb = fbDAO.getFeedbackByfbID(responseContent.getFeedbackid());
                String name = accDAO.getDislpayName(fb.getAccountID());
                String filename = fbDAO.getFileNameByFBid(responseContent.getFeedbackid());
                String supporter = accDAO.getDislpayName(responseContent.getAccountid());
                
                request.setAttribute("filename", filename);
                request.setAttribute("stdid", fb.getAccountID());
                request.setAttribute("name", name);
                request.setAttribute("supportname", supporter);
                request.setAttribute("feedback", fb);
                request.setAttribute("responseContent", responseContent);
                request.getRequestDispatcher("./supporter/responsedetails.jsp").forward(request, response);
            }
        } catch (NumberFormatException e) {
            request.setAttribute("format_error", "Feedback id is invalid");
            request.getRequestDispatcher("./supporter/responsedetails.jsp").forward(request, response);
        } catch (Exception ex) {
            request.setAttribute("format_error", "There are no match feedback");
            request.getRequestDispatcher("./supporter/responsedetails.jsp").forward(request, response);
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
        String content = request.getParameter("editcontent");
        String id = request.getParameter("id");
        String fid = request.getParameter("fid");
        String action = request.getParameter("action");

        ResponseContentDAO rpDAO = new ResponseContentDAO();

        if (action != null) {
            switch (action) {
                case "update":
                    if (rpDAO.updateResponse(Integer.parseInt(id), content)) {
                        request.setAttribute("message", "Update successfully");
                    } else {
                        request.setAttribute("message", "Update error");
                    }
                    response.sendRedirect("/StudentFeedback/responsedetails?rid=" + id);
                    break;
                case "delete":
                    if (rpDAO.deleteResponse(Integer.parseInt(id), Integer.parseInt(fid))) {
                        request.setAttribute("message", "Update successfully");
                    } else {
                        request.setAttribute("message", "Update error");
                    }
                    response.sendRedirect("/StudentFeedback/createresponse?fbid=" + fid);
                    break;
                default:
                    break;
            }
        }

        response.sendRedirect("/StudentFeedback/responsedetails?rid=" + id);
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
