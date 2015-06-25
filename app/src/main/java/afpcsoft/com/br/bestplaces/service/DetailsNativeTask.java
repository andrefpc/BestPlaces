package afpcsoft.com.br.bestplaces.service;

import android.content.Context;
import android.os.AsyncTask;
import android.text.util.Linkify;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import afpcsoft.com.br.bestplaces.R;
import afpcsoft.com.br.bestplaces.Utils.HttpUtils;
import afpcsoft.com.br.bestplaces.model.Place;

/**
 * Created by AndréFelipe on 23/06/2015.
 */
public class DetailsNativeTask extends AsyncTask<String, Void, Place> {

    private int id;
    private TextView phone;
    private TextView address;
    private TextView website;
    private ImageView imageView;
    private ProgressBar progressBar;
    private Context context;

    private OnPostExecuteListener onPostExecuteListener;

    public DetailsNativeTask(int id, TextView phone, TextView address, TextView website, ImageView imageView, ProgressBar progressBar, Context context, OnPostExecuteListener onPostExecuteListener ) {
        this.id = id;
        this.phone = phone;
        this.address = address;
        this.website = website;
        this.imageView = imageView;
        this.progressBar = progressBar;
        this.context = context;
        this.onPostExecuteListener = onPostExecuteListener;
        if (onPostExecuteListener == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }
    }

    public static interface OnPostExecuteListener{
        void onPostExecute(Place place);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Place doInBackground(String... params) {

        String url = "http://ec2-54-153-109-26.us-west-1.compute.amazonaws.com/Service.php?servico=detailsPlaces&id=" + id;
        String result = HttpUtils.makeGetRequest(url);

        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<Place>>() {}.getType();
        List<Place> places = gson.fromJson(result, listType);
        Place place = places.get(0);

        return place;
    }

    @Override
    protected void onPostExecute(Place place) {
        super.onPostExecute(place);
        if (onPostExecuteListener != null){
            try {
                onPostExecuteListener.onPostExecute(place);
            } catch (Exception e){
                e.printStackTrace();
            }
        }

        String photo = place.getImageBase64();
        String phoneText = place.getPhone();
        String addressText = place.getAddress();
        String webSite = place.getSite();

        if(phoneText != null) {
            phone.setText(phoneText);
            Linkify.addLinks(phone, Linkify.PHONE_NUMBERS);
        }else{
            phone.setText(context.getString(R.string.numero_telefone_indisponivel));
            phone.setVisibility(View.VISIBLE);
        }
        if(addressText != null){
            address.setText(addressText);
        }else{
            address.setText(context.getString(R.string.endereco_indisponivel));
        }

        if(webSite != null){
            website.setText(webSite);
            Linkify.addLinks(website, Linkify.ALL);
        }else{
            website.setText(context.getString(R.string.site_indisponivel));
            website.setVisibility(View.VISIBLE);
        }

        if(photo != null) {
            new LoadBase64ImageTask(imageView, progressBar, photo, context).execute();
        }else{
            progressBar.setVisibility(View.GONE);
            imageView.setVisibility(View.VISIBLE);
            imageView.setImageResource(R.drawable.no_image);
        }
    }
}
