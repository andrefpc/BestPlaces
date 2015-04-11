package afpcsoft.com.br.bestplaces.controller;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import afpcsoft.com.br.bestplaces.R;
import afpcsoft.com.br.bestplaces.Utils.PostoUtils;
import afpcsoft.com.br.bestplaces.model.LocalResult;
import afpcsoft.com.br.bestplaces.model.MyLocal;
import afpcsoft.com.br.bestplaces.model.Posto;
import afpcsoft.com.br.bestplaces.service.MyLocation;
import afpcsoft.com.br.bestplaces.service.PostoLocationsTask;
import afpcsoft.com.br.bestplaces.service.UserLocationTask;

public class MapsActivity extends BaseActivity implements GoogleMap.OnCameraChangeListener, UserLocationTask.OnPostExecuteListener, PostoLocationsTask.OnPostExecuteListener {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private MarkerOptions myLocationMarker;
    private ImageView imageBandeira;
    private TextView nameInfoWindow ;
    private TextView localInfoWindow;
    private TextView phoneInfoWindow;
    private ImageView streetViewInfoWindow;
    private Button goToInfoWindow;
    private String urlInfoWindow;
    private ViewGroup viewGroup;
    private ImageView refresh;
    private RotateAnimation rotation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        myLocationMarker = new MarkerOptions();
        myLocationMarker.title("You are here");
        myLocationMarker.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_user));

        rotation= new RotateAnimation(0f,360f, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        rotation.setDuration(600);
        rotation.setRepeatCount(RotateAnimation.INFINITE);

        refresh = (ImageView) findViewById(R.id.refresh);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                refresh.startAnimation(rotation);

                getUserLocation();
            }
        });

        setUpMapIfNeeded();
        initialize();
        leftMenu();
        rightMenu();


    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    @Override
    protected void onStart() {
        super.onStart();
        setUpMapIfNeeded();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        setUpMapIfNeeded();
    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();

            mMap.setOnCameraChangeListener(this);

            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }else{
                getUserLocation();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
//                Intent intent = new Intent(MapsActivity.this, StreetViewActivity.class);
//                intent.putExtra("lat", latLng.latitude);
//                intent.putExtra("lng", latLng.longitude);
//                startActivity(intent);
                drawerLayoutRight.openDrawer(drawerRight);
            }
        });

        getUserLocation();

        performClickInfoWindow();

        // map is a GoogleMap object
    }

    private void performClickInfoWindow() {
        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(final Marker marker) {
                drawerLayoutRight.openDrawer(drawerRight);
                int postoId = 0;
                try {
                    postoId = Integer.parseInt(marker.getSnippet());
                }catch (Exception e){
                    e.printStackTrace();
                }

                ImageView imageStreetView = (ImageView) findViewById(R.id.imageStreetView);
                imageStreetView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MapsActivity.this, StreetViewActivity.class);
                        intent.putExtra("lat", marker.getPosition().latitude);
                        intent.putExtra("lng", marker.getPosition().longitude);
                        startActivity(intent);
                    }
                });
                ImageView imageNavigation = (ImageView) findViewById(R.id.imageNavigation);
                imageNavigation.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Double lat = marker.getPosition().latitude;
                        Double lng = marker.getPosition().longitude;
                        Intent intent = new Intent(Intent.ACTION_VIEW,
                                Uri.parse("http://maps.google.com/maps?daddr=" + lat + "," + lng + ""));
                        startActivity(intent);
                    }
                });
            }
        });
    }

    private void getUserLocation(){
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            refresh.startAnimation(rotation);
            new UserLocationTask(MapsActivity.this, MapsActivity.this).execute();
//            MyLocation.LocationResult locationResult = new MyLocation.LocationResult(){
//                @Override
//                public void gotLocation(Location location){
//                    MyLocal myLocal = new MyLocal();
//                    myLocal.setLocation(location);
//                    generateMyLocalMarker(myLocal);
//
//                    new PostoLocationsTask(MapsActivity.this, MapsActivity.this).execute();
//                }
//            };
//            MyLocation myLocation = new MyLocation();
//            myLocation.getLocation(this, locationResult);
        }else{
            showGPSDisabledAlertToUser();
        }
    }

    private void showGPSDisabledAlertToUser(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Parece que seu GPS está desligado, ative caso queira a localização real, caso não queira você será marcado na localização do seu último login?")
                .setCancelable(false)
                .setPositiveButton("Ativar GPS",
                        new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int id){
                                Intent callGPSSettingIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(callGPSSettingIntent);
                            }
                        });
        alertDialogBuilder.setNegativeButton("Última Localização",
                new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id){
                        dialog.cancel();
                    }
                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    @Override
    public void onPostExecute(LocalResult localResult) {
        if(localResult.getOnPostFlag() == LocalResult.MYLOCAL) {
            new PostoLocationsTask(MapsActivity.this, MapsActivity.this).execute();
            generateMyLocalMarker(localResult.getMyLocal());
        }else if(localResult.getOnPostFlag() == LocalResult.POSTO){
            generatePostoMarkers(localResult.getPostos());
        }else if(localResult.getOnPostFlag() == LocalResult.BAR){

        }else if(localResult.getOnPostFlag() == LocalResult.ESTACIONAMENTO){

        }else if(localResult.getOnPostFlag() == LocalResult.HOSPITAL){

        }else if(localResult.getOnPostFlag() == LocalResult.RESTAURANTE){

        }
        if(rotation.isInitialized()) {
            rotation.cancel();
        }
    }

    private void generateMyLocalMarker(MyLocal myLocal) {
        double latitude = myLocal.getLocation().getLatitude();
        double longitude = myLocal.getLocation().getLongitude();

        while(true) {

            if (latitude != 0.0 || longitude != 0.0) {

                myLocationMarker.position(new LatLng(latitude, longitude));
                mMap.addMarker(myLocationMarker);

                LatLngBounds.Builder builder = new LatLngBounds.Builder();

                builder.include(myLocationMarker.getPosition());

                LatLngBounds bounds = builder.build();

                int padding = 10; // offset from edges of the map in pixels
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, padding);

                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(myLocationMarker.getPosition())      // Sets the center of the map to Mountain View
                        .zoom(13)                   // Sets the zoom
                        .build();

                //mMap.moveCamera(cameraUpdate);
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                break;
            }
        }
    }

    private void generatePostoMarkers( List<Posto> postosList) {
        int index = 0;
        for(final Posto posto : postosList) {
            double latitude = posto.getLatitude();
            double longitude = posto.getLongitude();


            MarkerOptions markerOptions = new MarkerOptions()
                    .position(new LatLng(latitude, longitude))
                    .title(posto.getNome())
                    .snippet(posto.getEndComp());

            int resourceId = PostoUtils.getBandeiraResourceId(posto.getBandeira_id());

            markerOptions.icon(BitmapDescriptorFactory.fromResource(resourceId));

            if (latitude != 0.0 || longitude != 0.0) {
                mMap.addMarker(markerOptions);
                mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                    @Override
                    public View getInfoWindow(final Marker marker) {
                        viewGroup = (ViewGroup) getLayoutInflater().inflate(R.layout.info_window_layout, null);

                        imageBandeira = (ImageView) viewGroup.findViewById(R.id.imageName);

                        nameInfoWindow = (TextView) viewGroup.findViewById(R.id.name);
                        nameInfoWindow.setText(marker.getTitle());
                        localInfoWindow = (TextView) viewGroup.findViewById(R.id.local);
                        localInfoWindow.setText(marker.getSnippet());
//                        phoneInfoWindow = (TextView) viewGroup.findViewById(R.id.phone);
//                        phoneInfoWindow.setText("(21) 3335-4099");
                        return viewGroup;
                    }

                    @Override
                    public View getInfoContents(final Marker marker) {

                        return null;
                    }

                });
            }
            index++;
        }
    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {
        System.out.println("entrei");
        System.out.println(cameraPosition.zoom);
        System.out.println(cameraPosition.target.latitude);
        System.out.println(cameraPosition.target.longitude);
    }
}
