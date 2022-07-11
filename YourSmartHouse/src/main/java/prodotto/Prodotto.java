package prodotto;

import ordine.Ordine;
import org.json.JSONObject;

import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;

public class Prodotto {
    private int productId, categoryId, disponibilita;
    private String foto, nome, descrizione;
    private double prezzo;
    private ArrayList<Ordine> ordini;

    public Prodotto() {
        super();
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(double prezzo) {
        this.prezzo = prezzo;
    }

    public int getDisponibilita() {
        return disponibilita;
    }

    public void setDisponibilita(int disponibilita) {
        this.disponibilita = disponibilita;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public ArrayList<Ordine> getOrdini() {
        return ordini;
    }

    public void setOrdini(ArrayList<Ordine> ordini) {
        this.ordini = ordini;
    }

    public void writeFoto(String up, Part st) throws IOException {
        try(InputStream filestream = st.getInputStream()) {
            File file = new File(up+foto);
            Files.copy(filestream, file.toPath());
        }
    }

    public String getNomeCategoria(){
        switch (this.categoryId) {
            case 1:
                return "Illuminazione";
            case 2:
                return "Altoparlanti";
            case 3:
                return "Prese";
            case 4:
                return "Sensori";
            case 5:
                return "Elettrodomestici";
            case 6:
                return "Sicurezza";
            default:
                return null;
        }
    }

    public JSONObject toJSON() {
        JSONObject object = new JSONObject();
        object.put("id", productId);
        object.put("nome", nome);
        object.put("foto", foto);
        object.put("prezzo", prezzo);
        return object;
    }

}
