package servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import datamodel.DAO;
import datamodel.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet(name = "NewServlet", urlPatterns = "/NewServlet")
public class NewServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DAO.registerDriver();
        response.setContentType("text/json");
        ArrayList<User> users = DAO.queryUsers();

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        PrintWriter out = response.getWriter();
        out.println(gson.toJson(users));
        out.close();
    }
}
