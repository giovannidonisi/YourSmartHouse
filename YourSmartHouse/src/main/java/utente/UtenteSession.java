package utente;

public class UtenteSession {
    private final String nome;
    private final int id;
    private final boolean admin;

    public UtenteSession(Utente u) {
        this.nome = u.getNome();
        this.id = u.getUserId();
        this.admin = u.isAdmin();
    }

    public String getNome() {
        return nome;
    }

    public int getId() {
        return id;
    }

    public boolean isAdmin() {
        return admin;
    }
}
