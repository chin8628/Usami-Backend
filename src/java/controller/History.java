/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Art;
import model.CommentModel;
import model.Profiles;

/**
 *
 * @author bellkung
 */
@WebServlet(name = "History", urlPatterns = {"/History"})
public class History extends HttpServlet {

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
            
            ServletContext ctx = getServletContext();
            Connection conn = (Connection) ctx.getAttribute("connection");
            
            HttpSession session = request.getSession();
            Profiles profile = (Profiles) session.getAttribute("profile");
            
            // Show Arts Upload
            ArrayList<Art> artUpload = profile.getAllArt();
            
            PreparedStatement pstmt;
            try {
                //Show Arts Comment
                pstmt = conn.prepareStatement("SELECT * FROM usami.Comment c JOIN usami.Image i USING (image_id) "
                        + "WHERE c.user_id = ? ORDER BY c.comm_date DESC;");
                pstmt.setString(1, profile.getUsername());
                
                ArrayList<CommentModel> myComm = new ArrayList<>();
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    
                    Profiles own_art = new Profiles(conn, rs.getString("i.user_id"));
                    CommentModel comm = new CommentModel();
                    comm.setImage_id(rs.getString("image_id"));
                    comm.setUrl_image(rs.getString("i.image_url"));
                    comm.setText(rs.getString("c.text"));
                    comm.setComm_date(rs.getString("c.comm_date"));
                    comm.setTitle(rs.getString("i.image_name"));
                    comm.setFirst_name(own_art.getFirst_name());
                    comm.setLast_name(own_art.getLast_name()); 
                    comm.setUsername(rs.getString("i.user_id"));
                    
                    myComm.add(comm);
                    
                    System.out.println(comm.getText());
                }
                request.setAttribute("myComm", myComm);
                
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            
            request.setAttribute("artUpload", artUpload);

            RequestDispatcher obj = request.getRequestDispatcher("/history.jsp");
            obj.forward(request, response);
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
        processRequest(request, response);
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
