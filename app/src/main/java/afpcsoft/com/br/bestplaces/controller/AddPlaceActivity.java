package afpcsoft.com.br.bestplaces.controller;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import afpcsoft.com.br.bestplaces.R;
import afpcsoft.com.br.bestplaces.Utils.ImageUtils;
import afpcsoft.com.br.bestplaces.model.Place;
import afpcsoft.com.br.bestplaces.service.AddPlaceTask;

public class AddPlaceActivity extends ActionBarActivity {

    protected static final int CAMERA_PIC_REQUEST = 2500;
    private Intent cameraIntent;
    private File photo;
    private String photoName;

    private EditText nameEditText;
    private EditText descriptionEditText;
    private EditText phoneEditText;
    private EditText siteEditText;
    private EditText socialNetworkEditText;
    private Button addPhoto;
    private Button savePlace;

//    private TextView textPhoto;

    private String type;

    private Switch switchRestaurant;
    private Switch switchParking;
    private Switch switchGasStation;

    private ImageView photoImageView;

    private String fileName;
    private File photoFile;

    private Place place;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_place);

        place = (Place) getIntent().getSerializableExtra("place");

        photoName = ImageUtils.photoName;
        photo = ImageUtils.createSdCardFile(ImageUtils.photoPath, photoName);
        cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo));

        generateLayouts();
    }

    private void generateLayouts() {
        nameEditText = (EditText) findViewById(R.id.nameEditText);
        nameEditText.setFocusable(true);
        descriptionEditText = (EditText) findViewById(R.id.descriptionEditText);
        phoneEditText = (EditText) findViewById(R.id.phoneEditText);
        siteEditText = (EditText) findViewById(R.id.siteEditText);
        socialNetworkEditText = (EditText) findViewById(R.id.socialNetworkEditText);
        savePlace = (Button) findViewById(R.id.savePlace);

        switchRestaurant = (Switch) findViewById(R.id.swichRestaurant);
        switchRestaurant.setFocusable(true);
        switchRestaurant.setChecked(false);
        switchRestaurant.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    switchGasStation.setChecked(false);
                    switchParking.setChecked(false);
                    type = "Restaurante";
                }
            }
        });

        switchParking = (Switch) findViewById(R.id.swichParking);
        switchParking.setChecked(false);
        switchParking.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    switchGasStation.setChecked(false);
                    switchRestaurant.setChecked(false);
                    type = "Estacionamento";
                }
            }
        });

        switchGasStation = (Switch) findViewById(R.id.swichGasStation);
        switchGasStation.setChecked(false);
        switchGasStation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    switchParking.setChecked(false);
                    switchRestaurant.setChecked(false);
                    type = "Posto";
                }
            }
        });

        photoImageView = (ImageView) findViewById(R.id.photoImageView);
        photoImageView.setVisibility(View.VISIBLE);
        addPhoto = (Button) findViewById(R.id.addPhoto);

        photoImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (cameraIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
                }

            }
        });

        addPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (cameraIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
                    Log.i("TESTE", "Entrei no onclick");
                }

            }
        });

        savePlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(nameEditText.getText().toString().equals("")){
                    Toast.makeText(AddPlaceActivity.this, getString(R.string.nameEmpty), Toast.LENGTH_LONG).show();
                    nameEditText.requestFocus();
                }else if(!switchParking.isChecked() && !switchRestaurant.isChecked() && !switchGasStation.isChecked()){
                    Toast.makeText(AddPlaceActivity.this, getString(R.string.typeEmpty), Toast.LENGTH_LONG).show();
                    switchRestaurant.requestFocus();
                }else {
                    place.setName(nameEditText.getText().toString());
                    place.setDescription(descriptionEditText.getText().toString());
                    place.setPhone(phoneEditText.getText().toString());
                    place.setSite(siteEditText.getText().toString());
                    place.setSocialNetwork(socialNetworkEditText.getText().toString());
                    place.setType(type);

                    new AddPlaceTask(AddPlaceActivity.this, place).execute();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        Log.i("TESTE", "Entrei no activityResult");

        if (requestCode == CAMERA_PIC_REQUEST) {
            Log.i("TESTE", "Entrei no requestCode");
            if (resultCode == RESULT_OK) {

                Log.i("TESTE", "Entrei no RESULT_OK");
                addPhoto.setVisibility(View.GONE);
                try {

                    Log.i("TESTE", "Entrei no try");
                    File file = ImageUtils.getSdCardFile(ImageUtils.photoPath, ImageUtils.photoName);
                    Uri uri = Uri.fromFile(file);

                    Bitmap resizedBitmap = (Bitmap) ImageUtils.getResizedImage(uri, 640, 480);

                    Log.i("TESTE", "Criei o bitmap");

                    photoImageView = (ImageView) findViewById(R.id.photoImageView);

                    photoImageView.setImageDrawable(new BitmapDrawable(resizedBitmap));
                    photoImageView.setVisibility(View.VISIBLE);

                    Log.i("TESTE", "Terminei o try");
                }catch (Exception e){
                    e.printStackTrace();
                    Log.i("TESTE", "Entrei no catch: " + e.getMessage());
//                    addPhoto.setVisibility(View.GONE);
//                    textPhoto.setText("Erro ao capturar foto!");
//                    textPhoto.setVisibility(View.VISIBLE);
                }
            } else if (resultCode == RESULT_CANCELED) {
                Log.i("TESTE", "Entrei no RESULT_CANCELED");
                // User cancelled the image capture
            } else {
                Log.i("TESTE", "Entrei no else do requestCode");
                // Image capture failed, advise user
//                addPhoto.setVisibility(View.GONE);
//                textPhoto.setText("Erro ao capturar foto!");
//                textPhoto.setVisibility(View.VISIBLE);
            }
        }else{
            Log.i("TESTE", "Entrei no else do requestCode");
        }



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_place, menu);
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
