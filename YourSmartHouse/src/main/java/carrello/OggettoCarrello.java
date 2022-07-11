package carrello;

import prodotto.Prodotto;

public class OggettoCarrello {
    private final Prodotto prodotto;
    private int quantita;

    public OggettoCarrello(Prodotto prodotto, int quantita) {
        this.prodotto = prodotto;
        this.quantita = quantita;
    }

    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }

    public Prodotto getProdotto() {
        return prodotto;
    }

    public int getQuantita() {
        return quantita;
    }

    public double totale() {
        double n = prodotto.getPrezzo()*quantita;
        n = Math.round(n * 100);
        return n/100; // Mi assicuro che i double abbiano 2 cifre dopo la virgola
    }
}
