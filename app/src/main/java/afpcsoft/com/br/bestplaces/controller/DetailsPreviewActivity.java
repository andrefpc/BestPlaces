package afpcsoft.com.br.bestplaces.controller;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import afpcsoft.com.br.bestplaces.R;
import afpcsoft.com.br.bestplaces.Utils.ImageUtils;
import afpcsoft.com.br.bestplaces.model.Place;

public class DetailsPreviewActivity extends ActionBarActivity {

    private Place place;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_preview);

        place = (Place) getIntent().getSerializableExtra("place");

        TextView textViewName = (TextView) findViewById(R.id.name);
        textViewName.setText(place.getName());
        TextView textViewPhone = (TextView) findViewById(R.id.phone);
        textViewPhone.setText(place.getPhone());
        TextView textViewAddress = (TextView) findViewById(R.id.address);
        textViewAddress.setText(place.getAddress());
        TextView textViewUrl = (TextView) findViewById(R.id.site);
        textViewUrl.setText(place.getSite());
        TextView textViewTags = (TextView) findViewById(R.id.type);
        textViewTags.setText(place.getType());

        Bitmap bitmap = new ImageUtils().decodeImageBase64(place.getImageBase64());
        bitmap = ImageUtils.rotateBitmap(bitmap);
//        new ImageUtils().decodeImageBase64(place.getImageBase64());

        ImageView imageView = (ImageView) findViewById(R.id.photo);
        if(bitmap != null) {
            imageView.setImageBitmap(bitmap);
        }
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ScrollView scrollView = (ScrollView) findViewById(R.id.scrollView);
                scrollView.setVisibility(View.INVISIBLE);
                ImageView expandImageVisibility = (ImageView) findViewById(R.id.img_expand);
                expandImageVisibility.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        v.setVisibility(View.GONE);
                        scrollView.setVisibility(View.VISIBLE);
                    }
                });
                expandImageVisibility.setVisibility(View.VISIBLE);
                ImageView expandImage = (ImageView) findViewById(v.getId());
                Drawable d = expandImage.getDrawable();
                Bitmap bitmap = Bitmap.createBitmap(d.getIntrinsicWidth(), d.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                d.draw(canvas);
                expandImageVisibility.setImageBitmap(bitmap);

                DisplayMetrics metrics = new DisplayMetrics();
//                    InfosActivity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
                expandImageVisibility.setMinimumHeight(metrics.heightPixels);
                expandImageVisibility.setMinimumWidth(metrics.widthPixels);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_details_preview, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
