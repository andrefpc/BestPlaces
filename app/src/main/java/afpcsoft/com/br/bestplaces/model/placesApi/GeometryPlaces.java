package afpcsoft.com.br.bestplaces.model.placesApi;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by AndréFelipe on 16/05/2015.
 */
public class GeometryPlaces implements Serializable {
    @SerializedName("location")
    private LocationPlaces location;

    public GeometryPlaces(LocationPlaces location) {
        this.location = location;
    }

    public GeometryPlaces() {
    }

    public LocationPlaces getLocation() {
        return location;
    }

    public void setLocation(LocationPlaces location) {
        this.location = location;
    }
}
