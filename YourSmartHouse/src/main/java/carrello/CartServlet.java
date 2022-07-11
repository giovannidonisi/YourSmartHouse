package carrello;

import http.Controller;
import http.InvalidRequestException;
import prodotto.Prodotto;
import prodotto.ProdottoDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "CartServlet", value = "/cart/*")
public class CartServlet extends Controller {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = getPath(req);
        switch (path) {
            case "/":
            case "/show":
                Carrello c = getSessionCart(req.getSession(false));
                if(c == null) {
                    c = new Carrello();
                    req.getSession(false).setAttribute("utenteCarrello", c);
                }
                req.getRequestDispatcher(view("utente/carrello")).forward(req, resp);
                break;
            default:
                resp.sendError(404);
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String path = getPath(req);
            switch (path) {
                case "/add":
                    add(req, resp);
                    break;
                case "/remove":
                    remove(req, resp);
                    break;
                case "/empty":
                    Carrello c = getSessionCart(req.getSession(false));
                    if(c != null)
                        c.reset();
                    resp.sendRedirect("/YourSmartHouse/cart/show");
                    break;
                default:
                    resp.sendError(405);
                    break;
            }
        } catch (InvalidRequestException e) {
            e.handle(req, resp);
        }
    }

    private void add(HttpServletRequest req, HttpServletResponse resp) throws InvalidRequestException {
        try {
            String id = req.getParameter("id");
            String quant = req.getParameter("quantita");
            if (!isNumber(id) || !isNumber(quant))
                throw new InvalidRequestException(500);

            ProdottoDAO dao = new ProdottoDAO();
            Prodotto p = dao.fetchProduct(Integer.parseInt(id));
            if(p != null) {
                int quantita = Integer.parseInt(quant);
                if(getSessionCart(req.getSession(false)) == null)
                    req.getSession(false).setAttribute("utenteCarrello", new Carrello());

                getSessionCart(req.getSession(false)).addProduct(p, quantita);
                resp.sendRedirect("/YourSmartHouse/products/show?id="+id);
            } else
                throw new InvalidRequestException(404);
        } catch (SQLException | IOException throwables) {
            throwables.printStackTrace();
            throw new InvalidRequestException(500);
        }
    }

    private void remove(HttpServletRequest req, HttpServletResponse resp) throws InvalidRequestException {
        try {
            String id = req.getParameter("id");
            if (!isNumber(id))
                throw new InvalidRequestException(500);

            if(getSessionCart(req.getSession(false)).removeProduct(Integer.parseInt(id)))
                resp.sendRedirect("/YourSmartHouse/cart/show");
            else
                throw new InvalidRequestException(404);
        } catch (IOException e) {
            throw new InvalidRequestException(500);
        }
    }

}
