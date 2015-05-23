package afpcsoft.com.br.bestplaces.model.placesApi;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by AndréFelipe on 16/05/2015.
 */
public class OpeningHoursPlaces implements Serializable {
    @SerializedName("open_now")
    private boolean openNow;

    @SerializedName("weekday_text")
    private List<String> weekDay;

    public OpeningHoursPlaces() {
    }

    public OpeningHoursPlaces(boolean openNow, List<String> weekDay) {
        this.openNow = openNow;
        this.weekDay = weekDay;
    }

    public boolean getOpenNow() {
        return openNow;
    }

    public void setOpenNow(boolean openNow) {
        this.openNow = openNow;
    }

    public List<String> getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(List<String> weekDay) {
        this.weekDay = weekDay;
    }
}
