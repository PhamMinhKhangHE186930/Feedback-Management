/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.FeedbackDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import model.*;

/**
 *
 * @author ADMIN
 */
public class CreateFeedbackServlet extends HttpServlet {

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
            out.println("<title>Servlet CreateFeedbackServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CreateFeedbackServlet at " + request.getContextPath() + "</h1>");
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
        request.getRequestDispatcher("studenthome.jsp").forward(request, response);
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
        PrintWriter writer = response.getWriter();
        String fbtittle = request.getParameter("fbtittle");
        String fbtype = request.getParameter("fbtype");
        String fbcontent = request.getParameter("fbcontent");
        String fbpublictype = request.getParameter("publictype");

        Part part = request.getPart("studentfile");
        writer.println(part);
        if (part != null) {
            String fileName = part.getSubmittedFileName();
//            String folderPath = getServletContext().getRealPath("/" + account.getAccountName());
            String realPathFile = getServletContext().getRealPath("/" + "studentsubmitfile" + File.separator + fileName);
            writer.println(realPathFile);
//            if (!Files.exists(Paths.get(folderPath))) {
//                Files.createDirectory(Paths.get(folderPath));
//            }
            try (InputStream filecontent = part.getInputStream(); FileOutputStream out = new FileOutputStream(realPathFile)) {
                int read = 0;
                byte[] bytes = new byte[1024];
                while ((read = filecontent.read(bytes)) != -1) {
                    out.write(bytes, 0, read);
                }
                writer.println("File upload: " + fileName + " to directory " + "file direction " + realPathFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        FeedbackDAO fDAO = new FeedbackDAO();
        LocalDateTime time = LocalDateTime.now();

//        if (fbtittle != null && fbtype != null && fbcontent != null && fbpublictype != null && !fbtittle.trim().equals("") && !fbcontent.trim().equals("")) {
//            boolean isPublic = (fbpublictype.equalsIgnoreCase("public"));
//            HttpSession session = request.getSession(false);
//            Account account = (Account) session.getAttribute("account");
//            if (fDAO.addFeedback(fbtype, fbtittle, fbcontent, false, Timestamp.valueOf(time), isPublic, account.getId())) {
//                request.setAttribute("createsuccess", "Sending feedback successfully.");
//                request.getRequestDispatcher("studenthome.jsp").forward(request, response);
//            } else {
//                request.setAttribute("createfalse", "Sending feedback error.");
//                request.getRequestDispatcher("studenthome.jsp").forward(request, response);
//            }
//        } else {
//            request.setAttribute("createfalse", "You need to input all section");
//            request.getRequestDispatcher("studenthome.jsp").forward(request, response);
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
