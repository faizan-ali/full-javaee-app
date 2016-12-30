# full-javaee-app
##### A complete Java EE Application w/ Servlets, JPA with significant functionality

###### An earlier version of the WorkAmerica platform
Visit us at www.workamerica.co

This codebase is amongst our earlier approaches to developing a full-fledged application (an MVP of sorts). Realizing that it's sitting around gathering dust, we decided to publish the code hoping that it may be of use to somebody out there.

Some of the technologies employed are rather archaic (at least in their implementation), while some can be found in modern applications today. The application is built in Java EE 7, serving content on Java Server Pages (JSPs). It employs EclipseLink 2.0 as the ORM communicating with a MySQL database. The frontend is built on Bootstrap 3 with sprinklings of pure (and sometimes lousy) JavaScript and jQuery here and there.

The application is essentially a database of candidates in the skilled trades (with profiles and logins) that can be searched for by specific criteria such as their credentials, trade, or location. It allows candidates to login and fill out their profiles, and allows employers to login, search for, and filter out candidates to hire. It has all the functionality to do all the above plus much more.

Over the next few months I'll compile examples here of what I think may be useful code across this codebase. This can include API calls on popular services such as Twilio or SendGrid, or more complex scenarios such as implementing a radius-based search of people using the Haversine formula. Since the product's core is data exchange and functionality via pure Servlets, an almost legacy technology (IMO), most of the useful tidbits will be found in helper classes I wrote. 

This is a "dead" codebase so existing bugs/unclear or outdated code will not be fixed. **But questions can be posted with the help-wanted label.** It was uploaded here almost "as is", stripped of proprietary functionality and keys/secrets.

Feel free to dig through and use what you may find, or contact me to clarify things in the codebase.

Cheers


##### Useful Examples:

###### External APIs:

1. [Add a new member to a list using the MailChimp API v3.0](https://github.com/faizan-ali/full-javaee-app/blob/master/src/co/workamerica/functionality/mailchimp/MailChimpNewsletter.java)
1. [Register a user using Facebook authentication](https://github.com/faizan-ali/full-javaee-app/blob/master/src/co/workamerica/functionality/facebook/FacebookSignupServlet.java)
1. [Send an e-mail via SendGrid](https://github.com/faizan-ali/full-javaee-app/blob/master/src/co/workamerica/functionality/sendgrid/API/SendGridObject.java)
5. [Add a new SendGrid contact](https://github.com/faizan-ali/full-javaee-app/blob/master/src/co/workamerica/functionality/sendgrid/API/SendGridContacts.java)
6. [Set up a Webhook for SendGrid delivery events via POST](https://github.com/faizan-ali/full-javaee-app/blob/master/src/co/workamerica/functionality/sendgrid/Webhooks/SendGridWebhook.java)
7. [Use Google's Geocode API](https://github.com/faizan-ali/full-javaee-app/blob/master/src/co/workamerica/functionality/google/api/Geocode.java)
9. [Send a text message using the official Twilio Helper Library v3.4.5](https://github.com/faizan-ali/full-javaee-app/blob/master/src/co/workamerica/functionality/twilio/API/Twilio.java)
10. [Purchase a Twilio number using the official Twilio Helper Library v3.4.5](https://github.com/faizan-ali/full-javaee-app/blob/master/src/co/workamerica/functionality/twilio/API/TwilioNumberPurchaser.java)
11. [Set up a Webhook for Twilio delivery events via POST](https://github.com/faizan-ali/full-javaee-app/blob/master/src/co/workamerica/functionality/twilio/Webhooks/TwilioWebhook.java)
12. [Set up a Webhook to receive Twilio text messages via POST](https://github.com/faizan-ali/full-javaee-app/blob/master/src/co/workamerica/functionality/twilio/Webhooks/TwilioReceiverServlet.java)
13. [Upload a file to an AWS S3 Bucket using the AWS SDK v1.9.6](https://github.com/faizan-ali/full-javaee-app/blob/master/src/co/workamerica/functionality/aws/s3/S3Object.java)

###### JPA Related:

14. [Obtain a JPA EntityManagerFactory with configurations obtained from Environment variables](https://github.com/faizan-ali/full-javaee-app/blob/master/src/co/workamerica/functionality/shared/EMFUtil.java)
15. [Retrieve a file from MySQL via a JPA entity and use Apache Tika to determine the file type](https://github.com/faizan-ali/full-javaee-app/blob/master/src/co/workamerica/functionality/shared/PictureRetrieverServlet.java)
1. [Upload a file via POST in a Servlet and persist it to MySQL in a JPA entity](https://github.com/faizan-ali/full-javaee-app/blob/master/src/co/workamerica/functionality/candidates/profile/PictureUploadServlet.java)
1. [Various JPA functionality](https://github.com/faizan-ali/full-javaee-app/tree/master/src/co/workamerica/functionality)

###### Java EE Related:

1. [Upload a file via POST in a Servlet](https://github.com/faizan-ali/full-javaee-app/blob/master/src/co/workamerica/functionality/candidates/profile/PictureUploadServlet.java)
2. [Implement a 'Login' via Servlet](https://github.com/faizan-ali/full-javaee-app/blob/master/src/co/workamerica/functionality/shared/authentication/LoginServlet.java)
3. [Implement a 'Logout' via Servlet if using sessions](https://github.com/faizan-ali/full-javaee-app/blob/master/src/co/workamerica/functionality/shared/authentication/LogoutServlet.java)
1. [Various Java EE functionality](https://github.com/faizan-ali/full-javaee-app/tree/master/src/co/workamerica/functionality)
1. [Examples of JSP files](https://github.com/faizan-ali/full-javaee-app/tree/master/WebContent)
1. [Importing a file using JSTL (look for c:import)](https://github.com/faizan-ali/full-javaee-app/blob/master/WebContent/pipeline.jsp)
1. [Using a JSTL for loop (look for c:forEach)](https://github.com/faizan-ali/full-javaee-app/blob/master/WebContent/pipeline.jsp)
1. [Using a JSTL if/else|switch statement (look for c:choose)](https://github.com/faizan-ali/full-javaee-app/blob/master/WebContent/pipeline.jsp)
1. [Using a JSTL if statement (look for c:if)](https://github.com/faizan-ali/full-javaee-app/blob/master/WebContent/pipeline.jsp)
1. [Using Expression Language (EL) in a JSP (look for ${} tags)](https://github.com/faizan-ali/full-javaee-app/blob/master/WebContent/pipeline.jsp)

###### Utilities/Misc/External Libraries.

8. [Calculate the distance between a pair of Latitude/Longitude Co-ordinates (Using the  Haversine formula)](https://github.com/faizan-ali/full-javaee-app/blob/master/src/co/workamerica/functionality/google/api/Geocode.java)
15. [Use Apache Tika to determine the file type of a Blob from a MySql database](https://github.com/faizan-ali/full-javaee-app/blob/master/src/co/workamerica/functionality/shared/PictureRetrieverServlet.java)
1. [Convert a Unix timestamp (in ms) to a Date object](https://github.com/faizan-ali/full-javaee-app/blob/master/src/co/workamerica/functionality/shared/utilities/Clock.java)
1. [Generate a random String of length n](https://github.com/faizan-ali/full-javaee-app/blob/master/src/co/workamerica/functionality/shared/utilities/CustomUtilities.java)
1. [Capitalize the first letter of a String](https://github.com/faizan-ali/full-javaee-app/blob/master/src/co/workamerica/functionality/shared/utilities/CustomUtilities.java)
1. [Get the response of a GET request as a String](https://github.com/faizan-ali/full-javaee-app/blob/master/src/co/workamerica/functionality/shared/utilities/CustomUtilities.java)
1. [Get the client IP from a Servlet request](https://github.com/faizan-ali/full-javaee-app/blob/master/src/co/workamerica/functionality/shared/utilities/CustomUtilities.java)
1. [Read through an .XLSX file using Apache POI](https://github.com/faizan-ali/full-javaee-app/blob/master/src/co/workamerica/functionality/administrators/candidates/ProfileImportServlet.java)
1. [Consume a POST or GET request using Jersey](https://github.com/faizan-ali/full-javaee-app/blob/master/src/co/workamerica/api/webhooks/Partners.java)
