package afpcsoft.com.br.bestplaces.service;

import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import afpcsoft.com.br.bestplaces.Utils.DialogUtils;
import afpcsoft.com.br.bestplaces.Utils.HttpUtils;
import afpcsoft.com.br.bestplaces.Utils.StaticValues;
import afpcsoft.com.br.bestplaces.model.Price;

/**
 * Created by AndréFelipe on 27/06/2015.
 */
public class ListPricesTask extends AsyncTask<String, Void, List<Price>> {

    private Context context;
    private Price price;
    private OnPostExecuteListener onPostExecuteListener;

    public ListPricesTask(Context context, Price price, OnPostExecuteListener onPostExecuteListener) {
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

    }

    @Override
    protected List<Price> doInBackground(String... params) {


        String url = "http://ec2-54-153-109-26.us-west-1.compute.amazonaws.com/Service.php?servico=listPrices" ;


        if(price.getType() == StaticValues.GoogleAPIPlaces  && price.getPlaceApiIdString() != null) {
            url += "&type=1&placeApiId=" + price.getPlaceApiIdString();
        }else if(price.getType() == StaticValues.NativePlaces  && price.getNativePlaceId() != 0){
            url += "&type=2&placeId=" + price.getNativePlaceId();
        }

        String result = HttpUtils.makeGetRequest(url);
        List<Price> prices = null;
        if(!result.equals("false")) {

            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<Price>>() {}.getType();
            prices = gson.fromJson(result, listType);
        }

        return prices;
    }

    @Override
    protected void onPostExecute(List<Price> result) {
        super.onPostExecute(result);

        if (onPostExecuteListener != null){
            try {
                onPostExecuteListener.onPostExecute(result);
            } catch (Exception e){
                e.printStackTrace();
            }
        }

    }
}