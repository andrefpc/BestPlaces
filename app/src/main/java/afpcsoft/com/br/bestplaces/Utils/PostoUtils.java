package afpcsoft.com.br.bestplaces.Utils;

import afpcsoft.com.br.bestplaces.R;

/**
 * Created by AndréFelipe on 31/03/2015.
 */
public class PostoUtils {

    public  static int getBandeiraResourceId(int bandeiraId){
        switch (bandeiraId){
            case 1:
                return R.drawable.br;
            case 2:
                return R.drawable.ipiranga;
            case 3:
                return R.drawable.shell;
            case 4:
                return R.drawable.texaco;
            case 6:
                return R.drawable.esso;
            case 7:
                return R.drawable.ale;
            case 8:
                return R.drawable.repsol;
            case 9:
                return R.drawable.branca;
            default:
                return R.drawable.no_flag;
        }
    }
}
