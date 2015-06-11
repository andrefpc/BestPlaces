package afpcsoft.com.br.bestplaces.Enums;

/**
 * Created by AndréFelipe on 20/04/2015.
 */
public enum ViewEnum {
    ZOOM_IN(1, "ZoomIn"),
    ZOOM_OUT(2, "ZoomOut"),
    MY_LOCAL(3, "MyLocal"),
    REFRESH(4, "Refresh"),
    SEARCH(5, "Search"),
    SETTINGS(6, "Settings");


    private int id;
    private String nome;

    private ViewEnum(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public static String getNomeById(int id){
        for(ViewEnum viewEnum : ViewEnum.values()){
            if(viewEnum.id == id){
                return viewEnum.getNome();
            }
        }
        return "N";
    }
}
