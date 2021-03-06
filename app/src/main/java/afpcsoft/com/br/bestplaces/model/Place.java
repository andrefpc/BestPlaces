package afpcsoft.com.br.bestplaces.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Place implements Serializable{

    private int id;
    private String name;
    private String description;
    private String address;
    private double lat;
    private double lng;
    private String phone;
    private int type;
    private String site;
    @SerializedName("facebook_page")
    private String facebookPage;
    @SerializedName("plus_page")
    private String plusPage;
    @SerializedName("photo")
    private String imageBase64;
    private int confirmation;

    public Place() {
    }

    public Place(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public Place(int id, String name, String description, String address, double lat, double lng, String phone, int type, String site, String facebookPage, String plusPage, String imageBase64, int confirmation) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.address = address;
        this.lat = lat;
        this.lng = lng;
        this.phone = phone;
        this.type = type;
        this.site = site;
        this.facebookPage = facebookPage;
        this.plusPage = plusPage;
        this.imageBase64 = imageBase64;
        this.confirmation = confirmation;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    public String getPlusPage() {
        return plusPage;
    }

    public void setPlusPage(String plusPage) {
        this.plusPage = plusPage;
    }

    public String getImageBase64() {
        return imageBase64;
    }

    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }

    public int getConfirmation() {
        return confirmation;
    }

    public void setConfirmation(int confirmation) {
        this.confirmation = confirmation;
    }
}
