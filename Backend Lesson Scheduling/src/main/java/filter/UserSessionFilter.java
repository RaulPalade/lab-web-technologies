package filter;

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
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With, remember-me");

        String action = request.getParameter("action");
        HttpSession session = request.getSession(false);

        if (action != null) {
            System.out.println(action);
            if (session == null && !action.equals("login") && !action.equals("logout")) {
                response.sendError(401, "Unauthorized");
            } else {
                chain.doFilter(request, response);
            }
        } else {
            System.out.println("NULL ACTION");
            response.sendError(400, "Bad Request");
        }
    }
}