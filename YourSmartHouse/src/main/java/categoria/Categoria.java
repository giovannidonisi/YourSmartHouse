package categoria;

import org.json.JSONObject;
import prodotto.Prodotto;

import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;

public class Categoria {
    private int categoryId;
    private String nome, foto;
    private ArrayList<Prodotto> prodotti;

    public Categoria() {
        super();
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public ArrayList<Prodotto> getProdotti() {
        return prodotti;
    }

    public void setProdotti(ArrayList<Prodotto> prodotti) {
        this.prodotti = prodotti;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public void writeFoto(String up, Part st) throws IOException {
        try(InputStream filestream = st.getInputStream()) {
            File file = new File(up+foto);
            Files.copy(filestream, file.toPath());
        }
    }

    public JSONObject toJSON() {
        JSONObject object = new JSONObject();
        object.put("id", categoryId);
        object.put("nome", nome);
        object.put("foto", foto);
        return object;
    }

}
