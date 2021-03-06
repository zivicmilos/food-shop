package beans;

import enumerations.ArticleTypes;

public class Article {
	private String name;
	private double price;
	private ArticleTypes articleType;
	private String restaurantID;
	private double amount;
	private String description;
	private String image;
	private int deleted;
	
	public Article() {
		super();
	}

	public Article(String name, double price, ArticleTypes articleType, String restaurantID, double amount,
			String description, String image) {
		super();
		this.name = name;
		this.price = price;
		this.articleType = articleType;
		this.restaurantID = restaurantID;
		this.amount = amount;
		this.description = description;
		this.image = image;
		this.deleted = 0;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public ArticleTypes getArticleType() {
		return articleType;
	}

	public void setArticleType(ArticleTypes articleType) {
		this.articleType = articleType;
	}

	public String getRestaurant() {
		return restaurantID;
	}

	public void setRestaurant(String restaurantID) {
		this.restaurantID = restaurantID;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public int getDeleted() {
		return deleted;
	}

	public void setDeleted(int deleted) {
		this.deleted = deleted;
	}
}
