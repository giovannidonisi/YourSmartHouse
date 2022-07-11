package prodotto;

import http.*;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(name = "ProductServlet", value = "/products/*")
public class ProductServlet extends Controller {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.setCharacterEncoding("UTF-8");
            String path = getPath(req);
            switch (path) {
                case "/":
                    showAll(req, resp);
                    break;
                case "/showAll":
                    showAll(req, resp);
                    break;
                case "/show":
                    show(req, resp);
                    break;
                case "/search":
                    search(req, resp);
                    break;
                case "/featured/api":
                    ProdottoDAO pdao = new ProdottoDAO();
                    ArrayList<Prodotto> prods = pdao.fetchRandomProducts(12);
                    JSONObject object = new JSONObject();
                    JSONArray array = new JSONArray();
                    object.put("prods", array);
                    prods.forEach(p -> array.put(p.toJSON()));
                    sendJSON(resp, object);
                    break;
                case "/search/api":
                    if(isAJAX(req)) {
                        ProdottoDAO dao = new ProdottoDAO();
                        Paginator paginator = new Paginator(1, 5);
                        ArrayList<Prodotto> prodotti = dao.fetchProducts(req.getParameter("query"), paginator);
                        JSONObject obj = new JSONObject();
                        JSONArray arr = new JSONArray();
                        obj.put("products", arr);
                        prodotti.forEach(p -> arr.put(p.toJSON()));
                        sendJSON(resp, obj);
                        break;
                    }
                    else
                        resp.sendError(404);
                default:
                    resp.sendError(404);
                    break;
            }
        } catch (InvalidRequestException e) {
            e.handle(req, resp);
        } catch (SQLException s) {
            resp.sendError(500);
        }
    }

    private void showAll(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, InvalidRequestException {
        try {
            int page = 1;
            int pages;
            ProdottoDAO prodottoDAO = new ProdottoDAO();
            ArrayList<Prodotto> prodotti;
            String pag = req.getParameter("page");
            if (isNumber(pag))
                page = Integer.parseInt(req.getParameter("page"));

            Paginator paginator = new Paginator(page, 24);
            prodotti = prodottoDAO.fetchProducts(paginator);
            pages = paginator.getPages(prodottoDAO.countAll());

            req.getSession(true).setAttribute("pages", pages);
            req.getSession(true).setAttribute("prodotti", prodotti);
            req.getRequestDispatcher(view("utente/prodotti")).forward(req, resp);
        } catch (SQLException e) {
            throw new InvalidRequestException(500);
        }
    }

    private void show(HttpServletRequest req, HttpServletResponse resp) throws InvalidRequestException {
        try {
            String id = req.getParameter("id");
            if(!isNumber(id))
                showAll(req, resp);

            ProdottoDAO dao = new ProdottoDAO();
            Prodotto p = dao.fetchProduct(Integer.parseInt(id));
            req.getSession(true).setAttribute("prod", p);
            req.getRequestDispatcher(view("utente/prodotto")).forward(req, resp);
        } catch (ServletException | IOException | SQLException e) {
            throw new InvalidRequestException(500);
        }
    }

    private void search(HttpServletRequest req, HttpServletResponse resp) throws InvalidRequestException {
        try {
            int page = 1;
            String p = req.getParameter("page");
            if (isNumber(p))
                page = Integer.parseInt(req.getParameter("page"));

            String query = req.getParameter("query");
            if (query.isBlank())
                showAll(req, resp);

            Paginator pagi = new Paginator(page, 12);
            ProdottoDAO pdao = new ProdottoDAO();
            ArrayList<Prodotto> prods = pdao.fetchProducts(query, pagi);
            int pages = pagi.getPages(pdao.countAll(query));

            req.getSession(true).setAttribute("pages", pages);
            req.getSession(true).setAttribute("query", query);
            req.getSession(true).setAttribute("prodotti", prods);
            req.getRequestDispatcher(view("utente/search")).forward(req, resp);
        } catch (ServletException | IOException | SQLException e) {
            throw new InvalidRequestException(500);
        }
    }

}
