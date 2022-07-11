package http;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class InvalidRequestException extends Exception {

    private final ArrayList<String> errors;
    private final int errorCode;

    public InvalidRequestException(ArrayList<String> errors, int errorCode) {
        super();
        this.errors = errors;
        this.errorCode = errorCode;
    }

    public InvalidRequestException(int errorCode) {
        super();
        this.errors = new ArrayList<>();
        this.errorCode = errorCode;
    }

    public void handle(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        if (errorCode == 400 && !errors.isEmpty()) {
            req.setAttribute("alert", new Alert(errors, "danger"));
            String backPath = (String) req.getAttribute("back");
            req.getRequestDispatcher(backPath).forward(req, resp);
        }
        else
            resp.sendError(errorCode);
    }

    public ArrayList<String> getErrors() {
        return errors;
    }

    public int getErrorCode() {
        return errorCode;
    }

}
