package afpcsoft.com.br.bestplaces.model.detailsPlacesApi;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by AndréFelipe on 17/05/2015.
 */
public class PhotoDetails {

    @SerializedName("height")
    private int height;

    @SerializedName("html_attributions")
    private List<String> htmlDistributions;

    @SerializedName("photo_reference")
    private String photoReference;

    @SerializedName("width")
    private int width;

    public PhotoDetails() {
    }

    public PhotoDetails(int height, List<String> htmlDistributions, String photoReference, int width) {

        this.height = height;
        this.htmlDistributions = htmlDistributions;
        this.photoReference = photoReference;
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public List<String> getHtmlDistributions() {
        return htmlDistributions;
    }

    public void setHtmlDistributions(List<String> htmlDistributions) {
        this.htmlDistributions = htmlDistributions;
    }

    public String getPhotoReference() {
        return photoReference;
    }

    public void setPhotoReference(String photoReference) {
        this.photoReference = photoReference;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

}
