package ordine;

import carrello.Carrello;
import carrello.OggettoCarrello;
import http.ConPool;
import http.Paginator;
import prodotto.Prodotto;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class OrdineDAO {

    public ArrayList<Ordine> fetchOrders(Paginator p) throws SQLException {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps =  con.prepareStatement("SELECT * FROM ordine LIMIT ?,?");
            ps.setInt(1, p.getOffset());
            ps.setInt(2, p.getLimit());
            ResultSet rs = ps.executeQuery();
            ArrayList<Ordine> ordini = new ArrayList<>();
            while (rs.next()) {
                Ordine o = new Ordine();
                o.setOrderId(rs.getInt("orderId"));
                o.setQuantita(rs.getInt("quantita"));
                o.setPrezzo(rs.getDouble("prezzo"));
                o.setUserId(rs.getInt("user_fk"));
                o.setData(rs.getDate("data").toLocalDate());
                ordini.add(o);
            }
            rs.close();
            return ordini;
        }
    }

    public Ordine fetchOrder(int id) throws SQLException {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM ordine WHERE orderId=?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Ordine o = new Ordine();
                o.setOrderId(rs.getInt("orderId"));
                o.setPrezzo(rs.getDouble("prezzo"));
                o.setQuantita(rs.getInt("quantita"));
                o.setData(rs.getDate("data").toLocalDate());
                o.setUserId(rs.getInt("user_fk"));
                return o;
            }
            return null;
        }
    }

    public ArrayList<Ordine> fetchOrdersByUser(int userId) throws SQLException {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM ordine WHERE user_fk=?");
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            ArrayList<Ordine> ordini = new ArrayList<>();
            while (rs.next()) {
                Ordine o = new Ordine();
                o.setOrderId(rs.getInt("orderId"));
                o.setPrezzo(rs.getDouble("prezzo"));
                o.setQuantita(rs.getInt("quantita"));
                o.setData(rs.getDate("data").toLocalDate());
                o.setUserId(rs.getInt("user_fk"));
                ordini.add(o);
            }
            return ordini;
        }
    }

    public boolean createOrder(Ordine o) throws SQLException {
        try (Connection con = ConPool.getConnection()) {
            con.setAutoCommit(false);
            try (
            PreparedStatement ps = con.prepareStatement("INSERT INTO ordine (quantita, prezzo, data, user_fk) VALUES (?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            PreparedStatement psAssoc = con.prepareStatement("INSERT INTO order_product (order_fk, product_fk, quantita) VALUES (?,?,?)")
            ) {
                ps.setInt(1, o.getQuantita());
                ps.setDouble(2, o.getPrezzo());
                ps.setDate(3, java.sql.Date.valueOf(o.getData()));
                ps.setInt(4, o.getUserId());

                int rows = ps.executeUpdate();
                int total = rows;
                ResultSet setId = ps.getGeneratedKeys();
                setId.next();
                o.setOrderId(setId.getInt(1));
                for (OggettoCarrello ogg : o.getCarrello().getOggetti()) {
                    psAssoc.setInt(1, o.getOrderId());
                    psAssoc.setInt(2, ogg.getProdotto().getProductId());
                    psAssoc.setInt(3, ogg.getQuantita());
                    total += psAssoc.executeUpdate();
                }
                if (total == (rows + o.getCarrello().getOggetti().size())) {
                    con.commit();
                    con.setAutoCommit(true);
                    return true;
                } else {
                    con.rollback();
                    con.setAutoCommit(true);
                    return false;
                }
            }
        }
    }

    public ArrayList<Ordine> fetchOrdersWithProducts(int userId, Paginator pa) throws SQLException {
        try (Connection con = ConPool.getConnection()) {
            String query = "SELECT * FROM order_product AS op INNER JOIN ordine ord ON op.order_fk=ord.orderId INNER JOIN prodotto pro ON op.product_fk=pro.productId LEFT JOIN categoria cat ON pro.category_fk=cat.categoryId WHERE ord.user_fk=? LIMIT ?,?";
            try(PreparedStatement ps = con.prepareStatement(query)) {
                ps.setInt(1, userId);
                ps.setInt(2, pa.getOffset());
                ps.setInt(3, pa.getLimit());
                ResultSet rs = ps.executeQuery();
                Map<Integer, Ordine> orderMap = new LinkedHashMap<>();
                while (rs.next()) {
                    int orderId = rs.getInt("ord.orderId");
                    if (!orderMap.containsKey(orderId)) {
                        Ordine o = new Ordine();
                        o.setPrezzo(rs.getDouble("prezzo"));
                        o.setQuantita(rs.getInt("quantita"));
                        o.setOrderId(orderId);
                        o.setData(rs.getDate("data").toLocalDate());
                        o.setUserId(rs.getInt("user_fk"));
                        o.setCarrello(new Carrello(new ArrayList<>()));
                        orderMap.put(orderId, o);

                        Prodotto p = new Prodotto();
                        p.setProductId(rs.getInt("productId"));
                        p.setNome(rs.getString("pro.nome"));
                        p.setDescrizione(rs.getString("descrizione"));
                        p.setFoto(rs.getString("foto"));
                        p.setPrezzo(rs.getDouble("pro.prezzo"));
                        p.setDisponibilita(rs.getInt("disponibilita"));
                        p.setCategoryId(rs.getInt("category_fk"));
                        orderMap.get(orderId).getCarrello().addProduct(p, rs.getInt("quantita"));
                    }
                    else {
                        Prodotto p = new Prodotto();
                        p.setProductId(rs.getInt("productId"));
                        p.setNome(rs.getString("pro.nome"));
                        p.setDescrizione(rs.getString("descrizione"));
                        p.setFoto(rs.getString("foto"));
                        p.setPrezzo(rs.getDouble("pro.prezzo"));
                        p.setDisponibilita(rs.getInt("disponibilita"));
                        p.setCategoryId(rs.getInt("category_fk"));
                        orderMap.get(orderId).getCarrello().addProduct(p, rs.getInt("quantita"));
                    }
                }
                return new ArrayList<>(orderMap.values());
            }
        }
    }

    public int countAll() throws SQLException {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps =  con.prepareStatement("SELECT COUNT(*) FROM ordine");
            ResultSet rs = ps.executeQuery();
            int i = 0;
            if(rs.next())
                i = rs.getInt(1);
            return i;
        }
    }

    public double totalIncome() throws SQLException {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps =  con.prepareStatement("SELECT prezzo FROM ordine");
            ResultSet rs = ps.executeQuery();
            double tot = 0;
            while (rs.next()) {
                tot += rs.getDouble(1);
            }
            rs.close();
            tot = Math.round(tot * 100);
            return tot/100;
        }
    }

}
