package co.workamerica.functionality.sendgrid.API;

import com.sendgrid.*;

public class SendGridObject {

    private final static String apiKey = "";

    public SendGridObject() {

    }


    public static boolean sendEmail(String emailAddress, String password, String name, String messageType)
             throws Exception {

        if (emailAddress.length() >= 6) {


            final String welcomeText = "Hi " + name + ", <br/><br/>"
                    + "It was great to meet you in class a few days ago! Thanks for filling out our paper registration form and joining WorkAmerica so that employers can find and contact you.<br/><br/>"
                    + "We've created your online profile and filled in the information you provided. Now you can log in and update it with a resume and profile picture so that employers can reach out to you. You can also update your account settings and password.<br/><br/>"
                    + "Please log into your account to verify the information entered. <br/><br/>"
                    + "<ul> <li>Access your account by clicking this link: http://www.workamerica.co/login </li>"
                    + "<li>Login E-mail: " + emailAddress + "</li>" + "<li>Login Password: " + password + "</li></ul><br/>"
                    + "We'll be sending emails over the next few weeks on some important job-hunting tactics, like resume building, how to best sell yourself, and more. If you have any question about your account or what we do please contact us.<br/><br/>"
                    + "Thank you, <br/>" + "The WorkAmerica Team!<br/><br/>"
                    + "Contact us at: info@workamerica.co or 877-750-2968, ext 2<br/>";
            final String schoolText = "Hi " + name + ", <br/><br/>"
                    + "Your school has taken the first step towards helping you find employment by starting your Job Seeker account at WorkAmerica.<br/><br/>"
                    + "WorkAmerica is the online recruiting website for students and graduates in all kinds of technical and skilled trades fields. Employers can already see your profile now!<br/><br/>"
                    + "<ul> <li>Access your account by clicking this link: http://www.workamerica.co/login </li>"
                    + "<li>Login E-mail: " + emailAddress + "</li>" + "<li>Login Password: " + password + "</li></ul><br/>"
                    + "If you haven't yet, make sure to fill out your profile completely, including your contact information, profile picture, credentials and resume.<br/><br/>"
                    + "As part of the WorkAmerica family our job is to help you stand out from the crowd, and help you find the perfect job for you. We'll be sending emails over the next few weeks on some important job-hunting tactics, like resume writing tips, best interview practices, and more. If you have any question about your account please contact us.<br/><br/>"
                    + "Thank you from the WorkAmerica Team!"
                    + "Contact us at: info@workamerica.co or 877-750-2968, ext 2<br/>";
            final String registerText = "Hi " + name + ", <br/><br/>"
                    + "Congrats on taking the first step towards finding employment by creating your WorkAmerica account. Employers can already see your profile now!<br/><br/>"
                    + "If you haven't yet, make sure to fill out your profile completely, including your contact information, profile picture, credentials and resume.<br/><br/>"
                    + "<ul> <li>Login to your profile using this link: http://www.workamerica.co/login </li>"
                    + "<li>Login E-mail: " + emailAddress + "</li>" + "<li>Login Password: " + password + "</li></ul><br/>"
                    + "As part of the WorkAmerica family our job is to help you stand out from the crowd, and help you find the perfect job for you. We'll be sending emails over the next few weeks on some important job-hunting tactics, like resume writing tips, best interview practices, and more. If you have any question about your account please contact us.<br/><br/>"
                    + "Thank you from the WorkAmerica Team!"
                    + "Contact us at: info@workamerica.co or 877-750-2968, ext 2<br/>";
            final String forgotText = "Hi " + name + ", <br/><br/>"
                    + "We've reset your password for your <b>WorkAmerica</b> profile. <br/><br/>"
                    + "Login to your profile with this temporary password, and be sure to change it under account settings. <br/><br/>"
                    + "Temporary password: " + password + "<br/>" + "E-mail: " + emailAddress + "<br/>"
                    + "Login website: http://www.workamerica.co/login <br/><br/>"
                    + "The WorkAmerica Team<br/>"
                    + "info@workamerica.co"
                    + "877-750-2968, ext 2<br/>";
            final String campaignText = "Thank you for requesting access to WorkAmerica. <br/>" +
                    "We've received your information and are reviewing it now. One of our team members will be " +
                                        "reaching out to you soon with a followup. <br/><br/>" +
                    "In the meantime, if you have any questions, you can reach us at info@workamerica.co, or 877-750-2968, ext 2. <br/><br/>" +
                    "We're looking forward to having you on the WorkAmerica network! <br/><br/>" +
                    "Best, <br/>" +
                    "The WorkAmerica Team";

            try {


                Email to = new Email();
                to.setEmail(emailAddress);
                to.setName(name);

                Email from = new Email();
                from.setEmail("info@workamerica.co");
                from.setName("Laura At WorkAmerica");

                String subject = "";
                Content content = new Content();
                content.setType("text/html");

                if (messageType.equalsIgnoreCase("Welcome")) {
                    subject = "Your WorkAmerica profile is live!";
                    content.setValue(welcomeText);
                } else if (messageType.equalsIgnoreCase("Error")) {
                    to.setEmail("faizan@workamerica.co");
                    to.setName("Faizan Ali");
                    from.setEmail("info@workamerica.co");
                    subject = "Error sending e-mail";
                    content.setValue("Error sending email to: " + emailAddress);
                } else if (messageType.equalsIgnoreCase("School")) {
                    subject = "Your WorkAmerica profile is live!";
                    content.setValue(schoolText);
                } else if (messageType.equalsIgnoreCase("Register")) {
                    subject = "Your WorkAmerica profile is live!";
                    content.setValue(registerText);
                } else if (messageType.equalsIgnoreCase("Forgot")) {
                    subject = "WorkAmerica Password Reset";
                    content.setValue(forgotText);
                } else if (messageType.equalsIgnoreCase("Campaign")) {
                    subject = "WorkAmerica Request Confirmation";
                    content.setValue(campaignText);
                }

                Mail mail = new Mail(from, subject, to, content);
                SendGrid sendgrid = new SendGrid(apiKey);
                Request request = new Request();
                request.method = Method.POST;
                request.endpoint = "mail/send";
                request.body = mail.build();
                Response response = sendgrid.api(request);
                String statusCode = response.statusCode + "";
                return statusCode.contains("20");
            } catch (Exception e) {
                e.printStackTrace();
            } catch (NoSuchMethodError e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
