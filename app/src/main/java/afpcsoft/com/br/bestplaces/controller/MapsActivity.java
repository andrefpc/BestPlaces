package afpcsoft.com.br.bestplaces.controller;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.ActionMenuView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import afpcsoft.com.br.bestplaces.Enums.BandeiraPostoEnum;
import afpcsoft.com.br.bestplaces.Enums.FastFoodTypeEnum;
import afpcsoft.com.br.bestplaces.R;
import afpcsoft.com.br.bestplaces.Utils.FastFoodUtils;
import afpcsoft.com.br.bestplaces.Utils.PostoUtils;
import afpcsoft.com.br.bestplaces.model.Bar;
import afpcsoft.com.br.bestplaces.model.Estacionamento;
import afpcsoft.com.br.bestplaces.model.FastFood;
import afpcsoft.com.br.bestplaces.model.Hospital;
import afpcsoft.com.br.bestplaces.model.LocalResult;
import afpcsoft.com.br.bestplaces.model.MyLocal;
import afpcsoft.com.br.bestplaces.model.Posto;
import afpcsoft.com.br.bestplaces.model.Restaurante;
import afpcsoft.com.br.bestplaces.service.EstacionamentoLocationsTask;
import afpcsoft.com.br.bestplaces.service.FastFoodLocationsTask;
import afpcsoft.com.br.bestplaces.service.MyLocation;
import afpcsoft.com.br.bestplaces.service.PostoLocationsTask;
import afpcsoft.com.br.bestplaces.service.UserLocationTask;

public class MapsActivity extends BaseActivity implements GoogleMap.OnCameraChangeListener, UserLocationTask.OnPostExecuteListener, PostoLocationsTask.OnPostExecuteListener, EstacionamentoLocationsTask.OnPostExecuteListener, FastFoodLocationsTask.OnPostExecuteListener {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private MarkerOptions myLocationMarker;
    private ImageView imageBandeira;
    private TextView phoneInfoWindow;
    private ImageView streetViewInfoWindow;
    private Button goToInfoWindow;
    private String urlInfoWindow;
    private ViewGroup viewGroup;

    private RotateAnimation rotation;
    private Animation shake;

    Map<View, LinearLayout> viewMap;

    private Map<Integer, Posto> postoMap;
    private Map<Integer, Bar> barMap;
    private Map<Integer, Estacionamento> estacionamentoMap;
    private Map<Integer, Restaurante> restauranteMap;
    private Map<Integer, Hospital> hospitalMap;
    private Map<Integer, FastFood> fastFoodMap;

    private static String POSTO = "Posto";
    private static String ESTACIONAMENTO = "Estacionamento";
    private static String FAST_FOOD = "Fast Food";
    private static String RESTAURANTE = "Restaurante";
    private static String BAR = "Bar";
    private static String HOSPITAL = "Hospital";

    private LinearLayout grid1;
    private LinearLayout grid2;
    private LinearLayout grid3;
    private LinearLayout grid4;
    private LayoutInflater inflater;

    private View search;
    private View refresh;
    private View zoomIn;
    private View zoomOut;
    private View myLocal;


    CameraPosition cameraPosition;


    private int x_cord;
    private int y_cord;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);


        postoMap =  new HashMap<Integer, Posto>();
        barMap =  new HashMap<Integer, Bar>();
        estacionamentoMap =  new HashMap<Integer, Estacionamento>();
        restauranteMap =  new HashMap<Integer, Restaurante>();
        hospitalMap =  new HashMap<Integer, Hospital>();
        fastFoodMap =  new HashMap<Integer, FastFood>();
        rotation= new RotateAnimation(0f,360f, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        rotation.setDuration(600);

       viewMap = new HashMap<View, LinearLayout>();

        shake = AnimationUtils.loadAnimation(MapsActivity.this, R.anim.shake);

        myLocationMarker = new MarkerOptions();
        myLocationMarker.title("You are here");
        myLocationMarker.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_user));

        generateLayouts();
        setUpMapIfNeeded();
        initialize();
        leftMenu();
        rightMenu();


    }

    private void generateLayouts() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width = (70 * metrics.densityDpi) / DisplayMetrics.DENSITY_DEFAULT;
        int heigh = (70 * metrics.densityDpi) / DisplayMetrics.DENSITY_DEFAULT;
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width,heigh);

        grid1 = (LinearLayout) findViewById(R.id.grid1);
        grid2 = (LinearLayout) findViewById(R.id.grid2);
        grid3 = (LinearLayout) findViewById(R.id.grid3);
        grid4 = (LinearLayout) findViewById(R.id.grid4);

        zoomIn = new ImageButton(MapsActivity.this);
        zoomIn.setLayoutParams(layoutParams);
        zoomIn.setPadding(10, 10, 10, 10);
        zoomIn.setBackgroundResource(R.drawable.plus);
        zoomIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rotation.setRepeatCount(RotateAnimation.ABSOLUTE);
                if(cameraPosition != null) {
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
        zoomOut = new ImageButton(MapsActivity.this);
        zoomOut.setLayoutParams(layoutParams);
        zoomOut.setPadding(10, 10, 10, 10);
        zoomOut.setBackgroundResource(R.drawable.minus);
        zoomOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rotation.setRepeatCount(RotateAnimation.ABSOLUTE);
                v.startAnimation(rotation);
                if(cameraPosition != null) {
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

        myLocal = new ImageButton(MapsActivity.this);
        myLocal.setLayoutParams(layoutParams);
        myLocal.setPadding(10, 10, 10, 10);
        myLocal.setBackgroundResource(R.drawable.location);
        myLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rotation.setRepeatCount(RotateAnimation.ABSOLUTE);
                v.startAnimation(rotation);
                if(myLocationMarker != null && myLocationMarker.getPosition() != null) {
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

        refresh = new ImageButton(MapsActivity.this);
        refresh.setLayoutParams(layoutParams);
        refresh.setPadding(10, 10, 10, 10);
        refresh.setBackgroundResource(R.drawable.refresh);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rotation.setRepeatCount(RotateAnimation.INFINITE);
                refresh.startAnimation(rotation);

                getUserLocation();
            }
        });

        search = new ImageButton(MapsActivity.this);
        search.setLayoutParams(layoutParams);
        search.setPadding(10, 10, 10, 10);
        search.setBackgroundResource(R.drawable.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                v.startAnimation(shake);
//                Intent intent = new Intent(MapsActivity.this, SearchActivity.class);
//                startActivity(intent);
                Intent intent = new Intent(MapsActivity.this, ButtonConfigurattionActivity.class);
                startActivity(intent);
            }
        });

//        search.setOnTouchListener(new MyTouchListener());
//        refresh.setOnTouchListener(new MyTouchListener());
//        myLocal.setOnTouchListener(new MyTouchListener());
//        zoomIn.setOnTouchListener(new MyTouchListener());
//        zoomOut.setOnTouchListener(new MyTouchListener());

        if (grid1.getChildCount() > 0) {
            grid1.removeViews(0, grid1.getChildCount());
        }
        if (grid2.getChildCount() > 0) {
            grid2.removeViews(0, grid2.getChildCount());
        }
        if (grid3.getChildCount() > 0) {
            grid3.removeViews(0, grid3.getChildCount());
        }
        if (grid4.getChildCount() > 0) {
            grid4.removeViews(0, grid4.getChildCount());
        }

        viewMap.put(search,grid1);
        viewMap.put(refresh,grid2);
        viewMap.put(myLocal,grid3);
        viewMap.put(zoomIn,grid4);
        viewMap.put(zoomOut,grid4);

        grid1.addView(search);
        grid2.addView(refresh);
        grid3.addView(myLocal);
        grid4.addView(zoomIn);
        grid4.addView(zoomOut);
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

    private void setUpMap() {

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                Intent intent = new Intent(MapsActivity.this, StreetViewActivity.class);
                intent.putExtra("lat", latLng.latitude);
                intent.putExtra("lng", latLng.longitude);
                startActivity(intent);
            }
        });

        getUserLocation();
    }

    private void getUserLocation(){
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){

            rotation.setRepeatCount(RotateAnimation.INFINITE);
            refresh.startAnimation(rotation);
            MyLocation.LocationResult locationResult = new MyLocation.LocationResult(){
                @Override
                public void gotLocation(Location location){
//                    mMap.clear();
                    MyLocal myLocal = new MyLocal();
                    myLocal.setLocation(location);
                    generateMyLocalMarker(myLocal);

                    new PostoLocationsTask(MapsActivity.this, MapsActivity.this, myLocal.getLocation().getLatitude(),myLocal.getLocation().getLongitude()).execute();
                    new EstacionamentoLocationsTask(MapsActivity.this, MapsActivity.this, myLocal.getLocation().getLatitude(), myLocal.getLocation().getLongitude()).execute();
                    new FastFoodLocationsTask(MapsActivity.this, MapsActivity.this, myLocal.getLocation().getLatitude(), myLocal.getLocation().getLongitude()).execute();

                }
            };
            MyLocation myLocation = new MyLocation();
            myLocation.getLocation(this, locationResult);

        }else{
            showGPSDisabledAlertToUser();
        }
    }

    private void showGPSDisabledAlertToUser(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setMessage("Seu GPS está desligado, ative caso queira a localização real, caso não queira, você pode marcar sua própria localização.")
                .setCancelable(false)
                .setPositiveButton("Ativar GPS",
                        new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int id){
                                Intent callGPSSettingIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivityForResult(callGPSSettingIntent, 0);
                            }
                        });
        alertDialogBuilder.setNegativeButton("Colocar localiação",
                new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id){
                        Intent intent = new Intent(MapsActivity.this, SearchActivity.class);
                        startActivity(intent);
                        finish();
                        dialog.cancel();
                    }
                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    @Override
    public void onPostExecute(LocalResult localResult) {
        if(localResult.getOnPostFlag() == LocalResult.MYLOCAL) {
//            generateMyLocalMarker(localResult.getMyLocal());
//            new PostoLocationsTask(MapsActivity.this, MapsActivity.this, localResult.getMyLocal().getLocation().getLatitude(),localResult.getMyLocal().getLocation().getLongitude()).execute();
//            new EstacionamentoLocationsTask(MapsActivity.this, MapsActivity.this, localResult.getMyLocal().getLocation().getLatitude(), localResult.getMyLocal().getLocation().getLongitude()).execute();
//            new FastFoodLocationsTask(MapsActivity.this, MapsActivity.this, localResult.getMyLocal().getLocation().getLatitude(), localResult.getMyLocal().getLocation().getLongitude()).execute();
        }else if(localResult.getOnPostFlag() == LocalResult.POSTO){
            generatePostoMarkers(localResult.getPostos());
        }else if(localResult.getOnPostFlag() == LocalResult.FAST_FOOD){
            generateFastFoodMarkers(localResult.getFastFoods());
            if(rotation.isInitialized()) {
                rotation.cancel();
            }
        }else if(localResult.getOnPostFlag() == LocalResult.BAR){
        }else if(localResult.getOnPostFlag() == LocalResult.ESTACIONAMENTO){
            generateEstacionamentoMarkers(localResult.getEstacionamentos());
        }else if(localResult.getOnPostFlag() == LocalResult.HOSPITAL){
        }else if(localResult.getOnPostFlag() == LocalResult.RESTAURANTE){
        }
    }

    private void generateMyLocalMarker(MyLocal myLocal) {
        if(myLocal != null & myLocal.getLocation() != null) {
            double latitude = myLocal.getLocation().getLatitude();
            double longitude = myLocal.getLocation().getLongitude();

            if (latitude != 0.0 || longitude != 0.0) {

                myLocationMarker.position(new LatLng(latitude, longitude));
                mMap.addMarker(myLocationMarker);

                cameraPosition = new CameraPosition.Builder()
                        .target(myLocationMarker.getPosition())      // Sets the center of the map to Mountain View
                        .zoom(15)                   // Sets the zoom
                        .build();

                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        }
    }

    private void generatePostoMarkers( List<Posto> postosList) {
        int index = 0;
        for(final Posto posto : postosList) {
            postoMap.put(posto.getId(), posto);
            double latitude = posto.getLatitude();
            double longitude = posto.getLongitude();


            MarkerOptions markerOptions = new MarkerOptions()
                    .position(new LatLng(latitude, longitude))
                    .title(POSTO)
                    .snippet(String.valueOf(posto.getId()));

            int resourceId = PostoUtils.getBandeiraResourceId(posto.getBandeira_id());

            markerOptions.icon(BitmapDescriptorFactory.fromResource(resourceId));

            if (latitude != 0.0 || longitude != 0.0) {
                mMap.addMarker(markerOptions);
                performClickInfoWindow();
            }
            index++;
        }
    }

    private void generateFastFoodMarkers( List<FastFood> fastFoodList) {
        int index = 0;
        for(final FastFood fastFood : fastFoodList) {
            fastFoodMap.put(fastFood.getId(), fastFood);
            double latitude = fastFood.getLatitude();
            double longitude = fastFood.getLongitude();


            MarkerOptions markerOptions = new MarkerOptions()
                    .position(new LatLng(latitude, longitude))
                    .title(FAST_FOOD)
                    .snippet(String.valueOf(fastFood.getId()));

            int resourceId = FastFoodUtils.getTipoResourceId(fastFood.getTipo_id());

            markerOptions.icon(BitmapDescriptorFactory.fromResource(resourceId));

            if (latitude != 0.0 || longitude != 0.0) {
                mMap.addMarker(markerOptions);
                performClickInfoWindow();
            }
            index++;
        }
    }

    private void generateEstacionamentoMarkers( List<Estacionamento> estacionamentoList) {
        int index = 0;
        for (final Estacionamento estacionamento : estacionamentoList) {
            estacionamentoMap.put(estacionamento.getId(), estacionamento);
            double latitude = estacionamento.getLatitude();
            double longitude = estacionamento.getLongitude();


            MarkerOptions markerOptions = new MarkerOptions()
                    .position(new LatLng(latitude, longitude))
                    .title(ESTACIONAMENTO)
                    .snippet(String.valueOf(estacionamento.getId()));

            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_park));

            if (latitude != 0.0 || longitude != 0.0) {
                mMap.addMarker(markerOptions);
                performClickInfoWindow();

            }
            index++;
        }
    }
    private void performClickInfoWindow() {

        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(final Marker marker) {

                viewGroup = (ViewGroup) getLayoutInflater().inflate(R.layout.info_window_layout, null);
                LinearLayout layoutInfoWindow = (LinearLayout) viewGroup.findViewById(R.id.layoutInfoWindow);
                TextView nameInfoWindow = (TextView) viewGroup.findViewById(R.id.name);
                TextView localInfoWindow = (TextView) viewGroup.findViewById(R.id.local);
                TextView phoneInfoWindow = (TextView) viewGroup.findViewById(R.id.phone);
                if(marker.getTitle().equals(POSTO)){
                    try {
                        layoutInfoWindow.setBackgroundColor((Color.parseColor("#000096")));
                        layoutInfoWindow.setAlpha(0.8f);
                        int id  = Integer.valueOf(marker.getSnippet());
                        Posto chosen = postoMap.get(id);

                        nameInfoWindow.setText(chosen.getNome());
                        localInfoWindow.setText(chosen.getEndComp());
                        phoneInfoWindow.setText(chosen.getTel());
                    }catch (Exception e){

                    }
                }else if(marker.getTitle().equals(ESTACIONAMENTO)){
                    try {
                        layoutInfoWindow.setBackgroundColor((Color.parseColor("#960000")));
                        layoutInfoWindow.setAlpha(0.8f);
                        int id  = Integer.valueOf(marker.getSnippet());
                        Estacionamento chosen = estacionamentoMap.get(id);

                        nameInfoWindow.setText(chosen.getNome());
                        localInfoWindow.setText(chosen.getEndComp());
                        phoneInfoWindow.setText(chosen.getTel());
                    }catch (Exception e){

                    }
                }else if(marker.getTitle().equals(FAST_FOOD)){
                    try {
                        layoutInfoWindow.setBackgroundColor(Color.parseColor("#009600"));
                        layoutInfoWindow.setAlpha(0.8f);
                        int id  = Integer.valueOf(marker.getSnippet());
                        FastFood chosen = fastFoodMap.get(id);

                        nameInfoWindow.setText(chosen.getNome());
                        localInfoWindow.setText(chosen.getEndComp());
                        phoneInfoWindow.setText(chosen.getTel());
                    }catch (Exception e){

                    }
                }else if(marker.getTitle().equals(BAR)){
                    try {
                        int id  = Integer.valueOf(marker.getSnippet());
                        Bar chosen = barMap.get(id);

//                        nameInfoWindow.setText(chosen.getNome());
//                        localInfoWindow.setText(chosen.getEndComp());
//                        phoneInfoWindow.setText(chosen.getTel());
                    }catch (Exception e){

                    }
                }else if(marker.getTitle().equals(RESTAURANTE)){
                    try {
                        int id  = Integer.valueOf(marker.getSnippet());
                        Restaurante chosen = restauranteMap.get(id);

//                        nameInfoWindow.setText(chosen.getNome());
//                        localInfoWindow.setText(chosen.getEndComp());
//                        phoneInfoWindow.setText(chosen.getTel());
                    }catch (Exception e){

                    }
                }else if(marker.getTitle().equals(HOSPITAL)){
                    try {
                        int id  = Integer.valueOf(marker.getSnippet());
                        Hospital chosen = hospitalMap.get(id);

//                        nameInfoWindow.setText(chosen.getNome());
//                        localInfoWindow.setText(chosen.getEndComp());
//                        phoneInfoWindow.setText(chosen.getTel());
                    }catch (Exception e){

                    }
                }

                return viewGroup;
            }

            @Override
            public View getInfoContents(final Marker marker) {

                return null;
            }

        });

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(final Marker marker) {
                drawerLayoutRight.openDrawer(drawerRight);

                TextView nomeTextView = (TextView) findViewById(R.id.textNomeRight);
                TextView descricaoTextView = (TextView) findViewById(R.id.textDescriptionRight);
                TextView endCompTextView = (TextView) findViewById(R.id.textEnderecoRight);
                TextView phoneTextView = (TextView) findViewById(R.id.textPhoneLeft);
                if (marker.getTitle().equals(POSTO)) {
                    try {
                        int id = Integer.valueOf(marker.getSnippet());
                        Posto chosen = postoMap.get(id);

                        nomeTextView.setText(chosen.getNome());
//                        descricaoTextView.setText(chosen.getDescricao());
                        descricaoTextView.setText(BandeiraPostoEnum.getNomeById(chosen.getBandeira_id()));
                        endCompTextView.setText(chosen.getEndComp());
                        phoneTextView.setText(chosen.getTel());
                    } catch (Exception e) {

                    }
                } else if (marker.getTitle().equals(ESTACIONAMENTO)) {
                    try {
                        int id = Integer.valueOf(marker.getSnippet());
                        Estacionamento chosen = estacionamentoMap.get(id);

                        nomeTextView.setText(chosen.getNome());
//                        descricaoTextView.setText(chosen.getDescricao());
                        descricaoTextView.setText(marker.getTitle());
                        endCompTextView.setText(chosen.getEndComp());
                        phoneTextView.setText(chosen.getTel());
                    } catch (Exception e) {

                    }
                } else if (marker.getTitle().equals(FAST_FOOD)) {
                    try {
                        int id = Integer.valueOf(marker.getSnippet());
                        FastFood chosen = fastFoodMap.get(id);

                        nomeTextView.setText(chosen.getNome());
                        descricaoTextView.setText(FastFoodTypeEnum.getNomeById(chosen.getTipo_id()));
//                        descricaoTextView.setText(chosen.getDescricao());
                        endCompTextView.setText(chosen.getEndComp());
                        phoneTextView.setText(chosen.getTel());
                    } catch (Exception e) {

                    }
                } else if (marker.getTitle().equals(BAR)) {
                    try {
                        int id = Integer.valueOf(marker.getSnippet());
                        Bar chosen = barMap.get(id);

//                        nomeTextView.setText(chosen.getNome());
//                        descricaoTextView.setText(chosen.getDescricao());
                        descricaoTextView.setText(marker.getTitle());
//                        endCompTextView.setText(chosen.getEndComp());
//                        phoneTextView.setText(chosen.getTel());
                    } catch (Exception e) {

                    }
                } else if (marker.getTitle().equals(RESTAURANTE)) {
                    try {
                        int id = Integer.valueOf(marker.getSnippet());
                        Restaurante chosen = restauranteMap.get(id);

//                        nomeTextView.setText(chosen.getNome());
//                        descricaoTextView.setText(chosen.getDescricao());
                        descricaoTextView.setText(marker.getTitle());
//                        endCompTextView.setText(chosen.getEndComp());
//                        phoneTextView.setText(chosen.getTel());
                    } catch (Exception e) {

                    }
                } else if (marker.getTitle().equals(HOSPITAL)) {
                    try {
                        int id = Integer.valueOf(marker.getSnippet());
                        Hospital chosen = hospitalMap.get(id);

//                        nomeTextView.setText(chosen.getNome());
//                        descricaoTextView.setText(chosen.getDescricao());
                        descricaoTextView.setText(marker.getTitle());
//                        endCompTextView.setText(chosen.getEndComp());
//                        phoneTextView.setText(chosen.getTel());
                    } catch (Exception e) {

                    }
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

    @Override
    public void onCameraChange(CameraPosition position) {
        // Get the center of the Map.
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        // Check which request we're responding to
//        if (requestCode == 0) {
//            // Make sure the request was successful
//            if (resultCode == RESULT_OK) {
                LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

                if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
                    mMap.clear();
                    rotation.setRepeatCount(RotateAnimation.INFINITE);
                    refresh.startAnimation(rotation);
                    MyLocation.LocationResult locationResult = new MyLocation.LocationResult(){
                        @Override
                        public void gotLocation(Location location){
                            mMap.clear();
                            MyLocal myLocal = new MyLocal();
                            myLocal.setLocation(location);
                            generateMyLocalMarker(myLocal);

                            new PostoLocationsTask(MapsActivity.this, MapsActivity.this, myLocal.getLocation().getLatitude(),myLocal.getLocation().getLongitude()).execute();
                            new EstacionamentoLocationsTask(MapsActivity.this, MapsActivity.this, myLocal.getLocation().getLatitude(), myLocal.getLocation().getLongitude()).execute();
                            new FastFoodLocationsTask(MapsActivity.this, MapsActivity.this, myLocal.getLocation().getLatitude(), myLocal.getLocation().getLongitude()).execute();

                        }
                    };
                    MyLocation myLocation = new MyLocation();
                    myLocation.getLocation(this, locationResult);

                }
//            }
//        }
    }

    private final class MyTouchListener implements View.OnTouchListener {

        public boolean onTouch(View v, MotionEvent me) {
            if (me.getAction() == MotionEvent.ACTION_MOVE) {
                Log.d("dragAndDrop", "ACTION_MOVE event");

                x_cord = (int) me.getRawX() - v.getWidth() / 2;
                y_cord = (int) (me.getRawY() - v.getHeight() * 1.5);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(v.getWidth(), v.getHeight());
                layoutParams.setMargins((int) me.getRawX() - v.getWidth() / 2, (int) (me.getRawY() - v.getHeight() * 1.5), (int) me.getRawX() - v.getWidth() / 2, (int) (me.getRawY() - v.getHeight() * 1.5));
                v.setLayoutParams(layoutParams);
                v.setAlpha(0.5f);

            }else if (me.getAction() == MotionEvent.ACTION_UP) {
                Log.d("dragAndDrop", "ACTION_UP event");

                Display display = getWindowManager().getDefaultDisplay();
                Point size = new Point();
                display.getSize(size);
                int width = size.x;
                int height = size.y;

                LinearLayout lastLayout = viewMap.get(v);
                viewMap.remove(v);
                lastLayout.removeView(v);



                if (grid3.getChildCount() > 0) {
                    grid3.removeViews(0, grid3.getChildCount());
                }
                if (grid4.getChildCount() > 0) {
                    grid4.removeViews(0, grid4.getChildCount());
                }

                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(v.getWidth(), v.getHeight());
                if(x_cord < width/2 && y_cord < height/2){
                    layoutParams.gravity = Gravity.START|Gravity.TOP;
                    v.setLayoutParams(layoutParams);
                    if (grid1.getChildCount() > 0) {
                        grid1.removeViews(0, grid1.getChildCount());
                    }
                    for(Map.Entry entry : viewMap.entrySet()){
                        LinearLayout linearLayout = (LinearLayout) entry.getValue();
                        if(linearLayout.equals(grid1)) {
                            View view = (View) entry.getKey();
                            grid1.addView(view);
                        }
                    }
                    grid1.addView(v);
                    viewMap.put(v, grid1);
                }else if(x_cord > width/2 && y_cord < height/2){
                    layoutParams.gravity = Gravity.END|Gravity.TOP;
                    v.setLayoutParams(layoutParams);
                    if (grid2.getChildCount() > 0) {
                        grid2.removeViews(0, grid2.getChildCount());
                    }
                    for(Map.Entry entry : viewMap.entrySet()){
                        LinearLayout linearLayout = (LinearLayout) entry.getValue();
                        if(linearLayout.equals(grid2)) {
                            View view = (View) entry.getKey();
                            grid2.addView(view);
                        }
                    }
                    grid2.addView(v);
                    viewMap.put(v,grid2);
                }   else if(x_cord < width/2 && y_cord > height/2) {
                    layoutParams.gravity = Gravity.START|Gravity.BOTTOM;
                    v.setLayoutParams(layoutParams);
                    if (grid3.getChildCount() > 0) {
                        grid3.removeViews(0, grid3.getChildCount());
                    }
                    for(Map.Entry entry : viewMap.entrySet()){
                        LinearLayout linearLayout = (LinearLayout) entry.getValue();
                        if(linearLayout.equals(grid3)) {
                            View view = (View) entry.getKey();
                            grid3.addView(view);
                        }
                    }
                    grid3.addView(v);
                    viewMap.put(v,grid2);
                }else if(x_cord > width/2 && y_cord > height/2){
                    layoutParams.gravity = Gravity.END|Gravity.BOTTOM;
                    v.setLayoutParams(layoutParams);
                    if (grid4.getChildCount() > 0) {
                        grid4.removeViews(0, grid4.getChildCount());
                    }
                    for(Map.Entry entry : viewMap.entrySet()){
                        LinearLayout linearLayout = (LinearLayout) entry.getValue();
                        if(linearLayout.equals(grid4)) {
                            View view = (View) entry.getKey();
                            grid4.addView(view);
                        }
                    }
                    grid4.addView(v);
                    viewMap.put(v,grid2);
                }

                v.setAlpha(1.0f);
            }

//                return true;

            return false;
        }
    }
}
