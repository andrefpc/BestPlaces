package afpcsoft.com.br.bestplaces.model;

import java.io.Serializable;

public class FastFood implements Serializable {
    private int id;
    private String nome;
    private String descricao;
    private String endComp;
    private String logradouro;
    private String numero;
    private String bairro;
    private String municipio;
    private String estado;
    private Double latitude;
    private Double longitude;
    private String tel;
    private int rating;
    private int tipo_id;

    public FastFood() {
    }

    public FastFood(int id, String nome, String descricao, String endComp, String logradouro, String numero, String bairro, String municipio, String estado, Double latitude, Double longitude, String tel, int rating, int tipo_id) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.endComp = endComp;
        this.logradouro = logradouro;
        this.numero = numero;
        this.bairro = bairro;
        this.municipio = municipio;
        this.estado = estado;
        this.latitude = latitude;
        this.longitude = longitude;
        this.tel = tel;
        this.rating = rating;
        this.tipo_id = tipo_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getEndComp() {
        return endComp;
    }

    public void setEndComp(String endComp) {
        this.endComp = endComp;
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

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
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

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getTipo_id() {
        return tipo_id;
    }

    public void setTipo_id(int tipo_id) {
        this.tipo_id = tipo_id;
    }
}
