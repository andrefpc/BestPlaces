package afpcsoft.com.br.bestplaces.service;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

import afpcsoft.com.br.bestplaces.model.placesApi.PlacesApiResult;

/**
 * Created by AndréFelipe on 16/05/2015.
 */
public class PlacesApiTask extends AsyncTask<String, Void, PlacesApiResult> {

    private Context context;
    private double lat;
    private double lng;
    private String type;
    private OnPostExecuteListener onPostExecuteListener;

    public PlacesApiTask(Context context, OnPostExecuteListener onPostExecuteListener, double lat, double lng, String type) {
        this.context = context;
        this.lat = lat;
        this.lng = lng;
        this.type = type;
        this.onPostExecuteListener = onPostExecuteListener;
        if (onPostExecuteListener == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }
    }

    public static interface OnPostExecuteListener{
        void onPostExecute(PlacesApiResult placesApiResult);
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected PlacesApiResult doInBackground(String... params) {

        String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json";
        url += "?location="+lat+","+lng;
        url += "&radius=5000";
        url += "&types="+type;
        url += "&key=AIzaSyBxyXNj5Pm-ArbAk_0LzIgZfWovgLUPLUM";


        Log.i("PLACES_API", url);

        PlacesApiResult placesApiResult = new PlacesApiResult();

        InputStream is = null;
        try {
            is = new URL(url).openStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            Log.i("PLACES_API", jsonText);
            Gson gson = new Gson();
            placesApiResult = gson.fromJson(jsonText,PlacesApiResult.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        placesApiResult.setType(type);

        return placesApiResult;
    }

    @Override
    protected void onPostExecute(PlacesApiResult placesApiResult) {
        super.onPostExecute(placesApiResult);
        if (onPostExecuteListener != null){
            try {
                onPostExecuteListener.onPostExecute(placesApiResult);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }
}
