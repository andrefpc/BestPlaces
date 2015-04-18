package afpcsoft.com.br.bestplaces.Enums;

/**
 * Created by AndréFelipe on 13/04/2015.
 */
public enum BandeiraPostoEnum {
    BR(1, "Posto BR"),
    IPIRANGA(2, "Posto Ipiranga"),
    SHELL(3, "Posto Shell"),
    TEXACO(4, "Posto Texaco"),
    OUTROS(5, "Posto Sem Bandeira"),
    ESSO(6, "Posto Esso"),
    ALE(7, "Posto Ale"),
    REPSOL(8, "Posto Repsol"),
    BANDEIRA_BANCA(9, "Posto com Bandeira Branca");

    private int id;
    private String nome;

    private BandeiraPostoEnum(int id, String nome) {
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
        for(BandeiraPostoEnum bandeiraPostoEnum : BandeiraPostoEnum.values()){
            if(bandeiraPostoEnum.id == id){
                return bandeiraPostoEnum.getNome();
            }
        }
        return "Posto sem bandeira ";
    }
}
