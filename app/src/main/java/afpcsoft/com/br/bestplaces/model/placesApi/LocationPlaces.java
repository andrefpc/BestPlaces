package afpcsoft.com.br.bestplaces.model.placesApi;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by AndréFelipe on 16/05/2015.
 */
public class LocationPlaces implements Serializable {
    @SerializedName("lat")
    private double lat;

    @SerializedName("lng")
    private double lng;

    public LocationPlaces() {
    }

    public LocationPlaces(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}

