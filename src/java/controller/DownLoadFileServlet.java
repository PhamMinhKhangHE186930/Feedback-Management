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
import model.*;
import dal.*;
import jakarta.servlet.annotation.WebServlet;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

/**
 *
 * @author ADMIN
 */
public class DownLoadFileServlet extends HttpServlet {

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
            out.println("<title>Servlet DownLoadFileServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet DownLoadFileServlet at " + request.getContextPath() + "</h1>");
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
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");
        if (account.getRole() == 1) {
            response.sendRedirect("supporthome");
        } else {
            response.sendRedirect("studenthome");
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
// NOTE: DO NOT CREATE PRINTWRITER IN THIS BECAUSE IT CAN CAUSE ERROR WHEN DOWNLOAD FILE
        String name = request.getParameter("fname");
        String stdid_raw = request.getParameter("stdid");

        AccountDAO accDAO = new AccountDAO();
        FeedbackDAO fbDAO = new FeedbackDAO();
        int stdid = 0;
        try {
            stdid = Integer.parseInt(stdid_raw);
        } catch (NumberFormatException e) {
        }

//        String path = getServletContext().getRealPath("/studentsubmitfile/" + accDAO.getAccountName(stdid) + File.separator + name);
        String path = "D:/Chuyen_nganh_SE_ki4_su24/PRJ301/Project/StudentFeedback/web/studentsubmitfile/" + accDAO.getAccountName(stdid) + File.separator + name;
        
        File file = new File(path);
//        boolean checkExist = fbDAO.checkFileExistWithUser(name, stdid);
        if (file.exists()) {
            response.setContentType("application/octet-stream"); // Set the appropriate content type
            response.setHeader("Content-Disposition", "attachment; filename=\"" + name + "\"");

            // FileInputStream: read data of file, OutputStream: write data to request
            try (FileInputStream in = new FileInputStream(file); OutputStream out = response.getOutputStream()) {
                byte[] buffer = new byte[2048];
                int bytesRead;
                while ((bytesRead = in.read(buffer)) > 0) {
                    out.write(buffer, 0, bytesRead);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND); // File not found
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
