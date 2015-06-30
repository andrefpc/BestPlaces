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
import afpcsoft.com.br.bestplaces.model.Place;
import afpcsoft.com.br.bestplaces.model.PlaceApiEdited;
import afpcsoft.com.br.bestplaces.model.Price;

/**
 * Created by AndréFelipe on 25/06/2015.
 */
public class DetailsApiEditedTask extends AsyncTask<String, Void, PlaceApiEdited> {

    private Context context;
    private PlaceApiEdited placeApiEdited;
    private ProgressDialog pd;
    private OnPostExecuteListener onPostExecuteListener;

    public DetailsApiEditedTask(Context context, PlaceApiEdited placeApiEdited, OnPostExecuteListener onPostExecuteListener) {
        this.onPostExecuteListener = onPostExecuteListener;
        if (onPostExecuteListener == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }
        this.context = context;
        this.placeApiEdited = placeApiEdited;
    }

    public static interface OnPostExecuteListener{
        void onPostExecute(PlaceApiEdited result);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected PlaceApiEdited doInBackground(String... params) {


        String url = "http://ec2-54-153-109-26.us-west-1.compute.amazonaws.com/Service.php?servico=detailsPlacesApi&placesApiId="+ placeApiEdited.getPlaceApiId();

        Log.i("URL %%%%%%", url);

        String result = HttpUtils.makeGetRequest(url);
        Log.i("RESULT %%%%%%", result);

        PlaceApiEdited newPlaceApiEdited = null;
        if(!result.equals("false")) {
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<PlaceApiEdited>>() {
            }.getType();
            List<PlaceApiEdited> placesApiEditedResult = gson.fromJson(result, listType);
            if(!placesApiEditedResult.isEmpty()) {
                newPlaceApiEdited = placesApiEditedResult.get(0);
            }
        }

        return newPlaceApiEdited;
    }

    @Override
    protected void onPostExecute(PlaceApiEdited result) {
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
