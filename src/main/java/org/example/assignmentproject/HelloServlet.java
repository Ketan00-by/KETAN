package org.example.assignmentproject;

import jakarta.servlet.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.*;

@WebServlet("/Login")
public class HelloServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException,ServletException {
        response.setContentType("text/html");
        String Username=request.getParameter("Username");
        String Password=request.getParameter("Password");
        PrintWriter pw= response.getWriter();
        if(Username.equals("Admin")&& Password.equals("Admin")){
            pw.write("<h1>Welcome Admin</h1>");
        } else{
            try {
                String url = "jdbc:mysql://localhost:3306/User1";
                String username = "root";
                String password = "rootroot";

                try (Connection conn = DriverManager.getConnection(url, username, password)) {
                    String sql = "SELECT Username, Password FROM User WHERE Username = ?";
                    PreparedStatement statement = conn.prepareStatement(sql);
                    statement.setString(1, Username);
                    ResultSet resultSet = statement.executeQuery();

                    if (resultSet.next()) {
                        String storedPassword = resultSet.getString("Password");
                        if (Password.equals(storedPassword)) {
                            pw.write("/Welcome.html");

                        }else{
                            RequestDispatcher rd = request.getRequestDispatcher("/Error.html");
                            rd.forward(request,response);
                        }
                    } else {
                        RequestDispatcher rd = request.getRequestDispatcher("/Error.html");
                        rd.forward(request,response);
                    }
                }
            } catch (Exception e) {
                pw.write("<h1>Error: " + e.getMessage() + "</h1>");
            }
        }

    }
}
