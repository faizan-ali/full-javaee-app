package co.workamerica.jobs.jsonModels.Indeed;

import co.workamerica.functionality.shared.utilities.CustomUtilities;
import com.google.gson.Gson;

public class Indeed {

    private static final String publisher = "publisher=2638867674656292", version = "v=2", format = "format=json", highlight = "highlight=0", preDefinedLimit = "15";

    public Indeed() {

    }


    public static IndeedResponse getResponse(String candidateLocation, String jobQuery) {


        String query = "q=" + jobQuery, location = "l=" + candidateLocation, sort = "sort=", radius = "radius=", jobType = "jt=", start = "start=",
                limit = "limit=" + preDefinedLimit, fromage = "fromage=", salary = "salary=",
                filter = "filter=1", userIP = "userip=127.0.0.1", userAgent = "useragent=Chrome",
                requestURL = "http://api.indeed.com/ads/apisearch?";

        // The master URL that is used to query the Indeed database
        requestURL += publisher + "&" + version + "&" + format + "&" + query + "&" + location + "&" + sort + "&"
                + radius + "&" + jobType + "&" + start + "&" + limit + "&" + fromage + "&" + highlight + "&" + filter
                + "&" + salary + "&" + userIP + "&" + userAgent;

        try {
            String json = CustomUtilities.RequestToString(requestURL);
            Gson gson = new Gson();
            IndeedResponse searchResponse = gson.fromJson(json, IndeedResponse.class);

            // Cleaning results
            for (IndeedResult result : searchResponse.getResults()) {
                String newSnippet = result.getSnippet();
                newSnippet = newSnippet.replace("\\?", "'");
                result.setSnippet(newSnippet);
            }
            return searchResponse;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
