package afpcsoft.com.br.bestplaces.model.detailsPlacesApi;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by AndréFelipe on 17/05/2015.
 */
public class DetailsApiResult {

    @SerializedName("html_attributions")
    private List<String> htmlAttributions;

    @SerializedName("result")
    private ResultDetails result;

    @SerializedName("status")
    private String status;

    public DetailsApiResult() {
    }

    public DetailsApiResult(List<String> htmlAttributions, ResultDetails result, String status) {

        this.htmlAttributions = htmlAttributions;
        this.result = result;
        this.status = status;
    }

    public List<String> getHtmlAttributions() {

        return htmlAttributions;
    }

    public void setHtmlAttributions(List<String> htmlAttributions) {
        this.htmlAttributions = htmlAttributions;
    }

    public ResultDetails getResult() {
        return result;
    }

    public void setResult(ResultDetails result) {
        this.result = result;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
