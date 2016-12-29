package co.workamerica.jobs.jsonModels.Indeed;

import com.google.gson.annotations.SerializedName;

import java.util.HashSet;
import java.util.LinkedHashSet;

public class IndeedResponse {

	@SerializedName("query")
	private String query;
	@SerializedName("location")
	private String location;
	@SerializedName("dupefilter")
	private String dupeFilter;
	@SerializedName("highlight")
	private String highlight;
	@SerializedName("totalResults")
	private String totalResults;
	@SerializedName("start")
	private String start;
	@SerializedName("end")
	private String end;
	@SerializedName("radius")
	private String radius;
	@SerializedName("pageNumber")
	private String pageNumber;
	@SerializedName("results")
	private LinkedHashSet<IndeedResult> results;
	
	public IndeedResponse () {
		
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDupeFilter() {
		return dupeFilter;
	}

	public void setDupeFilter(String dupeFilter) {
		this.dupeFilter = dupeFilter;
	}

	public String getHighlight() {
		return highlight;
	}

	public void setHighlight(String highlight) {
		this.highlight = highlight;
	}

	public String getTotalResults() {
		return totalResults;
	}

	public void setTotalResults(String totalResults) {
		this.totalResults = totalResults;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public String getRadius() {
		return radius;
	}

	public void setRadius(String radius) {
		this.radius = radius;
	}

	public String getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(String pageNumber) {
		this.pageNumber = pageNumber;
	}

	public HashSet<IndeedResult> getResults() {
		return results;
	}

	public void setResults(LinkedHashSet<IndeedResult> results) {
		this.results = results;
	}
	
	
}
