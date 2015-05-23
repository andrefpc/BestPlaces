package afpcsoft.com.br.bestplaces.controller;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.InputStream;

/**
 * Created by AndréFelipe on 17/05/2015.
 */
public class DownloadImageTask extends AsyncTask<String, Integer, Bitmap> {
    ImageView bmImage;
    ProgressBar progressBar;
    String imageReference;

    public DownloadImageTask(ImageView bmImage, ProgressBar progressBar, String imageReference) {
        this.bmImage = bmImage;
        this.progressBar = progressBar;
        this.imageReference = imageReference;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        bmImage.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected Bitmap doInBackground(String... urls) {
        String urldisplay = "https://maps.googleapis.com/maps/api/place/photo";
        urldisplay += "?maxwidth=400";
        urldisplay += "&photoreference="+imageReference;
        urldisplay += "&key=AIzaSyBxyXNj5Pm-ArbAk_0LzIgZfWovgLUPLUM";


        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mIcon11;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        if (progressBar != null) {
            progressBar.setProgress(values[0]);
        }
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        super.onPostExecute(result);
        bmImage.setImageBitmap(result);

        bmImage.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }
}