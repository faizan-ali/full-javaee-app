package co.workamerica.jobs.jsonModels.CareerBuilder;

import com.google.gson.annotations.SerializedName;

public class CareerBuilderMaster {
	@SerializedName("ResponseJobSearch")
	public CareerBuilderResponse response;
	
	public CareerBuilderResponse getResponse () {
		return response;
	}
}
