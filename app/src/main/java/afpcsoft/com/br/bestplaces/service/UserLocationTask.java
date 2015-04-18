package afpcsoft.com.br.bestplaces.service;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.text.Html;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;
import java.util.Locale;

import afpcsoft.com.br.bestplaces.R;
import afpcsoft.com.br.bestplaces.model.LocalResult;
import afpcsoft.com.br.bestplaces.model.MyLocal;
import afpcsoft.com.br.bestplaces.service.GPSTracker;

public class UserLocationTask extends AsyncTask<String, Void, LocalResult> {

    private Context context;
    private ProgressDialog dialog;
    private GoogleMap mMap;
    private MarkerOptions myLocationMarker;

    private String providerAsync;
    private LocationManager locationManagerAsync;
    double   latAsync=0.0;
    double lonAsync=0.0;
    String thikanaAsync="Scan sms for location";

    String AddressAsync="";
    Geocoder GeocoderAsync;
    Location location;

    private OnPostExecuteListener  onPostExecuteListener;


    private static String SUCESSO = "sucesso";
    private static String ERRO = "erro";


    public UserLocationTask(Context context, OnPostExecuteListener onPostExecuteListener) {
        this.context = context;
        this.onPostExecuteListener = onPostExecuteListener;
        if (onPostExecuteListener == null) {
            throw new IllegalArgumentException("Param cannot be null.");
        }
//        gps = new GPSTracker(context);
    }

    public static interface OnPostExecuteListener{
        void onPostExecute(LocalResult localResult);
    }



    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected LocalResult doInBackground(String... params) {
        locationManagerAsync = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);


        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);
        criteria.setCostAllowed(false);
        criteria.setPowerRequirement(Criteria.NO_REQUIREMENT);
        providerAsync = locationManagerAsync.getBestProvider(criteria, true);


//        if (locationManagerAsync.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
//            providerAsync = LocationManager.GPS_PROVIDER;
//        } else if (locationManagerAsync.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
//            providerAsync = LocationManager.NETWORK_PROVIDER;
//        } else if (locationManagerAsync.isProviderEnabled(LocationManager.PASSIVE_PROVIDER)) {
//            providerAsync = LocationManager.PASSIVE_PROVIDER;
//        }

        while(true) {
            location = locationManagerAsync.getLastKnownLocation(providerAsync);
            // Initialize the location fields
            if (location != null) {
                //  System.out.println("Provider " + provider + " has been selected.");
                latAsync = location.getLatitude();
                lonAsync = location.getLongitude();
                break;

            } else {
            }
        }

        LocalResult localResult = new LocalResult();

        if(location != null) {
            MyLocal myLocal = new MyLocal();

            myLocal.setLocation(location);

//            List<Address> addresses = null;
//            GeocoderAsync = new Geocoder(context, Locale.getDefault());
//            try {
//                addresses = GeocoderAsync.getFromLocation(latAsync, lonAsync, 1);
//
//                            String address = addresses.get(0).getAddressLine(0);
//                            String city = addresses.get(0).getAddressLine(1);
//
//                            myLocal.setLogradouro(address);
//                            myLocal.setEstado(city);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }

            localResult.setOnPostFlag(LocalResult.MYLOCAL);
            localResult.setMyLocal(myLocal);
        }else{

        }

        return localResult;
    }

    @Override
    protected void onPostExecute(LocalResult localResult) {
        super.onPostExecute(localResult);
        if (onPostExecuteListener != null){
            try {
                onPostExecuteListener.onPostExecute(localResult);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

//    public Location getUserLocation(){
//        Location location;
//        // Check if GPS enabled
//        if(gps.canGetLocation()) {
//            location = gps.getLocation();
//            return location;
//        }else{
//            return null;
//        }
//    }
}
