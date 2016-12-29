package co.workamerica.entities.candidates.jsonModels.apiLogs.sendgrid;

import java.util.ArrayList;

/**
 * Created by Faizan on 6/27/2016.
 */
public class SendGridWelcome {

    private String emailID;
    private String success;
    private ArrayList<DeliveryEvents> deliveryEvents;
    private ArrayList<EngagementEvents> engagementEvents;

    public SendGridWelcome() {

    }

    public String getEmailID() {
        return emailID;
    }

    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }

    public ArrayList<DeliveryEvents> getDeliveryEvents() {
        return deliveryEvents;
    }

    public void setDeliveryEvents(ArrayList<DeliveryEvents> deliveryEvents) {
        this.deliveryEvents = deliveryEvents;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public ArrayList<EngagementEvents> getEngagementEvents() {
        return engagementEvents;
    }

    public void setEngagementEvents(ArrayList<EngagementEvents> engagementEvents) {
        this.engagementEvents = engagementEvents;
    }
}
