
public class UserDTO {

	private String productNum;
	private String productName;
	private int price;
	private String maker;
	
	public String getProductNum() {
		return productNum;
	}
	public void setProductNum(String probuctNum) {
		this.productNum = probuctNum;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getMaker() {
		return maker;
	}
	public void setMaker(String maker) {
		this.maker = maker;
	}


	UserDTO(){}
	
}
