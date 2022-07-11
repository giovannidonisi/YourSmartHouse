package utente;

import http.*;
import ordine.OrdineDAO;
import prodotto.ProdottoDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(name = "AccountServlet", value = "/accounts/*")
public class AccountServlet extends Controller {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = getPath(req);

        switch (path) {
            case "/":
            case "/profile":
                try {
                    HttpSession session = req.getSession(false);
                    authenticate(session);
                    UtenteSession u = (UtenteSession) session.getAttribute("utenteSession");
                    UtenteDAO d = new UtenteDAO();
                    Utente utente = d.fetchAccount(u.getId());
                    req.setAttribute("user", utente);
                    req.getRequestDispatcher(view("utente/profilo")).forward(req, resp);
                    break;
                } catch (InvalidRequestException | SQLException e) {
                    e.printStackTrace();
                }
            case "/login":
                req.getRequestDispatcher(view("utente/login")).forward(req, resp);
                break;
            case "/signup":
                req.getRequestDispatcher(view("utente/signup")).forward(req, resp);
                break;
            case "/update":
                try {
                    HttpSession session = req.getSession(false);
                    authenticate(session);
                    UtenteSession u = (UtenteSession) session.getAttribute("utenteSession");
                    UtenteDAO d = new UtenteDAO();
                    Utente utente = d.fetchAccount(u.getId());
                    req.setAttribute("user", utente);
                    req.getRequestDispatcher(view("utente/update")).forward(req, resp);
                    break;
                } catch (InvalidRequestException | SQLException e) {
                    e.printStackTrace();
                }
            case "/changePassword":
                try {
                    HttpSession session = req.getSession(false);
                    authenticate(session);
                    UtenteSession u = (UtenteSession) session.getAttribute("utenteSession");
                    req.setAttribute("id", u.getId());
                    req.getRequestDispatcher(view("utente/changePwd")).forward(req, resp);
                    break;
                } catch (InvalidRequestException e) {
                    e.printStackTrace();
                }
            case "/secret":
                req.getRequestDispatcher(view("crm/secret")).forward(req, resp);
                break;
            case "/logout":
                doPost(req, resp);
                break;
            default:
                resp.sendError(404);
                break;
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String path = getPath(req);
            switch (path) {
                case "/":
                    break;
                case "/secret":
                    secret(req, resp);
                    break;
                case "/login":
                    login(req, resp);
                    break;
                case "/signup":
                    signup(req, resp);
                    break;
                case "/update":
                    update(req, resp);
                    break;
                case "/logout":
                    HttpSession session = req.getSession(false);
                    authenticate(session);
                    UtenteSession u = (UtenteSession) session.getAttribute("utenteSession");
                    logout(req, resp, u);
                    break;
                case "/changePassword":
                    changePassword(req, resp);
                    break;
                case "/delete":
                    HttpSession ses = req.getSession(false);
                    authenticate(ses);
                    UtenteSession us = (UtenteSession) ses.getAttribute("utenteSession");
                    UtenteDAO d = new UtenteDAO();
                    d.deleteAccount(us.getId());
                    logout(req, resp, us);
                    break;
                default:
                    resp.sendError(404);
                    break;
            }
        } catch (InvalidRequestException e) {
            e.handle(req, resp);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void logout(HttpServletRequest req, HttpServletResponse resp, UtenteSession s) throws IOException {
        HttpSession session = req.getSession(false);
        String r = s.isAdmin() ? "/YourSmartHouse/accounts/secret" : "/YourSmartHouse/accounts/login";
        session.removeAttribute("utenteSession");
        session.invalidate();
        resp.sendRedirect(r);
    }

    private void signup(HttpServletRequest req, HttpServletResponse resp) throws InvalidRequestException {
        try {
            req.setAttribute("back", view("utente/signup"));
            RequestValidator v = Validators.userValidator(req);
            if(v.hasErrors())
                throw new InvalidRequestException(v.getErrors(), 400);

            Utente u = new Utente();
            UtenteDAO dao = new UtenteDAO();
            u.setNome(req.getParameter("nome"));
            u.setEmail(req.getParameter("email"));
            u.setAdmin(false);
            String pwd = req.getParameter("password");
            String salt = u.generateSalt();
            u.setSalt(salt);
            u.setPassword(salt+pwd);
            if(dao.createAccount(u)) {
                UtenteSession session = new UtenteSession(u);
                req.getSession(true).setAttribute("utenteSession", session);
                resp.sendRedirect("/YourSmartHouse");
            }
            else throw new InvalidRequestException(500);
        } catch (SQLException | IOException | NoSuchAlgorithmException e) {
            throw new InvalidRequestException(500);
        }
    }

    private void login(HttpServletRequest req, HttpServletResponse resp) throws InvalidRequestException {
        try {
            Utente u = new Utente();
            UtenteDAO dao = new UtenteDAO();
            Utente tmp = dao.fetchAccount(req.getParameter("email"));
            if (tmp == null){
                req.setAttribute("back", view("utente/login"));
                ArrayList<String> a = new ArrayList<>();
                a.add("Email o password non validi");
                throw new InvalidRequestException(a,400);
            }
            String s = tmp.getSalt();
            s += req.getParameter("password");
            u.setPassword(s);
            if (!u.getPasswordHash().equals(tmp.getPasswordHash())){
                req.setAttribute("back", view("utente/login"));
                ArrayList<String> a = new ArrayList<>();
                a.add("Email o password non validi");
                throw new InvalidRequestException(a,400);
            }
            UtenteSession utenteSession = new UtenteSession(tmp);
            req.getSession(true).setAttribute("utenteSession", utenteSession);
            resp.sendRedirect("/YourSmartHouse");
        } catch (SQLException | IOException | NoSuchAlgorithmException e) {
            throw new InvalidRequestException(500);
        }
    }

    private void secret(HttpServletRequest req, HttpServletResponse resp) throws InvalidRequestException {
        try {
            Utente u = new Utente();
            UtenteDAO dao = new UtenteDAO();
            Utente tmp = dao.fetchAccount(req.getParameter("email"));
            if (tmp == null){
                req.setAttribute("back", view("crm/secret"));
                ArrayList<String> a = new ArrayList<>();
                a.add("Email o password non validi");
                throw new InvalidRequestException(a,400);
            }
            String s = tmp.getSalt();
            s += req.getParameter("password");
            u.setPassword(s);
            if (!u.getPasswordHash().equals(tmp.getPasswordHash())){
                req.setAttribute("back", view("crm/secret"));
                ArrayList<String> a = new ArrayList<>();
                a.add("Email o password non validi");
                throw new InvalidRequestException(a,400);
            }

            UtenteSession utenteSession = new UtenteSession(tmp);
            if(!utenteSession.isAdmin())
                throw new InvalidRequestException(403);
            req.getSession(true).setAttribute("utenteSession", utenteSession);

            ProdottoDAO pd = new ProdottoDAO();
            int nProd = pd.countAll();
            req.getSession(true).setAttribute("nProd", nProd);

            int nAcc = dao.countAll();
            req.getSession(true).setAttribute("nAcc", nAcc);

            OrdineDAO od = new OrdineDAO();
            int nOrd = od.countAll();
            double tot = od.totalIncome();
            req.getSession(true).setAttribute("nOrd", nOrd);
            req.getSession(true).setAttribute("tot", tot);

            resp.sendRedirect("/YourSmartHouse/crm/dashboard");
        } catch (NoSuchAlgorithmException | IOException | SQLException e) {
            throw new InvalidRequestException(500);
        }
    }

    private void update(HttpServletRequest req, HttpServletResponse resp) throws InvalidRequestException {
        try {
            authenticate(req.getSession(false));
            req.setAttribute("back", view("utente/update"));
            RequestValidator v = Validators.userUpdateValidator(req);
            if(v.hasErrors())
                throw new InvalidRequestException(v.getErrors(), 400);

            Utente u = new Utente();
            UtenteDAO dao = new UtenteDAO();
            u.setUserId(Integer.parseInt(req.getParameter("id")));
            u.setNome(req.getParameter("nome"));
            u.setEmail(req.getParameter("email"));
            u.setAdmin(false);
            u.setTelefono(req.getParameter("telefono"));
            u.setIndirizzo(req.getParameter("indirizzo"));

            if(dao.updateAccount(u)){
                resp.sendRedirect("/YourSmartHouse/accounts/");
            }
            else
                throw new InvalidRequestException(500);
        } catch (SQLException | IOException e) {
            throw new InvalidRequestException(500);
        }
    }

    private void changePassword(HttpServletRequest req, HttpServletResponse resp) throws InvalidRequestException {
        try {
            authenticate(req.getSession(false));
            req.setAttribute("back", view("utente/changePwd"));
            RequestValidator v = Validators.changePasswordValidator(req);
            if(v.hasErrors())
                throw new InvalidRequestException(v.getErrors(), 400);

            UtenteDAO dao = new UtenteDAO();
            String id = req.getParameter("id");
            if(!isNumber(id))
                throw new InvalidRequestException(400);

            int userId = Integer.parseInt(id);
            String oldPwd = req.getParameter("oldPassword");
            String newPwd = req.getParameter("newPassword");
            String newPwdC = req.getParameter("newPasswordConf");
            if(!newPwd.equals(newPwdC))
                throw new InvalidRequestException(400);

            Utente u = dao.fetchAccount(userId);
            Utente tmp = new Utente();

            String oldpass = u.getSalt()+oldPwd;
            tmp.setPassword(oldpass);

            if(!tmp.getPasswordHash().equals(u.getPasswordHash())) {
                ArrayList<String> a = new ArrayList<>();
                a.add("Password errata");
                throw new InvalidRequestException(a, 400);
            }

            String newpass = u.getSalt()+newPwd;
            u.setPassword(newpass);
            if(dao.changePwd(u))
                req.getRequestDispatcher(view("utente/pwdConfirm")).forward(req, resp);
            else
                throw new InvalidRequestException(500);
        } catch (SQLException | IOException | NoSuchAlgorithmException | ServletException e) {
            throw new InvalidRequestException(500);
        }
    }

}
