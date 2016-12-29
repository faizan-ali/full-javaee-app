package co.workamerica.jobs.jsonModels.Indeed;

import com.google.gson.annotations.SerializedName;

public class IndeedResult {
	
	@SerializedName("jobtitle")
	private String jobTitle;
	@SerializedName("company")
	private String company;
	@SerializedName("city")
	private String city;
	@SerializedName("state")
	private String state;
	@SerializedName("formattedLocation")
	private String formattedLocation;
	@SerializedName("formattedLocationFull")
	private String formattedLocationFull;
	@SerializedName("source")
	private String source;
	@SerializedName("date")
	private String date;
	@SerializedName("snippet")
	private String snippet;
	@SerializedName("url")
	private String URL;
	@SerializedName("jobkey")
	private String jobKey;
	@SerializedName("indeedApply")
	private String indeedApply;
	@SerializedName("formattedRelativeTime")
	private String formattedRelativeTime;
	
	public IndeedResult () {
		
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getFormattedLocationFull() {
		return formattedLocationFull;
	}

	public void setFormattedLocationFull(String formattedLocationFull) {
		this.formattedLocationFull = formattedLocationFull;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getSnippet() {
		return snippet;
	}

	public void setSnippet(String snippet) {
		this.snippet = snippet;
	}

	public String getURL() {
		return URL;
	}

	public void setURL(String uRL) {
		URL = uRL;
	}

	public String getJobKey() {
		return jobKey;
	}

	public void setJobKey(String jobKey) {
		this.jobKey = jobKey;
	}

	public String getIndeedApply() {
		return indeedApply;
	}

	public void setIndeedApply(String indeedApply) {
		this.indeedApply = indeedApply;
	}

	public String getFormattedRelativeTime() {
		return formattedRelativeTime;
	}

	public void setFormattedRelativeTime(String formattedRelativeTime) {
		this.formattedRelativeTime = formattedRelativeTime;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getFormattedLocation() {
		return formattedLocation;
	}

	public void setFormattedLocation(String formattedLocation) {
		this.formattedLocation = formattedLocation;
	}
}
