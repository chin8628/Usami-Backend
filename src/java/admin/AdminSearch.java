/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin;

import Utility.Searcher;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Art;
import model.CommentModel;
import model.User;

/**
 *
 * @author frostnoxia
 */
@WebServlet(name = "AdminSearch", urlPatterns = {"/AdminSearch", "/AdminSearch/"})
public class AdminSearch extends HttpServlet {

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
            
            String key = request.getParameter("key");
            int mode = Integer.parseInt(request.getParameter("mode"));
            
            ServletContext ctx = getServletContext();
            Connection conn = (Connection) ctx.getAttribute("connection");
            
            Searcher searcher = new Searcher(conn);
            
            if(mode == 1) {
                 ArrayList<Art> allArt = searcher.searchArt(key);
                
                request.setAttribute("allArt", allArt);
                request.setAttribute("mode", mode);
                request.setAttribute("key", key);
                RequestDispatcher obj = request.getRequestDispatcher("/administrator/result-search-art.jsp");
                obj.forward(request, response);
                return;
            }
            
            if(mode == 2) {
                ArrayList<User> allUser = searcher.searchUser(key);
                
                for(User user:allUser){
                    out.println(user.getUsername());
                }
                
                
                request.setAttribute("allUser", allUser);
                request.setAttribute("mode", mode);
                request.setAttribute("key", key);
                RequestDispatcher obj = request.getRequestDispatcher("/administrator/result-search-creator.jsp");
                obj.forward(request, response);
                return;
            }
            
            if(mode == 3) {
                 ArrayList<CommentModel> allComm = searcher.searchComment(key);
                
                request.setAttribute("allComm", allComm);
                request.setAttribute("mode", mode);
                request.setAttribute("key", key);
                RequestDispatcher obj = request.getRequestDispatcher("/administrator/result-search-comment.jsp");
                
                
                obj.forward(request, response);
                return;
            }
            
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
