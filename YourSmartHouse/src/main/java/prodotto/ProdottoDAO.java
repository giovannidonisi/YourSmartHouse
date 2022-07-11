package prodotto;

import http.ConPool;
import http.Paginator;

import java.sql.*;
import java.util.ArrayList;

public class ProdottoDAO {

    public ArrayList<Prodotto> fetchProducts(Paginator pa) throws SQLException {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps =  con.prepareStatement("SELECT * FROM prodotto LIMIT ?,?");
            ps.setInt(1, pa.getOffset());
            ps.setInt(2, pa.getLimit());
            ResultSet rs = ps.executeQuery();
            ArrayList<Prodotto> prodotti = new ArrayList<>();
            while (rs.next()) {
                Prodotto p = new Prodotto();
                p.setProductId(rs.getInt("productId"));
                p.setNome(rs.getString("nome"));
                p.setDescrizione(rs.getString("descrizione"));
                p.setFoto(rs.getString("foto"));
                p.setPrezzo(rs.getDouble("prezzo"));
                p.setDisponibilita(rs.getInt("disponibilita"));
                p.setCategoryId(rs.getInt("category_fk"));
                prodotti.add(p);
            }
            rs.close();
            return prodotti;
        }
    }

    public ArrayList<Prodotto> fetchProducts(String name, Paginator pa) throws SQLException {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps =  con.prepareStatement("SELECT * FROM prodotto WHERE nome LIKE ? LIMIT ?,?");
            String s = "%"+name.replace(" ", "%")+"%";
            ps.setString(1, s);
            ps.setInt(2, pa.getOffset());
            ps.setInt(3, pa.getLimit());
            ResultSet rs = ps.executeQuery();
            ArrayList<Prodotto> prodotti = new ArrayList<>();
            while (rs.next()) {
                Prodotto p = new Prodotto();
                p.setProductId(rs.getInt("productId"));
                p.setNome(rs.getString("nome"));
                p.setDescrizione(rs.getString("descrizione"));
                p.setFoto(rs.getString("foto"));
                p.setPrezzo(rs.getDouble("prezzo"));
                p.setDisponibilita(rs.getInt("disponibilita"));
                p.setCategoryId(rs.getInt("category_fk"));
                prodotti.add(p);
            }
            rs.close();
            return prodotti;
        }
    }

    public ArrayList<Prodotto> fetchProductsByCategory(int catId, Paginator pa) throws SQLException {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps =  con.prepareStatement("SELECT * FROM prodotto WHERE category_fk=? LIMIT ?,?");
            ps.setInt(1, catId);
            ps.setInt(2, pa.getOffset());
            ps.setInt(3, pa.getLimit());
            ResultSet rs = ps.executeQuery();
            ArrayList<Prodotto> prodotti = new ArrayList<>();
            while (rs.next()) {
                Prodotto p = new Prodotto();
                p.setProductId(rs.getInt("productId"));
                p.setNome(rs.getString("nome"));
                p.setDescrizione(rs.getString("descrizione"));
                p.setFoto(rs.getString("foto"));
                p.setPrezzo(rs.getDouble("prezzo"));
                p.setDisponibilita(rs.getInt("disponibilita"));
                p.setCategoryId(rs.getInt("category_fk"));
                prodotti.add(p);
            }
            rs.close();
            return prodotti;
        }
    }

    public Prodotto fetchProduct(int id) throws SQLException {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM prodotto WHERE productId=?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                Prodotto p = new Prodotto();
                p.setProductId(rs.getInt("productId"));
                p.setNome(rs.getString("nome"));
                p.setDescrizione(rs.getString("descrizione"));
                p.setFoto(rs.getString("foto"));
                p.setPrezzo(rs.getDouble("prezzo"));
                p.setDisponibilita(rs.getInt("disponibilita"));
                p.setCategoryId(rs.getInt("category_fk"));
                return p;
            }
            return null;
        }
    }

    public boolean createProduct(Prodotto p) throws SQLException {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("INSERT INTO prodotto (nome, descrizione, foto, prezzo, disponibilita, category_fk) VALUES (?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, p.getNome());
            ps.setString(2, p.getDescrizione());
            ps.setString(3, p.getFoto());
            ps.setDouble(4, p.getPrezzo());
            ps.setInt(5, p.getDisponibilita());
            ps.setInt(6, p.getCategoryId());
            return (ps.executeUpdate()==1);
        }
    }

    public boolean updateProduct(Prodotto p) throws SQLException {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps =  con.prepareStatement("UPDATE prodotto SET nome=?, descrizione=?, foto=?, prezzo=?, disponibilita=?, category_fk=? WHERE productId=?");
            ps.setInt(7, p.getProductId());
            ps.setString(1, p.getNome());
            ps.setString(2, p.getDescrizione());
            ps.setString(3, p.getFoto());
            ps.setDouble(4, p.getPrezzo());
            ps.setInt(5, p.getDisponibilita());
            ps.setInt(6, p.getCategoryId());
            return (ps.executeUpdate()==1);
        }
    }

    public boolean deleteProduct(Prodotto p ) throws SQLException{ // non elimina dal database, ma imposta disponibilità a zero elementi
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps =  con.prepareStatement("UPDATE prodotto SET disponibilita=0 WHERE productId=?");
            ps.setInt(1, p.getProductId());
            return (ps.executeUpdate()==1);
        }
    }

    public int countAll() throws SQLException {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps =  con.prepareStatement("SELECT COUNT(*) FROM prodotto");
            ResultSet rs = ps.executeQuery();
            int i = 0;
            if(rs.next())
                i = rs.getInt(1);
            return i;
        }
    }

    public int countAll(String query) throws SQLException {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps =  con.prepareStatement("SELECT COUNT(*) FROM prodotto WHERE nome LIKE ?");
            String s = "%"+query.replace(" ", "%")+"%";
            ps.setString(1, s);
            ResultSet rs = ps.executeQuery();
            int i = 0;
            if(rs.next())
                i = rs.getInt(1);
            return i;
        }
    }

    public int countAllByCategory(int catId) throws SQLException {
        try (Connection con = ConPool.getConnection()){
            PreparedStatement ps =  con.prepareStatement("SELECT COUNT(*) FROM categoria as cat INNER JOIN prodotto pro ON cat.categoryId=pro.category_fk WHERE cat.categoryId=?");
            ps.setInt(1, catId);
            ResultSet rs = ps.executeQuery();
            int i = 0;
            if(rs.next())
                i = rs.getInt(1);
            return i;
        }
    }

    public ArrayList<Prodotto> fetchRandomProducts(int limit) throws SQLException {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps =  con.prepareStatement("SELECT * FROM prodotto ORDER BY RAND() LIMIT ?");
            ps.setInt(1, limit);
            ResultSet rs = ps.executeQuery();
            ArrayList<Prodotto> prodotti = new ArrayList<>();
            while (rs.next()) {
                if(rs.getInt("disponibilita") >=1) {
                    Prodotto p = new Prodotto();
                    p.setProductId(rs.getInt("productId"));
                    p.setNome(rs.getString("nome"));
                    p.setDescrizione(rs.getString("descrizione"));
                    p.setFoto(rs.getString("foto"));
                    p.setPrezzo(rs.getDouble("prezzo"));
                    p.setDisponibilita(rs.getInt("disponibilita"));
                    p.setCategoryId(rs.getInt("category_fk"));
                    prodotti.add(p);
                }
            }
            rs.close();
            return prodotti;
        }
    }

    public boolean updateProduct(int id, int disponibilita) throws SQLException {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps =  con.prepareStatement("UPDATE prodotto SET disponibilita=? WHERE productId=?");
            ps.setInt(2, id);
            ps.setInt(1, disponibilita);
            return (ps.executeUpdate()==1);
        }
    }

}
