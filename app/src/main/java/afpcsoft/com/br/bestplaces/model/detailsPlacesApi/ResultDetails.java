package afpcsoft.com.br.bestplaces.model.detailsPlacesApi;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by AndréFelipe on 17/05/2015.
 */
public class ResultDetails {

    @SerializedName("formatted_address")
    private String formattedAddress;

    @SerializedName("formatted_phone_number")
    private String formattedPhoneNumber;

    @SerializedName("opening_hours")
    private OpeningHoursDetails openingHours;

    @SerializedName("photos")
    private List<PhotoDetails> photos;

    @SerializedName("url")
    private String url;

    public ResultDetails() {
    }

    public ResultDetails(String formattedAddress, String formattedPhoneNumber, OpeningHoursDetails openingHours, List<PhotoDetails> photos, String url) {

        this.formattedAddress = formattedAddress;
        this.formattedPhoneNumber = formattedPhoneNumber;
        this.openingHours = openingHours;
        this.photos = photos;
        this.url = url;
    }

    public String getFormattedAddress() {

        return formattedAddress;
    }

    public void setFormattedAddress(String formattedAddress) {
        this.formattedAddress = formattedAddress;
    }

    public String getFormattedPhoneNumber() {
        return formattedPhoneNumber;
    }

    public void setFormattedPhoneNumber(String formattedPhoneNumber) {
        this.formattedPhoneNumber = formattedPhoneNumber;
    }

    public OpeningHoursDetails getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(OpeningHoursDetails openingHours) {
        this.openingHours = openingHours;
    }

    public List<PhotoDetails> getPhotos() {
        return photos;
    }

    public void setPhotos(List<PhotoDetails> photos) {
        this.photos = photos;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
