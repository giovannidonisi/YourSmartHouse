package utente;

import categoria.Categoria;
import categoria.CategoriaDAO;
import http.*;
import ordine.Ordine;
import ordine.OrdineDAO;
import prodotto.Prodotto;
import prodotto.ProdottoDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

@MultipartConfig
@WebServlet(name = "CRMServlet", value = "/crm/*")
public class CRMServlet extends Controller {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            authorize(req.getSession(false));
            String path = getPath(req);
            switch (path) {
                case "/":
                    req.getRequestDispatcher(view("crm/home")).forward(req, resp);
                    break;
                case "/dashboard":
                    req.getRequestDispatcher(view("crm/home")).forward(req, resp);
                    break;
                case "/products":
                    productShowAll(req, resp);
                    break;
                case "/products/showAll":
                    productShowAll(req, resp);
                    break;
                case "/products/show":
                    productShow(req, resp);
                    break;
                case "/products/update":
                    productUpdateGet(req, resp);
                    break;
                case "/products/create":
                    CategoriaDAO categoriaDAO = new CategoriaDAO();
                    ArrayList<Categoria> categorie = categoriaDAO.fetchCategories();
                    req.getSession(true).setAttribute("categorie", categorie);
                    req.getRequestDispatcher(view("prodotto/crea")).forward(req, resp);
                    break;
                case "/products/search":
                    search(req, resp);
                    break;
                case "/products/delete":
                    String id = req.getParameter("id");
                    if (!isNumber(id))
                        throw new InvalidRequestException(400);
                    ProdottoDAO dao = new ProdottoDAO();
                    Prodotto p = new Prodotto();
                    p.setProductId(Integer.parseInt(id));
                    if (dao.deleteProduct(p))
                        resp.sendRedirect("./showAll");
                    else
                        resp.sendError(500);
                    break;
                case "/categories":
                    categoryShowAll(req, resp);
                    break;
                case "/categories/showAll":
                    categoryShowAll(req, resp);
                    break;
                case "/categories/show":
                    categoryShow(req, resp);
                    break;
                case "/categories/create":
                    req.getRequestDispatcher(view("categoria/crea")).forward(req, resp);
                    break;
                case "/categories/update":
                    categoryUpdateGet(req, resp);
                    break;
                case "/users/showAll":
                    userShowAll(req, resp);
                    break;
                case "/users":
                    userShowAll(req, resp);
                    break;
                case "/users/show":
                    userShow(req, resp);
                    break;
                case "/orders":
                    orderShowAll(req, resp);
                    break;
                case "/orders/show":
                    orderShow(req, resp);
                    break;
                case "/orders/showAll":
                    orderShowAll(req, resp);
                    break;
                case "/orders/user":
                    userOrderShow(req, resp);
                    break;
                default:
                    resp.sendError(404);
                    break;
            }
        }catch (InvalidRequestException e) {
            e.handle(req, resp);
        } catch (SQLException throwables) {
            resp.sendError(500);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            authorize(req.getSession(false));
            req.setCharacterEncoding("UTF-8");
            String path = getPath(req);
            switch (path) {
                case "/products/create":
                    productCreate(req, resp);
                    break;
                case "/products/update":
                    productUpdatePost(req, resp);
                    break;
                case "/categories/create":
                    categoryCreate(req, resp);
                    break;
                case "/categories/update":
                    categoryUpdatePost(req, resp);
                    break;
                default:
                    resp.sendError(404);
            }
        } catch (InvalidRequestException e) {
            e.handle(req, resp);
        }
    }

    private void productShowAll(HttpServletRequest req, HttpServletResponse resp) throws InvalidRequestException {
        try {
            int page = 1;
            int pages;
            ProdottoDAO prodottoDAO = new ProdottoDAO();
            ArrayList<Prodotto> prodotti;
            String p = req.getParameter("page");
            if (isNumber(p))
                page = Integer.parseInt(req.getParameter("page"));

            String catId = req.getParameter("categoryId");
            if (isNumber(catId)) { //Se il parametro "categoryId" è presente e valido, mostro solo i prodotti di quella categoria
                int cId = Integer.parseInt(catId);
                Paginator paginator = new Paginator(page, 10);
                CategoriaDAO cdao = new CategoriaDAO();
                prodotti = cdao.fetchCategoryWithProducts(cId, paginator).getProdotti();
                pages = paginator.getPages(prodottoDAO.countAllByCategory(cId));
                req.getSession(true).setAttribute("catId", catId);
            }
            else { //altrimenti mostro tutti i prodotti
                req.getSession().removeAttribute("catId");
                Paginator paginator = new Paginator(page, 15);
                prodotti = prodottoDAO.fetchProducts(paginator);
                pages = paginator.getPages(prodottoDAO.countAll());
            }
            req.getSession(true).setAttribute("pages", pages);
            req.getSession(true).setAttribute("prodotti", prodotti);
            req.getRequestDispatcher(view("crm/prodotti")).forward(req, resp);
        } catch (SQLException | ServletException | IOException e) {
            throw new InvalidRequestException(500);
        }
    }

    private void productShow(HttpServletRequest req, HttpServletResponse resp) throws InvalidRequestException {
        try {
            String id = req.getParameter("id");
            if(!isNumber(id))
                throw new InvalidRequestException(400);

            ProdottoDAO dao = new ProdottoDAO();
            Prodotto p = dao.fetchProduct(Integer.parseInt(id));
            req.getSession(true).setAttribute("prod", p);
            req.getRequestDispatcher(view("crm/prodotto")).forward(req, resp);
        } catch (ServletException | IOException | SQLException e) {
            throw new InvalidRequestException(500);
        }
    }

    private void productUpdateGet(HttpServletRequest req, HttpServletResponse resp) throws InvalidRequestException {
        try {
            String id = req.getParameter("id");
            if (!isNumber(id))
                throw new InvalidRequestException(400);

            ProdottoDAO dao = new ProdottoDAO();
            Prodotto p = dao.fetchProduct(Integer.parseInt(id));
            req.getSession(true).setAttribute("prod", p);

            CategoriaDAO categoriaDAO = new CategoriaDAO();
            ArrayList<Categoria> categorie = categoriaDAO.fetchCategories();
            req.getSession(true).setAttribute("categorie", categorie);

            req.getRequestDispatcher(view("prodotto/update")).forward(req, resp);
        } catch (SQLException | ServletException | IOException e) {
            throw new InvalidRequestException(500);
        }
    }

    private void productUpdatePost(HttpServletRequest req, HttpServletResponse resp) throws InvalidRequestException {
        try {
            req.setAttribute("back", view("prodotto/update"));
            RequestValidator v = Validators.productValidator(req);
            if(v.hasErrors())
                throw new InvalidRequestException(v.getErrors(), 400);

            String id = req.getParameter("id");
            if(!isNumber(id))
                throw new InvalidRequestException(400);
            ProdottoDAO dao = new ProdottoDAO();
            Prodotto p = new Prodotto();

            p.setNome(req.getParameter("nome"));
            p.setDescrizione(req.getParameter("descrizione"));
            p.setPrezzo(Double.parseDouble(req.getParameter("prezzo")));
            p.setProductId(Integer.parseInt(id));
            p.setCategoryId(Integer.parseInt(req.getParameter("categoria")));
            p.setDisponibilita(Integer.parseInt(req.getParameter("disponibilita")));

            Part filePart = req.getPart("foto");
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            Date d = new Date();
            long l = d.getTime();
            fileName = fileName.substring(0, fileName.length()-4)+l+".jpg";
            p.setFoto(fileName);

            if(dao.updateProduct(p)){
                p.writeFoto(getUploadPath(), filePart);
                resp.sendRedirect("./showAll");
            }
        } catch (ServletException | IOException | SQLException e) {
            throw new InvalidRequestException(500);
        }
    }

    public void productCreate(HttpServletRequest req, HttpServletResponse resp) throws InvalidRequestException {
        try {
            req.setAttribute("back", view("prodotto/crea"));
            RequestValidator v = Validators.productValidator(req);
            if(v.hasErrors())
                throw new InvalidRequestException(v.getErrors(), 400);

            ProdottoDAO dao = new ProdottoDAO();
            Prodotto p = new Prodotto();
            p.setNome(req.getParameter("nome"));
            p.setCategoryId(Integer.parseInt(req.getParameter("categoria")));
            p.setDisponibilita(Integer.parseInt(req.getParameter("disponibilita")));
            p.setPrezzo(Double.parseDouble(req.getParameter("prezzo")));
            p.setDescrizione(req.getParameter("descrizione"));

            Part filePart = req.getPart("foto");
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            Date d = new Date();
            long l = d.getTime();
            fileName = fileName.substring(0, fileName.length()-4)+l+".jpg"; //aggiungo timestamp per rendere unico il nome
            p.setFoto(fileName);

            if(dao.createProduct(p)) {
                p.writeFoto(getUploadPath(), filePart);
                resp.sendRedirect("./showAll");
            }
            else
                throw new InvalidRequestException(500);
        } catch (ServletException | IOException | SQLException e) {
            throw new InvalidRequestException(500);
        }
    }

    private void categoryUpdateGet(HttpServletRequest req, HttpServletResponse resp) throws InvalidRequestException {
        try {
            String id = req.getParameter("id");
            if (!isNumber(id))
                throw new InvalidRequestException(400);
            CategoriaDAO dao = new CategoriaDAO();
            Categoria c = dao.fetchCategory(Integer.parseInt(id));
            req.getSession(true).setAttribute("cat", c);
            req.getRequestDispatcher(view("categoria/update")).forward(req, resp);
        } catch (SQLException | ServletException | IOException e) {
            throw new InvalidRequestException(500);
        }
    }

    private void categoryShow(HttpServletRequest req, HttpServletResponse resp) throws InvalidRequestException {
        try {
            String id = req.getParameter("id");
            if(!isNumber(id))
                throw new InvalidRequestException(400);
            CategoriaDAO dao = new CategoriaDAO();
            Categoria c = dao.fetchCategory(Integer.parseInt(id));
            req.getSession(true).setAttribute("cat", c);
            req.getRequestDispatcher(view("crm/categoria")).forward(req, resp);
        } catch (ServletException | IOException | SQLException e) {
            throw new InvalidRequestException(500);
        }
    }

    private void categoryShowAll(HttpServletRequest req, HttpServletResponse resp) throws InvalidRequestException {
        try {
            int page = 1;
            String p = req.getParameter("page");
            if (isNumber(p))
                page = Integer.parseInt(req.getParameter("page"));
            Paginator paginator = new Paginator(page, 10);

            CategoriaDAO dao = new CategoriaDAO();
            ArrayList<Categoria> categorie = dao.fetchCategories(paginator);
            int pages = paginator.getPages(dao.countAll());

            req.getSession(true).setAttribute("pages", pages);
            req.getSession(true).setAttribute("categorie", categorie);
            req.getRequestDispatcher(view("crm/categorie")).forward(req, resp);
        } catch (SQLException | ServletException | IOException e) {
            throw new InvalidRequestException(500);
        }
    }

    private void categoryUpdatePost(HttpServletRequest req, HttpServletResponse resp) throws InvalidRequestException {
        try {
            req.setAttribute("back", view("categoria/update"));
            RequestValidator v = Validators.categoryValidator(req);
            if(v.hasErrors())
                throw new InvalidRequestException(v.getErrors(), 400);

            String id = req.getParameter("id");
            if(!isNumber(id))
                throw new InvalidRequestException(400);
            CategoriaDAO dao = new CategoriaDAO();
            Categoria c = new Categoria();
            c.setCategoryId(Integer.parseInt(id));
            c.setNome(req.getParameter("nome"));

            Part filePart = req.getPart("foto");
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            Date d = new Date();
            long l = d.getTime();
            fileName = fileName.substring(0, fileName.length()-4)+l+".jpg";
            c.setFoto(fileName);
            if(dao.updateCategory(c)){
                c.writeFoto(getUploadPath(), filePart);
                resp.sendRedirect("./showAll");
            }
        } catch (ServletException | IOException | SQLException e) {
            throw new InvalidRequestException(500);
        }
    }

    private void categoryCreate(HttpServletRequest req, HttpServletResponse resp) throws InvalidRequestException {
        try {
            req.setAttribute("back", view("categoria/crea"));
            RequestValidator v = Validators.categoryValidator(req);
            if(v.hasErrors())
                throw new InvalidRequestException(v.getErrors(), 400);

            CategoriaDAO dao = new CategoriaDAO();
            Categoria c = new Categoria();
            c.setNome(req.getParameter("nome"));

            Part filePart = req.getPart("foto");
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            Date d = new Date();
            long l = d.getTime();
            fileName = fileName.substring(0, fileName.length()-4)+l+".jpg";
            c.setFoto(fileName);

            if(dao.createCategory(c)){
                c.writeFoto(getUploadPath(), filePart);
                resp.sendRedirect("./showAll");
            }
        } catch (ServletException | IOException | SQLException e) {
            throw new InvalidRequestException(500);
        }
    }

    private void userShowAll(HttpServletRequest req, HttpServletResponse resp) throws InvalidRequestException {
        try {
            int page = 1;
            String p = req.getParameter("page");
            if (isNumber(p))
                page = Integer.parseInt(req.getParameter("page"));
            Paginator paginator = new Paginator(page, 15);

            UtenteDAO dao = new UtenteDAO();
            ArrayList<Utente> utenti = dao.fetchAccounts(paginator);
            int pages = paginator.getPages(dao.countAll());
            req.getSession(true).setAttribute("pages", pages);
            req.getSession(true).setAttribute("utenti", utenti);
            req.getRequestDispatcher(view("crm/utenti")).forward(req, resp);
        } catch (SQLException | ServletException | IOException e) {
            throw new InvalidRequestException(500);
        }
    }

    private void userShow(HttpServletRequest req, HttpServletResponse resp) throws InvalidRequestException {
        try {
            String id = req.getParameter("id");
            if(!isNumber(id))
                throw new InvalidRequestException(400);
            UtenteDAO dao = new UtenteDAO();
            Utente u = dao.fetchAccount(Integer.parseInt(id));
            req.getSession(true).setAttribute("user", u);
            req.getRequestDispatcher(view("crm/utente")).forward(req, resp);
        } catch (SQLException | ServletException | IOException e) {
            throw new InvalidRequestException(500);
        }
    }

    private void orderShow(HttpServletRequest req, HttpServletResponse resp) throws InvalidRequestException {
        try {
            String id = req.getParameter("id");
            if(!isNumber(id))
                throw new InvalidRequestException(400);

            OrdineDAO dao = new OrdineDAO();
            Ordine o = dao.fetchOrder(Integer.parseInt(id));
            req.getSession(true).setAttribute("order", o);
            req.getRequestDispatcher(view("crm/ordine")).forward(req, resp);
        } catch (SQLException | ServletException | IOException e) {
            throw new InvalidRequestException(500);
        }
    }

    private void userOrderShow(HttpServletRequest req, HttpServletResponse resp) throws InvalidRequestException {
        try {
            String id = req.getParameter("id");
            if(!isNumber(id))
                throw new InvalidRequestException(400);

            OrdineDAO dao = new OrdineDAO();
            ArrayList<Ordine> o = dao.fetchOrdersByUser(Integer.parseInt(id));
            req.getSession(true).setAttribute("ordini", o);
            req.getRequestDispatcher(view("crm/ordini")).forward(req, resp);
        } catch (SQLException | ServletException | IOException e) {
            throw new InvalidRequestException(500);
        }
    }

    private void orderShowAll(HttpServletRequest req, HttpServletResponse resp) throws InvalidRequestException {
        try {
            int page = 1;
            String p = req.getParameter("page");
            if (isNumber(p))
                page = Integer.parseInt(req.getParameter("page"));
            Paginator paginator = new Paginator(page, 15);

            OrdineDAO dao = new OrdineDAO();
            ArrayList<Ordine> ordini = dao.fetchOrders(paginator);
            int pages = paginator.getPages(dao.countAll());
            req.getSession(true).setAttribute("pages", pages);
            req.getSession(true).setAttribute("ordini", ordini);
            req.getRequestDispatcher(view("crm/ordini")).forward(req, resp);
        } catch (SQLException | ServletException | IOException e) {
            throw new InvalidRequestException(500);
        }
    }

    private void search(HttpServletRequest req, HttpServletResponse resp) throws InvalidRequestException {
        try {
            int page = 1;
            String p = req.getParameter("page");
            if (isNumber(p))
                page = Integer.parseInt(req.getParameter("page"));

            Paginator pagi = new Paginator(page, 20);
            ProdottoDAO pdao = new ProdottoDAO();
            ArrayList<Prodotto> prods = pdao.fetchProducts(req.getParameter("query"), pagi);
            int pages = pagi.getPages(prods.size());

            req.getSession(true).setAttribute("pages", pages);
            req.getSession(true).setAttribute("prodotti", prods);
            req.getRequestDispatcher(view("crm/prodotti")).forward(req, resp);
        } catch (ServletException | IOException | SQLException e) {
            throw new InvalidRequestException(500);
        }
    }

}
