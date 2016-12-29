package co.workamerica.jobs.jsonModels.CareerBuilder;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CareerBuilderResult {

	@SerializedName("JobSearchResult")
	public List<CareerBuilderJobSearchResult> jobResults;

	public List<CareerBuilderJobSearchResult> getJobResults() {
		return jobResults;
	}

	public void setJobResults(List<CareerBuilderJobSearchResult> jobResults) {
		this.jobResults = jobResults;
	}
	
}
