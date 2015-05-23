package afpcsoft.com.br.bestplaces.model.detailsPlacesApi;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by AndréFelipe on 17/05/2015.
 */
public class OpeningHoursDetails {

    @SerializedName("open_now")
    private boolean openNow;

    @SerializedName("weekday_text")
    private List<String> weekdayText;

    public OpeningHoursDetails() {
    }

    public OpeningHoursDetails(boolean openNow, List<String> weekdayText) {

        this.openNow = openNow;
        this.weekdayText = weekdayText;
    }

    public boolean isOpenNow() {

        return openNow;
    }

    public void setOpenNow(boolean openNow) {
        this.openNow = openNow;
    }

    public List<String> getWeekdayText() {
        return weekdayText;
    }

    public void setWeekdayText(List<String> weekdayText) {
        this.weekdayText = weekdayText;
    }
}
