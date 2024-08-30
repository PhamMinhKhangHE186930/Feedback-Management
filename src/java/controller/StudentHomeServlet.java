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
import model.*;
import dal.*;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author ADMIN
 */
@MultipartConfig
public class StudentHomeServlet extends HttpServlet {

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
        HttpSession session = request.getSession();
        if (session.getAttribute("account") != null) {
            Account account = (Account) session.getAttribute("account");
            List<ResponseContent> responseList = account.getResponseList();
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
        FeedbackTypeDAO feedbackTypeDAO = new FeedbackTypeDAO();
        List<FeedbackType> list = feedbackTypeDAO.getAllTypes();
        
        request.setAttribute("typelist", list);
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

        HttpSession session = request.getSession(false);
        Account account = (Account) session.getAttribute("account");

        FeedbackDAO fDAO = new FeedbackDAO();
        LocalDateTime time = LocalDateTime.now();
        FeedbackTypeDAO feedbackTypeDAO = new FeedbackTypeDAO();
        List<FeedbackType> list = feedbackTypeDAO.getAllTypes();
        
        request.setAttribute("typelist", list);

        Part part = request.getPart("files");
        // get file name was submitted
        String fileName = part.getSubmittedFileName();
        String folderPath = "D:/Chuyen_nganh_SE_ki4_su24/PRJ301/Project/StudentFeedback/web/studentsubmitfile/" + account.getAccountName();
        String realPathFile = "D:/Chuyen_nganh_SE_ki4_su24/PRJ301/Project/StudentFeedback/web/studentsubmitfile/" + account.getAccountName() + File.separator + fileName;

        if (part != null && part.getSize() != 0) {
            //check if student directory is exist or not, if not then create one enquivalen to account name
            if (!Files.exists(Paths.get(folderPath))) {
                Files.createDirectory(Paths.get(folderPath));
            }
            // another way
//            File folder = new File(folderPath);
//            if(folder.isDirectory());
            File file = new File(realPathFile);
            if (file.exists()) {
                while (true) {
                    fileName = renameFile(fileName);
                    realPathFile = folderPath + File.separator + fileName;
                    File temp = new File(realPathFile);
                    if (!temp.exists()) {
                        break;
                    }
                }
            }

        } else {
            fileName = null;
        }

        if (fbtittle != null && fbtype != null && fbcontent != null && fbpublictype != null && !fbtittle.trim().equals("") && !fbcontent.trim().equals("")) {
            boolean isPublic = (fbpublictype.equalsIgnoreCase("public"));
            int fbtypeid = Integer.parseInt(fbtype);
            if (fDAO.addFeedback(fbtypeid, fbtittle, fbcontent, false, Timestamp.valueOf(time), isPublic, account.getId(), fileName)) {
                if (fileName != null) {
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

                request.setAttribute("createsuccess", "Sending feedback successfully.");
                request.getRequestDispatcher("studenthome.jsp").forward(request, response);
            } else {
                request.setAttribute("createfalse", "Sending feedback error.");
                request.getRequestDispatcher("studenthome.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("createfalse", "You need to input all section");
            request.getRequestDispatcher("studenthome.jsp").forward(request, response);
        }
    }

    public String renameFile(String fileName) {
        String newName = "";
        String[] namearr = fileName.split("\\.");
        if (namearr[0].matches(".*\\(\\d+\\)$")) {
            try {
                int i = Integer.parseInt(namearr[0].replaceAll("[^0-9]", ""));
                i++;
                String name = namearr[0].replaceAll("\\(\\d+\\)$", "");
                newName = name + "(" + i + ")." + namearr[1];
            } catch (NumberFormatException e) {
                System.out.println("Cannot rename");
            }
        } else {
            newName = namearr[0] + "(1)." + namearr[1];
        }
        return newName;
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
