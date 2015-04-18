package afpcsoft.com.br.bestplaces.Enums;

/**
 * Created by AndréFelipe on 13/04/2015.
 */
public enum FastFoodTypeEnum {

    MC_DONALD(1, "Mc Donald's Fast Foods"),
    BOBS(2, "Bob's Fast Foods"),
    SUBWAY(3, "Subway Fast Foods"),
    HABIBS(4, "Habib's Fast Foods"),
    BURGUER_KING(5, "Burger King Fast Foods"),
    GIRAFFAS(6, "Giraffas Fast Foods"),
    OUTROS(7, "Demais Fast Foods");

    private int id;
    private String nome;

    private FastFoodTypeEnum(int id, String nome) {
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
        for(FastFoodTypeEnum fastFoodTypeEnum : FastFoodTypeEnum.values()){
            if(fastFoodTypeEnum.id == id){
                return fastFoodTypeEnum.getNome();
            }
        }
        return "Demais Fast Foods ";
    }
}
