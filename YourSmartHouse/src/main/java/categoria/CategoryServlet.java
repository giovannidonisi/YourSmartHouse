package categoria;

import http.*;
import org.json.JSONArray;
import org.json.JSONObject;
import prodotto.Prodotto;
import prodotto.ProdottoDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(name = "CategoryServlet", value = "/categories/*")
public class CategoryServlet extends Controller {

    @Override
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
                case "/api":
                    if(isAJAX(req)) {
                        CategoriaDAO cd = new CategoriaDAO();
                        ArrayList<Categoria> categorie = cd.fetchCategories();
                        JSONObject obj = new JSONObject();
                        JSONArray arr = new JSONArray();
                        obj.put("categories", arr);
                        categorie.forEach(c -> arr.put(c.toJSON()));
                        sendJSON(resp, obj);
                        break;
                    }
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

    private void show(HttpServletRequest req, HttpServletResponse resp) throws InvalidRequestException {
        try {
            String id = req.getParameter("id");
            if(!isNumber(id))
                throw new InvalidRequestException(400);
            int cId = Integer.parseInt(id);

            CategoriaDAO dao = new CategoriaDAO();
            Categoria c = dao.fetchCategory(cId);
            if(cId > dao.countAll())
                throw new InvalidRequestException(400);

            int page = 1;
            String p = req.getParameter("page");
            if (isNumber(p))
                page = Integer.parseInt(req.getParameter("page"));

            Paginator pagi = new Paginator(page, 12);
            ProdottoDAO pdao = new ProdottoDAO();
            ArrayList<Prodotto> prodotti = pdao.fetchProductsByCategory(cId, pagi);
            int pages = pagi.getPages(pdao.countAllByCategory(cId));

            req.getSession(true).setAttribute("cat", c);
            req.getSession(true).setAttribute("prodotti", prodotti);
            req.getSession(true).setAttribute("pages", pages);
            req.getRequestDispatcher(view("utente/categoria")).forward(req, resp);
        } catch (ServletException | IOException | SQLException e) {
            throw new InvalidRequestException(500);
        }
    }

    private void showAll(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, InvalidRequestException {
        try {
            int page = 1;
            String p = req.getParameter("page");
            if (p != null && !p.equals(""))
                page = Integer.parseInt(req.getParameter("page"));
            Paginator paginator = new Paginator(page, 10);

            CategoriaDAO dao = new CategoriaDAO();
            ArrayList<Categoria> categorie = dao.fetchCategories(paginator);
            int pages = paginator.getPages(dao.countAll());

            req.getSession(true).setAttribute("pages", pages);
            req.getSession(true).setAttribute("categorie", categorie);
            req.getRequestDispatcher(view("utente/categorie")).forward(req, resp);
        } catch (SQLException e) {
            throw new InvalidRequestException(500);
        }
    }

}
