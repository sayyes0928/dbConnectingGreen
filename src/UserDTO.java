
public class UserDTO {
	

	private String probuctNum;
	
	public String getProbuctNum() {
		return probuctNum;
	}
	public void setProbuctNum(String probuctNum) {
		this.probuctNum = probuctNum;
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
	private String productName;
	private int price;
	private String maker;

	UserDTO(){}
	public UserDTO(String probuctNum, String productName, int productPrice, String productMaker) {
		this.probuctNum = probuctNum;
		this.productName = productName;
		this.price = productPrice;
		this.maker = productMaker;
	}
}
