package test;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@Path("/getReviews")
public class ReviewAPI {
	
	/**
	 * This API retrieves reviews from an inputted Business Name from Google Reviews or Yelp in JSON format, then
	 * outputs the result in HTML format.
	 * 
	 * A FEW THINGS TO KEEP IN MIND:
	 * 1. Ensure business name is inputted accurately!
	 * 2. Due to restrictions on non-premium accounts, sometimes it will return a code 500 error due to 
	 * 	  too many requests. Keep trying though, it has consistently worked after leaving it for a minute
	 *    and trying again!
	 * 3. Whilst the Google Reviews function takes directly from the currently uploaded database, due to my lack
	 * 	  of space for a file as large as the Yelp json files, only the items located in the JSON files uploaded
	 *    my personal github will work when interacting with those files. However, I am confident this solution
	 *    will work for the full Yelp database where it accessible. For example, using a URL such as 
	 *    http://localhost:8080/JavaAPI/rest/getReviews?businessName=MountainView%20Hospital&type=yelp
	 *    will return reviews for that business, as data related to that business is in the github files. 
	 * 4. Have fun, and please, pardon the mess!
	 * @param businessName
	 * @param type
	 * @return response
	 */
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String reviewRetriever(@QueryParam("businessName") String businessName, @QueryParam("type") String type) {
		String response = "<html>" + "<title>" + "Retrieved Reviews for "+businessName+ "</title>"
				+"<body>";
		if (type.equalsIgnoreCase("google")) {
			response+="<h1>Reviews for "+businessName+" from Google Reviews. I'm crossing my nonexistent fingers that they're positive ones...</h1><br>";
			String result1 = GoogleReviewGetter.getPlaceID(businessName);
			ArrayList<Review> googleReviews = GoogleReviewGetter.getReviewInfo(result1);
			Iterator<Review> i1 = googleReviews.iterator();
			while (i1.hasNext()) {
				Review googleReview = (Review) i1.next();
				response+="<h2>Review by "+googleReview.getReviewer()+"</h2>";
				response+="<h4>Score: "+googleReview.getRating()+" Stars</h4>";
				response+="<h3>"+googleReview.getReview()+"</h3>";
			}
		} else if (type.equalsIgnoreCase("yelp")) {
			response+="<h1>Reviews for "+businessName+" from Yelp Reviews. I'm crossing my nonexistent fingers that they're positive ones...</h1><br>";
			String result2 = YelpReviewGetter.getPlaceID(businessName);
			ArrayList<Review> yelpReviews = YelpReviewGetter.getReviewInfo(result2);
			Iterator<Review> i2 = yelpReviews.iterator();
			while (i2.hasNext()) {
				Review yelpReview = (Review) i2.next();
				response+="<h2>Review by "+yelpReview.getReviewer()+"</h2>";
				response+="<h4>Score: "+yelpReview.getRating()+" Stars</h4>";
				response+="<h3>"+yelpReview.getReview()+"</h3>";
			}
		} else {
			response+="<h1>Terribly sorry, friend, but that TYPE isn't going work for me. Please use either 'Google' or 'Yelp'.</h1>";
		}
		response+="</body>" + "</html>";
		return response;
	}

}
