package afpcsoft.com.br.bestplaces.controller;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
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
import afpcsoft.com.br.bestplaces.service.PostoLocationsTask;
import afpcsoft.com.br.bestplaces.service.UserLocationTask;

/**
 * Created by AndréFelipe on 17/04/2015.
 */
public class SearchActivity extends BaseActivity implements GoogleMap.OnCameraChangeListener, UserLocationTask.OnPostExecuteListener, PostoLocationsTask.OnPostExecuteListener, EstacionamentoLocationsTask.OnPostExecuteListener, FastFoodLocationsTask.OnPostExecuteListener {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private MarkerOptions myLocationMarker;
    private ImageView imageBandeira;
    private TextView phoneInfoWindow;
    private ImageView streetViewInfoWindow;
    private Button goToInfoWindow;
    private String urlInfoWindow;
    private ViewGroup viewGroup;
    private RotateAnimation rotation;
    private ImageView markerOffline;
    private LatLng myLocationLatLng;
    private Button setLocationBtn;
    private ImageButton searchBtn;
    private ImageButton refresh;
    private ImageButton myLocal;

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

    CameraPosition cameraPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        postoMap =  new HashMap<Integer, Posto>();
        barMap =  new HashMap<Integer, Bar>();
        estacionamentoMap =  new HashMap<Integer, Estacionamento>();
        restauranteMap =  new HashMap<Integer, Restaurante>();
        hospitalMap =  new HashMap<Integer, Hospital>();
        fastFoodMap =  new HashMap<Integer, FastFood>();

        LatLng rioLatLng = new LatLng(-22.066452,-42.9232368);

        cameraPosition = new CameraPosition.Builder()
                .target(rioLatLng)      // Sets the center of the map to Mountain View
                .zoom(5)                   // Sets the zoom
                .build();


        rotation= new RotateAnimation(0f,360f, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        rotation.setDuration(600);

        markerOffline = (ImageView) findViewById(R.id.marker_offline);
        markerOffline.setVisibility(View.VISIBLE);

        setLocationBtn = (Button) findViewById(R.id.setLocationBtn);
        setLocationBtn.setVisibility(View.VISIBLE);
        setLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                markerOffline.setVisibility(View.GONE);
                v.setVisibility(View.GONE);
                searchBtn.setVisibility(View.VISIBLE);
                myLocal.setVisibility(View.VISIBLE);
                refresh.setVisibility(View.VISIBLE);

                mMap.addMarker(myLocationMarker.position(myLocationLatLng));

                cameraPosition = new CameraPosition.Builder()
                        .target(myLocationLatLng)      // Sets the center of the map to Mountain View
                        .zoom(12)                   // Sets the zoom
                        .build();

                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                new PostoLocationsTask(SearchActivity.this, SearchActivity.this, myLocationLatLng.latitude, myLocationLatLng.longitude).execute();
//                new EstacionamentoLocationsTask(SearchActivity.this, SearchActivity.this, myLocationLatLng.latitude, myLocationLatLng.longitude).execute();
//                new FastFoodLocationsTask(SearchActivity.this, SearchActivity.this, myLocationLatLng.latitude, myLocationLatLng.longitude).execute();

            }
        });

        myLocationMarker = new MarkerOptions();
        myLocationMarker.title("You are here");
        myLocationMarker.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_user));

        myLocal = (ImageButton) findViewById(R.id.myLocationBtn);
        myLocal.setVisibility(View.GONE);
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

        refresh = (ImageButton) findViewById(R.id.refreshBtn);
        refresh.setVisibility(View.GONE);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rotation.setRepeatCount(RotateAnimation.INFINITE);
                refresh.startAnimation(rotation);

                new PostoLocationsTask(SearchActivity.this, SearchActivity.this, myLocationLatLng.latitude, myLocationLatLng.longitude).execute();
                new EstacionamentoLocationsTask(SearchActivity.this, SearchActivity.this, myLocationLatLng.latitude, myLocationLatLng.longitude).execute();
                new FastFoodLocationsTask(SearchActivity.this, SearchActivity.this, myLocationLatLng.latitude, myLocationLatLng.longitude).execute();


            }
        });

        ImageButton zoomIn = (ImageButton) findViewById(R.id.plusBtn);
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
        ImageButton zoomOut = (ImageButton) findViewById(R.id.minusBtn);
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

        searchBtn = (ImageButton) findViewById(R.id.searchBtn);
        searchBtn.setVisibility(View.GONE);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setVisibility(View.GONE);
                refresh.setVisibility(View.GONE);
                myLocal.setVisibility(View.GONE);
                markerOffline.setVisibility(View.VISIBLE);
                setLocationBtn.setVisibility(View.VISIBLE);
                mMap.clear();

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
                Intent intent = new Intent(SearchActivity.this, StreetViewActivity.class);
                intent.putExtra("lat", latLng.latitude);
                intent.putExtra("lng", latLng.longitude);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onPostExecute(LocalResult localResult) {
        if(localResult.getOnPostFlag() == LocalResult.POSTO){
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
//                TextView localInfoWindow = (TextView) viewGroup.findViewById(R.id.local);
//                TextView phoneInfoWindow = (TextView) viewGroup.findViewById(R.id.phone);
                if(marker.getTitle().equals(POSTO)){
                    try {
                        layoutInfoWindow.setBackgroundColor((Color.parseColor("#000096")));
                        layoutInfoWindow.setAlpha(0.8f);
                        int id  = Integer.valueOf(marker.getSnippet());
                        Posto chosen = postoMap.get(id);

                        nameInfoWindow.setText(chosen.getNome());
//                        localInfoWindow.setText(chosen.getEndComp());
//                        phoneInfoWindow.setText(chosen.getTel());
                    }catch (Exception e){

                    }
                }else if(marker.getTitle().equals(ESTACIONAMENTO)){
                    try {
                        layoutInfoWindow.setBackgroundColor((Color.parseColor("#960000")));
                        layoutInfoWindow.setAlpha(0.8f);
                        int id  = Integer.valueOf(marker.getSnippet());
                        Estacionamento chosen = estacionamentoMap.get(id);

                        nameInfoWindow.setText(chosen.getNome());
//                        localInfoWindow.setText(chosen.getEndComp());
//                        phoneInfoWindow.setText(chosen.getTel());
                    }catch (Exception e){

                    }
                }else if(marker.getTitle().equals(FAST_FOOD)){
                    try {
                        layoutInfoWindow.setBackgroundColor(Color.parseColor("#009600"));
                        layoutInfoWindow.setAlpha(0.8f);
                        int id  = Integer.valueOf(marker.getSnippet());
                        FastFood chosen = fastFoodMap.get(id);

                        nameInfoWindow.setText(chosen.getNome());
//                        localInfoWindow.setText(chosen.getEndComp());
//                        phoneInfoWindow.setText(chosen.getTel());
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
                if(marker.getTitle().equals(POSTO)){
                    try {
                        int id  = Integer.valueOf(marker.getSnippet());
                        Posto chosen = postoMap.get(id);

                        nomeTextView.setText(chosen.getNome());
//                        descricaoTextView.setText(chosen.getDescricao());
                        descricaoTextView.setText(BandeiraPostoEnum.getNomeById(chosen.getBandeira_id()));
                        endCompTextView.setText(chosen.getEndComp());
                        phoneTextView.setText(chosen.getTel());
                    }catch (Exception e){

                    }
                }else if(marker.getTitle().equals(ESTACIONAMENTO)){
                    try {
                        int id  = Integer.valueOf(marker.getSnippet());
                        Estacionamento chosen = estacionamentoMap.get(id);

                        nomeTextView.setText(chosen.getNome());
//                        descricaoTextView.setText(chosen.getDescricao());
                        descricaoTextView.setText(marker.getTitle());
                        endCompTextView.setText(chosen.getEndComp());
                        phoneTextView.setText(chosen.getTel());
                    }catch (Exception e){

                    }
                }else if(marker.getTitle().equals(FAST_FOOD)){
                    try {
                        int id  = Integer.valueOf(marker.getSnippet());
                        FastFood chosen = fastFoodMap.get(id);

                        nomeTextView.setText(chosen.getNome());
                        descricaoTextView.setText(FastFoodTypeEnum.getNomeById(chosen.getTipo_id()));
//                        descricaoTextView.setText(chosen.getDescricao());
                        endCompTextView.setText(chosen.getEndComp());
                        phoneTextView.setText(chosen.getTel());
                    }catch (Exception e){

                    }
                }else if(marker.getTitle().equals(BAR)){
                    try {
                        int id  = Integer.valueOf(marker.getSnippet());
                        Bar chosen = barMap.get(id);

//                        nomeTextView.setText(chosen.getNome());
//                        descricaoTextView.setText(chosen.getDescricao());
                        descricaoTextView.setText(marker.getTitle());
//                        endCompTextView.setText(chosen.getEndComp());
//                        phoneTextView.setText(chosen.getTel());
                    }catch (Exception e){

                    }
                }else if(marker.getTitle().equals(RESTAURANTE)){
                    try {
                        int id  = Integer.valueOf(marker.getSnippet());
                        Restaurante chosen = restauranteMap.get(id);

//                        nomeTextView.setText(chosen.getNome());
//                        descricaoTextView.setText(chosen.getDescricao());
                        descricaoTextView.setText(marker.getTitle());
//                        endCompTextView.setText(chosen.getEndComp());
//                        phoneTextView.setText(chosen.getTel());
                    }catch (Exception e){

                    }
                }else if(marker.getTitle().equals(HOSPITAL)){
                    try {
                        int id  = Integer.valueOf(marker.getSnippet());
                        Hospital chosen = hospitalMap.get(id);

//                        nomeTextView.setText(chosen.getNome());
//                        descricaoTextView.setText(chosen.getDescricao());
                        descricaoTextView.setText(marker.getTitle());
//                        endCompTextView.setText(chosen.getEndComp());
//                        phoneTextView.setText(chosen.getTel());
                    }catch (Exception e){

                    }
                }

                ImageView imageStreetView = (ImageView) findViewById(R.id.imageStreetView);
                imageStreetView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(SearchActivity.this, StreetViewActivity.class);
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
            myLocationLatLng = position.target;
            cameraPosition = position;
    }


}