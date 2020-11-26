package servlet;

import datamodel.Booking;
import datamodel.DAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "BookingServlet", urlPatterns = "/BookingServlet")
public class BookingServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("The POST request has been made to /BookingServlet");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("The GET request has been made to /BookingServlet");
        DAO.registerDriver();
        ArrayList<Booking> bookings = DAO.queryBookings();
        for (Booking b : bookings) {
            response.getWriter().println("<li>" + b + "</li>");
        }
    }
}
