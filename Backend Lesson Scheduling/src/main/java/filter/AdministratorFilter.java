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
 * @author Raul Palade
 * @project Backend Lesson Scheduling
 */
@WebFilter(filterName = "AdministratorFilter", urlPatterns = {"/*"})
public class AdministratorFilter implements Filter {
    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String action = request.getParameter("action");

        if (action != null && req.getSession() != null) {
            if ((action.equals("insert-user") ||
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
                    action.equals("list-bookings")) ||
                    action.equals("list-time-slots") &&
                            !DAO.isAdmin(new User(req.getSession().getAttribute("emailUser").toString()))) {
                res.sendError(403, "Forbidden");
                System.out.println("ERRORE ADMIN");
            } else {
                chain.doFilter(request, response);
            }
        }
    }

    @Override
    public void init(FilterConfig config) throws ServletException {

    }

}