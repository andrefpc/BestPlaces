package afpcsoft.com.br.bestplaces.model;

import android.location.Location;

public class MyLocal {
    private String nome;
    private String logradouro;
    private String numero;
    private String municipio;
    private String estado;
    private Location location;

    public MyLocal(String nome, String logradouro, String numero, String municipio, String estado, Location location) {
        this.nome = nome;
        this.logradouro = logradouro;
        this.numero = numero;
        this.municipio = municipio;
        this.estado = estado;
        this.location = location;
    }

    public MyLocal() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
