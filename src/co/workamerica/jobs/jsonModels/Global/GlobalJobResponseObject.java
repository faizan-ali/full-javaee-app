package co.workamerica.jobs.jsonModels.Global;

import co.workamerica.jobs.jsonModels.CareerBuilder.CareerBuilderJobSearchResult;
import co.workamerica.jobs.jsonModels.Indeed.IndeedResponse;
import co.workamerica.jobs.jsonModels.Indeed.IndeedResult;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;

public class GlobalJobResponseObject {

	private String query;

	private String location;

	private String totalResults;

	private String start;

	private String end;

	private String pageNumber;

	private LinkedHashSet<GlobalJobResultObject> results = new LinkedHashSet<GlobalJobResultObject>();

	public GlobalJobResponseObject () {
		this.results = new LinkedHashSet<>();
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

	public String getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(String pageNumber) {
		this.pageNumber = pageNumber;
	}

	public HashSet<GlobalJobResultObject> getResults() {
		return results;
	}

	public void setResults(LinkedHashSet<GlobalJobResultObject> results) {
		this.results = results;
	}

	public void mergeResults (IndeedResponse indeedResponse, List<CareerBuilderJobSearchResult> cbResponse) {

		if (indeedResponse != null) {
			for (IndeedResult indeed : indeedResponse.getResults()) {
				GlobalJobResultObject obj = new GlobalJobResultObject();
				obj.setJobTitle(indeed.getJobTitle());
				obj.setCompany(indeed.getCompany());
				obj.setCity(indeed.getCity());
				obj.setState(indeed.getState());
				obj.setLocation(indeed.getFormattedLocationFull());
				obj.setDate(indeed.getFormattedRelativeTime());
				obj.setSnippet(indeed.getSnippet());
				obj.setURL(indeed.getURL());
				this.results.add(obj);
			}
		}

		if (cbResponse != null) {
			for (CareerBuilderJobSearchResult cb : cbResponse) {
				GlobalJobResultObject obj = new GlobalJobResultObject();
				obj.setJobTitle(cb.getJobTitle());
				obj.setCompany(cb.getCompany());
				obj.setCity(cb.getCity());
				obj.setState(cb.getState());
				obj.setLocation(cb.getLocation());
				obj.setDate(cb.getPostedDate());
				obj.setSnippet(cb.getSnippet());
				obj.setURL(cb.getURL());
				this.results.add(obj);
			}
		}
	}
}
