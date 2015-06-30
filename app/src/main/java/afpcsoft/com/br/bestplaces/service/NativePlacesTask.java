package afpcsoft.com.br.bestplaces.service;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import afpcsoft.com.br.bestplaces.R;
import afpcsoft.com.br.bestplaces.Utils.DialogUtils;
import afpcsoft.com.br.bestplaces.Utils.HttpUtils;
import afpcsoft.com.br.bestplaces.model.Place;

/**
 * Created by AndréFelipe on 18/06/2015.
 */
public class NativePlacesTask extends AsyncTask<String, Void, List<Place>> {

    private Context context;
    private ProgressDialog pd;
    private int type;
    private OnPostExecuteListener onPostExecuteListener;

    public NativePlacesTask(Context context, OnPostExecuteListener onPostExecuteListener, int type) {
        this.context = context;
        this.type = type;
        this.onPostExecuteListener = onPostExecuteListener;
        if (onPostExecuteListener == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }
    }

    public static interface OnPostExecuteListener{
        void onPostExecute(List<Place> result);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected List<Place> doInBackground(String... params) {

        String url = "http://ec2-54-153-109-26.us-west-1.compute.amazonaws.com/Service.php?servico=listPlaces&type=" + type;
        String result = HttpUtils.makeGetRequest(url);

        List<Place> place = null;
        if(!result.equals("false")) {
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<Place>>() {}.getType();
            place = gson.fromJson(result, listType);
        }

        return place;
    }

    @Override
    protected void onPostExecute(List<Place> result) {
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