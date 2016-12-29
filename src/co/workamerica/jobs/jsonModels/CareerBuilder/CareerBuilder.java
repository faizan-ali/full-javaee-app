package co.workamerica.jobs.jsonModels.CareerBuilder;

import co.workamerica.functionality.shared.utilities.CustomUtilities;
import com.google.gson.Gson;
import org.json.XML;

import java.util.List;

public class CareerBuilder {

	private static final String preDefinedLimit = "15", developerKey = "developerkey=WDHV6XG6DTMS41C66DY5";

	public CareerBuilder() {

	}

	
	public static List<CareerBuilderJobSearchResult> getResponse (String candidateLocation, String jobQuery) {
		
		// Keywords matching CareerBuilder matched to Indeed : keywords = query, location = location, orderBy = sort, empType = jobType, pageNumber = start
		// perPage = limit, payLow = salary
		String keywords = "keywords=" + jobQuery, location = "location=" + candidateLocation, orderBy = "orderby=", empType = "emptype=", pageNumber = "pagenumber=",
				perPage = "perpage=" + preDefinedLimit, payLow = "paylow=", requestURL = "http://api.careerbuilder.com/v2/jobsearch?";

		// Master URL used to query CB database
		requestURL += developerKey + "&" + keywords + "&" + location + "&" + orderBy + "&" + empType + "&" + pageNumber + "&" + perPage + "&" + payLow;

        try {
            // Reading XML, converting to JSON
            String xml = CustomUtilities.RequestToString(requestURL);
            if (xml != null) {
                String json = XML.toJSONObject(xml).toString();
                Gson gson = new Gson();
                CareerBuilderMaster s = gson.fromJson(json, CareerBuilderMaster.class);
                return s.getResponse().getResult().getJobResults();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
	}
}
