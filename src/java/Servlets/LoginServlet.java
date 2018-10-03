/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import utilities.User;
import utilities.UserService;
import utilities.CookieUtil;

/**
 *
 * @author 754632
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet
{

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
        HttpSession ses = request.getSession(false);
        
         Cookie[] cookies = request.getCookies();
        if(cookies != null)
        {
            String username = CookieUtil.getCookieValue(cookies, "usernameCookie");
            if(username != null || !username.equals(""))
            {
                request.setAttribute("uname", username);
                
            }
        }
        
        if(request.getParameter("Logout") != null)
        {
            request.setAttribute("message", "you have logged out.");
            ses.invalidate();
        }
        
        if(request.getSession().getAttribute("userName") != null )
        {
            response.sendRedirect("home");
        }
        else
        {
            getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
        }
        
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
        
        String username = request.getParameter("name");
        String password = request.getParameter("password");
        UserService us = new UserService();
        
         User u = new User();
         u = us.login(username, password);
        
        if(u == null)
        {
            request.setAttribute("message", "Please enter a valid username/password");
            getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
        }
        else
        {
            if(request.getParameter("remember") != null)
            {
                 Cookie c = new Cookie("usernameCookie", username);
                 c.setMaxAge(60 * 30); 
                 c.setPath("/");                   
                 response.addCookie(c);
            }
            else
            {
                Cookie[] cookies = request.getCookies();
                for (Cookie cookie : cookies) 
                {
                    cookie.setMaxAge(0); //delete the cookie
                    cookie.setPath("/"); //allow the download application to access it
                    response.addCookie(cookie);
                }
            }
            HttpSession ses = request.getSession();
            ses.setAttribute("userName", u.getUsername());
            response.sendRedirect("home");
        }
    
        
    }
    
}
