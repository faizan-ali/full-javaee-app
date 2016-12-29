package co.workamerica.jobs.jsonModels.CareerBuilder;

import com.google.gson.annotations.SerializedName;

public class CareerBuilderJobSearchResult {

	@SerializedName("City")
	public String city;
	@SerializedName("State")
	public String state;
	@SerializedName("Company")
	public String company;
	@SerializedName("EmploymentType")
	public String employmentType;
	@SerializedName("LocationLatitude")
	public String latitude;
	@SerializedName("LocationLongitude")
	public String longitude;
	@SerializedName("Location")
	public String location;
	@SerializedName("PostedDate")
	public String postedDate;
	@SerializedName("Pay")
	public String pay;
	@SerializedName("JobTitle")
	public String jobTitle;
	@SerializedName("JobDetailsURL")
	public String URL;
	@SerializedName("DescriptionTeaser")
	public String snippet;
	
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getEmploymentType() {
		return employmentType;
	}
	public void setEmploymentType(String employmentType) {
		this.employmentType = employmentType;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getPostedDate() {
		return postedDate;
	}
	public void setPostedDate(String postedDate) {
		this.postedDate = postedDate;
	}
	public String getPay() {
		return pay;
	}
	public void setPay(String pay) {
		this.pay = pay;
	}
	public String getJobTitle() {
		return jobTitle;
	}
	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getURL() {
		return URL;
	}
	public void setURL(String uRL) {
		URL = uRL;
	}
	public String getSnippet() {
		return snippet;
	}
	public void setSnippet(String snippet) {
		this.snippet = snippet;
	}
	
}
