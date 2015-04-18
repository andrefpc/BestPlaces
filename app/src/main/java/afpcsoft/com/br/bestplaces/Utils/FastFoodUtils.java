package afpcsoft.com.br.bestplaces.Utils;

import afpcsoft.com.br.bestplaces.R;

public class FastFoodUtils {

    public  static int getTipoResourceId(int tipoId){
        switch (tipoId){
            case 1:
                return R.drawable.mc_donalds;
            case 2:
                return R.drawable.bobs;
            case 3:
                return R.drawable.subway;
            case 4:
                return R.drawable.habibs;
            case 5:
                return R.drawable.burger_king;
            case 6:
                return R.drawable.giraffas;
            default:
                return R.drawable.other_ff;
        }
    }
}
