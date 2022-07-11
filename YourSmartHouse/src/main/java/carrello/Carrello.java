package carrello;

import prodotto.Prodotto;

import java.util.ArrayList;
import java.util.Optional;

public class Carrello {
    private ArrayList<OggettoCarrello> oggetti;

    public Carrello() {
        super();
        this.oggetti = new ArrayList<>();
    }

    public Carrello(ArrayList<OggettoCarrello> o){
        this.oggetti = o;
    }

    public boolean addProduct(Prodotto p, int q) {
        Optional<OggettoCarrello> opt = find(p.getProductId());
        if(opt.isPresent()) {
            opt.get().setQuantita(q);
            return true;
        }
        else
            return oggetti.add(new OggettoCarrello(p, q));
    }

    public boolean removeProduct(int id) {
        return oggetti.removeIf(it -> it.getProdotto().getProductId() == id);
    }

    public Optional<OggettoCarrello> find(int id) {
        return oggetti.stream().filter(it -> it.getProdotto().getProductId() == id).findFirst();
    }

    public void reset() {
        oggetti.clear();
    }

    public ArrayList<OggettoCarrello> getOggetti() {
        return oggetti;
    }

    public void setOggetti(ArrayList<OggettoCarrello> oggetti) {
        this.oggetti = oggetti;
    }

    public double getTotale() {
        double x = 0.0;
        for(OggettoCarrello o : oggetti){
            x += o.totale();
        }
        x = Math.round(x * 100);
        return x/100; // Mi assicuro che i double abbiano 2 cifre dopo la virgola
    }

    public int getQuantita() {
        int a = 0;
        for(OggettoCarrello o : oggetti) {
            a += o.getQuantita();
        }
        return a;
    }
}
