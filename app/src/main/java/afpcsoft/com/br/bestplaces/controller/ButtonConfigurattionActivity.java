//package afpcsoft.com.br.bestplaces.controller;
//
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.graphics.Color;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.GridLayout;
//import android.widget.LinearLayout;
//
//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.SupportMapFragment;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import afpcsoft.com.br.bestplaces.R;
//import afpcsoft.com.br.bestplaces.Utils.GridGenerator;
//
///**
// * Created by AndréFelipe on 18/04/2015.
// */
//public class ButtonConfigurattionActivity extends BaseActivity {
//
//    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
//    private ViewGroup viewGroup;
//
//
//    Map<View, LinearLayout> viewMap;
//
//    GridLayout gridLayout;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_button_configuration);
//
//        viewMap = new HashMap<View, LinearLayout>();
//
//        setUpMapIfNeeded();
//        initialize();
//        generateLayouts();
//        leftMenu();
//    }
//
//    private void generateLayouts() {
//
//        Button setBtn = new Button(this);
//        setBtn.setBackgroundResource(R.drawable.button_shape);
//        setBtn.setText("Set");
//        setBtn.setTextColor(Color.WHITE);
//        setBtn.setTextSize(20);
//        setBtn.setPadding(3, 0, 3, 0);
//
//        SharedPreferences shared = getSharedPreferences(GridGenerator.BUTTON_PREFS, MODE_PRIVATE);
//        Integer zoomInValue = Integer.parseInt(shared.getString("1", "5"));
//        Integer zoomOutValue = Integer.parseInt(shared.getString("2", "5"));
//        Integer myLocalValue = Integer.parseInt(shared.getString("3", "5"));
//        Integer refreshValue = Integer.parseInt(shared.getString("4", "6"));
//        Integer searchValue = Integer.parseInt(shared.getString("5", "6"));
//        Integer seetingsValue = Integer.parseInt(shared.getString("6", "6"));
//
//        Map<Integer, Integer> mapView = new HashMap<Integer,Integer>();
//        mapView.put(1,zoomInValue);
//        mapView.put(2,zoomOutValue);
//        mapView.put(3,myLocalValue);
//        mapView.put(4,refreshValue);
//        mapView.put(5,searchValue);
//        mapView.put(6,seetingsValue);
//
//        final GridGenerator gridGenerator = new GridGenerator(ButtonConfigurattionActivity.this, ButtonConfigurattionActivity.this, GridGenerator.BUTTON_CONFIGURATION, mapView, setBtn);
//
//        gridGenerator.generateGrid();
//
//        setBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Log.i("drag", "entrei no Onclick");
//                Map<Integer, Integer> map = gridGenerator.getMapView();
//                SharedPreferences pref = getSharedPreferences(GridGenerator.BUTTON_PREFS, Context.MODE_PRIVATE);
//                SharedPreferences.Editor editor = pref.edit();
//                for(Map.Entry entry : map.entrySet()){
//                    editor.putString(String.valueOf(entry.getKey()), String.valueOf(entry.getValue()));
//                    Log.i("drag", "Key: " + entry.getKey() + ", Value: " + entry.getValue());
//                }
//                editor.commit();
//                onBackPressed();
//            }
//        });
//    }
//
//    @Override
//    public void onBackPressed(){
//        super.onBackPressed();
//
//        Intent intent = new Intent(ButtonConfigurattionActivity.this, MapsActivity.class);
//        startActivity(intent);
//        finish();
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        setUpMapIfNeeded();
//    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        setUpMapIfNeeded();
//    }
//
//    @Override
//    protected void onRestart() {
//        super.onRestart();
//        setUpMapIfNeeded();
//    }
//
//    private void setUpMapIfNeeded() {
//        // Do a null check to confirm that we have not already instantiated the map.
//        if (mMap == null) {
//            // Try to obtain the map from the SupportMapFragment.
//            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
//                    .getMap();
//        }
//    }
//
//}
