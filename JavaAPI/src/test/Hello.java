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

@Path("/hello")
public class Hello {
	/**
	@GET
	@Produces(MediaType.TEXT_XML)
	public String sayHello() {
		String resource="<? xml version='1.0' ?>" +
		"<hello>Sup loser, we're comin at you live from XML!</hello>";
		
		return resource;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String sayHelloJSON() {
		String resource=null;
		return resource;
	}
	
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String sayHelloHTML(@QueryParam("name") String name, @QueryParam("Card_no") String Card_no, 
			@QueryParam("amount") int amount) {
		System.out.println("Name is "+name);
		System.out.println("Amount is "+amount);
		String response;
		
		if(amount>10000) {
			System.out.println("Amount is greater than ten thousand");
			response = "Sorry, pal. No credit card for you.";
		} else {
			System.out.println("Amount is less than ten thousand.");
			response = "You want a credit card? You got one, pal.";
		}
		
		return "<html>" + "<title>" +"Credit Card Authorisation " +name+ "</title>" + "<body><h1>" +
			response +"</h1></body>" + "</html>"; 
	}
	**/
	
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
