package co.workamerica.entities.candidates.jsonModels.apiLogs.twilio;

import java.util.ArrayList;

/**
 * Created by Faizan on 6/27/2016.
 */
public class TwilioWelcome {

    private String phone;
    private String messageID;
    private String body;
    private String success;
    private ArrayList<TwilioWelcomeEvents> twilioWelcomeEvents;

    public TwilioWelcome() {

    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getMessageID() {
        return messageID;
    }

    public void setMessageID(String messageID) {
        this.messageID = messageID;
    }

    public ArrayList<TwilioWelcomeEvents> getTwilioWelcomeEvents() {
        return twilioWelcomeEvents;
    }

    public void setTwilioWelcomeEvents(ArrayList<TwilioWelcomeEvents> twilioWelcomeEvents) {
        this.twilioWelcomeEvents = twilioWelcomeEvents;
    }

    public void addToTwilioWelcomeEvents (TwilioWelcomeEvents event) {
        if (event != null) {
            if (twilioWelcomeEvents == null) {
                twilioWelcomeEvents = new ArrayList<TwilioWelcomeEvents>();
            }
            twilioWelcomeEvents.add(event);
        }
    }
}
