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

public class YelpReviewGetter {
	
	/*public static void main(String[] args) {
		// TODO Auto-generated method stub
		String result = getPlaceID("MountainView Hospital");
		ArrayList<Review> yelpReviews = getReviewInfo(result);
	}*/
	
	public static String getPlaceID(String placeName) {
		String output = "";
		try {
			URL url = new URL("https://raw.githubusercontent.com/cdonegan01/jsonStorage/master/testBusiness.json");
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
					JSONObject jsonobject = (JSONObject)parse.parse(scanner.nextLine());
					if (jsonobject.get("name").toString().equalsIgnoreCase(placeName)) {
						output = jsonobject.get("business_id").toString();
						break;
					}
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
	
	public static String getUserName(String userID) {
		String output = "";
		try {
			URL url = new URL("https://raw.githubusercontent.com/cdonegan01/jsonStorage/master/testUser.json");
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
					JSONObject jsonobject = (JSONObject)parse.parse(scanner.nextLine());
					if (jsonobject.get("user_id").toString().equalsIgnoreCase(userID)) {
						output = jsonobject.get("name").toString();
						break;
					}
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
	
	public static ArrayList<Review> getReviewInfo(String placeID) {
		ArrayList<Review> reviewList = new ArrayList<Review>();
		try {
			URL url1 = new URL("https://raw.githubusercontent.com/cdonegan01/jsonStorage/master/testReview.json");
			HttpURLConnection conn1 = (HttpURLConnection)url1.openConnection();
			conn1.setRequestMethod("GET");
			conn1.connect();
			int responsecode = conn1.getResponseCode();
			if(responsecode != 200) {
				throw new RuntimeException("HttpResponseCode: "+responsecode);
			} else {
				JSONParser parse = new JSONParser();
				Scanner scanner = new Scanner(url1.openStream());
				while (scanner.hasNext()) {
					JSONObject jsonobject = (JSONObject)parse.parse(scanner.nextLine());
					if (jsonobject.get("business_id").toString().equalsIgnoreCase(placeID)) {
						Review newReview = new Review();
						newReview.setReviewer(getUserName(jsonobject.get("user_id").toString()));
						newReview.setReview(jsonobject.get("text").toString());
						newReview.setRating(jsonobject.get("stars").toString());
						reviewList.add(newReview);
					}
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
