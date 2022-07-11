package categoria;

import http.ConPool;
import http.Paginator;
import prodotto.Prodotto;

import java.sql.*;
import java.util.ArrayList;

public class CategoriaDAO {

    public ArrayList<Categoria> fetchCategories(Paginator p) throws SQLException {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps =  con.prepareStatement("SELECT * FROM categoria LIMIT ?,?");
            ps.setInt(1, p.getOffset());
            ps.setInt(2, p.getLimit());
            ResultSet rs = ps.executeQuery();
            ArrayList<Categoria> categorie = new ArrayList<>();
            while (rs.next()) {
                Categoria c = new Categoria();
                c.setCategoryId(rs.getInt("categoryId"));
                c.setNome(rs.getString("nome"));
                c.setFoto(rs.getString("foto"));
                categorie.add(c);
            }
            rs.close();
            return categorie;
        }
    }

    public ArrayList<Categoria> fetchCategories() throws SQLException {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps =  con.prepareStatement("SELECT * FROM categoria");
            ResultSet rs = ps.executeQuery();
            ArrayList<Categoria> categorie = new ArrayList<>();
            while (rs.next()) {
                Categoria c = new Categoria();
                c.setCategoryId(rs.getInt("categoryId"));
                c.setNome(rs.getString("nome"));
                c.setFoto(rs.getString("foto"));
                categorie.add(c);
            }
            rs.close();
            return categorie;
        }
    }

    public Categoria fetchCategory(int id) throws SQLException {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps =  con.prepareStatement("SELECT * FROM categoria WHERE categoryId=?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            Categoria c = new Categoria();
            if(rs.next()) {
                c.setCategoryId(rs.getInt("categoryId"));
                c.setNome(rs.getString("nome"));
                c.setFoto(rs.getString("foto"));
            }
            rs.close();
            return c;
        }
    }

    public boolean createCategory(Categoria c) throws SQLException {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("INSERT INTO categoria (nome, foto) VALUE (?,?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, c.getNome());
            ps.setString(2, c.getFoto());
            return (ps.executeUpdate()==1);
        }
    }

    public boolean updateCategory(Categoria c) throws SQLException {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps =  con.prepareStatement("UPDATE categoria SET nome=?, foto=? WHERE categoryId=?");
            ps.setInt(3, c.getCategoryId());
            ps.setString(1, c.getNome());
            ps.setString(2, c.getFoto());
            return (ps.executeUpdate()==1);
        }
    }

    public Categoria fetchCategoryWithProducts(int catId, Paginator paginator) throws SQLException {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps =  con.prepareStatement("SELECT * FROM categoria as cat INNER JOIN prodotto pro ON cat.categoryId=pro.category_fk WHERE cat.categoryId=? LIMIT ?,?");
            ps.setInt(1, catId);
            ps.setInt(2, paginator.getOffset());
            ps.setInt(3, paginator.getLimit());
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                Categoria c = new Categoria();
                c.setCategoryId(rs.getInt("categoryId"));
                c.setNome(rs.getString("cat.nome"));
                c.setFoto(rs.getString("cat.foto"));
                ArrayList<Prodotto> prodotti = new ArrayList<>();
                Prodotto p = new Prodotto();
                p.setProductId(rs.getInt("productId"));
                p.setNome(rs.getString("pro.nome"));
                p.setDescrizione(rs.getString("descrizione"));
                p.setFoto(rs.getString("pro.foto"));
                p.setPrezzo(rs.getDouble("prezzo"));
                p.setDisponibilita(rs.getInt("disponibilita"));
                p.setCategoryId(rs.getInt("category_fk"));
                prodotti.add(p);
                while(rs.next()) {
                    Prodotto pp = new Prodotto();
                    pp.setProductId(rs.getInt("productId"));
                    pp.setNome(rs.getString("pro.nome"));
                    pp.setDescrizione(rs.getString("descrizione"));
                    pp.setFoto(rs.getString("pro.foto"));
                    pp.setPrezzo(rs.getDouble("prezzo"));
                    pp.setDisponibilita(rs.getInt("disponibilita"));
                    pp.setCategoryId(rs.getInt("category_fk"));
                    prodotti.add(pp);
                }
                c.setProdotti(prodotti);
                return c;
            }
            return null;
        }
    }

    public int countAll() throws SQLException {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps =  con.prepareStatement("SELECT COUNT(*) FROM categoria");
            ResultSet rs = ps.executeQuery();
            int i = 0;
            if(rs.next())
                i = rs.getInt(1);
            return i;
        }
    }

}
