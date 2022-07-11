package ordine;

import carrello.Carrello;
import prodotto.Prodotto;

import java.time.LocalDate;
import java.util.ArrayList;

public class Ordine {
    private int orderId, quantita, userId;
    private LocalDate data;
    private double prezzo;
    private ArrayList<Prodotto> prodotti;
    private Carrello carrello;

    public Ordine() {
        super();
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public double getPrezzo() {
        return prezzo;
    }

    public int getQuantita() {
        return quantita;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public ArrayList<Prodotto> getProdotti() {
        return prodotti;
    }

    public void setProdotti(ArrayList<Prodotto> prodotti) {
        this.prodotti = prodotti;
    }

    public Carrello getCarrello() {
        return carrello;
    }

    public void setCarrello(Carrello carrello) {
        this.carrello = carrello;
    }

    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }

    public void setPrezzo(double prezzo) {
        this.prezzo = prezzo;
    }
}
