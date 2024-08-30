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
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import model.*;

/**
 *
 * @author ADMIN
 */
public class CreateResponseServlet extends HttpServlet {

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
            out.println("<title>Servlet CreateResponseServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CreateResponseServlet at " + request.getContextPath() + "</h1>");
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
            String filename = fbDAO.getFileNameByFBid(fbid);
            String name = accDAO.getDislpayName(fb.getAccountID());
            List<ResponseContent> rList = resDAO.getResponsByFeedbackID(fbid);
            List<Account> aList = accDAO.getAllAccounts();

            // set attribute to get file
            request.setAttribute("filename", filename);
            request.setAttribute("stdid", fb.getAccountID());

            request.setAttribute("aList", aList);
            request.setAttribute("rList", rList);
            request.setAttribute("name", name);
            request.setAttribute("feedback", fb);
            request.getRequestDispatcher("./supporter/createresponse.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            request.setAttribute("format_error", "Feedback id is invalid");
            request.getRequestDispatcher("./supporter/createresponse.jsp").forward(request, response);
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
        // get form data submit
        String rtittle = request.getParameter("rtittle");
        String rcontent = request.getParameter("rcontent");
        String fbID_raw = request.getParameter("fbid");
        int fbID = Integer.parseInt(fbID_raw);

        LocalDateTime now = LocalDateTime.now();
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");

        // call dao
        FeedbackDAO fbDAO = new FeedbackDAO();
        AccountDAO accDAO = new AccountDAO();
        ResponseContentDAO resDAO = new ResponseContentDAO();

        // get feedback need to response and name of user send feedback
        Feedback fb = fbDAO.getFeedbackByfbID(fbID);
        String name = accDAO.getDislpayName(fb.getAccountID());
        String filename = fbDAO.getFileNameByFBid(fbID);
        // get list of response was response to the above feedback and name of user response
        List<ResponseContent> rList = resDAO.getResponsByFeedbackID(fbID);
        request.setAttribute("rList", rList);

        List<Account> aList = accDAO.getAllAccounts();
        request.setAttribute("aList", aList);
        request.setAttribute("filename", filename);
        request.setAttribute("stdid", fb.getAccountID());
        request.setAttribute("name", name);
        request.setAttribute("feedback", fb);
        request.setAttribute("aList", aList);
        ResponseContentDAO responseDAO = new ResponseContentDAO();
        if (rtittle != null && rcontent != null && !rtittle.trim().equals("") && !rcontent.trim().equals("")) {
            if (responseDAO.addResponse(rtittle, rcontent, Timestamp.valueOf(now), account.getId(), fbID)) {
                request.setAttribute("createsuccess", "Response feedback successfully.");
                request.getRequestDispatcher("./supporter/createresponse.jsp").forward(request, response);
            } else {
                request.setAttribute("createfalse", "Sending feedback error.");
                request.getRequestDispatcher("./supporter/createresponse.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("createfalse", "You need to input all section");
            request.getRequestDispatcher("./supporter/createresponse.jsp").forward(request, response);
        }

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
