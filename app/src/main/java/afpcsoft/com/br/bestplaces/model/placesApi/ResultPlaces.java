package afpcsoft.com.br.bestplaces.model.placesApi;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by AndréFelipe on 16/05/2015.
 */
public class ResultPlaces implements Serializable {

    @SerializedName("geometry")
    private GeometryPlaces geometry;

    @SerializedName("icon")
    private String iconUrl;

    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("opening_hours")
    private OpeningHoursPlaces openingHours;

    @SerializedName("place_id")
    private String placeId;

    @SerializedName("reference")
    private String reference;

    @SerializedName("scope")
    private String scope;

    @SerializedName("types")
    private List<String> types;

    @SerializedName("vicinity")
    private String vicinity;

    public ResultPlaces() {
    }

    public ResultPlaces(GeometryPlaces geometry, String iconUrl, String id, String name, OpeningHoursPlaces openingHours, String placeId, String reference, String scope, List<String> types, String vicinity) {

        this.geometry = geometry;
        this.iconUrl = iconUrl;
        this.id = id;
        this.name = name;
        this.openingHours = openingHours;
        this.placeId = placeId;
        this.reference = reference;
        this.scope = scope;
        this.types = types;
        this.vicinity = vicinity;
    }

    public GeometryPlaces getGeometry() {
        return geometry;
    }

    public void setGeometry(GeometryPlaces geometry) {
        this.geometry = geometry;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public OpeningHoursPlaces getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(OpeningHoursPlaces openingHours) {
        this.openingHours = openingHours;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    public String getVicinity() {
        return vicinity;
    }

    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }
}
