package afpcsoft.com.br.bestplaces.controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;

import com.facebook.Profile;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.Map;

import afpcsoft.com.br.bestplaces.R;
import afpcsoft.com.br.bestplaces.Utils.DialogUtils;
import afpcsoft.com.br.bestplaces.model.Place;
import afpcsoft.com.br.bestplaces.model.placesApi.PlacesApiResult;
import afpcsoft.com.br.bestplaces.model.placesApi.ResultPlaces;
import afpcsoft.com.br.bestplaces.service.DownloadImageTask;
import afpcsoft.com.br.bestplaces.service.PlacesApiTask;

/**
 * Created by AndréFelipe on 17/04/2015.
 */
public class SearchActivity extends BaseActivity implements GoogleMap.OnCameraChangeListener, PlacesApiTask.OnPostExecuteListener {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private MarkerOptions myLocationMarker;
    private ViewGroup viewGroup;

    private static String RESTAURANT = "restaurant";
    private static String PARKING = "parking";
    private static String GAS_STATION = "gas_station";

    PlacesApiResult placesApiResultRestaurant;
    PlacesApiResult placesApiResultParking;
    PlacesApiResult placesApiResultGasStation;

    private Place place;

    private RotateAnimation rotation;

    Map <String, ResultPlaces> resultPlacesMap;


    private ImageButton refresh;
    private ImageButton zoomIn;
    private ImageButton zoomOut;
    private ImageButton location;

    Switch switchRestaurant;
    Switch switchParking;
    Switch switchGasStation;

    private SharedPreferences sharedPreferences;
    private static String FILTER_PREFERENCES = "filterPreferences";

    private ImageView markerOffline;
    private LatLng myLocationLatLng;
    private Button setLocationBtn;
    private Profile profile;
    private LatLng locationSetted;

    CameraPosition cameraPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        LinearLayout button = (LinearLayout) findViewById(R.id.logout_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtils.showLogoutAlertToUser(SearchActivity.this);
            }
        });

        Intent intent = getIntent();
        profile = intent.getParcelableExtra("profile");

        Log.d("profile", profile.getName());

        TextView profileName = (TextView) findViewById(R.id.profileName);
        ImageView profileImage = (ImageView) findViewById(R.id.profileImage);
        ProgressBar loading = (ProgressBar) findViewById(R.id.loading);

        if(profile != null) {
            Bitmap bitmap = null;
            profileName.setText(profile.getName());
            Uri imageUri  = profile.getProfilePictureUri(150, 150);
            Log.i("profile", imageUri.getPath());
            if (imageUri != null) {
                new DownloadImageTask(profileImage, loading, imageUri.toString()).execute();
            } else {
                loading.setVisibility(View.GONE);
                profileImage.setVisibility(View.VISIBLE);
                profileImage.setImageResource(R.drawable.com_facebook_profile_picture_blank_portrait);
            }
        }

        LatLng rioLatLng = new LatLng(-22.066452,-42.9232368);

        cameraPosition = new CameraPosition.Builder()
                .target(rioLatLng)      // Sets the center of the map to Mountain View
                .zoom(5)                   // Sets the zoom
                .build();

        rotation= new RotateAnimation(0f,360f, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        rotation.setDuration(600);

        resultPlacesMap = new HashMap<String, ResultPlaces>();

        rotation= new RotateAnimation(0f,360f, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        rotation.setDuration(600);

        myLocationMarker = new MarkerOptions();
        myLocationMarker.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_user));

        generateFilter();
        generateLayouts();
        setUpMapIfNeeded();
        leftMenu();
        rightMenu();

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        if (drawerToggle.onOptionsItemSelected(item)) {
            drawerLayoutRight.closeDrawer(drawerRight);
            return true;
        }

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }else if(id == R.id.action_search){
            markerOffline.setVisibility(View.VISIBLE);
            setLocationBtn.setVisibility(View.VISIBLE);
            refresh.setVisibility(View.GONE);
            location.setVisibility(View.GONE);
//            zoomIn.setVisibility(View.GONE);
//            zoomOut.setVisibility(View.GONE);

            mMap.clear();
        }else if(id == R.id.action_filter){
            if(drawerLayoutRight.isDrawerOpen(drawerRight)) {
                drawerLayoutRight.closeDrawer(drawerRight);
            }else{
                drawerLayoutRight.openDrawer(drawerRight);
                drawerLayoutLeft.closeDrawer(drawerLeft);
            }
        }else if(id == R.id.action_add){
            Intent intent = new Intent(getApplicationContext(), AddPlaceActivity.class);
            intent.putExtra("place", place);

            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    private void generateFilter() {
        sharedPreferences = getSharedPreferences(FILTER_PREFERENCES, Context.MODE_PRIVATE);

        boolean restaurantSwinch = sharedPreferences.getBoolean(RESTAURANT, true);
        boolean parkingSwinch = sharedPreferences.getBoolean(PARKING, true);
        boolean gasStationSwinch = sharedPreferences.getBoolean(GAS_STATION, true);

        switchRestaurant = (Switch) findViewById(R.id.swichRestaurant);
        if (restaurantSwinch){
            switchRestaurant.setChecked(true);
        }else{
            switchRestaurant.setChecked(false);
        }
        switchRestaurant.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mMap.clear();
                mMap.addMarker(myLocationMarker);
                if (switchParking.isChecked() && placesApiResultParking != null) {
                    GenerateMarkersPlacesAPI(placesApiResultParking);
                }
                if (switchGasStation.isChecked() && placesApiResultGasStation != null) {
                    GenerateMarkersPlacesAPI(placesApiResultGasStation);
                }
                if (isChecked && placesApiResultRestaurant != null) {
                    GenerateMarkersPlacesAPI(placesApiResultRestaurant);
                }

                SharedPreferences.Editor editor = sharedPreferences.edit();

                if(isChecked){
                    editor.putBoolean(RESTAURANT, true);
                }else{
                    editor.putBoolean(RESTAURANT, false);
                }

                editor.commit();
            }
        });

        switchParking = (Switch) findViewById(R.id.swichParking);
        if (parkingSwinch){
            switchParking.setChecked(true);
        }else{
            switchParking.setChecked(false);
        }
        switchParking.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mMap.clear();
                mMap.addMarker(myLocationMarker);
                if (switchRestaurant.isChecked() && placesApiResultRestaurant != null) {
                    GenerateMarkersPlacesAPI(placesApiResultRestaurant);
                }
                if (switchGasStation.isChecked() && placesApiResultGasStation != null) {
                    GenerateMarkersPlacesAPI(placesApiResultGasStation);
                }
                if (isChecked && placesApiResultParking != null) {
                    GenerateMarkersPlacesAPI(placesApiResultParking);
                }

                SharedPreferences.Editor editor = sharedPreferences.edit();

                if(isChecked){
                    editor.putBoolean(PARKING, true);
                }else{
                    editor.putBoolean(PARKING, false);
                }
                editor.commit();
            }
        });

        switchGasStation = (Switch) findViewById(R.id.swichGasStation);
        if (gasStationSwinch){
            switchGasStation.setChecked(true);
        }else{
            switchGasStation.setChecked(false);
        }
        switchGasStation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mMap.clear();
                mMap.addMarker(myLocationMarker);
                if(switchParking.isChecked() && placesApiResultParking != null){
                    GenerateMarkersPlacesAPI(placesApiResultParking);
                }
                if(switchRestaurant.isChecked() && placesApiResultRestaurant != null){
                    GenerateMarkersPlacesAPI(placesApiResultRestaurant);
                }
                if(isChecked && placesApiResultGasStation != null){
                    GenerateMarkersPlacesAPI(placesApiResultGasStation);
                }

                SharedPreferences.Editor editor = sharedPreferences.edit();
                if(isChecked) {
                    editor.putBoolean(GAS_STATION, true);
                }else{
                    editor.putBoolean(GAS_STATION, false);
                }
                editor.commit();
            }
        });
    }

    private void generateLayouts() {


        zoomIn = (ImageButton) findViewById(R.id.zoomIn);
//        zoomIn.setVisibility(View.GONE);
        zoomIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rotation.setRepeatCount(RotateAnimation.ABSOLUTE);
                if (cameraPosition != null) {
                    v.startAnimation(rotation);
                    float zoom = cameraPosition.zoom + 1;
                    LatLng target = cameraPosition.target;
                    cameraPosition = new CameraPosition.Builder()
                            .target(target)      // Sets the center of the map to Mountain View
                            .zoom(zoom)                   // Sets the zoom
                            .build();
                    mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                }
            }
        });

        zoomOut = (ImageButton) findViewById(R.id.zoomOut);
//        zoomOut.setVisibility(View.GONE);
        zoomOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rotation.setRepeatCount(RotateAnimation.ABSOLUTE);
                v.startAnimation(rotation);
                if (cameraPosition != null) {
                    float zoom = cameraPosition.zoom - 1;
                    LatLng target = cameraPosition.target;
                    cameraPosition = new CameraPosition.Builder()
                            .target(target)      // Sets the center of the map to Mountain View
                            .zoom(zoom)                   // Sets the zoom
                            .build();
                    mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                }
            }
        });

        location = (ImageButton) findViewById(R.id.location);
        location.setVisibility(View.GONE);
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rotation.setRepeatCount(RotateAnimation.ABSOLUTE);
                v.startAnimation(rotation);
                if (myLocationMarker != null && myLocationMarker.getPosition() != null) {
                    if (myLocationMarker.getPosition().latitude != 0 && myLocationMarker.getPosition().longitude != 0) {

                        cameraPosition = new CameraPosition.Builder()
                                .target(myLocationMarker.getPosition())      // Sets the center of the map to Mountain View
                                .zoom(15)                   // Sets the zoom
                                .build();

                        //mMap.moveCamera(cameraUpdate);
                        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                    }
                }
            }
        });

        refresh = (ImageButton) findViewById(R.id.refresh);
        refresh.setVisibility(View.GONE);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rotation.setRepeatCount(RotateAnimation.INFINITE);
                refresh.startAnimation(rotation);
                setTitle(R.string.loading);

                mMap.clear();

                new PlacesApiTask(SearchActivity.this, SearchActivity.this,locationSetted.latitude, locationSetted.longitude, RESTAURANT).execute();
                new PlacesApiTask(SearchActivity.this, SearchActivity.this,locationSetted.latitude, locationSetted.longitude, PARKING).execute();
                new PlacesApiTask(SearchActivity.this, SearchActivity.this,locationSetted.latitude, locationSetted.longitude, GAS_STATION).execute();

            }
        });

        markerOffline = (ImageView) findViewById(R.id.marker_offline);
        markerOffline.setVisibility(View.VISIBLE);

        setLocationBtn = (Button) findViewById(R.id.setLocationBtn);
        setLocationBtn.setVisibility(View.VISIBLE);
        setLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                markerOffline.setVisibility(View.GONE);
                v.setVisibility(View.GONE);
                refresh.setVisibility(View.VISIBLE);
                location.setVisibility(View.VISIBLE);
                zoomIn.setVisibility(View.VISIBLE);
                zoomOut.setVisibility(View.VISIBLE);
                myLocationMarker.position(myLocationLatLng);
                mMap.addMarker(myLocationMarker);

                cameraPosition = new CameraPosition.Builder()
                        .target(myLocationLatLng)      // Sets the center of the map to Mountain View
                        .zoom(15)                   // Sets the zoom
                        .build();

                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                locationSetted = myLocationLatLng;

                place = new Place(myLocationLatLng.latitude, myLocationLatLng.longitude);


                new PlacesApiTask(SearchActivity.this, SearchActivity.this,myLocationLatLng.latitude, myLocationLatLng.longitude, RESTAURANT).execute();
                new PlacesApiTask(SearchActivity.this, SearchActivity.this,myLocationLatLng.latitude, myLocationLatLng.longitude, PARKING).execute();
                new PlacesApiTask(SearchActivity.this, SearchActivity.this,myLocationLatLng.latitude, myLocationLatLng.longitude, GAS_STATION).execute();

                MenuItem item = menu.findItem(R.id.action_add);
                item.setVisible(true);

            }
        });
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

            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    private void setUpMap() {

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                DialogUtils.showStreetViewAlertToUser(latLng, SearchActivity.this);
            }
        });


    }

    private void performClickInfoWindow() {

        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(final Marker marker) {

                refresh.setVisibility(View.GONE);
                location.setVisibility(View.GONE);
                zoomIn.setVisibility(View.GONE);
                zoomOut.setVisibility(View.GONE);

                viewGroup = (ViewGroup) getLayoutInflater().inflate(R.layout.info_window_layout, null);
                LinearLayout layoutInfoWindow = (LinearLayout) viewGroup.findViewById(R.id.layoutInfoWindow);
                TextView nameInfoWindow = (TextView) viewGroup.findViewById(R.id.name);
                TextView localInfoWindow = (TextView) viewGroup.findViewById(R.id.endereco);

                nameInfoWindow.setText(marker.getTitle());
                ResultPlaces resultPlaces = resultPlacesMap.get(marker.getSnippet());
                localInfoWindow.setText(resultPlaces.getVicinity());

                layoutInfoWindow.setBackgroundColor(getResources().getColor(R.color.material_blue_grey_800));

                return viewGroup;
            }

            @Override
            public View getInfoContents(final Marker marker) {

                return null;
            }

        });

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                refresh.setVisibility(View.VISIBLE);
                location.setVisibility(View.VISIBLE);
                zoomIn.setVisibility(View.VISIBLE);
                zoomOut.setVisibility(View.VISIBLE);
            }
        });

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(final Marker marker) {

                Intent intent = new Intent(SearchActivity.this, InfosActivity.class);
                ResultPlaces resultPlaces = resultPlacesMap.get(marker.getSnippet());
                intent.putExtra("resultPlaces", resultPlaces);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onPostExecute(PlacesApiResult placesApiResult) {
        Log.i("profile", placesApiResult.getType());
        if(placesApiResult.getType().equals(RESTAURANT)){
            placesApiResultRestaurant = placesApiResult;
        }else if(placesApiResult.getType().equals(PARKING)){
            placesApiResultParking = placesApiResult;
        }else if(placesApiResult.getType().equals(GAS_STATION)){
            placesApiResultGasStation = placesApiResult;
        }

        if(switchParking.isChecked() && placesApiResult != null && placesApiResult.getType().equals(PARKING)){
            GenerateMarkersPlacesAPI(placesApiResult);
        }
        if(switchRestaurant.isChecked() && placesApiResult != null && placesApiResult.getType().equals(RESTAURANT)){
            GenerateMarkersPlacesAPI(placesApiResult);
        }
        if(switchGasStation.isChecked() && placesApiResult != null && placesApiResult.getType().equals(GAS_STATION)){
            GenerateMarkersPlacesAPI(placesApiResult);
        }
    }

    private void GenerateMarkersPlacesAPI(PlacesApiResult placesApiResult) {
        for (ResultPlaces resultPlaces : placesApiResult.getResults()){
            resultPlacesMap.put(resultPlaces.getId(), resultPlaces);
            double latitude = resultPlaces.getGeometry().getLocation().getLat();
            double longitude = resultPlaces.getGeometry().getLocation().getLng();

            Log.i("PLACES_API", latitude + "/" + longitude);

            MarkerOptions markerOptions = new MarkerOptions()
                    .position(new LatLng(latitude, longitude))
                    .title(resultPlaces.getName())
                    .snippet(resultPlaces.getId());

            if(placesApiResult.getType().equals(RESTAURANT)){
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.restaurant));
            }else if(placesApiResult.getType().equals(PARKING)){
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.parking));
            }else if(placesApiResult.getType().equals(GAS_STATION)){
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.gas_station));
            }

            if (latitude != 0.0 || longitude != 0.0) {
                mMap.addMarker(markerOptions);
                performClickInfoWindow();

            }

            if(rotation.isInitialized()) {
                rotation.cancel();
                setTitle(R.string.app_name);
            }
        }
    }




    @Override
    public void onCameraChange(CameraPosition position) {
        // Get the center of the Map.
            myLocationLatLng = position.target;
            cameraPosition = position;
    }


}