package afpcsoft.com.br.bestplaces.model;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

import java.io.Serializable;

public class Posto implements Serializable, ClusterItem {

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
    private int gas;
    private String tel;
    private int bandeira_id;
    private int rating;
    private String preco_gas;
    private String preco_gasolina;
    private String preco_alcool;
    private String preco_disel;
    private LatLng mPosition;

    public Posto() {
    }

    public Posto(int id, String nome, String descricao, String endComp, String logradouro, String numero, String bairro, String municipio, String estado, Double latitude, Double longitude, int gas, String tel, int bandeira_id, int rating, String preco_gas, String preco_gasolina, String preco_alcool, String preco_disel) {
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
        this.gas = gas;
        this.tel = tel;
        this.bandeira_id = bandeira_id;
        this.rating = rating;
        this.preco_gas = preco_gas;
        this.preco_gasolina = preco_gasolina;
        this.preco_alcool = preco_alcool;
        this.preco_disel = preco_disel;
        mPosition = new LatLng(latitude, longitude);
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

    public int getGas() {
        return gas;
    }

    public void setGas(int gas) {
        this.gas = gas;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public int getBandeira_id() {
        return bandeira_id;
    }

    public void setBandeira_id(int bandeira_id) {
        this.bandeira_id = bandeira_id;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getPreco_gas() {
        return preco_gas;
    }

    public void setPreco_gas(String preco_gas) {
        this.preco_gas = preco_gas;
    }

    public String getPreco_gasolina() {
        return preco_gasolina;
    }

    public void setPreco_gasolina(String preco_gasolina) {
        this.preco_gasolina = preco_gasolina;
    }

    public String getPreco_alcool() {
        return preco_alcool;
    }

    public void setPreco_alcool(String preco_alcool) {
        this.preco_alcool = preco_alcool;
    }

    public String getPreco_disel() {
        return preco_disel;
    }

    public void setPreco_disel(String preco_disel) {
        this.preco_disel = preco_disel;
    }

    @Override
    public LatLng getPosition() {
        return mPosition;
    }
}
