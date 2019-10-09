package test;

public class Review {

	private String reviewer;
	private String review;
	private String rating;
	
	public Review() {

	}
	
	public Review(String reviewer, String review, String rating) {
		super();
		this.reviewer = reviewer;
		this.review = review;
		this.rating = rating;
	}
	public String getReviewer() {
		return reviewer;
	}
	public void setReviewer(String reviewer) {
		this.reviewer = reviewer;
	}
	public String getReview() {
		return review;
	}
	public void setReview(String review) {
		this.review = review;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}
	
	
	
	

}
