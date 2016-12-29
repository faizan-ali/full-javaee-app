package co.workamerica.entities.candidates.jsonModels.apiLogs.twilio;

/**
 * Created by Faizan on 6/27/2016.
 */
public class TwilioWelcomeEvents {

    private String date;
    private String time;
    private String status;
    private String error;

    public TwilioWelcomeEvents() {

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
