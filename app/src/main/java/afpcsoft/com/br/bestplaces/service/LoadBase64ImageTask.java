package afpcsoft.com.br.bestplaces.service;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.InputStream;

import afpcsoft.com.br.bestplaces.Utils.ImageUtils;

/**
 * Created by AndréFelipe on 23/06/2015.
 */
public class LoadBase64ImageTask extends AsyncTask<String, Integer, Bitmap> {
    ImageView bmImage;
    ProgressBar progressBar;
    String base64;
    Context context;

    public LoadBase64ImageTask(ImageView bmImage, ProgressBar progressBar, String base64, Context context) {
        this.bmImage = bmImage;
        this.progressBar = progressBar;
        this.base64 = base64;
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

        Bitmap bitmap = new ImageUtils().decodeImageBase64(base64);
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
