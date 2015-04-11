package afpcsoft.com.br.bestplaces.model;

import java.util.List;

public class LocalResult {

    public static int MYLOCAL = 1;
    public static int POSTO = 2;
    public static int BAR = 3;
    public static int ESTACIONAMENTO = 4;
    public static int RESTAURANTE = 5;
    public static int HOSPITAL = 6;

    private int onPostFlag;
    private MyLocal myLocal;
    private List<Posto> postos;
    private List<Bar> bares;
    private List<Estacionamento> estacionamentos;
    private List<Restaurante> restaurantes;
    private List<Hospital> hospitais;

    public LocalResult() {
    }

    public LocalResult(int onPostFlag, MyLocal myLocal, List<Posto> postos, List<Bar> bares, List<Estacionamento> estacionamentos, List<Restaurante> restaurantes, List<Hospital> hospitais) {
        this.onPostFlag = onPostFlag;
        this.myLocal = myLocal;
        this.postos = postos;
        this.bares = bares;
        this.estacionamentos = estacionamentos;
        this.restaurantes = restaurantes;
        this.hospitais = hospitais;
    }

    public int getOnPostFlag() {
        return onPostFlag;
    }

    public void setOnPostFlag(int onPostFlag) {
        this.onPostFlag = onPostFlag;
    }

    public MyLocal getMyLocal() {
        return myLocal;
    }

    public void setMyLocal(MyLocal myLocal) {
        this.myLocal = myLocal;
    }

    public List<Posto> getPostos() {
        return postos;
    }

    public void setPostos(List<Posto> postos) {
        this.postos = postos;
    }

    public List<Bar> getBares() {
        return bares;
    }

    public void setBares(List<Bar> bares) {
        this.bares = bares;
    }

    public List<Estacionamento> getEstacionamentos() {
        return estacionamentos;
    }

    public void setEstacionamentos(List<Estacionamento> estacionamentos) {
        this.estacionamentos = estacionamentos;
    }

    public List<Restaurante> getRestaurantes() {
        return restaurantes;
    }

    public void setRestaurantes(List<Restaurante> restaurantes) {
        this.restaurantes = restaurantes;
    }

    public List<Hospital> getHospitais() {
        return hospitais;
    }

    public void setHospitais(List<Hospital> hospitais) {
        this.hospitais = hospitais;
    }

}
