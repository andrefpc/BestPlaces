package afpcsoft.com.br.bestplaces.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by AndréFelipe on 25/06/2015.
 */
public class Price implements Serializable{

    private int id;
    private String name;
    private String description;
    private String price;
    @SerializedName("place_added_id")
    private int nativePlaceId;
    @SerializedName("place_api_id")
    private int apiPlaceId;
    private int type;
    private String placeApiIdString;

    public Price() {
    }

    public Price(int id, String name, String description, String price, int nativePlaceId, int apiPlaceId, int type, String placeApiIdString) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.nativePlaceId = nativePlaceId;
        this.apiPlaceId = apiPlaceId;
        this.type = type;
        this.placeApiIdString = placeApiIdString;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getNativePlaceId() {
        return nativePlaceId;
    }

    public void setNativePlaceId(int nativePlaceId) {
        this.nativePlaceId = nativePlaceId;
    }

    public int getApiPlaceId() {
        return apiPlaceId;
    }

    public void setApiPlaceId(int apiPlaceId) {
        this.apiPlaceId = apiPlaceId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getPlaceApiIdString() {
        return placeApiIdString;
    }

    public void setPlaceApiIdString(String placeApiIdString) {
        this.placeApiIdString = placeApiIdString;
    }
}
