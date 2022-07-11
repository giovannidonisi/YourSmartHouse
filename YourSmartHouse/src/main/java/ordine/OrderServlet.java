package ordine;

import carrello.Carrello;
import carrello.OggettoCarrello;
import http.Controller;
import http.InvalidRequestException;
import http.Paginator;
import prodotto.Prodotto;
import prodotto.ProdottoDAO;
import utente.Utente;
import utente.UtenteDAO;
import utente.UtenteSession;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

@WebServlet(name = "OrderServlet", value = "/orders/*")
public class OrderServlet extends Controller {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String path = getPath(req);
            switch (path) {
                case "/":
                case "/show":
                    String p = req.getParameter("page");
                    int page = 1;
                    if (isNumber(p))
                        page = Integer.parseInt(p);

                    Paginator pag = new Paginator(page, 10);
                    OrdineDAO dao = new OrdineDAO();
                    UtenteSession utenteSession = (UtenteSession) req.getSession(false).getAttribute("utenteSession");
                    if(utenteSession == null)
                        throw new InvalidRequestException(401);

                    ArrayList<Ordine> ordini = dao.fetchOrdersWithProducts(utenteSession.getId(), pag);
                    req.getSession(true).setAttribute("ordini", ordini);
                    req.getRequestDispatcher(view("utente/ordini")).forward(req, resp);
                    break;
                case "/checkout":
                    Carrello c = getSessionCart(req.getSession(false));
                    if(c == null)
                        throw new InvalidRequestException(400);

                    UtenteDAO udao = new UtenteDAO();
                    UtenteSession us = (UtenteSession) req.getSession(false).getAttribute("utenteSession");
                    if(us == null) {
                        ArrayList<String> a = new ArrayList<>();
                        a.add("Accedi prima di continuare");
                        req.setAttribute("back", view("utente/login"));
                        throw new InvalidRequestException(a, 400);
                    }

                    Utente u = udao.fetchAccount(us.getId());

                    if(u.getIndirizzo() == null || u.getTelefono() == null) {
                        ArrayList<String> a = new ArrayList<>();
                        a.add("Completa il profilo prima di continuare");
                        req.setAttribute("back", view("utente/update"));
                        req.setAttribute("user", u);
                        throw new InvalidRequestException(a, 400);
                    }
                    req.getSession(true).setAttribute("cart", c);
                    req.getSession(true).setAttribute("user", u);
                    req.getRequestDispatcher(view("utente/checkout")).forward(req, resp);
                    break;
                default:
                    throw new InvalidRequestException(404);
            }
        } catch (InvalidRequestException e) {
            e.handle(req, resp);
        } catch (SQLException throwables) {
            resp.sendError(500);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String path = getPath(req);
            switch (path) {
                case "/create":
                    HttpSession session = req.getSession(false);
                    authenticate(session);

                    UtenteSession se = (UtenteSession) session.getAttribute("utenteSession");
                    Carrello c = getSessionCart(session);
                    OrdineDAO dao = new OrdineDAO();
                    Ordine o = new Ordine();
                    o.setUserId(se.getId());
                    o.setCarrello(c);
                    o.setData(LocalDate.now());
                    o.setPrezzo(c.getTotale());
                    o.setQuantita(c.getQuantita());

                    if(dao.createOrder(o)) {
                        ProdottoDAO pdao = new ProdottoDAO();
                        for(OggettoCarrello ogg : c.getOggetti()) {
                            Prodotto p = ogg.getProdotto();
                            pdao.updateProduct(p.getProductId(), (p.getDisponibilita() - ogg.getQuantita()));
                        }
                        c.reset();
                        resp.sendRedirect("/YourSmartHouse/orders/show");
                    }
                    else
                        throw new InvalidRequestException(500);
                    break;
                default:
                    throw new InvalidRequestException(404);
            }
        }  catch (InvalidRequestException e) {
            e.handle(req, resp);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            resp.sendError(500);
        }
    }
}
