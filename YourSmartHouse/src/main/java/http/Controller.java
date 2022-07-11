package http;

import carrello.Carrello;
import org.json.JSONObject;
import utente.UtenteSession;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public abstract class Controller extends HttpServlet {

    private static final String basePath = "/WEB-INF/views/";

    protected static String getPath(HttpServletRequest req) {
        String path = req.getPathInfo();
        if(path == null)
            path = "/";
        return path;
    }

    protected static String view(String viewPath) { // La path deve essere da views in poi
        return basePath + viewPath + ".jsp";
    }


    protected String getUploadPath() {
        return System.getenv("CATALINA_HOME") + File.separator + "uploads" + File.separator;
    }

    protected void authorize(HttpSession session) throws InvalidRequestException {
        authenticate(session);
        UtenteSession s = (UtenteSession) session.getAttribute("utenteSession");
        if(!s.isAdmin())
            throw new InvalidRequestException(403);
    }

    protected void authenticate(HttpSession session) throws InvalidRequestException {
        if(session==null || session.getAttribute("utenteSession")==null) {
            throw new InvalidRequestException(401);
        }
    }

    protected void validate(RequestValidator validator) throws InvalidRequestException {
        if(validator.hasErrors())
            throw new InvalidRequestException(validator.getErrors(), 400);
    }

    protected boolean isAdmin(HttpSession session) {
        UtenteSession s = (UtenteSession) session.getAttribute("utenteSession");
        return s.isAdmin();
    }

    protected boolean isNumber(String s) {
        return (s != null && !s.isBlank() && s.matches("\\d+"));
    }

    protected boolean isAJAX(HttpServletRequest req) {
        return "XMLHttpRequest".equals(req.getHeader("X-Requested-With"));
    }

    protected void sendJSON(HttpServletResponse resp, JSONObject obj) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter w = resp.getWriter();
        w.println(obj.toString());
        w.flush();
    }

    protected Carrello getSessionCart(HttpSession session) {
        return (Carrello) session.getAttribute("utenteCarrello");
    }

}
