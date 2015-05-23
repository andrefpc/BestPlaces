package afpcsoft.com.br.bestplaces.controller;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.SupportStreetViewPanoramaFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.StreetViewPanoramaLocation;

import afpcsoft.com.br.bestplaces.R;


public class StreetViewActivity extends BaseActivity
        implements GoogleMap.OnMarkerDragListener, StreetViewPanorama.OnStreetViewPanoramaChangeListener {

    private StreetViewPanorama svp;
    private GoogleMap mMap;
    private Marker marker;

    // George St, Sydney
    private LatLng local;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_street_view);

        Intent intent = getIntent();
        Double lat = intent.getDoubleExtra("lat", 0);
        Double lng = intent.getDoubleExtra("lng", 0);

        local = new LatLng(lat,lng);

        initialize();
        leftMenu();

        setUpStreetViewPanoramaIfNeeded(savedInstanceState);
    }

    private void setUpStreetViewPanoramaIfNeeded(Bundle savedInstanceState) {
        if (svp == null) {
            svp = ((SupportStreetViewPanoramaFragment) getSupportFragmentManager().findFragmentById(R.id.streetviewpanorama)).getStreetViewPanorama();

            if (svp != null) {
                if (savedInstanceState == null) {
                    svp.setPosition(local);
                }
                svp.setOnStreetViewPanoramaChangeListener(this);
            }
        }
    }

    @Override
    public void onStreetViewPanoramaChange(StreetViewPanoramaLocation location) {
        if (location != null) {
            //marker.setPosition(location.position);
        }else{
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage("Não possui StreetView no local selecionado!")
                    .setCancelable(false)
                    .setPositiveButton("Ok",
                            new DialogInterface.OnClickListener(){
                                public void onClick(DialogInterface dialog, int id){
                                    Intent intent = new Intent(StreetViewActivity.this, MapsActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            });

            AlertDialog alert = alertDialogBuilder.create();
            alert.show();
        }
    }



    @Override
    public void onMarkerDragStart(Marker marker) {
    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        svp.setPosition(marker.getPosition(), 150);
    }

    @Override
    public void onMarkerDrag(Marker marker) {
    }
}