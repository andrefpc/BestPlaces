package afpcsoft.com.br.bestplaces.model;

import java.io.Serializable;

public class Place implements Serializable{

    private String type;
    private String name;
    private String description;
    private String address;
    private double lat;
    private double lng;
    private String phone;
    private String site;
    private String socialNetwork;
    private String imageBase64;
    private int confirmation;

    public Place() {
    }

    public Place(String type, String name, String description, String address, double lat, double lng, String phone, String site, String socialNetwork, String imageBase64, int confirmation) {
        this.type = type;
        this.name = name;
        this.description = description;
        this.address = address;
        this.lat = lat;
        this.lng = lng;
        this.phone = phone;
        this.site = site;
        this.socialNetwork = socialNetwork;
        this.imageBase64 = imageBase64;
        this.confirmation = confirmation;
    }

    public Place(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getSocialNetwork() {
        return socialNetwork;
    }

    public void setSocialNetwork(String socialNetwork) {
        this.socialNetwork = socialNetwork;
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
