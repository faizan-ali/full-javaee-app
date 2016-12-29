package co.workamerica.jobs.jsonModels.CareerBuilder;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CareerBuilderMetaData {
	@SerializedName("ResultsAlteredByUsersSearchPreferences")
	public String altered;
	@SerializedName("SearchLocations")
	public List<CareerBuilderSearchLocation> searchLocations;
	public String getAltered() {
		return altered;
	}
	public void setAltered(String altered) {
		this.altered = altered;
	}
	public List<CareerBuilderSearchLocation> getSearchLocations() {
		return searchLocations;
	}
	public void setSearchLocations(List<CareerBuilderSearchLocation> searchLocations) {
		this.searchLocations = searchLocations;
	}
	
	
}
