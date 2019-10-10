package test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class GoogleReviewGetter {
	
	
	/**
	 * This method retrieves the ID of the business from the related Google Business JSON file. 
	 * @param placeName
	 * @return output
	 */
	public static String getPlaceID(String placeName) {
		String output = "";
		try {
			URL url = new URL("https://maps.googleapis.com/maps/api/place/findplacefromtext/json?input="+placeName+"&inputtype=textquery&fields=place_id&key=AIzaSyAcM2vc8-2JY9I5P7jgvt61TCYa1vo0b98");
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod("GET");
			conn.connect();
			int responsecode = conn.getResponseCode();
			if(responsecode != 200) {
				throw new RuntimeException("HttpResponseCode: "+responsecode);
			} else {
				JSONParser parse = new JSONParser();
				Scanner scanner = new Scanner(url.openStream());
				while (scanner.hasNext()) {
					output+=scanner.nextLine();
				}
				JSONObject jsonobject = (JSONObject)parse.parse(output);
				JSONArray resultArray = (JSONArray) jsonobject.get("candidates");
				for(int count=0; count<1;count++) {
					JSONObject jsonobject1 = (JSONObject)resultArray.get(count);
					System.out.println(placeName+" Place ID: "+jsonobject1.get("place_id"));
					output = (String) jsonobject1.get("place_id");
				}
				scanner.close();
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return output;
	}
	
	/**
	 * This method retrieves the associated reviews by using the placeID retrieved with the "getPlaceID" method.
	 * @param placeID
	 * @return reviewList
	 */
	public static ArrayList<Review> getReviewInfo(String placeID) {
		ArrayList<Review> reviewList = new ArrayList<Review>();
		String output = "";
		try {
			URL url = new URL("https://maps.googleapis.com/maps/api/place/details/json?place_id="+placeID+"&fields=review&key=AIzaSyAcM2vc8-2JY9I5P7jgvt61TCYa1vo0b98");
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod("GET");
			conn.connect();
			int responsecode = conn.getResponseCode();
			if(responsecode != 200) {
				throw new RuntimeException("HttpResponseCode: "+responsecode);
			} else {
				JSONParser parse = new JSONParser();
				Scanner scanner = new Scanner(url.openStream());
				while (scanner.hasNext()) {
					output+=scanner.nextLine();
				}
				JSONObject jsonobject = (JSONObject)parse.parse(output);
				JSONObject jsonobject2 = (JSONObject) jsonobject.get("result");
				JSONArray reviewArray = (JSONArray)jsonobject2.get("reviews");
				for (int count=0; count<reviewArray.size(); count++) {
					JSONObject jsonObject3 = (JSONObject) reviewArray.get(count);
					Review newReview = new Review();
					 newReview.setReviewer(jsonObject3.get("author_name").toString());
					 newReview.setReview(jsonObject3.get("text").toString());
					 newReview.setRating(jsonObject3.get("rating").toString()+".0");
					 reviewList.add(newReview);
				}
				System.out.println("Reviews retrieved.");
				scanner.close();
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return reviewList;
	}

}
