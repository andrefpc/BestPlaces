package afpcsoft.com.br.bestplaces.model.placesApi;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by AndréFelipe on 16/05/2015.
 */
public class PlacesApiResult {
    @SerializedName("html_attributions")
    private List<String> htmlAttributions;

    @SerializedName("next_page_token")
    private String nextPageToken;

    @SerializedName("results")
    private List<ResultPlaces> results;

    @SerializedName("status")
    private String status;

    private String type;

    public PlacesApiResult() {
    }

    public PlacesApiResult(List<String> htmlAttributions, String nextPageToken, List<ResultPlaces> results, String status) {
        this.htmlAttributions = htmlAttributions;
        this.nextPageToken = nextPageToken;
        this.results = results;
        this.status = status;
    }

    public String getStatus() {

        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getHtmlAttributions() {
        return htmlAttributions;
    }

    public void setHtmlAttributions(List<String> htmlAttributions) {
        this.htmlAttributions = htmlAttributions;
    }

    public String getNextPageToken() {
        return nextPageToken;
    }

    public void setNextPageToken(String nextPageToken) {
        this.nextPageToken = nextPageToken;
    }

    public List<ResultPlaces> getResults() {
        return results;
    }

    public void setResults(List<ResultPlaces> results) {
        this.results = results;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
