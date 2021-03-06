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
import java.util.ArrayList;
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
import model.User;
import model.CommentModel;
import model.PremiumProduct;

/**
 *
 * @author frostnoxia
 */
@WebServlet(name = "Market", urlPatterns = {"/Market"})
public class Market extends HttpServlet {

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
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            
            ServletContext ctx = getServletContext();
            Connection conn = (Connection) ctx.getAttribute("connection");
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");
            
            PreparedStatement pstmt =  conn.prepareStatement(""
                    + "SELECT * "
                    + "FROM usami.Image "
                    + "JOIN usami.Product "
                    + "USING (image_id) "
                    + "WHERE status = 1 "
                    + "AND price > 0 "
                    + "ORDER BY upload_date DESC;");
            
            ResultSet rs = pstmt.executeQuery();
            
            ArrayList<Art> allArt = new ArrayList<Art>();
            
            while (rs.next()){
                Art art = new Art(conn, rs.getString("image_id"));
                allArt.add(art);
                
            }
            
            
            
            pstmt = conn.prepareStatement("SELECT * FROM Product WHERE pgroup_id like 'prm'");
            
            rs = pstmt.executeQuery();
            
            ArrayList<PremiumProduct> allPrm = new ArrayList<PremiumProduct>();
            
            while(rs.next()) {
                
                PremiumProduct temp = new PremiumProduct();
                String text = rs.getString("product_id");
                temp.setProduct_id(text);
                temp.setPrice(rs.getInt("price"));
                temp.setDuration(Integer.parseInt(text.substring(3)));
                temp.setName("Premium Account - " + text.substring(3) + " Days");
                
                allPrm.add(temp);
            }
            
            
            

            request.setAttribute("allArt", allArt);
            request.setAttribute("allPrm", allPrm);
            request.setAttribute("user", user);
            RequestDispatcher obj = request.getRequestDispatcher("market.jsp");
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
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(Market.class.getName()).log(Level.SEVERE, null, ex);
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
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(Market.class.getName()).log(Level.SEVERE, null, ex);
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
