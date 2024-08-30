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
import dal.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpSession;
import model.*;

/**
 *
 * @author ADMIN
 */
public class LoginServlet extends HttpServlet {

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
            out.println("<title>Servlet LoginServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LoginServlet at " + request.getContextPath() + "</h1>");
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
        request.getRequestDispatcher("login.jsp").forward(request, response);
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
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        String remember = request.getParameter("remember");
        
        HttpSession session = request.getSession();
        // check if user input both user name and password
        if (name != null && !name.trim().equals("") && password != null && !password.trim().equals("")) {
            AccountDAO accDAO = new AccountDAO();
            Account account = accDAO.getUser(name, password);
            if(account!=null){
                Cookie cname = new Cookie("cname", name);
                Cookie cpass = new Cookie("cpassword", password);
                Cookie cremember = new Cookie("cremember", remember);
                if(remember!=null){
                    cname.setMaxAge(60*60*24*2);
                    cpass.setMaxAge(60*60*24*2);
                    cremember.setMaxAge(60*60*24*2);
                }else{
                    cname.setMaxAge(0);
                    cpass.setMaxAge(0);
                    cremember.setMaxAge(0);
                }
                //store cookies at browser
                response.addCookie(cname);
                response.addCookie(cpass);
                response.addCookie(cremember);
                
                //create session
                session.setAttribute("account", account);
                if(account.getRole() == 1){
                    response.sendRedirect("supporthome");
                }else{
                    response.sendRedirect("studenthome");
                }
            }else{
                request.setAttribute("message", "User name or password is wrong, please try again.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("message", "Please enter username and password");
            request.getRequestDispatcher("login.jsp").forward(request, response);
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
