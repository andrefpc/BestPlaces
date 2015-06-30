package afpcsoft.com.br.bestplaces.service;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import afpcsoft.com.br.bestplaces.R;
import afpcsoft.com.br.bestplaces.Utils.DialogUtils;
import afpcsoft.com.br.bestplaces.Utils.HttpUtils;
import afpcsoft.com.br.bestplaces.Utils.StaticValues;
import afpcsoft.com.br.bestplaces.model.Place;
import afpcsoft.com.br.bestplaces.model.Price;

/**
 * Created by Andr�Felipe on 25/06/2015.
 */
public class AddPriceTask extends AsyncTask<String, Void, List<Price>> {

    private Context context;
    private Price price;
    private ProgressDialog pd;
    private OnPostExecuteListener onPostExecuteListener;

    public AddPriceTask(Context context, Price price, OnPostExecuteListener onPostExecuteListener) {
        this.onPostExecuteListener = onPostExecuteListener;
        if (onPostExecuteListener == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }
        this.context = context;
        this.price = price;
    }

    public static interface OnPostExecuteListener{
        void onPostExecute(List<Price> result);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pd = new ProgressDialog(context);
        pd.setMessage(context.getString(R.string.messageDialog));
        pd.show();
    }

    @Override
    protected List<Price> doInBackground(String... params) {


        String url = "http://ec2-54-153-109-26.us-west-1.compute.amazonaws.com/Service.php?servico=insertPrice&type=" + price.getType() ;

        List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(4);

        nameValuePair.add(new BasicNameValuePair("name", price.getName()));
        nameValuePair.add(new BasicNameValuePair("description", price.getDescription()));
        nameValuePair.add(new BasicNameValuePair("price", price.getPrice()));
        if(price.getType() == StaticValues.GoogleAPIPlaces) {
            nameValuePair.add(new BasicNameValuePair("placeApiId", price.getPlaceApiIdString()));
        }else {
            nameValuePair.add(new BasicNameValuePair("placeId", String.valueOf(price.getNativePlaceId())));
        }

        String result = HttpUtils.makePostRequest(nameValuePair, url);
        List<Price> prices = null;
        if(!result.equals("false")) {
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<Price>>() {
            }.getType();
            prices = gson.fromJson(result, listType);
        }

        return prices;
    }

    @Override
    protected void onPostExecute(List<Price> result) {
        super.onPostExecute(result);
        Log.i("HTTP RESULT", result.toString());

        if (onPostExecuteListener != null){
            try {
                onPostExecuteListener.onPostExecute(result);
            } catch (Exception e){
                e.printStackTrace();
            }
        }

        if(result == null){
            DialogUtils.showErrorSendMessageDialog(context);
        }

        pd.cancel();

    }
}
