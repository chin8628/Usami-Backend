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
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.ArtTag;
import model.User;

/**
 *
 * @author bellkung
 */
@WebServlet(name = "FollowTag", urlPatterns = {"/FollowTag/"})
public class FollowTag extends HttpServlet {

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
            User user = (User) session.getAttribute("user");
            
            int tag_id = Integer.parseInt(request.getParameter("tag"));
            
            // Follow Tag
            PreparedStatement pstmt;
            ResultSet rs;
            try {
                
                ArtTag tag = new ArtTag(conn, tag_id);
                
                pstmt = conn.prepareStatement("SELECT * FROM usami.Profile_focus p "
                        + "JOIN usami.Tag t USING (tag_id) WHERE t.tag_id = ? AND p.user_id = ?");
                pstmt.setInt(1, tag_id);
                pstmt.setString(2, user.getUsername());
                
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    tag.unfollowTag(user.getUsername());
                } else {
                    tag.followTag(user.getUsername());
                }
                
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            
            response.sendRedirect("/Usami/Gallery/?tag="+tag_id);
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
