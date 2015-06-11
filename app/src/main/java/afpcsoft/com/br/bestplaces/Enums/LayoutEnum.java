package afpcsoft.com.br.bestplaces.Enums;

/**
 * Created by AndréFelipe on 20/04/2015.
 */
public enum LayoutEnum {
    LAYOUT11(11, "Layout11"),
    LAYOUT12(12, "Layout12"),
    LAYOUT13(13, "Layout13"),
    LAYOUT14(14, "Layout14"),
    LAYOUT15(15, "Layout15"),
    LAYOUT16(16, "Layout16"),
    LAYOUT17(17, "Layout17"),
    LAYOUT18(18, "Layout18"),
    LAYOUT21(21, "Layout21"),
    LAYOUT22(22, "Layout22"),
    LAYOUT23(23, "Layout23"),
    LAYOUT24(24, "Layout24"),
    LAYOUT25(25, "Layout25"),
    LAYOUT26(26, "Layout26"),
    LAYOUT27(27, "Layout27"),
    LAYOUT28(28, "Layout28"),
    LAYOUT31(31, "Layout31"),
    LAYOUT32(32, "Layout32"),
    LAYOUT33(33, "Layout33"),
    LAYOUT34(34, "Layout34"),
    LAYOUT35(35, "Layout35"),
    LAYOUT36(36, "Layout36"),
    LAYOUT37(37, "Layout37"),
    LAYOUT38(38, "Layout38"),
    LAYOUT41(41, "Layout41"),
    LAYOUT42(42, "Layout42"),
    LAYOUT43(43, "Layout43"),
    LAYOUT44(44, "Layout44"),
    LAYOUT45(45, "Layout45"),
    LAYOUT46(46, "Layout46"),
    LAYOUT47(47, "Layout47"),
    LAYOUT48(48, "Layout48");

    private int id;
    private String nome;

    private LayoutEnum(int id, String nome) {
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
        for(LayoutEnum layoutEnum : LayoutEnum.values()){
            if(layoutEnum.id == id){
                return layoutEnum.getNome();
            }
        }
        return "N";
    }
}
