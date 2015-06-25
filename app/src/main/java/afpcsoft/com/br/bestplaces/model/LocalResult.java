package afpcsoft.com.br.bestplaces.model;

import java.util.List;

public class LocalResult {

    public static int MYLOCAL = 1;
    public static int POSTO = 2;
    public static int BAR = 3;
    public static int ESTACIONAMENTO = 4;
    public static int RESTAURANTE = 5;
    public static int HOSPITAL = 6;
    public static int FAST_FOOD = 7;

    private int onPostFlag;
    private MyLocal myLocal;

    public LocalResult() {
    }

    public LocalResult(MyLocal myLocal) {
        this.myLocal = myLocal;
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

}
