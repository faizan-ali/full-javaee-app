package co.workamerica.functionality.candidates.jobs;

import co.workamerica.entities.candidates.Candidates;
import co.workamerica.jobs.jsonModels.CareerBuilder.CareerBuilder;
import co.workamerica.jobs.jsonModels.CareerBuilder.CareerBuilderJobSearchResult;
import co.workamerica.jobs.jsonModels.Global.GlobalJobResponseObject;
import co.workamerica.jobs.jsonModels.Indeed.Indeed;
import co.workamerica.jobs.jsonModels.Indeed.IndeedResponse;

import java.util.List;

public class RecommendedJobsRetriever {

	public RecommendedJobsRetriever() {

	}

	// Returns a GlobalJobResponseObject containing job results from both Indeed
	// and CB
	// TODO: Exceptions?
	public static GlobalJobResponseObject getRecommendedJobs(Candidates candidate) {

		GlobalJobResponseObject globalResponse = new GlobalJobResponseObject();
        IndeedResponse indeedResponse;
        List<CareerBuilderJobSearchResult> cbResponse;

		// Pulls jobs from both obtained and anticipated certifications
		String certifications = "";
		if (candidate.getAnticipatedCertification() != null && !candidate.getAnticipatedCertification().isEmpty()) {
			certifications = candidate.getAnticipatedCertification().replaceAll("\\s+", "+");
		}

		if (candidate.getObtainedCertification() != null && !candidate.getObtainedCertification().isEmpty() && certifications.isEmpty()) {
				certifications = candidate.getObtainedCertification().replaceAll("\\s+", "+");
		}

		if (!certifications.isEmpty()) {
            String location = "";

			if (candidate.getZip() != null && !candidate.getZip().isEmpty()) {
                location = candidate.getZip().trim();

			} else if (candidate.getCity() != null && !candidate.getCity().isEmpty() && candidate.getState() != null && !candidate.getState().isEmpty()) {
                location = candidate.getCity().replace("\\s+", "+") + "," + candidate.getState();
			} else {
                return null;
            }

            indeedResponse = Indeed.getResponse(location, certifications);
            cbResponse = CareerBuilder.getResponse(location, certifications);
            globalResponse.setLocation(location);

			globalResponse.setQuery(certifications);
			globalResponse.mergeResults(indeedResponse, cbResponse);
		}
		return globalResponse;
	}
}
