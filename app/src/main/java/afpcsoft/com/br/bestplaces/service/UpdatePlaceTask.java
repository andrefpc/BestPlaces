package afpcsoft.com.br.bestplaces.service;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import afpcsoft.com.br.bestplaces.R;
import afpcsoft.com.br.bestplaces.Utils.DialogUtils;
import afpcsoft.com.br.bestplaces.Utils.HttpUtils;
import afpcsoft.com.br.bestplaces.Utils.StaticValues;

/**
 * Created by AndréFelipe on 29/06/2015.
 */
public class UpdatePlaceTask extends AsyncTask<String, Void, String> {

    private Context context;
    private int type;
    private int item;
    private int placeId;
    private String placeApiId;
    private String itemStr;
    private ProgressDialog pd;
    private OnPostExecuteListener onPostExecuteListener;

    public UpdatePlaceTask(Context context, int type, int item, int placeId, String placeApiId, String itemStr, OnPostExecuteListener onPostExecuteListener) {
        this.onPostExecuteListener = onPostExecuteListener;
        if (onPostExecuteListener == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }
        this.context = context;
        this.type = type;
        this.item = item;
        this.placeId = placeId;
        this.placeApiId = placeApiId;
        this.itemStr = itemStr;
    }

    public static interface OnPostExecuteListener{
        void onPostExecute(String result);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pd = new ProgressDialog(context);
        pd.setMessage(context.getString(R.string.messageDialog));
        pd.show();
    }

    @Override
    protected String doInBackground(String... params) {

        String url = "http://ec2-54-153-109-26.us-west-1.compute.amazonaws.com/Service.php?servico=";

        if(type == StaticValues.GoogleAPIPlaces){
            if(item == StaticValues.PHONE){
                url += "updateApiPhone&placeApiId=" + placeApiId + "&phone=";
            }else if(item == StaticValues.SITE){
                url += "updateApiSite&placeApiId=" + placeApiId + "&site=";
            }else if(item == StaticValues.FACE){
                url += "updateApiFacebook&placeApiId=" + placeApiId + "&facebookPage=";
            }
        }else if(type == StaticValues.NativePlaces){
            if(item == StaticValues.PHONE){
                url += "updateNativePhone&placeId=" + placeId + "&phone=";
            }else if(item == StaticValues.SITE){
                url += "updateNativeSite&placeId=" + placeId + "&site=";
            }else if(item == StaticValues.FACE){
                url += "updateNativeFacebookPage&placeId=" + placeId + "&facebookPage=";
            }else if(item == StaticValues.PLUS){
                url += "updateNativePlusPage&placeId=" + placeId + "&plusPage=";
            }
        }

        url += itemStr;

        Log.i("$$$$$$ URL", url);

        String result = HttpUtils.makeGetRequest(url);

        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        Log.i("HTTP RESULT", result.toString());

        if (onPostExecuteListener != null){
            try {
                onPostExecuteListener.onPostExecute(result);
            } catch (Exception e){
                e.printStackTrace();
            }
        }


        pd.cancel();
        if(result.equals("false")){
            DialogUtils.showErrorSendMessageDialog(context);
        }else {

            String message = "";
            if (item == StaticValues.PHONE) {
                message = context.getString(R.string.sucessUpdatePhone);
            } else if (item == StaticValues.SITE) {
                message = context.getString(R.string.sucessUpdateSite);
            } else if (item == StaticValues.FACE) {
                message = context.getString(R.string.sucessUpdateFace);
            } else if (item == StaticValues.PLUS) {
                message = context.getString(R.string.sucessUpdatePlus);
            }

            Toast.makeText(context, message, Toast.LENGTH_LONG).show();

        }

    }
}