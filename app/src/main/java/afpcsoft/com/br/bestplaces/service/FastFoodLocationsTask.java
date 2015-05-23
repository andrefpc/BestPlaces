package afpcsoft.com.br.bestplaces.service;

import android.app.ProgressDialog;
import android.content.Context;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import afpcsoft.com.br.bestplaces.model.FastFood;
import afpcsoft.com.br.bestplaces.model.LocalResult;

public class FastFoodLocationsTask extends AsyncTask<String, Void, LocalResult> {
    private Context context;
    private ProgressDialog dialog;
    private GoogleMap mMap;
    private MarkerOptions myLocationMarker;

    private String providerAsync;
    private LocationManager locationManagerAsync;
    double   latAsync=0.0;
    double lonAsync=0.0;

    String AddressAsync="";
    Geocoder GeocoderAsync;
    Location location;

    double lat;
    double lng;

    private OnPostExecuteListener  onPostExecuteListener;


    private static String SUCESSO = "sucesso";
    private static String ERRO = "erro";


    public FastFoodLocationsTask(Context context, OnPostExecuteListener onPostExecuteListener, double lat, double lng) {
        this.context = context;
        this.lat = lat;
        this.lng = lng;
        this.onPostExecuteListener = onPostExecuteListener;
        if (onPostExecuteListener == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }
//        gps = new GPSTracker(context);
    }

    public static interface OnPostExecuteListener{
        void onPostExecute(LocalResult localResult);
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected LocalResult doInBackground(String... params) {

        String url = "http://ec2-54-153-109-26.us-west-1.compute.amazonaws.com/fastfood.php?lat="+lat+"&lng="+lng;

        System.out.println(url);
        LocalResult localResult = new LocalResult();
        List<FastFood> fastFoodList = new ArrayList<FastFood>();

        InputStream is = null;
        try {
            is = new URL(url).openStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<FastFood>>() {}.getType();
            fastFoodList = gson.fromJson(jsonText,listType);
        } catch (Exception e) {
            e.printStackTrace();
        }

        localResult.setOnPostFlag(LocalResult.FAST_FOOD);
        localResult.setFastFoods(fastFoodList);

        return localResult;
    }

    @Override
    protected void onPostExecute(LocalResult localResult) {
        super.onPostExecute(localResult);
        if (onPostExecuteListener != null){
            try {
                onPostExecuteListener.onPostExecute(localResult);
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

