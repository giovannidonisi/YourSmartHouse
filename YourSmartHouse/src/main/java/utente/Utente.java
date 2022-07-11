package utente;

import ordine.Ordine;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;

public class Utente {
    private int userId;
    private boolean admin;
    private String email, passwordHash, nome, indirizzo, telefono, salt;
    private ArrayList<Ordine> ordini;

    public Utente(){
        super();
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public void setAdmin(int admin) {
        this.admin = (admin == 1);
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public void setPassword(String p) throws NoSuchAlgorithmException {
        MessageDigest d = MessageDigest.getInstance("SHA-512");
        byte[] h = d.digest(p.getBytes(StandardCharsets.UTF_8));
        StringBuilder b = new StringBuilder();
        for(byte by : h) {
            b.append(String.format("%02x", by));
        }
        this.passwordHash = b.toString();
    }

    public String generateSalt() {
        SecureRandom sr = new SecureRandom();
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        StringBuilder b = new StringBuilder();
        for(byte by : salt) {
            b.append(String.format("%02x", by));
        }
        return b.toString();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public ArrayList<Ordine> getOrdini() {
        return ordini;
    }

    public void setOrdini(ArrayList<Ordine> ordini) {
        this.ordini = ordini;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
}
