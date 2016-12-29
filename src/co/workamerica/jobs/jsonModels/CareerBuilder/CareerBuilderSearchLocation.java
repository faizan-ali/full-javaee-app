package co.workamerica.jobs.jsonModels.CareerBuilder;

import com.google.gson.annotations.SerializedName;

public class CareerBuilderSearchLocation {
	@SerializedName("City")
	public String city;
	@SerializedName("StateCode")
	public String state;
	@SerializedName("PostalCode")
	public String zip;
	@SerializedName("Valid")
	public String valid;
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
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getValid() {
		return valid;
	}
	public void setValid(String valid) {
		this.valid = valid;
	}
	
}
