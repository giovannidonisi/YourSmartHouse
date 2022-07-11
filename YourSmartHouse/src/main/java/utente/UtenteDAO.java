package utente;

import http.ConPool;
import http.Paginator;

import java.sql.*;
import java.util.ArrayList;

public class UtenteDAO {

    public ArrayList<Utente> fetchAccounts(Paginator p) throws SQLException {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps =  con.prepareStatement("SELECT * FROM utente LIMIT ?,?");
            ps.setInt(1, p.getOffset());
            ps.setInt(2, p.getLimit());
            ResultSet rs = ps.executeQuery();
            ArrayList<Utente> utenti = new ArrayList<>();
            while (rs.next()) {
                Utente u = new Utente();
                u.setAdmin(rs.getBoolean("admin"));
                u.setEmail(rs.getString("email"));
                u.setIndirizzo(rs.getString("indirizzo"));
                u.setNome(rs.getString("nome"));
                //u.setOrdini(rs.getArray("ordini"));
                u.setPasswordHash(rs.getString("passwordHash"));
                u.setSalt(rs.getString("salt"));
                u.setTelefono(rs.getString("telefono"));
                u.setUserId(rs.getInt("userId"));
                utenti.add(u);
            }
            rs.close();
            return utenti;
        }
    }

    public Utente fetchAccount(String email) throws SQLException {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM utente WHERE email=?");
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Utente u = new Utente();
                u.setAdmin(rs.getBoolean("admin"));
                u.setEmail(rs.getString("email"));
                u.setIndirizzo(rs.getString("indirizzo"));
                u.setNome(rs.getString("nome"));
                u.setSalt(rs.getString("salt"));
                u.setPasswordHash(rs.getString("passwordHash"));
                u.setTelefono(rs.getString("telefono"));
                u.setUserId(rs.getInt("userId"));
                return u;
            }
            return null;
        }
    }

    public Utente fetchAccount(int id) throws SQLException {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM utente WHERE userId=?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Utente u = new Utente();
                u.setAdmin(rs.getBoolean("admin"));
                u.setEmail(rs.getString("email"));
                u.setIndirizzo(rs.getString("indirizzo"));
                u.setNome(rs.getString("nome"));
                u.setSalt(rs.getString("salt"));
                u.setPasswordHash(rs.getString("passwordHash"));
                u.setTelefono(rs.getString("telefono"));
                u.setUserId(rs.getInt("userId"));
                return u;
            }
            return null;
        }
    }

    public boolean createAccount(Utente u) throws SQLException {
        try (Connection con = ConPool.getConnection()){
            PreparedStatement ps = con.prepareStatement("INSERT INTO utente (nome, email, passwordHash, admin, salt) VALUES(?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, u.getNome());
            ps.setString(2, u.getEmail());
            ps.setString(3,u.getPasswordHash());
            ps.setBoolean(4, u.isAdmin());
            ps.setString(5, u.getSalt());
            return (ps.executeUpdate()==1);
        }
    }

    public boolean updateAccount(Utente u) throws SQLException {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps =  con.prepareStatement("UPDATE utente SET nome=?, indirizzo=?, telefono=?, email=? WHERE userId=?");
            ps.setInt(5, u.getUserId());
            ps.setString(1, u.getNome());
            ps.setString(2, u.getIndirizzo());
            ps.setString(3, u.getTelefono());
            ps.setString(4, u.getEmail());
            return (ps.executeUpdate()==1);
        }
    }

    public boolean deleteAccount(int id) throws SQLException{
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps =  con.prepareStatement("DELETE FROM utente WHERE userId=?");
            ps.setInt(1, id);
            return (ps.executeUpdate()==1);
        }
    }

    public int countAll() throws SQLException {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps =  con.prepareStatement("SELECT COUNT(*) FROM utente");
            ResultSet rs = ps.executeQuery();
            int i = 0;
            if(rs.next())
                i = rs.getInt(1);
            return i;
        }
    }

    public boolean changePwd(Utente u) throws SQLException {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps =  con.prepareStatement("UPDATE utente SET passwordHash=? WHERE userId=?");
            ps.setInt(2, u.getUserId());
            ps.setString(1, u.getPasswordHash());
            return (ps.executeUpdate()==1);
        }
    }

}
