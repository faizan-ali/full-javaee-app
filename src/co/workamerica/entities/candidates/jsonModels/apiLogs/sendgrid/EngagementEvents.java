package co.workamerica.entities.candidates.jsonModels.apiLogs.sendgrid;

/**
 * Created by Faizan on 6/28/2016.
 */
public class EngagementEvents {

    private String date;
    private String time;
    private String event;
    private String userAgent;

    public EngagementEvents  () {

    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }
}
