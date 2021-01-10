package filter;

import datamodel.DAO;
import datamodel.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Deve essere possibile vedere i corsi, docenti e corsi-docenti da non loggato
 * Deve essere possibile vedere tutti gli studenti e tutte le prenotazioni solo da amministratore
 * Tutto il resto deve essere loggato
 */
@WebFilter(filterName = "UserSessionFilter", urlPatterns = "/*")
public class UserSessionFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        System.out.println("GENERAL FILTER");

        ((HttpServletResponse) response).addHeader("Access-Control-Allow-Origin", "*");
        ((HttpServletResponse) response).addHeader("Access-Control-Allow-Methods", "GET, OPTIONS, HEAD, PUT, POST");
        ((HttpServletResponse) response).addHeader("Access-Control-Allow-Headers", "Content-Type");
        ((HttpServletResponse) response).addHeader("Access-Control-Max-Age", "86400");

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String action = request.getParameter("action");

        HttpSession session = req.getSession(false);

        if (action != null) {
            System.out.println(action);
            if (session == null && (!action.equals("login") && !action.equals("logout")
                    && !action.equals("list-teachers")
                    && !action.equals("list-courses")
                    && !action.equals("list-teacher-courses"))) {
                res.sendError(401, "Unauthorized access request");
            }

            if (session != null && (action.equals("insert-user") ||
                    action.equals("activate-user") ||
                    action.equals("deactivate-user") ||
                    action.equals("insert-teacher") ||
                    action.equals("activate-teacher") ||
                    action.equals("deactivate-teacher") ||
                    action.equals("insert-course") ||
                    action.equals("activate-course") ||
                    action.equals("deactivate-course") ||
                    action.equals("insert-time-slot") ||
                    action.equals("activate-time-slot") ||
                    action.equals("deactivate-time-slot") ||
                    action.equals("assign-teaching") ||
                    action.equals("activate-teaching") ||
                    action.equals("deactivate-teaching") ||
                    action.equals("list-users") ||
                    action.equals("list-bookings") ||
                    action.equals("list-time-slots")) &&
                    !DAO.isAdmin(new User(req.getSession().getAttribute("emailUser").toString()))) {
                res.sendError(403, "Forbidden");
                System.out.println(DAO.isAdmin(new User(req.getSession().getAttribute("emailUser").toString())));
                System.out.println("ERRORE ADMIN");
            } else {
                chain.doFilter(request, response);
            }
        } else {
            System.out.println("NULL ACTION");
            res.sendError(400, "Bad Request");
        }
    }
}