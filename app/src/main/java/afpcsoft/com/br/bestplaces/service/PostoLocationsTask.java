package afpcsoft.com.br.bestplaces.service;


import android.app.ProgressDialog;
import android.content.Context;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.text.Html;

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
import java.util.Locale;

import afpcsoft.com.br.bestplaces.model.LocalResult;
import afpcsoft.com.br.bestplaces.model.Posto;

public class PostoLocationsTask extends AsyncTask<String, Void, LocalResult> {
    private Context context;
    private ProgressDialog dialog;
    private GoogleMap mMap;
    private MarkerOptions myLocationMarker;

    private String providerAsync;
    private LocationManager locationManagerAsync;
    double   latAsync=0.0;
    double lonAsync=0.0;
    String thikanaAsync="Scan sms for location";

    String AddressAsync="";
    Geocoder GeocoderAsync;
    Location location;

    private OnPostExecuteListener  onPostExecuteListener;


    private static String SUCESSO = "sucesso";
    private static String ERRO = "erro";


    public PostoLocationsTask(Context context, OnPostExecuteListener onPostExecuteListener) {
        this.context = context;
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

        String url = "http://ec2-54-153-109-26.us-west-1.compute.amazonaws.com/";

        LocalResult localResult = new LocalResult();
        List<Posto> postosList = new ArrayList<Posto>();

        InputStream is = null;
        try {
            is = new URL(url).openStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<Posto>>() {}.getType();
            postosList = gson.fromJson(jsonText,listType);
        } catch (Exception e) {
            e.printStackTrace();
        }

        localResult.setOnPostFlag(LocalResult.POSTO);
        localResult.setPostos(postosList);

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
