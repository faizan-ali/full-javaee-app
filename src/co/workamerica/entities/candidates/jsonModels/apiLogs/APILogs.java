package co.workamerica.entities.candidates.jsonModels.apiLogs;

import co.workamerica.entities.candidates.jsonModels.apiLogs.sendgrid.SendGridWelcome;
import co.workamerica.entities.candidates.jsonModels.apiLogs.twilio.TwilioWelcome;

import java.util.ArrayList;

/**
 * Created by Faizan on 6/27/2016.
 */
public class APILogs {

    private ArrayList<TwilioWelcome> twilioWelcomes;
    private ArrayList<SendGridWelcome> sendGridWelcomes;

    public APILogs() {

    }

    public ArrayList<TwilioWelcome> getTwilioWelcomes() {
        return twilioWelcomes;
    }

    public void setTwilioWelcomes(ArrayList<TwilioWelcome> twilioWelcomes) {
        this.twilioWelcomes = twilioWelcomes;
    }

    public void addToTwilioWelcome(TwilioWelcome twilioWelcome) {
        if (twilioWelcome != null) {
            if (twilioWelcomes == null) {
                twilioWelcomes = new ArrayList<TwilioWelcome>();
            }
            twilioWelcomes.add(twilioWelcome);
        }
    }

    public TwilioWelcome getTwilioWelcomeByID(String messageID) {
        if (messageID != null && !messageID.isEmpty() && twilioWelcomes != null && !twilioWelcomes.isEmpty()) {
            for (TwilioWelcome twilioWelcome : twilioWelcomes) {
                if (twilioWelcome.getMessageID().equals(messageID)) {
                    return twilioWelcome;
                }
            }
        }
        return null;
    }

    public ArrayList<SendGridWelcome> getSendGridWelcomes() {
        return sendGridWelcomes;
    }

    public void setSendGridWelcomes(ArrayList<SendGridWelcome> sendGridWelcomes) {
        this.sendGridWelcomes = sendGridWelcomes;
    }

    public void addToSendGridWelcomes(SendGridWelcome welcome) {
        if (welcome != null) {
            if (sendGridWelcomes == null) {
                sendGridWelcomes = new ArrayList<SendGridWelcome>();
            }
            sendGridWelcomes.add(welcome);
        }
    }
}
