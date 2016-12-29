package co.workamerica.jobs.jsonModels.CareerBuilder;

import com.google.gson.annotations.SerializedName;

public class CareerBuilderResponse {
	
	@SerializedName("TotalPages")
	public String totalPages;
	@SerializedName("TotalCount")
	public String totalCount;
	@SerializedName("FirstItemIndex")
	public String firstItemIndex;
	@SerializedName("LastItemIndex")
	public String lastItemIndex;
	@SerializedName("Results")
	public CareerBuilderResult result;
	
	public String getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(String totalPages) {
		this.totalPages = totalPages;
	}
	public String getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}
	public String getFirstItemIndex() {
		return firstItemIndex;
	}
	public void setFirstItemIndex(String firstItemIndex) {
		this.firstItemIndex = firstItemIndex;
	}
	public String getLastItemIndex() {
		return lastItemIndex;
	}
	public void setLastItemIndex(String lastItemIndex) {
		this.lastItemIndex = lastItemIndex;
	}
	public CareerBuilderResult getResult() {
		return result;
	}
	public void setResult (CareerBuilderResult result) {
		this.result = result;
	}
}
