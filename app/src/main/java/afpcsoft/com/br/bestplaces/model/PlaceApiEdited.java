package afpcsoft.com.br.bestplaces.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by AndréFelipe on 25/06/2015.
 */
public class PlaceApiEdited implements Serializable {

    private int id;
    @SerializedName("place_api_id")
    private String placeApiId;
    private String phone;
    private String site;
    @SerializedName("facebook_page")
    private String facebookPage;
    private String photo;
    @SerializedName("price_added")
    private int priceAdded;

    public PlaceApiEdited() {
    }

    public PlaceApiEdited(int id, String placeApiId, String phone, String site, String facebookPage, String photo, int priceAdded) {

        this.id = id;
        this.placeApiId = placeApiId;
        this.phone = phone;
        this.site = site;
        this.facebookPage = facebookPage;
        this.photo = photo;
        this.priceAdded = priceAdded;
    }

    public PlaceApiEdited(String placeApiId) {

        this.placeApiId = placeApiId;
    }


    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlaceApiId() {
        return placeApiId;
    }

    public void setPlaceApiId(String placeApiId) {
        this.placeApiId = placeApiId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getFacebookPage() {
        return facebookPage;
    }

    public void setFacebookPage(String facebookPage) {
        this.facebookPage = facebookPage;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getPriceAdded() {
        return priceAdded;
    }

    public void setPriceAdded(int priceAdded) {
        this.priceAdded = priceAdded;
    }
}
