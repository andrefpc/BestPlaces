package afpcsoft.com.br.bestplaces.controller;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.InputStream;
import android.net.Uri;

/**
 * Created by AndréFelipe on 17/05/2015.
 */
public class DownloadImageTask extends AsyncTask<String, Integer, Bitmap> {
    ImageView bmImage;
    ProgressBar progressBar;
    String url;
    Uri uri;
    Context context;

    public DownloadImageTask(ImageView bmImage, ProgressBar progressBar, String url) {
        this.bmImage = bmImage;
        this.progressBar = progressBar;
        this.url = url;
    }

    public DownloadImageTask(ImageView bmImage, ProgressBar progressBar, Uri uri, Context context) {
        this.bmImage = bmImage;
        this.progressBar = progressBar;
        this.uri = uri;
        this.context = context;
    }



    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        bmImage.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected Bitmap doInBackground(String... urls) {

        Bitmap bitmap = null;
        try {
            if(uri == null) {
                InputStream in = new java.net.URL(url).openStream();
                bitmap = BitmapFactory.decodeStream(in);
            }else{
                bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
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