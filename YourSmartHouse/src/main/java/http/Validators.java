package http;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;

public class Validators {

    public static RequestValidator productValidator(HttpServletRequest req) {
        RequestValidator v = new RequestValidator(req);
        v.assertMatch("nome", Pattern.compile("^.{1,100}$"), "Il nome non deve superare i 100 caratteri");
        v.assertMatch("descrizione", Pattern.compile("^[\\s\\S]{1,1500}$"), "La descrizione non deve superare i 1500 caratteri");
        v.assertDouble("prezzo", "Il prezzo deve essere un double");
        v.assertInt("categoria", "La categoria deve essere un intero positivo");
        v.assertInt("disponibilita", "La disponibilità deve essere un intero positivo");
        return v;
    }

    public static RequestValidator categoryValidator(HttpServletRequest req) {
        RequestValidator v = new RequestValidator(req);
        v.assertMatch("nome", Pattern.compile("^\\w{1,30}$"), "Il nome non deve superare i 30 caratteri");
        return v;
    }

    public static RequestValidator userValidator(HttpServletRequest req) {
        RequestValidator v = new RequestValidator(req);
        v.assertMatch("nome", Pattern.compile("^.{1,50}$"), "Il nome non deve superare i 50 caratteri");
        v.assertMatch("password", Pattern.compile("^.{8,32}$"), "La password deve essere compresa tra gli 8 e i 32 caratteri");
        v.assertEmail("email", "Formato email non valido");
        return v;
    }

    public static RequestValidator userUpdateValidator(HttpServletRequest req) {
        RequestValidator v = new RequestValidator(req);
        v.assertMatch("nome", Pattern.compile("^.{1,50}$"), "Il nome non deve superare i 50 caratteri");
        v.assertEmail("email", "Formato email non valido");
        v.assertMatch("indirizzo", Pattern.compile("^.{1,100}$"), "L'indirizzo non deve superare i 100 caratteri");
        v.assertMatch("telefono", Pattern.compile("^\\+?\\d{10,12}$"), "Formato numero di telefono non valido");
        return v;
    }

    public static RequestValidator changePasswordValidator(HttpServletRequest req) {
        RequestValidator v = new RequestValidator(req);
        v.assertMatch("oldPassword", Pattern.compile("^.{8,32}$"), "La password deve essere compresa tra gli 8 e i 32 caratteri");
        v.assertMatch("newPassword", Pattern.compile("^.{8,32}$"), "La password deve essere compresa tra gli 8 e i 32 caratteri");
        v.assertMatch("newPasswordConf", Pattern.compile("^.{8,32}$"), "La password deve essere compresa tra gli 8 e i 32 caratteri");
        return v;
    }

}
