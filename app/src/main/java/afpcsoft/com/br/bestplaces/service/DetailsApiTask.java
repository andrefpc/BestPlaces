package afpcsoft.com.br.bestplaces.service;

import android.content.Context;
import android.os.AsyncTask;
import android.text.util.Linkify;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;

import afpcsoft.com.br.bestplaces.R;
import afpcsoft.com.br.bestplaces.controller.DownloadImageTask;
import afpcsoft.com.br.bestplaces.model.detailsPlacesApi.DetailsApiResult;
import afpcsoft.com.br.bestplaces.model.detailsPlacesApi.PhotoDetails;
import afpcsoft.com.br.bestplaces.model.placesApi.PlacesApiResult;

/**
 * Created by AndréFelipe on 17/05/2015.
 */
public class DetailsApiTask extends AsyncTask<String, Void, DetailsApiResult> {

    private String placeId;
    private TextView phone;
    private TextView address;
    private TextView url;
    private ImageView imageView;
    private ProgressBar progressBar;
    private Context context;

    public DetailsApiTask(String placeId, TextView phone, TextView address, TextView url, ImageView imageView, ProgressBar progressBar, Context context ) {
        this.placeId = placeId;
        this.phone = phone;
        this.address = address;
        this.url = url;
        this.imageView = imageView;
        this.progressBar = progressBar;
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected DetailsApiResult doInBackground(String... params) {


        String url = "https://maps.googleapis.com/maps/api/place/details/json";
        url += "?placeid="+placeId;
        url += "&key=AIzaSyBxyXNj5Pm-ArbAk_0LzIgZfWovgLUPLUM";

        Log.i("PLACES_API", url);

        DetailsApiResult detailsApiResult = new DetailsApiResult();

        InputStream is = null;
        try {
            is = new URL(url).openStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            Log.i("PLACES_API", jsonText);
            Gson gson = new Gson();
            detailsApiResult = gson.fromJson(jsonText,DetailsApiResult.class);
        } catch (Exception e) {
            e.printStackTrace();
        }


        return detailsApiResult;
    }

    @Override
    protected void onPostExecute(DetailsApiResult detailsApiResult) {
        super.onPostExecute(detailsApiResult);

        List<PhotoDetails> photos = detailsApiResult.getResult().getPhotos();
        String phoneText = detailsApiResult.getResult().getFormattedPhoneNumber();
        String addressText = detailsApiResult.getResult().getFormattedAddress();
        String urlText = detailsApiResult.getResult().getUrl();

        if(phoneText != null) {
            phone.setText(phoneText);
        }else{
            phone.setText(context.getString(R.string.numero_telefone_indisponivel));
        }
        if(addressText != null){
            address.setText(addressText);
        }else{
            address.setText(context.getString(R.string.endereco_indisponivel));
        }

        if(urlText != null){
            url.setText(detailsApiResult.getResult().getUrl());
            Linkify.addLinks(url, Linkify.ALL);
        }else{
            url.setText(context.getString(R.string.perfil_online_indisponivel));
        }

        if(photos != null) {
            String urldisplay = "https://maps.googleapis.com/maps/api/place/photo";
            urldisplay += "?maxwidth=400";
            urldisplay += "&photoreference="+photos.get(0).getPhotoReference();
            urldisplay += "&key=AIzaSyBxyXNj5Pm-ArbAk_0LzIgZfWovgLUPLUM";
            new DownloadImageTask(imageView, progressBar, urldisplay).execute();
        }else{
            progressBar.setVisibility(View.GONE);
            imageView.setVisibility(View.VISIBLE);
            imageView.setImageResource(R.drawable.no_image);
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

