package afpcsoft.com.br.bestplaces.service;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import afpcsoft.com.br.bestplaces.R;
import afpcsoft.com.br.bestplaces.Utils.DialogUtils;
import afpcsoft.com.br.bestplaces.Utils.HttpUtils;
import afpcsoft.com.br.bestplaces.Utils.ImageUtils;
import afpcsoft.com.br.bestplaces.controller.DetailsPreviewActivity;
import afpcsoft.com.br.bestplaces.model.Place;

public class AddPlaceTask extends AsyncTask<String, Void, String> {

    private Context context;
    private Place place;
    private ProgressDialog pd;

    public AddPlaceTask(Context context, Place place) {
        this.context = context;
        this.place = place;
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

        File file = ImageUtils.resizeFile(ImageUtils.photoPath, ImageUtils.photoName);

        String imgBase64 = new ImageUtils().encodeImageBase64(file);

        place.setImageBase64(imgBase64);

        if(place.getAddress() == null) {
            Geocoder geocoder;
            List<Address> addresses;
            geocoder = new Geocoder(context, Locale.getDefault());

            try {
                addresses = geocoder.getFromLocation(place.getLat(), place.getLng(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

                String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                String city = addresses.get(0).getLocality();
                String state = addresses.get(0).getAdminArea();
                String country = addresses.get(0).getCountryName();
                String postalCode = addresses.get(0).getPostalCode();
                String knownName = addresses.get(0).getFeatureName(); // Only if available else return

                place.setAddress(address + " - " + city + " - " + country);

                Log.i("GEOCODE", address + " - " + city + " - " + country);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String url = "http://ec2-54-153-109-26.us-west-1.compute.amazonaws.com/Service.php?servico=insertPlace";

        List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(10);

        nameValuePair.add(new BasicNameValuePair("name", place.getName()));
        nameValuePair.add(new BasicNameValuePair("description", place.getDescription()));
        nameValuePair.add(new BasicNameValuePair("address", place.getAddress()));
        nameValuePair.add(new BasicNameValuePair("lat", String.valueOf(place.getLat())));
        nameValuePair.add(new BasicNameValuePair("lng", String.valueOf(place.getLng())));
        nameValuePair.add(new BasicNameValuePair("phone", place.getPhone()));
        nameValuePair.add(new BasicNameValuePair("type", String.valueOf(place.getType())));
        nameValuePair.add(new BasicNameValuePair("site", place.getSite()));
        nameValuePair.add(new BasicNameValuePair("facebookPage", place.getFacebookPage()));
        nameValuePair.add(new BasicNameValuePair("plusPage", place.getPlusPage()));
        nameValuePair.add(new BasicNameValuePair("photo", imgBase64));

        String result = HttpUtils.makePostRequest(nameValuePair, url);

        Log.i("IMAGE ENCODED", imgBase64);

        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        Log.i("HTTP RESULT", result);

        if(result.equals("\t1")){
            Intent intent = new Intent(context, DetailsPreviewActivity.class);
            intent.putExtra("place", place);
            context.startActivity(intent);
        }else{
            DialogUtils.showErrorSendMessageDialog(context);
        }

        pd.cancel();

        Toast.makeText(context, "Terminou de enviar", Toast.LENGTH_LONG).show();

    }
}
