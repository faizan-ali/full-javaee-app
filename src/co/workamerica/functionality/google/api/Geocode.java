package co.workamerica.functionality.google.api;

import co.workamerica.functionality.shared.utilities.CustomUtilities;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;

public class Geocode {

	private final static String key = "";
	// Radius of earth in KM for Haversine calculation
	public static final double R = 6372.8;

	// Returns an array where [0] is latitude and [1] is longitude
	// Expects a validated String (City,State or ZIP)
	public static String[] toGeocode(String location) {

        String request = "https://maps.googleapis.com/maps/api/geocode/json?";
        String address = "address=";

		String[] result = new String [] {"", ""};
		String finalRequest = request + address + location + "&" + key;

		String jsonString = CustomUtilities.RequestToString(finalRequest);

        if (jsonString != null) {
            JSONObject json = new JSONObject(jsonString);

            if (json.getString("status").equals("OK")) {
                String latitude = "", longitude = "";

                latitude = "" + json.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getDouble("lat");
                longitude = "" + json.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getDouble("lng");

                result[0] = latitude;
                result[1] = longitude;
            }
        }
		return result;
	}

	// Returns a ZIP that encompasses the given latitude and longitude
	// TODO: Rewrite to clean up JSON parsing
	@SuppressWarnings("rawtypes")
	public static String getZipFromLatLong(String latitude, String longitude) {
        String request = "https://maps.googleapis.com/maps/api/geocode/json?";
        String address = "address=";
        String coOrdinates = "latlng=";
		CustomUtilities custom = new CustomUtilities();
		String zip = "";
		String finalRequest = request + coOrdinates + latitude + "," + longitude + "&" + key;

		// To convert resulting JSON into Map
		Gson gson = new Gson();

		// Models expected JSON, very basic
		Type type = new TypeToken<Map<String, Object>>() {
		}.getType();

		// Pulls JSON from api request
		String json = custom.RequestToString(finalRequest);

		// Creates map using Gson
		Map<String, Object> map = gson.fromJson(json, type);
		ArrayList list = ((ArrayList)((LinkedTreeMap)((ArrayList) map.get("results")).get(0)).get("address_components"));
		
		for (Object elt : list) {
			LinkedTreeMap treeElt = (LinkedTreeMap) elt;
			String name = (String) ((ArrayList)treeElt.get("types")).get(0);
			
			if (name.equals("postal_code")) {
				zip = (String) (treeElt.get("short_name"));
				break;
			}
		}
		
		return zip == null ? " " : zip;
	}

	public static String [] toGeoCodeHelper (String zip, String city, String state) {
		if (zip != null && !zip.isEmpty()) {
			return toGeocode (zip);
		} else {
			if (city != null && state != null && city.length() > 2 && state.length() == 2) {
				return toGeocode(city.replaceAll("\\s", "+") + "+" + state.replaceAll("\\s", "+"));
			}
		}
		return new String [] {"", ""};
	}
	// Calculates the distance between a pair of lat/long co-ordinates and
	// returns the number
	// as an Integer ceiling of the resultant Double in miles
	public static int haversine(double lat1, double lon1, double lat2, double lon2) {
		double dLat = Math.toRadians(lat2 - lat1);
		double dLon = Math.toRadians(lon2 - lon1);
		lat1 = Math.toRadians(lat1);
		lat2 = Math.toRadians(lat2);

		double a = Math.pow(Math.sin(dLat / 2), 2) + Math.pow(Math.sin(dLon / 2), 2) * Math.cos(lat1) * Math.cos(lat2);
		double c = 2 * Math.asin(Math.sqrt(a));

		// *0.621 to convert to miles
		return (int) Math.ceil(R * c * 0.621);
	}
}
